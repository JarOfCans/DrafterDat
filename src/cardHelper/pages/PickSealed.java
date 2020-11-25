package cardHelper.pages;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import cardHelper.MainClass;
import cardHelper.MainClass.Pages;
import cardHelper.card.Card;
import cardHelper.pack.Pack;
import cardHelper.pages.pickDraft.SetButton;
import cardHelper.toolBox.Clickable;
import cardHelper.pages.pickSealed.*;

public class PickSealed extends Page {

	public PickSealed(MainClass main) {
		super(main, Pages.pickSealed);
		for (int i = 0; i < main.setList.size();i++) {
			click.add(new SetButton(main.setList.get(i).getName(), i));
		//cellar.fillRect(150,50,90,50);
		}
		click.add(new ClearButton());
		click.add(new GenerateButton());
	}

	@Override
	public void draw(Graphics cellar) {
		cellar.setColor(Color.decode("#000000"));
		//Draws the selected sets 
		cellar.drawString("Pick a Set", MainClass.screenWidth*4/9, 50);
		
		for (int i = 0;i < main.packschoosen.size();i++){
			cellar.drawString(main.packschoosen.get(i).getName(), MainClass.screenWidth*i/7+100,MainClass.screenHeight-100);
		}
		
		cellar.drawString("Current Player Generated: " + main.getSealedGenerated(), 100, 48);
	}

	@Override
	public void onClick(int x, int y, MainClass main) {
		String action="none";
		for (Clickable ping:click) {
			if (ping.Check(x, y)) {
				action=ping.word;
			}
		}
		System.out.println(action);
		if (!action.equals("none")) {
			switch (action) {
			case "Generate":
				if (main.packschoosen.size()==6) {
				writesealeddecklist(generateSealed(main.packschoosen.get(0).buildpack(),main.packschoosen.get(1).buildpack(),main.packschoosen.get(2).buildpack()
						,main.packschoosen.get(3).buildpack(),main.packschoosen.get(4).buildpack(),main.packschoosen.get(5).buildpack()),main.getSealedGenerated());
				main.incrementSealedGenerated();
				}
				break;
			case "Clear":
				main.packschoosen.clear();
				break;
			default:
				int check = main.getidSet(action);
				if (check>=0&&main.packschoosen.size()<6) {
					main.packschoosen.add(main.setList.get(check));
				}
			}
		}
		main.repaint();
	}
	public ArrayList<String> generateSealed(Pack set1,Pack set2, Pack set3,Pack set4,Pack set5,Pack set6){
		ArrayList<String> doom = new ArrayList<String>(6*20);
			for (Card pull:set1.getCards()){
				doom.add(pull.getName());
			}
			for (Card pull:set2.getCards()){
				doom.add(pull.getName());
			}
			for (Card pull:set3.getCards()){
				doom.add(pull.getName());
			}
			for (Card pull:set4.getCards()){
				doom.add(pull.getName());
			}
			for (Card pull:set5.getCards()){
				doom.add(pull.getName());
			}
			for (Card pull:set6.getCards()){
				doom.add(pull.getName());
			}
		return doom;
	}
	public void writesealeddecklist(ArrayList<String> arrayList, int player){
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(MainClass.programDataFolder()+"player"+player+"sealedv.cod"))){
			bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			bw.write(System.lineSeparator());
			bw.write("<cockatrice_deck version=\"1\">");
			bw.write(System.lineSeparator());
			bw.write("<deckname>PlayerSealed"+player+"</deckname>");
			bw.write(System.lineSeparator());
			bw.write("<comments></comments>");
			bw.write(System.lineSeparator());
			bw.write("<zone name=\"side\">");
			bw.write(System.lineSeparator());
			for (String run:arrayList){
				bw.write("<card number=\"1\" name=\""+run+"\"/>");
				bw.write(System.lineSeparator());
			}
			bw.write("</zone>");
			bw.write(System.lineSeparator());
			bw.write("</cockatrice_deck>");
			bw.write(System.lineSeparator());
			System.out.println("Completed deck "+player);
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}
	}
}
