package cardHelper.pages.pickSealed;

import cardHelper.MainClass;
import cardHelper.toolBox.ClickableButton;

public class ClearButton extends ClickableButton {

	public ClearButton() {
		super("Clear", MainClass.BASICFONT, MainClass.DDTEAL);
	}

	@Override
	public int getX() {
		return 630;
	}

	@Override
	public int getY() {
		return 700;
	}

	@Override
	public int getX1() {
		return 90;
	}

	@Override
	public int getY1() {
		return 50;
	}

}
