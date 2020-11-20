package cardHelper.toolBox;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import cardHelper.MainClass;
import cardHelper.card.Card;

public class HoverableCardName extends ClickableCard {
	private Card card;

	public HoverableCardName(Card cardIn, int x, int y, int x1, int y1) {
		super(-1, cardIn.getPic(false), x, y, x1, y1);
		visible = false;
		card = cardIn;
	}
	
	public void draw(Graphics g)
	{
		System.out.println(x + " " + y);
		System.out.println(card.getName());
		//g.drawImage(image, 0, 0, 300, 400, null);
		g.setColor(card.getColorBase());
		//g.drawRect(x, y, x1, y1);
		g.drawString(card.cost+" "+card.getName(), x, y + 20);
		//g.setColor(card.getColorBase());//TODO Colorcode based on cards color
		g.drawLine(x - 5, y + y1, x + x1, y + y1);//Y line
		g.drawLine(x - 5, y + y1 - 10, x - 5, y + y1);//X line
	}
}
