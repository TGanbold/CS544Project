package edu.team3.onlineshop.exceptions;

/**
 * @author team 3
 *
 */
public class ItemNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ItemNotFoundException(String message, Long id) {
		super(message + " : " + id);
	}

	public ItemNotFoundException(String message, Throwable cause) {
		super(message + " " + cause.getMessage());
	}

	public ItemNotFoundException(String message, Object object) {
		super(message + " " + object.getClass());
	}
}
