package edu.team3.onlineshop.exceptions;

/**
 * @author team 3
 *
 */
public class AdminsCannotDeleteThemselvesException extends RuntimeException{
    public AdminsCannotDeleteThemselvesException(String username) { super(username + " you can't delete yourself."); }
}
