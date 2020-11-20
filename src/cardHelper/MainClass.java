package cardHelper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import cardHelper.MainClass.Pages;
import cardHelper.card.Card;
import cardHelper.pages.CardView;
import cardHelper.pages.Collection;
import cardHelper.pages.Draft;
import cardHelper.pages.MainPage;
import cardHelper.pages.Packopen;
import cardHelper.pages.Page;
import cardHelper.pages.PickChaos;
import cardHelper.pages.PickCollection;
import cardHelper.pages.PickDraft;
import cardHelper.pages.PickOpen;
import cardHelper.pages.PickSealed;
import cardHelper.set.Set;
import cardHelper.table.ClassicDraftTable;
import cardHelper.toolBox.Clickable;
import cardHelper.toolBox.ClickableButton;
//Summer projected by Johnathan H
public class MainClass extends JFrame implements KeyListener, MouseListener, MouseMotionListener, Runnable{
	
	public static final String VERSION = "4.0 Alpha";
	/**
	 * Clickable ( to reset to main.
	 */
	private JPanel drawPanel;
	public final Clickable RESET = new ResetButton();
	private static String boxMessage;
	private static int boxMessageAge;
	private static MainClass thisProgram;
	private static final int MAIN_FPS = 2;
	static Dimension screenSize;
	public static int screenHeight;
	public static int screenWidth;
	
	/**
	 * Integer to keep track of the number of generated sealed pools.
	 */
	private int sealedGenerated;
	public String editView = "count";
	private int editnum = 0;
	public static final Set BLANK_SET = new Set();
	//Set currentPack = blankSet;
	int playerhold=8;
	public ArrayList<Set> packschoosen;
	public ArrayList<Set> setList = getSets();
	private Card cardChosen;
	private BufferStrategy bs;
	int movecount=0;
	private Thread thread;
	public boolean running = true;
	int x=0,y=0,x1=0,xtrue=10;
	int hover=-1;
	private Page mainpage;
	private Collection collection;
	private Draft draft;
	private Page pickSealed, pickCollection, pickDraft, pickOpen, pickChaos;
	private Packopen packOpen;
	private Page cScreen;
	private ClassicDraftTable table;
	public static final Font BASICFONT = new Font("Arial",Font.PLAIN,20);
	public int imageHeight = (int)(523/1.5);
	public int imageWidth = (int)(375/1.5);
	public static final Color DDGREEN = Color.decode("#A8DEF0");
	public static final Color DDTEAL = Color.decode("#00F0EC");
	/**
	 * int that will store miliseconds since the last repaint;.
	 */
	private int threadCheck = 0;
		
		//Warning: There is currently a 64 set limit!
	public enum Pages {
		main, collection, draft, pickSealed, pickCollection, pickDraft, pickOpen, pickChaos, openPack, tempPage, collectionList;
	}
	/**
	 * Main method for the card program Drafter Dat, creates a new instance of the program.	
	 */
	public MainClass()
	{
		super(String.format("Drafter Dat %s", VERSION));
		System.out.println("hoi");
		prepFolder(programDataFolder());
		System.out.println(String.format("Set up data file: %s", programDataFolder().substring(0, programDataFolder().length() - 1)));
		Settings.pullSettings();
		mainpage = new MainPage(this);
		collection = new Collection(this);
		draft = new Draft(this);
		pickSealed = new PickSealed(this);
		pickCollection = new PickCollection(this);
		pickDraft = new PickDraft(this);
		pickOpen = new PickOpen(this);
		packOpen = new Packopen(this);
		pickChaos = new PickChaos(this);
		packschoosen =new ArrayList<Set>(6);
		cScreen=mainpage;
		sealedGenerated = 0;
		boxMessage = "";
		boxMessageAge = -1;
		//setBoxMessage("Test");
		Settings.settingValue("ArchetypeValue", "1");
		Settings.settingValue("aArchetypeValue", "5");
		Settings.settingValue("maxArchetypeValue", "18");
		Settings.settingValue("0PointColor", "15");
		Settings.settingValue("PointInterval", "2");
		//setSize(screenWidth,screenHeight);
		initPaint();
		addKeyListener(this);
		setMinimumSize(new Dimension(1200, 800));
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		//createBufferStrategy(4);
		//bs = this.getBufferStrategy();
		thread = new Thread(this);
		thread.start();
	}
	/**
	 * Main method. Creates this.
	 * @param args Whatever java likes.
	 */
	public static void main(String[] args)
	{
			thisProgram = new MainClass();
		//thisProgram.cycle();
	}
	/**
	 * Initializes the Paint settings.
	 */
	private void initPaint() {
		drawPanel = new drawPanel();
		drawPanel.setBackground(Color.decode("#E3E3E3"));
		drawPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setContentPane(drawPanel);
		drawPanel.addMouseListener(this);
		drawPanel.addMouseMotionListener(this);
	}
	/**
	 * Thread cycle.
	 */
	/*public void cycle()
	{
		while (true){
			repaint();
			try {Thread.sleep(800);}
			catch (Exception e) {System.out.println(e.getMessage());}
		}
	}*/
	
