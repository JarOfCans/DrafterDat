package cardHelper.toolBox;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

public class ClickableCard extends Clickable {
	/**
	 * Card id number.
	 */
	int card;
	
	/**
	 * Constructor for a clickable card object.
	 * @param cardnum Number id for the clickable card.
	 * @param image Image url for the clickable card. Throws a nullPointerException if null.
	 * @param x x coordinate for the left of the clickable card.
	 * @param y y coordinate for the top of the clickable card.
	 * @param x1 width of the clickable card.
	 * @param y1 height of the clickable card.
	 */
	public ClickableCard(int cardnum,Image image, int x, int y, int x1, int y1) throws NullPointerException {
		if (image == null)
		{
			throw new NullPointerException("Error: Passed null image in clickable card.");
		}
		this.x = x;
		this.x1 = x1;
		this.y = y;
		this.y1 = y1;
		this.image = image;
		this.card=cardnum;
		word=Integer.toString(cardnum);
		type="card";
	}
	public ClickableCard(int cardnum, int x, int y, int x1, int y1)
	{
		this.x = x;
		this.x1 = x1;
		this.y = y;
		this.y1 = y1;
		this.card=cardnum;
		word=Integer.toString(cardnum);
		type="card";
		visible = false;
	}
	public void draw(Graphics g) {
		if (visible) {
			g.drawImage(image,x,y,x1,y1,null);
		}
	}
	public Image makeImage(String hi){
		Image hello = (new ImageIcon(hi)).getImage();
		return hello;
	}
}
