package cardHelper.pack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import cardHelper.MainClass;

public class PackSetup {
	private ArrayList <String> each = new ArrayList<String>();
	public PackSetup(String name) {
		if (name.equals("blank"))
		{
			;
		}
		else if (name.equals("All")) {
			for (int i = 0; i < 10; i++)
			each.add("C");
			for (int i = 0; i < 3; i++)
				each.add("U");
			each.add("RRRRRRRM");
			each.add("L");
		}
		else
		{
		try (BufferedReader br = new BufferedReader(new FileReader(MainClass.programDataFolder()+"Packs/"+name+"/Setup.txt"))){
			int cardsperpack=Integer.parseInt(br.readLine());
			for (int i = 0; i < cardsperpack;i++) {
				each.add(br.readLine());
			}
		}
		catch (IOException e) {System.out.println(e.getMessage());}
		}
	}
	public int [] getRarities() {
		int [] doom = {0,0,0,0,0,0};
		for (String hoi:each) {
			String next = randomise(hoi);
			if (next.equals("C")) {
				doom[0]++;
			}
			else if (next.equals("U")) {
				doom[1]++;
			}
			else if (next.equals("R")) {
				doom[2]++;
			}
			else if (next.equals("M")) {
				doom[3]++;
			}
			else if (next.equals("S")) {
				doom[4]++;
			}
			else if (next.equals("L")) {
				doom[5]++;
			}
		}
		return doom;
	}
	public String randomise(String ran) {
		int tran = (int)Math.floor(Math.random()*ran.length());
		return ran.substring(tran,tran+1);
	}
	/**
	 * Gets the rarities of a set, guaranteeing a specific slot (if possible).
	 * @param slot Slot to guarantee.
	 * @return A array of numbers with the slots in a pack.
	 */
	public int [] getGuaranteedRarities(String slot) {
		int [] doom = {0,0,0,0,0,0};
		boolean used = false;
		for (String hoi:each) {
			String next = hoi;
			if (!used && next.contains(slot)) {
				next = slot;
				used = true;
			}
			next = randomise(next);
			if (next.equals("C")) {
				doom[0]++;
			}
			else if (next.equals("U")) {
				doom[1]++;
			}
			else if (next.equals("R")) {
				doom[2]++;
			}
			else if (next.equals("M")) {
				doom[3]++;
			}
			else if (next.equals("S")) {
				doom[4]++;
			}
			else if (next.equals("L")) {
				doom[5]++;
			}
		}
		return doom;
	}
}
