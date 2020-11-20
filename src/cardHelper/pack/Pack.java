package cardHelper.pack;

import java.util.ArrayList;
import java.util.Collections;

import cardHelper.card.Card;

public class Pack /*extends PackCallings*/ {
	public String packSet;
	boolean isBlank;
	Card blankCard = new Card("","","",new String[0],new String[0],true);
	private ArrayList<Card> currentCards;
	public ArrayList<String> pickedNames=new ArrayList<String>();
	public int commoncardsonload=0;
	/**
	 * A boolean value determining whether or not the pack has been seen.
	 */
	private boolean beenSeen=false;
	//Card [] currentCards= {blankCard,blankCard,blankCard,blankCard,blankCard,blankCard,blankCard,blankCard,blankCard,blankCard,blankCard,blankCard,blankCard,blankCard};
	//public String getCard()
	/*public void reorderCards(){
		for (int i = 0; i <13;i++){
			if (currentCards[i].testBlank()){
				currentCards[i]=currentCards[i+1];
				currentCards[i+1]=blankCard;
			}
		}
	}*/
	/**
	 * Sets whether or not the pack has been seen.
	 * @param seen Variable above.
	 */
	public void setBeenSeen(boolean seen)
	{
		beenSeen = seen;
	}
	/**
	 * Returns whether or not this pack has been seen.
	 * @return Boolean value, explained above.
	 */
	public boolean getBeenSeen()
	{
		return beenSeen;
	}
	public int numOfCards() {return currentCards.size();}
	public ArrayList<Card> getCards() {return currentCards;};
	public boolean cardBlank(int num) {if (num>=currentCards.size()||getCard(num).isBlank){return true;}else return false;}
	public void setCard(Card theCard, int num){currentCards.set(num, theCard);}
	public void addCard(Card theCard) {currentCards.add(theCard);}
	public String getSet(){return packSet;}
	public boolean testBlank(){return isBlank;}
	public Card getCard(int num){return currentCards.get(num);}
	public void pickedCard(int num){if(beenSeen) {System.out.println(currentCards.get(num).cost+" "+currentCards.get(num).getName());pickedNames.add(currentCards.get(num).cost+" "+currentCards.get(num).getName());}currentCards.remove(num);}
	public boolean isEmpty(){
		return currentCards.size()==0;
	}
	public Pack(boolean isblank){
		isBlank = isblank;
		currentCards = new ArrayList<Card>(20);
		/*for (int i = 0;i < 20.;i++) {
			currentCards.set(i, blankCard);
		}*/
	}
	public void sortPackQt() {
			ArrayList<Card> qtvalue = new ArrayList<Card>();
			for (Card hoi:currentCards) {
				qtvalue.add(hoi);
				for (int i =qtvalue.size()-1;i>0;i--) {
					if (qtvalue.get(i).getqt()>qtvalue.get(i-1).getqt()) {
						Collections.swap(qtvalue, i, i-1);
					}
				}
			}
			currentCards=qtvalue;
	}
	public void sortPackId() {
		ArrayList<Card> id = new ArrayList<Card>();
		for (Card hoi:currentCards) {
			id.add(hoi);
			for (int i =id.size()-1;i>0;i--) {
				if (id.get(i).getId()>id.get(i-1).getId()) {
					Collections.swap(id, i, i-1);
				}
			}
		}
		currentCards=id;
}
	public boolean hasCard(String name) {
		for (Card hoi:currentCards) {
			if (hoi.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	public boolean hasAll(int check) {
		int[] doom = {0,0,0,0,0};
		int yep = 4;
		int omega = 0;
		int omegacheck=(int)Math.floor(check/2)+1;
		if (check<1) {
			yep=3;
		}
		for (Card hoi:currentCards) {
			if (hoi.monoWhiteable()) {
				doom[0]++;
				if (doom[0]==yep) {
					omega++;
				}
			}
			if (hoi.monoBlueable()) {
				doom[1]++;
				if (doom[1]==yep) {
					omega++;
				}
			}
			if (hoi.monoBlackable()) {
				doom[2]++;
				if (doom[2]==yep) {
					omega++;
				}
			}
			if (hoi.monoRedable()) {
				doom[3]++;
				if (doom[3]==yep) {
					omega++;
				}
			}
			if (hoi.monoGreenable()) {
				doom[4]++;
				if (doom[4]==yep) {
					omega++;
				}
			}
		}
		commoncardsonload=currentCards.size();
		return doom[0]>0&&doom[1]>0&&doom[2]>0&&doom[3]>0&&doom[4]>0&&doom[0]<4&&doom[1]<4&&doom[2]<4&&doom[3]<4&&doom[4]<4&&omega<omegacheck;
	}
	public int canMono() {
		int doom = 0;
		for (int i = 0; i < commoncardsonload;i++) {
			if (currentCards.get(i).canMono) {
				doom++;
			}
		}
		return doom;
	}
	public int noMono() {
		int doom = 0;
		for (int i = 0; i < commoncardsonload;i++) {
			if (!currentCards.get(i).canMono) {
				doom++;
			}
		}
		return doom;
	}
	public void setIds()
	{
		for (int i = 0; i < currentCards.size(); i++)
		{
			currentCards.get(i).setId(i);
		}
	}
	/*
	
	public Pack(String cube,int [] theNumbers){
		isBlank = false;
		packSet = "Cubes/"+cube+"Cube";
		String [] thisPack = generatePack(packSet,theNumbers);
			for (int i = 0;i<thisPack.length;i++){
					currentCards[i] = new Card(packSet,thisPack[i],false);
					if (currentCards[i].isError()){
						System.out.println("Error on " + thisPack[i]);
					}
				}
	}*/
}
