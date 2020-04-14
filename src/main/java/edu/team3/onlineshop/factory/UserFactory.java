package edu.team3.onlineshop.factory;


import edu.team3.onlineshop.domain.Role;
import edu.team3.onlineshop.domain.User;

/**
 * @author team 3
 */
public class UserFactory {

    protected UserFactory() {
    }

    private static UserFactory userFactory;

    public static UserFactory getInstance() {
        if (userFactory == null) {
            return userFactory = new UserFactory();
        }
        return userFactory;
    }


    public User createUser(User user, String type) {

        System.err.println("Type:  " + type);
        if (type.equalsIgnoreCase("BUYER")) {
            user.setRole(new Role(1));
            return user;
        }
        if (type.equalsIgnoreCase("MERCHANT")) {
            user.setRole(new Role(2));
            return user;
            //return user;
        }
        if (type.equalsIgnoreCase("ADMIN")) {
            user.setRole(new Role(3));
            return user;
        }
        return null;

    }
}

