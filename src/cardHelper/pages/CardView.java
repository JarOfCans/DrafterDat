package cardHelper.pages;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import cardHelper.MainClass;
import cardHelper.card.Card;
import cardHelper.toolBox.Clickable;
import cardHelper.pages.cardView.*;

public class CardView extends TemporaryPage {
	public Card card;
	public CardView(MainClass main,Card card,Page previous) {
		super(main, previous);
		click.add(new CVBackButton());
		//click.add(new ClickableButton("Reset Winrate", 750, 60, 200, 50, MainClass.BASICFONT, Color.decode("#00F0EC")));
		String [] holder = new String[] {"Q1+","Q1-","Q2+","Q2-","Q3+","Q3-","Q4+","Q4-","W+","W-","U+","U-","B+","B-","R+","R-","G+","G-","MVC","LVC"};
		for (int i = 0; i < holder.length;i++) {
			click.add(new CVModifyButton(holder[i], i));
		}
		if (previous instanceof Collection) {
			click.add(new CVDraftWithCardButton());
		}
		this.card=card;
	}

	@Override
	public void draw(Graphics cellar) {
		cellar.drawImage(card.getPic(false), 200, 130, main.imageWidth*2, main.imageHeight*2, main);
		cellar.setColor(Color.BLACK);
		cellar.drawString(card.getName(), 200, 100);
		cellar.drawString("Winrate = " + card.winrateString() + "   ("+ card.wins + " - " + card.loses + ")", 500, 100);
		cellar.drawString("Qt: "+String.valueOf(card.getqt()), 1135, 190);
		cellar.drawString(String.valueOf(card.getq1()), 1150, 230);
		cellar.drawString(String.valueOf(card.getq2()), 1150, 300);
		cellar.drawString(String.valueOf(card.getq3()), 1150, 370);
		cellar.drawString(String.valueOf(card.getq4()), 1150, 440);
		cellar.drawString("Development", 900, 230);
		cellar.drawString("Parity", 900, 300);
		cellar.drawString("Win More", 900, 370);
		cellar.drawString("Lose Less", 900, 440);
		cellar.drawString(String.valueOf(card.colorboost[0]), 1150, 520);
		cellar.drawString(String.valueOf(card.colorboost[1]), 1150, 590);
		cellar.drawString(String.valueOf(card.colorboost[2]), 1150, 660);
		cellar.drawString(String.valueOf(card.colorboost[3]), 1150, 730);
		cellar.drawString(String.valueOf(card.colorboost[4]), 1150, 800);
		cellar.drawString("Archetypes:", 1400, 550);
		ArrayList<String> archetypes = card.getArchetypes();
		int counter = 570;
		for (String hoi: archetypes)
		{
			cellar.drawString(hoi, 1400, counter);
			counter += 20;
		}
	}

	@Override
	public void onClick(int x, int y, MainClass main) {
		String action="none";
		for (Clickable ping:click) {
			if (ping.Check(x, y)) {
				action= ping.word;
			}
		}
		switch(action) {
		case "Back":
			revert();
			break;
		case "Q1+":
			main.editValue(card,"Q1",card.getq1()+1);
			break;
		case "Q1-":
			main.editValue(card,"Q1",card.getq1()-1);
				break;
		case "Q2+":
			main.editValue(card,"Q2",card.getq2()+1);
				break;
		case "Q2-":
			main.editValue(card,"Q2",card.getq2()-1);
				break;
		case "Q3+":
			main.editValue(card,"Q3",card.getq3()+1);
				break;
		case "Q3-":
			main.editValue(card,"Q3",card.getq3()-1);
				break;
		case "Q4+":
			main.editValue(card,"Q4",card.getq4()+1);
				break;
		case "Q4-":
			main.editValue(card,"Q4",card.getq4()-1);
				break;
		case "W+":
			main.editValue(card,"W",card.colorboost[0]+1);
				break;
		case "W-":
			main.editValue(card,"W",card.colorboost[0]-1);
				break;
		case "U+":
			main.editValue(card,"U",card.colorboost[1]+1);
				break;
		case "U-":
			main.editValue(card,"U",card.colorboost[1]-1);
				break;
		case "B+":
			main.editValue(card,"B",card.colorboost[2]+1);
				break;
		case "B-":
			main.editValue(card,"B",card.colorboost[2]-1);
				break;
		case "R+":
			main.editValue(card,"R",card.colorboost[3]+1);
				break;
		case "R-":
			main.editValue(card,"R",card.colorboost[3]-1);
				break;
		case "G+":
			 main.editValue(card,"G",card.colorboost[4]+1);
				break;
		case "G-":
			main.editValue(card,"G",card.colorboost[4]-1);
				break;
		case "MVC":
			card.wins += 2;
			card.Save(false);
			main.repaint();
			break;
		case "LVC":
			card.loses += 2;
			card.Save(false);
			main.repaint();
			break;
		case "Reset Winrate":
			card.wins = 0;
			card.loses = 0;
			card.Save(false);
			main.repaint();
			break;
		case "Draft with Card":
			main.draftWithCard(((Collection)previousPage).viewSet, card);
			break;
		}
	}

}
