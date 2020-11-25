package cardHelper.pages.mainPage;

import cardHelper.MainClass;
import cardHelper.toolBox.ClickableButton;

public class MPSealedButton extends ClickableButton {

	public MPSealedButton() {
		super("Sealed", MainClass.BASICFONT, MainClass.DDGREEN);
	}

	@Override
	public int getX() {
		return MainClass.screenWidth*2/3;
	}

	@Override
	public int getY() {
		return MainClass.screenHeight*1/3+200;
	}

	@Override
	public int getX1() {
		return 120;
	}

	@Override
	public int getY1() {
		return 60;
	}

}
