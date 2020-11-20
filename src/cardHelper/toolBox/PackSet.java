/*package cardHelper.toolBox;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.ImageIcon;

import cardHelper.pack.PackCallings;

public class PackSet extends PackCallings {
	String [] cardName;
	int [] cardCount;
	int packTotal;
	String theSet;
	int setSize;
	public Image getPic(int num){
		ImageIcon thisImage = new ImageIcon(imageFilter(cardName[num-1]));
		return thisImage.getImage();
		}
	public int getCount(int num){return cardCount[num];}
	public int getSetSize(){return setSize;}
	public int getPackTotal(){return packTotal;}
	public PackSet(String set){
		try (BufferedReader br = new BufferedReader(new FileReader("Packs/"+set+"/Opened"+set+".txt"))){
			setSize = Integer.parseInt(br.readLine());
			cardName = new String[setSize];
			cardCount = new int[setSize];
			String packTotalLine = br.readLine();
			packTotal = Integer.parseInt(packTotalLine.substring(12,packTotalLine.length()-1));
			theSet = set;
			for (int i = 0; i<setSize;i++){
				String inQuestion = br.readLine();
				for (int m = 4;m< inQuestion.length()-1;m++){
					if(inQuestion.substring(m, m+2).equals(" =")){
						cardName[i] = (inQuestion.substring(4,m));
						cardCount[i] = Integer.parseInt(inQuestion.substring(m+3,inQuestion.length()-1));
					}
				}
			}
		}
		catch(IOException e){
			System.out.print(e.getMessage());
		}
	}
}*/
