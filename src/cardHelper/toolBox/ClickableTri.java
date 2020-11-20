package cardHelper.toolBox;

import java.awt.Color;
import java.awt.Graphics;

public class ClickableTri extends Clickable {
	public int [] xs, ys;
	public Color color;
	public ClickableTri(String function, int [] xs, int [] ys, Color color) {
		this.xs = xs;
		this.ys=ys;
		word=function;
		this.color=color;
		type="tri";
	}
	public void draw(Graphics cellar) {
		cellar.setColor(color);
		cellar.fillPolygon(xs,ys,3);
	}
	public boolean Check(int x,int y) {
		if ((x>=xs[0]||x>=xs[1]||x>=xs[2])&&(x<=xs[0]||x<=xs[1]||x<=xs[2])&&(y>=ys[0]||y>=ys[1]||y>=ys[2])&&(y<=ys[0]||y<=ys[1]||y<=ys[2])) {
			return true;
		}
		return false;
	}
	protected void testintarray(int[] hoi) {
		for (int hoi1:hoi) {
			System.out.println(hoi1);
		}
	}
}
