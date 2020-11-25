package cardHelper.pages.packOpen;

import java.awt.Image;

import cardHelper.toolBox.ClickableCard;

public class PackCard extends ClickableCard {

	public PackCard(int cardnum, Image image) throws NullPointerException {
		super(cardnum, image);
	}

	@Override
	public int getX() {
		return 20+180*(card%5);
	}

	@Override
	public int getY() {
		return 20+250*(card/5);
	}

	@Override
	public int getX1() {
		return 180;
	}

	@Override
	public int getY1() {
		return 250;
	}

}
