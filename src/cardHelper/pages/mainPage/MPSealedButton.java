package cardHelper.pages.mainPage;

import cardHelper.MainClass;
import cardHelper.toolBox.ClickableButton;

public class MPSealedButton extends ClickableButton {

	public MPSealedButton() {
		super("Sealed", MainClass.BASICFONT, MainClass.DDGREEN);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return MainClass.screenWidth*2/3;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return MainClass.screenHeight*1/3+200;
	}

	@Override
	public int getX1() {
		// TODO Auto-generated method stub
		return 100;
	}

	@Override
	public int getY1() {
		// TODO Auto-generated method stub
		return 50;
	}

}