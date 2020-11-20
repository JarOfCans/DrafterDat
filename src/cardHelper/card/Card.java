package cardHelper.card;

import java.awt.Color;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import cardHelper.MainClass;
import cardHelper.Settings;
import cardHelper.customErrors.CostNotFoundException;
import cardHelper.customErrors.SimpleCardException;
import cardHelper.set.Set;
import cardHelper.toolBox.ColorBase;

public class Card {
	String Name, Type, name2;
	private int red=0, green=0, blue=0, black=0, white=0, CMC=0;
	private ColorBase colorBase;
	public boolean canMono = false;
	public String [] colors = {"White","Blue","Black","Red","Green"};
	public int [] colorboost = {0,0,0,0,0};
	public int [] count;
	public boolean nonBasic=true;
	public String keywords=" ";
	public String [] stack = new String[0];
	public int [] stackdata;
	public String [] group = new String[0];
	public int [] groupdata;
	public ImageIcon thisImage;
	public String cost;
	boolean hybrid=false;
	public int wins = 0;
	public int loses = 0;
	public String set;
	public int q1,q2,q3,q4;
	int qt;
	public boolean isBlank;
	/**
	 * Value for packs to store order.
	 */
	private int id;
	private int setid;
	public boolean testBlank(){return isBlank;}
	public int getRed(){return red+colorboost[3];}
	public int getGreen(){return green+colorboost[4];}
	public int getBlue(){return blue+colorboost[1];}
	public int getBlack(){return black+colorboost[2];}
	public int getWhite(){return white+colorboost[0];}
	private ArrayList<String> archetypes = new ArrayList<String>();
	public boolean isTested()
	{
		return wins+loses>=24;
	}
	public String winrateString()
	{
		if (this.isTested())
		{
			return String.format("%d%s", this.winrate(), "%");
		}
		return "Untested";
	}
	public ArrayList<String> getArchetypes(){
		return archetypes;
	}
	/**
	 * Determines if the archetype exists for this card.
	 * @param archetype Archetype string that may exist in this card.
	 * @return false if it doesn't exist or if archetype is null, true otherwise.
	 */
	public boolean hasArchetype(String archetype)
	{
		return (archetype == null) ? false : archetypes.contains(archetype.toLowerCase());
	}
	/**
	 * Sets the id of the card.
	 * @param newId New id.
	 */
	public void setId(int newId)
	{
		id = newId;
	}
	/**
	 * Gets the id value of the card.
	 * @return id of the Card.
	 */
	public int getId()
	{
		return id;
	}
	/**
	 * Sets the setid of the card.
	 * @param newId New setid.
	 */
	public void setsetId(int newId)
	{
		setid = newId;
	}
	/**
	 * Gets the setid value of the card.
	 * @return setid of the Card.
	 */
	public int getsetId()
	{
		return setid;
	}
	public boolean monoWhiteable(){
		boolean doom = false;
		for (int i = 0; i < cost.length();i++) {
			String yep=cost.substring(i, i+1);
			if (yep.equals("W")) {
				doom=true;
			}
			if (yep.equals("U")) {
				if (i>2) {
					if (cost.substring(i-2, i).equals("W/")) {
						continue;
					}
				}
				return false;
			}
			if (yep.equals("B")) {
				if (i>2) {
					if (cost.substring(i-2, i).equals("W/")) {
						continue;
					}
				}
				return false;
			}
			if (yep.equals("R")) {
				if (i<cost.length()-2) {
					if (cost.substring(i+1, i+3).equals("/W")) {
						continue;
					}
				}
				return false;
			}
			if (yep.equals("G")) {
				if (i<cost.length()-2) {
					if (cost.substring(i+1, i+3).equals("/W")) {
						continue;
					}
				}
			}
		}
		return doom;
	}
	public boolean monoBlueable(){
		boolean doom = false;
		for (int i = 0; i < cost.length();i++) {
			String yep=cost.substring(i, i+1);
			if (yep.equals("U")) {
				doom=true;
			}
			if (yep.equals("B")) {
				if (i>2) {
					if (cost.substring(i-2, i).equals("U/")) {
						continue;
					}
				}
				return false;
			}
			if (yep.equals("R")) {
				if (i>2) {
					if (cost.substring(i-2, i).equals("U/")) {
						continue;
					}
				}
				return false;
			}
			if (yep.equals("G")) {
				if (i<cost.length()-2) {
					if (cost.substring(i+1, i+3).equals("/U")) {
						continue;
					}
				}
				return false;
			}
			if (yep.equals("W")) {
				if (i<cost.length()-2) {
					if (cost.substring(i+1, i+3).equals("/U")) {
						continue;
					}
				}
			}
		}
		return doom;}
	public boolean monoBlackable(){
		boolean doom = false;
		for (int i = 0; i < cost.length();i++) {
			String yep=cost.substring(i, i+1);
			if (yep.equals("B")) {
				doom=true;
			}
			if (yep.equals("R")) {
				if (i>2) {
					if (cost.substring(i-2, i).equals("B/")) {
						continue;
					}
				}
				return false;
			}
			if (yep.equals("G")) {
				if (i>2) {
					if (cost.substring(i-2, i).equals("B/")) {
						continue;
					}
				}
				return false;
			}
			if (yep.equals("W")) {
				if (i<cost.length()-2) {
					if (cost.substring(i+1, i+3).equals("/B")) {
						continue;
					}
				}
				return false;
			}
			if (yep.equals("U")) {
				if (i<cost.length()-2) {
					if (cost.substring(i+1, i+3).equals("/B")) {
						continue;
					}
				}
			}
		}
		return doom;}
	public boolean monoRedable(){
		boolean doom = false;
		for (int i = 0; i < cost.length();i++) {
			String yep=cost.substring(i, i+1);
			if (yep.equals("R")) {
				doom=true;
			}
			if (yep.equals("G")) {
				if (i>2) {
					if (cost.substring(i-2, i).equals("R/")) {
						continue;
					}
				}
				return false;
			}
			if (yep.equals("W")) {
				if (i>2) {
					if (cost.substring(i-2, i).equals("R/")) {
						continue;
					}
				}
				return false;
			}
			if (yep.equals("U")) {
				if (i<cost.length()-2) {
					if (cost.substring(i+1, i+3).equals("/R")) {
						continue;
					}
				}
				return false;
			}
			if (yep.equals("B")) {
				if (i<cost.length()-2) {
					if (cost.substring(i+1, i+3).equals("/R")) {
						continue;
					}
				}
			}
		}
		return doom;}
	public boolean monoGreenable(){
		boolean doom = false;
		for (int i = 0; i < cost.length();i++) {
			String yep=cost.substring(i, i+1);
			if (yep.equals("G")) {
				doom=true;
			}
			if (yep.equals("W")) {
				if (i>2) {
					if (cost.substring(i-2, i).equals("G/")) {
						continue;
					}
				}
				return false;
			}
			if (yep.equals("U")) {
				if (i>2) {
					if (cost.substring(i-2, i).equals("G/")) {
						continue;
					}
				}
				return false;
			}
			if (yep.equals("B")) {
				if (i<cost.length()-2) {
					if (cost.substring(i+1, i+3).equals("/G")) {
						continue;
					}
				}
				return false;
			}
			if (yep.equals("R")) {
				if (i<cost.length()-2) {
					if (cost.substring(i+1, i+3).equals("/G")) {
						continue;
					}
				}
			}
		}
		return doom;}
	public int getCMC(){return CMC;}
	public String getName(){return Name;}
	public String getType(){return Type;}
	public boolean testType(String cardType){return testString(Type,cardType);}
	public boolean nonBasic(){return nonBasic;}
	public boolean isError(){
		if (Name==null){
			return true;
		}
		return (getName().equals(""));
	}
	public boolean typeCreature(){return testType("Creature");}
	public int getq1(){return q1;}
	public int getq2(){return q2;}
	public int getq3(){return q3;}
	public int getq4(){return q4;}
	public int getqt(){return q1+q2+q3+q4;}
	public boolean isHybrid() {return hybrid;}
	public String getColors(){
		String doom = "C";
		if (white>0){doom = doom +"W";}
		if (blue>0){doom = doom + "U";}
		if (black>0){doom = doom + "B";}
		if (red>0){doom = doom + "R";}
		if (green>0){doom = doom + "G";}
		if (doom.length()>1){doom = doom.substring(1, doom.length());}
		return doom;
	}
	/**
	 * 
	 * @param name
	 * @param cost
	 * @param type
	 * @param stacks
	 * @param groups
	 * @param isblank
	 * @deprecated
	 */
	public Card(String name,String cost,String type,String [] stacks,String [] groups, boolean isblank){
		isBlank = true;
		Name = name;
	}
	/**
	 * Constructor for a nonBlank card.
	 * @param settings String of proper settings.
	 * @param setin Set of this card.
	 */
	public Card(ArrayList<String> settings, Set setin)
	{
		//System.out.println(settings);
		boolean [] check = {false, false, false};
		if (settings.size() < 3)
		{
			throw new SimpleCardException("2 settings is too few to build a card.");
		}
		set = setin.getName();
		isBlank = false;
		for (String hoi: settings)
		{
			String value = Settings.getValueHelper(hoi);
			//System.out.println(Settings.getNameHelper(hoi));
			//System.out.println(Settings.getValueHelper(hoi));
			switch (Settings.getNameHelper(hoi))
			{
			case "name":
				Name = value;
				check[0] = true;
				break;
			case "type":
				Type = value;
				check[1] = true;
				break;
			case "cost":
				cost = value;
				check[2] = true;
				if (value == null || cost.equals(""))
				{
					//System.out.println(Name);
					cost = "0";
				}
				break;
			case "Keyword":
				getValues();
			case "s":
				archetypes.add(value.toLowerCase());
			}
			//System.out.println(Name + ".");
		}
		if (!check[0] || !check[1] || !check[2])
		{
			throw new SimpleCardException("Missing a setting from: " + Name + ","+ cost + ","+ Type);
		}
		getValues();
		try 
		{
			red = pullColor(cost,"R");
		}
		catch (CostNotFoundException cnfe)
		{
			System.out.println(cnfe.getMessage());
			red = 0;
			cost = "0";
		}
		green = pullColor(cost,"G");
		black = pullColor(cost,"B");
		blue = pullColor(cost,"U");
		white = pullColor(cost,"W");
		CMC = generateCMC(cost);
		refresh();
		nonBasic = ((!Name.equals("Island"))&&(!Name.equals("Mountain"))&&(!Name.equals("Plains"))&&(!Name.equals("Forest"))&&(!Name.equals("Swamp")));
	}
	/**
	 * Default Constructor, Creates a blank card.
	 */
	public Card()
	{
		isBlank = true;
	}
	@Override
	public boolean equals(Object inputObject) {
		return inputObject instanceof Card && ((Card)inputObject).getName().equals(getName());
	}
	@Override
	public String toString() {
		return getName();
	}
	
