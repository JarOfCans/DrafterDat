package cardHelper.table;

import java.util.ArrayList;

import cardHelper.MainClass;
import cardHelper.card.Card;
import cardHelper.pack.Pack;
import cardHelper.player.Player;
import cardHelper.set.Set;


public class ClassicDraftTable {
	public ArrayList<Player> players = new ArrayList<Player>(8);
	public ArrayList<Pack> tablepacks = new ArrayList<Pack>(24);
	public ArrayList<Pack> heldpacks = new ArrayList<Pack>(players.size());
	public ArrayList<Pack> trashbin = new ArrayList<Pack>(24);
	public Pack blankpack=new Pack(true);
	public int wait = -1, waitpack=0;
	public boolean waitplayer= false;
	public MainClass main;
	private boolean newPack = true;
	public boolean tablekill=false;
	/*public ClassicDraftTable(ArrayList<Set> packlist, MainClass main, boolean[] isplayer) {
		this.main = main;
		for (Set all:packlist) {
			for (int i = 0; i < 8;i++) {
				tablepacks.add(all.buildpack());
			}
		}
		newPacks();
		for (int i = 0; i < 8; i++){
			players.add(new Player(i,this,isplayer[i]));
			for (int m = 0;m<packlist.size();m++) {
				players.get(i).packnames[m]=packlist.get(m).getName();
			}
		}
		pickcheck();
	}*/
	public ClassicDraftTable(ArrayList<Set> packlist, MainClass main, boolean[] isplayer, Card guaranteedCard) {
		this.main = main;
		//Creates the Packs to Draft with.
		for (Set all:packlist) {
			for (int i = 0; i < 8;i++) {
				if (guaranteedCard != null) {
					tablepacks.add(all.buildPack(guaranteedCard));
					guaranteedCard = null;
				}
				else {
					tablepacks.add(all.buildpack());
				}
			}
		}
		newPacks();
		//Gives the cards to the players.
		for (int i = 0; i < 8; i++){
			players.add(new Player(i,this,isplayer[i]));
			for (int m = 0;m<packlist.size();m++) {
				players.get(i).packnames[m]=packlist.get(m).getName();
			}
		}
		pickcheck();
	}
	public void pickcheck() {
		for (int breaksupport = 0; breaksupport <1;breaksupport++) {
		wait++;
		newPack=true;
		if (wait==8) {
			wait=0;
			ArrayList<Pack> hold = new ArrayList<Pack>();
			if (waitpack==1) {
				for (int i = 0;i < 8;i++) {
					hold.add(heldpacks.get((i+7)%8));
				}
			}
			else {
				for (int i = 0;i < 8;i++) {
					hold.add(heldpacks.get((i+1)%8));
				}
			}
			heldpacks=hold;
			if (heldpacks.get(0).isEmpty()) {
				if (!newPacks()) {
					for (Player player:players) {
						player.constructDeck(player.id);
					}
					main.resetall();
					tablekill=true;
					break;
				}
			}
		}
		if (!players.get(wait).isPlayer) {
			waitplayer=false;
			players.get(wait).pickCard(0);
		}
		else {
			main.repaint();
			waitplayer=true;
		}
	}
	}
	public Pack getMypack(int i) {
		if (!tablekill)
		return heldpacks.get(i);
		return blankpack;
	}
	public void RotateClockwise() {
		ArrayList<Pack> next = new ArrayList<Pack>(8);
		for (int i = 0; i < 8; i++) {
			next.set(i, heldpacks.get((i+7)%8));
		}
		heldpacks=next;
	}
	public void RotateCounterClockwise() {
		ArrayList<Pack> next = new ArrayList<Pack>(8);
		for (int i = 0; i < 8; i++) {
			next.set(i, heldpacks.get((i+1)%8));
		}
		heldpacks=next;
	}
	public Pack waitPack() {
			return getMypack(wait);
	}
	public boolean newPacks() {
		if (waitpack>0) {
			for (Pack held:heldpacks) {
				trashbin.add(held);
			}
			heldpacks.clear();
		}
		if (waitpack==3) {
			return false;
		}
		else {
			for (int i = 0;i < 8;i++) {
				heldpacks.add(tablepacks.get(0));
				tablepacks.remove(0);
			}
		}
		waitpack++;
		return true;
	}
	public boolean anyPlayers() {
		for (Player hoi:players) {
			if (hoi.isPlayer){
				return true;
			}
		}
		return false;
	}
	public String toString()
	{
		return "This classic draft table contains " + players.size() + "players.";
	}
	/**
	 * Returns if there is a new Pack available.
	 * @return true if there is a new Pack, false otherwise.
	 */
	public boolean getNewPack()
	{
		return newPack;
	}
	/**
	 * Sets whether or not a new Pack is available
	 * @param newPackValue Boolean value as to whether there is a new Pack availible.
	 */
	public void setNewPack(boolean newPackValue)
	{
		newPack = newPackValue;
	}
}