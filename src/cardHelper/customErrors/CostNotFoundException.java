package cardHelper.customErrors;

public class CostNotFoundException extends RuntimeException {
	public CostNotFoundException()
	{
		super("The cost of this card is not found.");
	}
	public CostNotFoundException(String message)
	{
		super(message);
	}
}
