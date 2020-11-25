package cardHelper.pages.mainPage;

import cardHelper.MainClass;
import cardHelper.toolBox.ClickableButton;

public class MainPageOpenPackButton extends ClickableButton {

	public MainPageOpenPackButton() {
		super("Open Pack",MainClass.BASICFONT,MainClass.DDGREEN);
	}

	@Override
	public int getX() {
		System.out.println(MainClass.screenWidth);
		return MainClass.screenWidth*2/3;
	}

	@Override
	public int getY() {
		return MainClass.screenHeight*1/3;
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
