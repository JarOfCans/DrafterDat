package cardHelper.pages.draft;

import java.awt.Image;

import cardHelper.MainClass;
import cardHelper.toolBox.ClickableCard;

public class DFCard extends ClickableCard {
	public static final int MODIFY_SIZE = 1420;
	public static final int MODIFY_X_SIZE = 144;
	public static final int MODIFY_Y_SIZE = 200;

	public DFCard(int cardnum, Image image) throws NullPointerException {
		super(cardnum, image);
	}

	@Override
	public int getX() {
		return (MainClass.screenWidth > MODIFY_SIZE)?20+180*(card%5):MODIFY_X_SIZE*(card%4);
	}

	@Override
	public int getY() {
		return (MainClass.screenWidth > MODIFY_SIZE)?20+250*(card/5):MODIFY_Y_SIZE*(card/4);
	}

	@Override
	public int getX1() {
		return (MainClass.screenWidth > MODIFY_SIZE)?180:MODIFY_X_SIZE;
	}

	@Override
	public int getY1() {
		return (MainClass.screenWidth > MODIFY_SIZE)?250:MODIFY_Y_SIZE;
	}

}
