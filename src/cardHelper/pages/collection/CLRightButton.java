package cardHelper.pages.collection;

import cardHelper.MainClass;
import cardHelper.toolBox.ClickableTri;

public class CLRightButton extends ClickableTri {

	public CLRightButton() {
		//Color used to be #07D600
		super("Right", MainClass.DDGREEN);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int[] getXS() {
		int screenWidth = MainClass.screenWidth;
		return new int [] {screenWidth-100,screenWidth-100,screenWidth-20};
	}

	@Override
	public int[] getYS() {
		int screenHeight = MainClass.screenHeight;
		return new int [] {screenHeight/2+40,screenHeight/2-40,screenHeight/2};
	}

}
