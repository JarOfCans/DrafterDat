package cardHelper.pages.mainPage;

import cardHelper.MainClass;
import cardHelper.toolBox.ClickableButton;

public class MPCollectionButton extends ClickableButton {

	public MPCollectionButton() {
		super("Collection", MainClass.BASICFONT, MainClass.DDGREEN);
	}

	@Override
	public int getX() {
		return MainClass.screenWidth*2/3;
	}

	@Override
	public int getY() {
		return MainClass.screenHeight*1/3-100;
	}

	@Override
	public int getX1() {
		return 100;
	}

	@Override
	public int getY1() {
		return 50;
	}

}
