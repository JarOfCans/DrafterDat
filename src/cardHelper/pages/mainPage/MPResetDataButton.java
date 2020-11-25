package cardHelper.pages.mainPage;

import cardHelper.MainClass;
import cardHelper.toolBox.ClickableButton;

public class MPResetDataButton extends ClickableButton {

	public MPResetDataButton() {
		super("Reset Data", MainClass.BASICFONT, MainClass.DDGREEN);
	}

	@Override
	public int getX() {
		return MainClass.screenWidth-245;
	}

	@Override
	public int getY() {
		return MainClass.screenHeight-50;
	}

	@Override
	public int getX1() {
		return 120;
	}

	@Override
	public int getY1() {
		return 50;
	}

}
