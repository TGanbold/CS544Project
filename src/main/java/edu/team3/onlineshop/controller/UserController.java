package edu.team3.onlineshop.controller;

import com.fasterxml.jackson.annotation.JsonView;

import edu.team3.onlineshop.View;
import edu.team3.onlineshop.domain.Merchant;
import edu.team3.onlineshop.domain.User;
import edu.team3.onlineshop.exceptions.AdminsCannotDeleteThemselvesException;
import edu.team3.onlineshop.exceptions.ItemNotFoundException;
import edu.team3.onlineshop.factory.UserFactory;
import edu.team3.onlineshop.security.JwtUtil;
import edu.team3.onlineshop.service.FileStorageService;
import edu.team3.onlineshop.service.UserService;
import edu.team3.onlineshop.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author team 3
 *
 */

@RestController
@CrossOrigin(allowedHeaders = "*")
@RequestMapping("/api/v1/users")
public class UserController {

    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserServiceImpl userDetailService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Secured({"ROLE_BUYER", "ROLE_MERCHANT", "ROLE_ADMIN"})
    @PostMapping("/changePassword")
    public String changePassword(@RequestBody Map<String, String> payload) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        this.userService.setUserPassword(currentUsername, payload.get("password"));
        return payload.get("password");
    }

    @Secured({"IS_AUTHENTICATED_ANONYMOUSLY"})
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> payload) throws Exception {
        String username = payload.get("username");
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, payload.get("password")));
        } catch (AuthenticationException e) {
            throw new Exception("Invalid username/password");
        }

        final UserServiceImpl userDetail = (UserServiceImpl) userDetailService.loadUserByUsername(username);
        final String jwtToken = jwtUtil.generateToken((UserDetails) userDetail);
        Map<String, Object> response = new HashMap<>();
        response.put("token", jwtToken);
        response.put("uid", userDetail.getUser().getId());
        response.put("type", "Bearer");
        response.put("role", userDetail.getUser().getRole().getType());
        response.put("name", userDetail.getUser().getFirstName());
        response.put("account", userDetail.getUser().getUsername());
        return ResponseEntity.ok(response);
    }

    @JsonView(View.Summary.class)
    @Secured({"IS_AUTHENTICATED_ANONYMOUSLY"})
    @PostMapping("/register")
    public User createUser(@RequestBody @Valid User user) {
        UserFactory userFactory = UserFactory.getInstance();
        return userService.saveUser(userFactory.createUser(user, "buyer"));
    }

    @JsonView(View.Summary.class)
    @Secured({"IS_AUTHENTICATED_ANONYMOUSLY"})
    @PostMapping("/merchants/register")
    public User createMerchant(@RequestBody @Valid Merchant user) {
        UserFactory userFactory = UserFactory.getInstance();
        return userService.saveUser(userFactory.createUser(user, "merchant"));
    }

    @Secured(value = {"ROLE_ADMIN", "ROLE_MERCHANT", "ROLE_BUYER"})
    @PutMapping("/update")
    public User updateUser(@RequestBody @Valid User user) {
        return userService.saveUser(user);
    }

    @JsonView(View.Summary.class)
    @Secured(value = {"ROLE_ADMIN"})
    @GetMapping("/buyers")
    public Page<User> allBuyers(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return userService.listBuyers(page, size);
    }

    @JsonView(View.Summary.class)
    @Secured(value = {"ROLE_ADMIN"})
    @GetMapping("/merchants")
    public Page<Merchant> allMerchants(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return userService.listMerchant(page, size);
    }

    @Secured(value = {"ROLE_ADMIN"})
    @GetMapping(value = {"/merchantsByStatus"})
    public Page<Merchant> getAllMerchantByApproveAndPageAndSize(
            @RequestParam(name = "status", defaultValue = "0") int status,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {

        return userService.getListByApproveStatus(status, page, size);
    }

    @Secured(value = {"ROLE_ADMIN"})
    @PutMapping("/merchantApproval/{merchantId}/{approvalStatus}")
    public boolean merchantApproval(@PathVariable("merchantId") long merchantId, @PathVariable("approvalStatus") int status) {
        Merchant m = userService.findMerchantById(merchantId).get();
        if (m != null) {
            if (status != 1 && status != 0)
                throw new ItemNotFoundException("Invalid status sent", status);
            if (m.isApproved() && status == 1) {
                throw new ItemNotFoundException("Merchant Already Approved", merchantId);
            }
            m.setApproved(status == 1 ? true : false);
            userService.saveUser(m);
            return true;
        } else {
            throw new ItemNotFoundException("Invalid MerchantId", merchantId);
        }
    }


    @Secured(value = {"ROLE_MERCHANT", "ROLE_ADMIN"})
    @PostMapping("/{merchantId}/uploadPhoto")
    public String uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("merchantId") long merchantId) {
        Merchant m = userService.findMerchantById(merchantId).get();
        if (m == null) {
            throw new ItemNotFoundException("Invalid merchant", merchantId);
        }

        String fileName = fileStorageService.storeFile(file, merchantId, false);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/merchantimages/")
                .path(fileName)
                .toUriString();
        if (fileDownloadUri == null) {
            throw new ItemNotFoundException("Upload Failed", merchantId);
        }

        m.setIdentityProof(fileDownloadUri);
        userService.saveUser(m);


        return fileDownloadUri;
    }


    @JsonView(View.Summary.class)
    @Secured(value = {"ROLE_ADMIN", "ROLE_MERCHANT", "ROLE_BUYER"})
    @GetMapping("/{username}")
    public User one(@PathVariable String username) {
        return userService.findUserByUsername(username)
                .orElseThrow(() -> new ItemNotFoundException(username, User.class));
    }

    @JsonView(View.Summary.class)
    @Secured(value = {"ROLE_ADMIN", "ROLE_MERCHANT", "ROLE_BUYER"})
    @GetMapping("/admin/{userId}")
    public User one(@PathVariable long userId) {
        return userService.findUserById(userId);

    }

    @Secured(value = {"ROLE_ADMIN"})
    @DeleteMapping("/{username}")
    public User deleteUser(@PathVariable String username) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if (username.equals(currentUsername))
            throw new AdminsCannotDeleteThemselvesException(username);
        User user = userService.findUserByUsername(username).map(u -> {
            userService.deleteUser(u);
            return u;
        }).orElse(null);

        return user;
    }

}
