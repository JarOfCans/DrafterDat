package cardHelper.pages;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import cardHelper.MainClass;
import cardHelper.MainClass.Pages;
import cardHelper.card.Card;
import cardHelper.set.Set;
import cardHelper.table.ClassicDraftTable;
import cardHelper.toolBox.Clickable;
import cardHelper.pages.pickDraft.*;

public class PickDraft extends Page {
	public static final int BUTTON_ROW_FROM_BOTTOM = 200;
	public boolean[] isPlayering = new boolean[]{true,false,false,false,false,false,false,false};
	public PickDraft(MainClass main) {
		super(main, Pages.pickDraft);
		for (int i = 0; i < main.setList.size();i++) {
			click.add(new SetButton(main.setList.get(i).getName(), i));
		}
		for (int i = 0; i < 8; i++) {
			click.add(new PDPlayerButton(i));
		}
		click.add(new PDClearButton());
		click.add(new PDStartButton());
		click.add(new PDRecordWinButton());
		click.add(new PDRecordLossButton());
		click.add(new PDResetWinsButton());
	}

	@Override
	public void draw(Graphics cellar) {
		cellar.setColor(Color.decode("#000000"));
		cellar.drawString("Pick a Set", MainClass.screenWidth*4/9, 50);
			for (int i = 0;i < main.packschoosen.size();i++){
			cellar.drawString("Pack "+i +": "+main.packschoosen.get(i).getName(), i*200+50,MainClass.screenHeight-BUTTON_ROW_FROM_BOTTOM-25);
			}
			for (int i = 0; i < 8; i++) {
				cellar.drawString(playerno(isPlayering[i]), 100*i+30, MainClass.screenHeight - 60);
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
		if (!action.equals("none")) {
			if (action.length()==8) {
			if (action.substring(0, 6).equals("Player")) {
				isPlayering[Integer.parseInt(action.substring(7, 8))]=!isPlayering[Integer.parseInt(action.substring(7, 8))];
			}
			}
			switch (action) {
			case "Start":
				if (main.packschoosen.size()==3) {
					ClassicDraftTable tableR = main.createTable(isPlayering);
					System.out.println(tableR);
					main.setDraftTable(tableR);
					if (tableR.anyPlayers()) {
						main.setPage(Pages.draft);
					}
				}
				break;
			case "Clear":
				main.packschoosen.clear();
				break;
			case "Record Win":
				JFileChooser chooser = new JFileChooser();
		        FileNameExtensionFilter filter = new FileNameExtensionFilter(
		                "Cockatrice Deck Files -- Winning Deck", "cod");
		        chooser.setCurrentDirectory(new File(main.programDataFolder()));
		        chooser.setFileFilter(filter);
		        int returnVal = chooser.showOpenDialog(null);
		        if(returnVal == JFileChooser.APPROVE_OPTION) {
			        recordMatch(chooser.getSelectedFile(), true);
		        }
				break;
			case "Record Loss":
				chooser = new JFileChooser();
		        filter = new FileNameExtensionFilter(
		                "Cockatrice Deck Files -- Loosing Deck", "cod");
		        chooser.setCurrentDirectory(new File(main.programDataFolder()));
		        chooser.setFileFilter(filter);
		        returnVal = chooser.showOpenDialog(null);
		        if(returnVal == JFileChooser.APPROVE_OPTION) {
			        recordMatch(chooser.getSelectedFile(), false);
		        }
				break;
			case "Reset Wins":
				for (Set set: main.setList)
				{
					for (Card hoi: set.sortedCards("rarity"))
					{
						hoi.wins = 0;
						hoi.loses = 0;
						hoi.Save(false);
					}
				}
				break;
			default:
				int check = main.getidSet(action);
				if (check>=0&&main.packschoosen.size()<3) {
					main.packschoosen.add(main.setList.get(check));
				}
			}
		}
		main.repaint();
	}
	public String playerno(boolean check) {
		if (check==true) {
			return "Player";
		}
		return "CPU";
	}
	
	private void recordMatch(File file, boolean win)
	{
		try {
			File fXmlFile = file ;
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("card");
			System.out.println("----------------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				System.out.println(String.format("Recorded match for %s, value " + win,getAttribute(nNode, "name")));
				main.changeNamedWinrate(getAttribute(nNode, "name"), Integer.parseInt(getAttribute(nNode, "number")),
						win, getAttribute(nNode.getParentNode(), "name").equals("main"));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static String getAttribute(Node nodeIn, String attribute)
	{
		return nodeIn.getAttributes().getNamedItem(attribute).getNodeValue();
	}
}