	class drawPanel extends JPanel {
		drawPanel ()
		{
			setPreferredSize(new Dimension(screenWidth, screenHeight));
		}
		
		@Override
		public void paintComponent(Graphics cellar) {
			try {
				super.paintComponent(cellar);
				cellar.setFont(new Font("Arial",Font.PLAIN,20));
				screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				screenHeight = (int)screenSize.getHeight();
				screenWidth = (int)screenSize.getWidth();
				cScreen.draw(cellar);
				cScreen.render(cellar);
				RESET.draw(cellar);
				cScreen.hoverCard(x, y,cellar);
				if (cScreen instanceof MainPage && boxMessageAge >= 0)
				{
					cellar.setColor(Color.WHITE);
					cellar.fillRect(screenWidth/2-200, screenHeight/2-50, 400, 100);
					cellar.setColor(Color.BLACK);
					cellar.drawRect(screenWidth/2-200, screenHeight/2-50, 400, 100);
					cellar.drawString(boxMessage, screenWidth/2-195, screenHeight/2+2);
					cellar.drawString(String.valueOf(4 - boxMessageAge/MAIN_FPS), screenWidth/2+170, screenHeight/2+40);
				}
				} catch (Exception e)
				{
					throwError(e);
				}
		}
	}
	
	//Get value
	public void keyReleased(KeyEvent k) {}
	public void keyTyped(KeyEvent k) {
		try {
			System.out.println(k);
			cScreen.onType(k.getKeyChar());
			repaint();
		} catch (Exception e) {
			throwError(e);
		}
	}
	public void keyPressed(KeyEvent k) {}
	public void mouseReleased(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		//System.out.println(String.format("Mouse Released: %d, %d", e.getX() , e.getY()));
		if (RESET.Check(x, y)) {
			resetall();
		}
		else {
			if (cScreen instanceof MainPage && boxMessageAge >= 0)
			{
				if (boxMessageAge >= 4*MAIN_FPS) {
					clearBoxMessage();
				}
			}
			else
			{
				try {
					cScreen.onClick(x, y, this);
				} catch (Exception exception) {
					throwError(exception);
				}
			}
		}
		
		repaint();
	}
	public void mousePressed(MouseEvent e) {
		//System.out.println(String.format("Mouse Pressed: %d, %d", e.getX() , e.getY()));
		}
	public void mouseClicked(MouseEvent e) {
		//System.out.println(String.format("Mouse Clicked: %d, %d", e.getX() , e.getY()));
		}
	public void mouseEntered(MouseEvent e) {
		//System.out.println(String.format("Mouse Entered: %d, %d", e.getX() , e.getY()));
		}
	public void mouseExited(MouseEvent e) {
		//System.out.println(String.format("Mouse Exited: %d, %d", e.getX() , e.getY()));
		}
	public void CalibrateData(boolean reset){
		setList = getSets();
		Set all = new Set("All");
		for (int i = 0; i < setList.size();i++) {
			setList.get(i).resetCount();
			all.mergeSets(setList.get(i));
		}
		setList.add(all);
	}
	public Set allignPack(String name){
		for (int i = 0;i < setList.size();i++){
			if (setList.get(i).getName().equals(name)){
				return setList.get(i);
			}
		}
		System.out.println("Set "+name+" not on set list. Using "+setList.get(0).getName());
		return setList.get(0);
	}
	public int getEditValue(Card card){
		if (editnum==0){
			if (playerhold<8&&playerhold>=0) {
			return card.count[playerhold];
			}
			else {return 0;}
		}
		if (editnum==1){
			return card.q1;
		}
		if (editnum==2){
			return card.q2;
		}
		if (editnum==3){
			return card.q3;
		}
		if (editnum==4){
			return card.q4;
		}
		if (editnum==5){
			return card.getqt();
		}
		if (editnum==6){
			return card.colorboost[0];
		}
		if (editnum==7){
			return card.colorboost[1];
		}
		if (editnum==8){
			return card.colorboost[2];
		}
		if (editnum==9){
			return card.colorboost[3];
		}
		if (editnum==10){
			return card.colorboost[4];
		}
		return 0;
	}
	public void editValue(Card card,String function, int num){
		if (num>-1&&num<10) {
		
		if (function.equals("Q1")){
			card.q1 =num;
		}
		else if (function.equals("Q2")){
			card.q2=num;
		}
		else if (function.equals("Q3")){
			card.q3=num;
		}
		else if (function.equals("Q4")){
			card.q4=num;
		}
		else if (function.equals("W")){
			card.colorboost[0]=num;
		}
		else if (function.equals("U")){
			card.colorboost[1]=num;
		}
		else if (function.equals("B")){
			card.colorboost[2]=num;
		}
		else if (function.equals("R")){
			card.colorboost[3]=num;
		}
		else if (function.equals("G")){
			card.colorboost[4]=num;
		}
		
		card.Save(false);
		repaint();
		}
	}
	/**
	 * Changes all copies of the specified card in all sets in the way of their wins or loses based.
	 * On the passed parameters.
	 * @param name Name of the card.
	 * @param won Whether or not the card one.
	 * @param side Whether or not the card was main board.
	 */
	public void changeNamedWinrate(String name, int cardCount, boolean won, boolean main)
	{
		int winCount = 1;
		if (main)
		{
			winCount *= 4;
		}
		for (Set set: setList)
		{
			for (Card hoi: set.sortedCards("rarity"))
			{
				if (hoi.getName().equals(name))
				{
					if (won)
					{
						hoi.wins += winCount;
						hoi.Save(false);
					}
					else
					{
						hoi.loses += winCount;
						hoi.Save(false);
					}
				}
			}
		}
	}
	public ArrayList<Set> getSets() {
		ArrayList<Set> doom = new ArrayList<Set>(30);
		try (BufferedReader br = new BufferedReader(new FileReader(MainClass.programDataFolder()+"Packs/Setlist.txt"))){
			String insert;
			while ((insert = br.readLine())!=null) {
				doom.add(new Set(insert));
			}
		}
		catch (IOException e) {
			System.out.println("Error finding sets");
		}
		Set all = new Set("All");
		for (Set set: doom) {
			all.mergeSets(set);
		}
		doom.add(all);
		return doom;
		
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		x=e.getX();
		y=e.getY();
	}
	public void paintcycle() {
		
	}
	@Override
	public void run() {
		while(running){
			cScreen.hoverCheck(x, y);
			if (boxMessageAge >= 0 && cScreen instanceof MainPage)
			{
				threadCheck += 50;
				if ((1000/MAIN_FPS)/threadCheck >= 1)
				{
					boxMessageAge++;
					threadCheck %=  1000/MAIN_FPS;
					repaint();
				}
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * Resets all of the current important variables and sends the user to the mainpage, except the Box Message.
	 */
	public void resetall() {
		cScreen = mainpage;
		editnum = 0;
		editView = "count";
		packschoosen.clear();
		cardChosen = null;
		unloadCards();
		repaint();
		System.out.println("done..?");
	}
	
	public int getidSet(String setname) {
		for (int i = 0; i < setList.size();i++) {
			if (setList.get(i).getName().equals(setname)) {
				return i;
			}
		}
		return -1;
	}
	
	public void setDraftTable(ClassicDraftTable tableIn)
	{
		table = tableIn;
		draft.setTable(table);
	}
	
	public ClassicDraftTable createTable(boolean[] pass) {
		ClassicDraftTable output = new ClassicDraftTable(packschoosen, this, pass, cardChosen);
		cardChosen = null;
		return output;
	}
	/**
	 * Attempts to prep folder, returns value based on if it was created. A blank or null String will not be attempted.
	 * @param folderDir Folder directory based on main folder for new folder.
	 * @return true if the folder is created and is a valid string, false otherwise.
	 */
	public static boolean prepFolder(String folderDir)
	{
		if (folderDir == null || folderDir.equals("")) {
			return false;
		}
		if (folderDir.substring(folderDir.length() - 1).equals("\\"))
		{
			System.out.println("Found a \\");
			folderDir = folderDir.substring(0, folderDir.length() - 1);
		}
		File f;
		if (folderDir.equals(programDataFolder())) {
			f = new File(folderDir);
		}
		else {
			f = new File(String.format("%s%s", programDataFolder(), folderDir));
		}
		return f.mkdirs();
	}
	/**
	 * Resets the program and sets the error message box to the desired message.
	 * @param message Message to make visible in the error message box.
	 */
	public static void sendMainWithError(String message)
	{
		setBoxMessage(message);
		MainClass.thisProgram.resetall();
	}
	/**
	 * Sets the error box's message to the input amount.
	 * @param message Message to make visible in the error message box.
	 */
	public static void setBoxMessage(String message)
	{
		if (message == null || message.equals("")) {
			
		}
		else {
			boxMessage = message;
			boxMessageAge = 0;
		}
	}
	/**
	 * Sets the error box's message to the default empty value and removes it from the screen.
	 */
	public static void clearBoxMessage()
	{
		boxMessage = "";
		boxMessageAge = -1;
	}
	/**
	 * Retrieves the program data folder for this Program.
	 * @return String in the "{This programs data folder}/" format.
	 */
	public static String programDataFolder()
	{
		return String.format("%s\\AppData\\Local\\DrafterDat\\", System.getProperty("user.home"));
	}
	/**
	 * Gets the number of generated sealed pools.
	 * @return The number of generated sealed pools.
	 */
	public int getSealedGenerated()
	{
		return sealedGenerated;
	}
	/**
	 * Increases the count of generated sealed pools by 1.
	 */
	public void incrementSealedGenerated()
	{
		sealedGenerated++;
	}
	/**
	 * Sets the screen to the inserted page.
	 * @param newPage Pages enum of the desired page.
	 */
	public void setPage(Pages newPage)
	{
		switch (newPage)
		{
		case main:
			cScreen = mainpage;
			break;
		case collection:
			cScreen = collection;
			break;
		case draft:
			cScreen = draft;
			break;
		case pickSealed:
			cScreen = pickSealed;
			break;
		case pickCollection:
			cScreen = pickCollection;
			break;
		case pickDraft:
			cScreen = pickDraft;
			break;
		case pickOpen:
			cScreen = pickOpen;
			break;
		case pickChaos:
			cScreen = pickChaos;
			break;
		case openPack:
			cScreen = packOpen;
			break;
		default:
			sendMainWithError("Error: Sent to a non-Existant page.");
		}
	}
	/**
	 * Sets the screen to a specific CardView page
	 * @param input CardView Page to set the screen to view.
	 */
	public void setPage(CardView input)
	{
		cScreen = input;
	}
	/**
	 * Sets a Page's Set to the input set.
	 * @param inputPage Page to give a new set.
	 * @param inputSet Set to become Page's Set.
	 * @return True if the Page can except the Set, false otherwise.
	 */
	public boolean setPageSet(Pages inputPage, Set inputSet)
	{
		boolean output;
		switch (inputPage)
		{
		case collection:
			collection.setSet(inputSet);
			output = true;
			break;
		case openPack:
			packOpen.setSet(inputSet);
			output = true;
			break;
		default:
			output = false;
		}
		return output;
	}
	/**
	 * Fully alerts the user of a given error and deposits a log of the error in system files.
	 * @param e Exception thrown.
	 */
	public void throwError(Exception e)
	{
		String filename = new SimpleDateFormat("yyyyMMddHHmm'.txt'").format(new Date());
		prepFolder(String.format("%sErrorLog/", programDataFolder()));
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(MainClass.programDataFolder()+"ErrorLog/"+filename);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		PrintWriter print = new PrintWriter(fos);
		StackTraceElement [] input = e.getStackTrace();
		print.write(e.toString());
		for (int i = 0; i < input.length; i++)
		{
			print.write(String.format("%s%s", System.lineSeparator(), input[i].toString()));
		}
		print.close();
		sendMainWithError(e.getLocalizedMessage());
	}
	
	private void unloadCards()
	{
		for (Set set : setList) {
			set.unloadCardImages();
		}
	}
	/**
	 * Sets up the pick draft Page with the desired Card in the first Pack.
	 * @param set Set of the card.
	 * @param card Card to guarantee in pack one.
	 */
	public void draftWithCard(Set set, Card card)
	{
		setPage(Pages.pickDraft);
		if (packschoosen.size()<3) {
			packschoosen.add(set);
		}
		cardChosen = card;
	}
}