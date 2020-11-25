package cardHelper.pages.collection;

import cardHelper.MainClass;
import cardHelper.toolBox.ClickableTri;

public class CLLeftButton extends ClickableTri {

	public CLLeftButton() {
		//Used to be #07D600
		super("Left", MainClass.DDGREEN);
	}

	@Override
	public int[] getXS() {
		int screenWidth = MainClass.screenWidth;
		return new int [] {screenWidth-120,screenWidth-120,screenWidth-200};
	}

	@Override
	public int[] getYS() {
		int screenHeight = MainClass.screenHeight;
		return new int [] {screenHeight/2+40,screenHeight/2-40,screenHeight/2};
	}

}
