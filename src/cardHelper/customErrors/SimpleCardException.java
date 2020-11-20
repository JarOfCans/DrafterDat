package cardHelper.customErrors;

public class SimpleCardException extends RuntimeException {
	public SimpleCardException()
	{
		super("Card is too simple for the current time");
	}
	public SimpleCardException(String message)
	{
		super(message);
	}
}
