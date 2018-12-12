package Logic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import GUI.AIStarter;

public class AI {
	private char letter1 = 'S', letter2 = 'O';
	private int length;
	private int lastValue;
	private int lastReq;
	private int lastCol;

	public AI(int len)
	{
		length = len;
	}
	
	public static void main(String[] args) throws IOException
	{
		AI ai = new AI(3);
		//ai.initCSV();
		//ai.writeCSV(1,0,0);
	}
	
	private char[] pickRandomMove()
	{
		char[] values = new char[3];
		
		if (Math.random()<0.5)
			values[0] = letter1;
		else
			values[0] = letter2;
		
		values[1] = getPos(length);
		
		values[2] = getPos(length);
		
		System.out.println(Arrays.toString(values));
		
		return values;
	}
	
	public char[] pickMove(int row)
	{
		char[] values = new char[3];
		
		int sequence = readCSV(row);
		
		lastCol = sequence;
		
		System.out.println("AI60:"+sequence);
		
		char row1 =  Integer.toString( (( (int) sequence/6))).charAt(0);
		
		char col1 = Integer.toString( (( (int) sequence%3))).charAt(0);
		
		char letter = ' ';
		if (sequence%2==0)
			letter='S';
		else
			letter='O';
			
		values[0]=letter;
		values[1]=row1;
		values[2]=col1;
		
		System.out.println("AI74:"+row1 +" "+col1);
		return values;
	}
	
	private String[] values;
	
	private int readCSV(int row)
	{
		lastReq=row;
		String file = "qL.csv";
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			int count = 0;
			String line = "";
			while ((line = br.readLine()) != null) {
				if(count == row)
				{
					values = line.split(",");
					System.out.println(row+" "+" "+Arrays.toString(values));
					int maxval = 0;
					for(int i=0; i<values.length; i++)
					{
						if (Integer.parseInt(values[i].replaceAll("[^0-9]", ""))>maxval && Integer.parseInt(values[i].replaceAll("[^0-9]", "")) !=100)
							maxval = Integer.parseInt(values[i].replaceAll("[^0-9]", ""));
					}
					lastValue = maxval;
					System.out.println("AI101:"+maxval+" "+row+" ");
					for(int i=0; i<values.length; i++)
					{
						if (Integer.parseInt(values[i].replaceAll("[^0-9]", ""))==maxval)
						{
							if(Math.random()>.05)
								return (int) (Math.random()*18);
							else
								return i;
						}
					}
				}
				count++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ex) {
	        ex.printStackTrace();
	    }
		return 100;
	}
	
	public void writeCSV(int reward, int row, int col, GameLogic game) throws IOException
	{
		System.out.println(reward+" "+lastReq+" "+lastCol);
//		if(reward!=-100)
//		{
//			int nextStateMax = readCSV(game.getSequence());
//			reward = (int) (0.1*(reward + 0.9*nextStateMax -lastValue));
//		}
		String file = "qL.csv";
		CSVReader reader = new CSVReader(new FileReader(new File(file)));
		List<String[]> csvBody = reader.readAll();
		csvBody.get(lastReq)[lastCol] = Integer.toString(reward);
		System.out.println("AI136:"+csvBody.get(lastReq)[lastCol]);
		reader.close();
		
		CSVWriter writer = new CSVWriter(new FileWriter(new File(file)));
		writer.writeAll(csvBody);
		writer.flush();
		writer.close();
	}
	
	private void initCSV()
	{
		try {
		    PrintWriter pw = new PrintWriter(new File("qL.csv"));
	        StringBuilder sb = new StringBuilder();
			
		    for(int row=0; row<196607;row++)
		    {
		    		for(int col=0; col<18;col++)
		    		{
		    			sb.append("0");
		    			sb.append(',');
		    		}
		    		sb.deleteCharAt(sb.length()-1);
		    		sb.append('\n');
		    }
		    pw.write(sb.toString());
	        pw.close();
		}
		catch(Exception ioe) {
		    ioe.printStackTrace();
		}
	}
	
	private char getPos(int len)
	{
		return (char)((int) (Math.random()*len) + 48);
	}
}
