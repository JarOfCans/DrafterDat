package cardHelper.pages;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import cardHelper.MainClass;
import cardHelper.MainClass.Pages;
import cardHelper.toolBox.Clickable;
import cardHelper.toolBox.HoverableCardName;

public abstract class Page {
	public static final int TICKS_TO_EXPAND = 10;
	ArrayList<Clickable> click = new ArrayList<Clickable>(25);
	protected MainClass main;
	//int screenWidth;
	//int screenHeight;
	private Pages pageEnum;
	protected double imgMulti=2;
	public Page(MainClass main, Pages inputEnum){
		this.main = main;
		pageEnum = inputEnum;
	}
	public abstract void draw(Graphics g);
	public void render(Graphics g) {
		for (Clickable go:click) {
			go.draw(g);
		}
	}
	public abstract void onClick(int x, int y,MainClass main);
	/**
	 * Method for subclasses to implement if they want to use the keyboard.
	 * @param c Character input into the keyboard.
	 */
	public void onType(char c)
	{
		//Availible for subclasses to implement.
	}
	public void hoverCheck(int x, int y) {
		try
		{
		for (Clickable hoi:click) {
			if (hoi.type.equals("card")) {
				if (hoi.Check(x, y)) {
					hoi.hoverhold++;
					if (hoi.hoverhold==TICKS_TO_EXPAND) {
						//System.out.println("hoi, hover checked");
						main.repaint();
					}
				}
				else {
					if (hoi.hoverhold>1) {
						if (hoi.hoverhold>=TICKS_TO_EXPAND) {
							main.repaint();
						}
					//System.out.println("Set to 0");
					hoi.hoverhold=0;
					}
				}
			}
			}
		}
		catch (ConcurrentModificationException cme)
		{
			System.out.println(cme.getMessage());
		}
	}
	public void hoverCard(int x, int y,Graphics cellar) {
		for (Clickable hoi:click) {
			if (hoi.type.equals("card")) {
				if (hoi.Check(x, y)) {
					//System.out.println("yep "+hoi.hoverhold);
					if (hoi.hoverhold>=TICKS_TO_EXPAND) {
						//System.out.println(main.screenHeight-(hoi.y1*imgMulti));
						int top = hoi.getY();
						int left = hoi.getX() + hoi.getX1();
						int height = (int) (hoi.getY1()*imgMulti);
						int width = (int) (hoi.getX1()*imgMulti);
						if (hoi instanceof HoverableCardName)
						{
							left = hoi.getX() - 5;
							height = (int) (250 * imgMulti);
							width = (int) (180 * imgMulti);
						}
						if (top + height > MainClass.screenHeight) {
							top = top - height + hoi.getY1();
						}
						if (left + width > MainClass.screenWidth) {
							left = left - width;
						}
						//cellar.drawImage(hoi.image,left,(int)(hoi.y-hoi.y1*(imgMulti-1)),(int)(hoi.x1*imgMulti),(int)(hoi.y1*imgMulti),main, x, y, observer)
						cellar.drawImage(hoi.image,left,top,width,height,main);
					}
				}
			}
		}
	}
	/**
	 * Method for a subclass to write if it has its own need to check if the mouse is being hovered.
	 * @param x X value of the mouse currently
	 * @param y Y value of the mouse currently
	 * @param cellar Paint object
	 */
	protected void pageHoverCheck(int x, int y,Graphics cellar)
	{
		//Intended to be blank, optional override
	}
	/**
	 * Retrieves this Page's identifier.
	 * @return This Page's Pages enum identifier.
	 */
	public Pages getPageEnum()
	{
		return pageEnum;
	}
	/**
	 * Determines if the input Pages enum references this page.
	 * @param input Pages enum to compare to this Page's enum.
	 * @return true if the Pages enums match, false otherwise.
	 */
	public boolean equals(Pages input)
	{
		return input == pageEnum;
	}
}
