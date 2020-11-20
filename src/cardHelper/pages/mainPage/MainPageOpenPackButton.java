package cardHelper.pages.mainPage;

import cardHelper.MainClass;
import cardHelper.toolBox.ClickableButton;

public class MainPageOpenPackButton extends ClickableButton {

	public MainPageOpenPackButton() {
		super("Open Pack",MainClass.BASICFONT,MainClass.DDGREEN);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getX() {
		return MainClass.screenWidth*2/3;
	}

	@Override
	public int getY() {
		return MainClass.screenHeight*1/3;
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
