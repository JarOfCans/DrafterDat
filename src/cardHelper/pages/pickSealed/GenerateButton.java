package cardHelper.pages.pickSealed;

import cardHelper.MainClass;
import cardHelper.toolBox.ClickableButton;

public class GenerateButton extends ClickableButton {

	public GenerateButton() {
		super("Generate", MainClass.BASICFONT, MainClass.DDTEAL);
	}

	@Override
	public int getX() {
		return 830;
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