	public void loadImage() {
		thisImage  = new ImageIcon(imageFilter(Name));
		//System.out.println(imageFilter(Name));
	}
	public void unloadImage() {
		thisImage = null;
	}
	public void refresh() {
		ArrayList<String> list = getCardStats(Name);
		for (String hoi: list)
		{
			String value = Settings.getValueHelper(hoi);
			//System.out.println(Settings.getNameHelper(hoi));
			//System.out.println(Settings.getValueHelper(hoi));
			switch (Settings.getNameHelper(hoi))
			{
			case "Count":
				count = parseCount(value);
				break;
			case "Value":
				q1 = Integer.parseInt(value.substring(0, 1));
				q2 = Integer.parseInt(value.substring(2, 3));
				q3 = Integer.parseInt(value.substring(4, 5));
				q4 = Integer.parseInt(value.substring(6, 7));
				qt = q1+q2+q3+q4;
				break;
			case "White":
				colorboost[0] += Integer.parseInt(value);
				break;
			case "Blue":
				colorboost[1] += Integer.parseInt(value);
				break;
			case "Black":
				colorboost[2] += Integer.parseInt(value);
				break;
			case "Red":
				colorboost[3] += Integer.parseInt(value);
				break;
			case "Green":
				colorboost[4] += Integer.parseInt(value);
				break;
			case "Wins":
				wins += Integer.parseInt(value);
				break;
			case "Loses":
				loses += Integer.parseInt(value);
				break;
			/*case "Keyword":
				getValues();*/
			}
			//System.out.println(Name + ".");
		}
		nonBasic = ((!Name.equals("Island"))&&(!Name.equals("Mountain"))&&(!Name.equals("Plains"))&&(!Name.equals("Forest"))&&(!Name.equals("Swamp")));
		setColorBase();
	}
	public int pullColor(String cost, String color) throws CostNotFoundException{
		if (cost == null)
		{
			throw new CostNotFoundException();
		}
		if (color.equals("R")){
			cost = cost.replaceAll("[^R]", "");
		}
		else if (color.equals("U")){
			cost = cost.replaceAll("[^U]", "");
		}
		else if (color.equals("G")){
			cost = cost.replaceAll("[^G]", "");
		}
		else if (color.equals("B")){
			cost = cost.replaceAll("[^B]", "");
		}
		else if (color.equals("W")){
			cost = cost.replaceAll("[^W]", "");
		}
			return cost.length();
		}
	public boolean testString(String original, String testfor){
		if (original.equals(testfor)){
			return true;
		}
		if (original.length()<testfor.length()){
			return false;
		}
		for (int i = 0; i<= (original.length()-testfor.length());i++){
			if(original.substring(i,i+testfor.length()).equals(testfor)){
				return true;
			}
		}
			return false;
	}
	public int generateCMC(String castingCost){
		int doom = 0;
		int i = 0;
		String holdnum = "0";
		for (; i <castingCost.length();i++) {
			if (isNumeric(castingCost.substring(i, i+1))) {
				if (i == 0) {
					holdnum=castingCost.substring(i,i+1);
				}
				else {
					holdnum+=castingCost.substring(i,i+1);
				}
			}
			else if (castingCost.substring(i,i+1).equals("X")||castingCost.substring(i,i+1).equals("Y")) {
				
			}
			else if (castingCost.substring(i,i+1).equals("/")){
				hybrid=true;
				doom--;
			}
			else {
				doom++;
			}
		}
		doom += Integer.parseInt(holdnum);
		return doom;
	}
	public static boolean isNumeric(String str)
	{
	  return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}
	public ArrayList<String> getCardStats(String cardName){
		ArrayList<String> output = new ArrayList<>(2);
		if (!(cardName==null)){
			try (BufferedReader rC = new BufferedReader(new FileReader(MainClass.programDataFolder()+"DraftData/"+cardName+".txt"))){
				String nextLine;
				while ((nextLine = rC.readLine()) != null)
				{
					if (Settings.checkValid(nextLine))
					{
						output.add(nextLine);
					}
				}
			return output;
			}
			catch (IOException e){
				//System.out.println(e.getMessage());
			}
		}
		System.out.println("Error: data not located with card: " + cardName+". Creating Data");
		MainClass.prepFolder("DraftData/");
		
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(MainClass.programDataFolder()+"DraftData/"+cardName+".txt"))){
			bw.write("Count=0,0,0,0,0,0,0,0"+System.lineSeparator());
			bw.write("Value=4,4,4,4"+System.lineSeparator());
		}
		catch(IOException e){
			
		}
		
		//Default
		output.add("Count=0");
		output.add("Value=4,4,4,4");
		return output;
	}
	
	public Image getPic(boolean flip){
		if (thisImage==null) {
			loadImage();
		}
		if (flip) {
			thisImage = new ImageIcon(imageFilter(name2));
		}
		//System.out.println(thisImage);
		return thisImage.getImage();
	}
	public String imageFilter(String cardName){
		cardName = cardName.replaceAll(",","");
		cardName = cardName.replaceAll("#","");
		cardName = cardName.replaceAll("’","");
		cardName = cardName.replaceAll("'", "");
		File f = new File(MainClass.programDataFolder()+"cardImages/"+cardName+".full.jpg");
        if(!f.exists()) {
            System.out.println("Card image for "+cardName+" not found");
        }
		return MainClass.programDataFolder()+"cardImages/"+cardName+".full.jpg";
		//}
	}
	public void Save(boolean reset){
		try(BufferedWriter bw=new BufferedWriter(new FileWriter(MainClass.programDataFolder()+"DraftData/"+Name+".txt"))){
			if (reset) {
				bw.write("Count=0,0,0,0,0,0,0,0"+System.lineSeparator());
			}
			else {
				bw.write("Count=");
				for (int i = 0;i < 7;i++) {
					bw.write(count[i]+",");
				}
				bw.write(count[7]+System.lineSeparator());
			}
			bw.write("Value="+q1+","+q2+","+q3+","+q4+System.lineSeparator());
			bw.write("Keyword=" + System.lineSeparator());
			bw.write("Wins=" + wins + System.lineSeparator());
			bw.write("Loses=" + loses);
			for (int i = 0; i < stack.length;i++){
				if (stackdata[i] > 0){
					bw.write(stack[i]+stackdata[i]);
				}
			}
			for (int i = 0; i < group.length;i++){
				if (groupdata[i] > 0){
					bw.write(group[i]+groupdata[i]);
				}
			}
			if (colorboost[0]>0){
				bw.write(System.lineSeparator() + "White="+colorboost[0]);
			}
			if (colorboost[1]>0){
				bw.write(System.lineSeparator() + "Blue="+colorboost[1]);
			}
			if (colorboost[2]>0){
				bw.write(System.lineSeparator() + "Black="+colorboost[2]);
			}
			if (colorboost[3]>0){
				bw.write(System.lineSeparator() + "Red="+colorboost[3]);
			}
			if (colorboost[4]>0){
				bw.write(System.lineSeparator() + "Green="+colorboost[4]);
			}
		}
		catch(IOException e){
			System.out.println("Error Updating Card "+Name);
		}
	}
	public void getValues(){
		for (int m = 0; m < stack.length;m++){
			for (int i = 0; i < keywords.length()-stack[m].length();i++){
				if (keywords.substring(i,i+stack[m].length()).equals(stack[m])){
					stackdata[m]+=Integer.parseInt(keywords.substring(i+stack[m].length(),i+stack[m].length()+1));
				}
			}
		}
		for (int m = 0; m < group.length;m++){
			for (int i = 0; i < keywords.length()-group[m].length();i++){
				if (keywords.substring(i,i+group[m].length()).equals(group[m])){
					groupdata[m]+=Integer.parseInt(keywords.substring(i+group[m].length(),i+group[m].length()+1));
				}
			}
		}
		for (int m = 0; m < colors.length;m++){
			for (int i = 0; i < keywords.length()-colors[m].length();i++){
				if (keywords.substring(i,i+colors[m].length()).equals(colors[m])){
					colorboost[m]+=Integer.parseInt(keywords.substring(i+colors[m].length(),i+colors[m].length()+1));
				}
			}
		}
	}
	/**
	 * 
	 * @param name
	 * @return
	 * @throws NullPointerException
	 * @deprecated
	 */
	public String flipcall(String name) throws NullPointerException {
		String truename = "";
		if (name == null)
		{
			throw new NullPointerException("Name not given for this card.");
		}
		if (name.length()>2) {
		if (name.substring(0, 2).equals("//")) {
			for (int i = 2;i<100;i++) {
			if (name.substring(i, i+2).equals("//")) {
				name2 = name.substring(i+2);
				i+=100;
			}
			else {
				truename+=name.substring(i, i+1);
			}
			}
		}
		else {
			name2 = name;
			return name;
		}
		}
		return truename;
	}
	public int [] parseCount(String hoi){
		String [] commas = new String[8];
		int [] doom=new int[8];
		int commacount = 0;
		if (!isBlank)
		{
		for (int i = 0;i<hoi.length();i++) {
			if (hoi.substring(i,i+1).equals(",")) {
				commacount++;
				if (commacount==8) {
					break;
				}
			}
			else {
				if (commas[commacount]==null) {
					commas[commacount]=hoi.substring(i,i+1);
				}
				else {
				commas[commacount]+=hoi.substring(i,i+1);
				}
			}
		}
		if (commacount==7) {
			for (int i = 0;i<=7;i++) {
			doom[i]=Integer.parseInt(commas[i]);
			}
			return doom;
		}
		}
		else
		{
			for (int i = 0; i < 8; i++)
			{
				doom[i] = 0;
			}
		}
		return doom;
	}
	public int winrate()
	{
		int winrate;
		try {
			winrate = (wins*100)/(loses + wins);
		}
		catch (ArithmeticException | NullPointerException e)
		{
			winrate = -1;
		}
		return winrate;
	}
	public Color getColorBase()
	{
		Color output;
		if (colorBase == null)
		{
			refresh();
		}
		switch (colorBase)
		{
			case White:
				output = Color.decode("#C7CB58");
				break;
			case Blue:
				output = Color.decode("#2130DF");
				break;
			case Black:
				output = Color.BLACK;
				break;
			case Red:
				output = Color.RED;
				break;
			case Green:
				output = Color.decode("#029400");
				break;
			case Colorless:
				output = Color.decode("#989898");
				break;
			case Hybrid:
				output = Color.decode("#C739C0");
				break;
			case Multicolor:
				output = Color.decode("#EAA30A");
				break;
			default:
				output = Color.WHITE;
		}
		
		return output;
	}
	private void setColorBase()
	{
		//System.out.println(String.format("%s: has colorboost %s", Name, colorBoostString()));
		if (getWhite() > 0 && getBlue() + getBlack() + getRed() + getGreen() == 0)
		{
			colorBase = ColorBase.White;
		}
		else if (getBlue() > 0 && getWhite() + getBlack() + getRed() + getGreen() == 0)
		{
			colorBase = ColorBase.Blue;
		}
		else if (getBlack() > 0 && getBlue() + getWhite() + getRed() + getGreen() == 0)
		{
			colorBase = ColorBase.Black;
		}
		else if (getRed() > 0 && getBlue() + getBlack() + getWhite() + getGreen() == 0)
		{
			colorBase = ColorBase.Red;
		}
		else if (getGreen() > 0 && getBlue() + getBlack() + getRed() + getWhite() == 0)
		{
			colorBase = ColorBase.Green;
		}
		else if (getGreen() + getBlue() + getBlack() + getRed() + getWhite() == 0)
		{
			colorBase = ColorBase.Colorless;
		}
		else if (hybrid)
		{
			colorBase = ColorBase.Hybrid;
		}
		else
		{
			colorBase = ColorBase.Multicolor;
		}
	}
	public String colorBoostString()
	{
		return String.format("[%d, %d, %d, %d, %d]", colorboost[0], colorboost[1], colorboost[2], colorboost[3], colorboost[4]);
	}
}
