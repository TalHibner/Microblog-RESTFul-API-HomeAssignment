package microblog.controllers;

public class NotYourPostException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5604522855012106580L;

	public NotYourPostException(String message) {
        super(message);
    }
}
