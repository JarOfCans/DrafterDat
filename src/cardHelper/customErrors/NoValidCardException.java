package cardHelper.customErrors;

public class NoValidCardException extends RuntimeException {
	public NoValidCardException()
	{
		super("No card exists under these circumstances.");
	}
	public NoValidCardException(String message)
	{
		super(message);
	}
}
