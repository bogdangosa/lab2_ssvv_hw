
package repository;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import model.Participant;



public class Repository {
	
	private String participantsFile; 

	private ArrayList<Participant> participantsList;
	
	
	public Repository(String participantsFile) 
	{
		this.participantsFile= participantsFile;
		participantsList= new ArrayList<Participant>();
	}
	
	public void cleanFiles()
	{
		FileWriter fw;
		try {
			fw = new FileWriter(participantsFile);
			PrintWriter out=new PrintWriter(fw);
			out.print("");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		participantsList = new ArrayList<Participant>();
	}
	
	public String[] getParticipantsFromFile() throws IOException
	{
		int n = 0;
		BufferedReader in = new BufferedReader(new FileReader(participantsFile));
		while ((in.readLine()) != null) {
			n++;
		}
		in.close();
		
		String[] la=new String[n];
		String s = new String();
		int i = 0;
		in = new BufferedReader(new FileReader(participantsFile));
		while ((s=in.readLine()) != null) {
			la[i] = s;
			i++;
		}
		in.close();
		return la;
	}
	
	
	public List<Participant> getParticipantsList()
	{
		List<Participant> lp = new ArrayList<Participant>();
		try {
			String[] tokens = getParticipantsFromFile();
			
			String tok = new String();
			String[] infoOneParticipant;
			int i = 0;
			while (i < tokens.length)
			{ 
				tok = tokens[i];
				infoOneParticipant = tok.split(",");
				String pointsS= infoOneParticipant[2];
				int points =Integer.parseInt(pointsS);
				lp.add(new Participant(infoOneParticipant[0],infoOneParticipant[1],points));	
				i = i + 1;
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return lp;	
	}
	
	
	public void saveParticipantsToFile(Participant p) throws IOException		// save to file
	{
		int n=0;
		BufferedReader in = new BufferedReader(new FileReader(participantsFile));
		while((in.readLine())!=null)
			n++;
		in.close();
		String[] sl=new String[n];
		String str;
		int i=0;
		in = new BufferedReader(new FileReader(participantsFile));
		while((str=in.readLine())!=null)
		{
			sl[i] = str;
			i++;
		}
		in.close(); // append
		FileWriter fw=new FileWriter(participantsFile);
		PrintWriter out=new PrintWriter(fw);
		for (i=0; i<sl.length; i++)
			out.println(sl[i]);
		out.println(p.toString());
		out.close();
	}
	
	public void addNewParticipant(Participant p){
		participantsList.add(p);
		try{
			this.saveParticipantsToFile(p);
		}catch(IOException e){
			System.out.println(e.toString());
			
		}
	}
	
}
