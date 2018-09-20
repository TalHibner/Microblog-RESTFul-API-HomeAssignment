package microblog.services;

public class AlreadyVotedException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5688615907586178959L;

	public AlreadyVotedException(String message) {
        super(message);
    }
}
