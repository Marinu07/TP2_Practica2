package Exceptions;

public class IncorrectArgumentException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public IncorrectArgumentException() { super(); }
	public IncorrectArgumentException(String message){ super(message); }
	public IncorrectArgumentException(String message, Throwable cause){
	 super(message, cause);
	}
	public IncorrectArgumentException(Throwable cause){ super(cause); }
}
