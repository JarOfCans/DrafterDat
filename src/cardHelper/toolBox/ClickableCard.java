package cardHelper.toolBox;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public abstract class ClickableCard extends Clickable {
	/**
	 * Card id number.
	 */
	protected int card;
	
	/**
	 * Constructor for a clickable card object.
	 * @param cardnum Number id for the clickable card.
	 * @param image Image url for the clickable card. Throws a nullPointerException if null.
	 * @param x x coordinate for the left of the clickable card.
	 * @param y y coordinate for the top of the clickable card.
	 * @param x1 width of the clickable card.
	 * @param y1 height of the clickable card.
	 */
	public ClickableCard(int cardnum,Image image) throws NullPointerException {
		if (image == null)
		{
			throw new NullPointerException("Error: Passed null image in clickable card.");
		}
		this.image = image;
		this.card=cardnum;
		word=Integer.toString(cardnum);
		type="card";
	}
	public ClickableCard(int cardnum)
	{
		this.card=cardnum;
		word=Integer.toString(cardnum);
		type="card";
		visible = false;
	}
	public void draw(Graphics g) {
		if (visible) {
			g.drawImage(image,getX(),getY(),getX1(),getY1(),null);
		}
	}
	public Image makeImage(String hi){
		Image hello = (new ImageIcon(hi)).getImage();
		return hello;
	}
}
