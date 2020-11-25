package cardHelper.pages.collection;

import java.awt.Image;

import cardHelper.pages.Collection;
import cardHelper.toolBox.ClickableCard;

public class CLCard extends ClickableCard {

	public CLCard(int cardnum, Image image) throws NullPointerException {
		super(cardnum, image);
	}

	@Override
	public int getX() {
		return 20+(Collection.getImageWidth()*(card%4));
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return 20+Collection.getImageHeight()*((card%8)/4);
	}

	@Override
	public int getX1() {
		return Collection.getImageWidth();
	}

	@Override
	public int getY1() {
		// TODO Auto-generated method stub
		return Collection.getImageHeight();
	}

}
