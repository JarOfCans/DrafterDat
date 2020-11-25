package cardHelper.toolBox;

import java.awt.Graphics;
import cardHelper.card.Card;

public abstract class HoverableCardName extends ClickableCard {
	private Card card;

	public HoverableCardName(Card cardIn) {
		super(-1, cardIn.getPic(false));
		visible = false;
		card = cardIn;
	}
	
	public void draw(Graphics g)
	{
		int x = getX(), y = getY(), x1 = getX1(), y1 = getY1();
		//System.out.println(x + " " + y);
		//System.out.println(card.getName());
		//g.drawImage(image, 0, 0, 300, 400, null);
		g.setColor(card.getColorBase());
		//g.drawRect(x, y, x1, y1);
		g.drawString(card.cost+" "+card.getName(), x, y + 20);
		//g.setColor(card.getColorBase());//TODO Colorcode based on cards color
		g.drawLine(x - 5, y + y1, x + x1, y + y1);//Y line
		g.drawLine(x - 5, y + y1 - 10, x - 5, y + y1);//X line
	}
}
