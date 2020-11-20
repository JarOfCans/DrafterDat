package cardHelper.customErrors;

public class SettingNotFoundException extends IllegalArgumentException {
	/**
	 * @deprecated Not in use, never in use.
	 */
	public SettingNotFoundException()
	{
		super("This setting is not found in the file.");
	}
	/**
	 * @deprecated Not in use, never in use.
	 */
	public SettingNotFoundException(String message)
	{
		super(message);
	}
}
