package cardHelper.pages.mainPage;

import cardHelper.MainClass;
import cardHelper.toolBox.ClickableButton;

public class MPForceErrorButton extends ClickableButton {

	public MPForceErrorButton() {
		super("Force Error", MainClass.BASICFONT, MainClass.DDGREEN);
	}

	@Override
	public int getX() {
		return MainClass.screenWidth-370;
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
