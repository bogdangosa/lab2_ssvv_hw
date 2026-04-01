package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Participant;

import controller.ParticipantController;

public class ParticipantUI {
	public ParticipantController ctrl;
	Scanner in;
	
	public ParticipantUI(ParticipantController ctrl)
	{
		this.ctrl=ctrl;
		this.in=new Scanner(System.in);
	}
	
	public ParticipantController getCtrl()
	{
		return this.ctrl;
	}
	
	public Scanner getIn()
	{
		return this.in;
	}
	
	public void setCtrl(ParticipantController newCtrl)
	{
		this.ctrl=newCtrl;
	}
	
	public void setIn(Scanner newIn)
	{
		this.in=newIn;
	}
	
	public void printMenu()
	{
		String menu;
		menu="Participant Management Menu: \n";
		menu +="\t 1 - to add a new participant; \n";
		menu +="\t 2 - to list number of participants with maximum points; \n";
		menu +="\t 3 - to list all the participants, having the maximum points; \n";		
		menu +="\t 0 - exit \n";
		
		System.out.println(menu);
	}
	
	
	
	
	public void Run()
	{
		printMenu();
		int cmd=in.nextInt();
		in.nextLine();	
		while(cmd!=0)
		{
			if(cmd==1)
			{
				Participant p = null;
				System.out.println("Enter id:");
				String id = in.nextLine();				
				System.out.println("Enter name:");
				String name = in.nextLine();
				System.out.println("Enter points:");
				String points= in.nextLine();
				int pointsInt= Integer.parseInt(points);
				p = new Participant(id, name, pointsInt);
				ctrl.addParticipant(p);				
			}
			if(cmd==2){
				int nOfParticipant = ctrl.getNumberOfParticipantWithMaxPointsReceived();
				System.out.println("The number of participants with the maximal points =" + nOfParticipant);				
				
			}

			if(cmd==3){
				List<Participant> participantsList = ctrl.getParticipantsWithMaxPoints();
				for (int i=0;i<participantsList.size();i++)
						System.out.println(participantsList.get(i).toString());				
				
			}

			printMenu();
			cmd=in.nextInt();
			in.nextLine();
		}
	}
}

