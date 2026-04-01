
package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import repository.Repository;
import model.Participant;


public class ParticipantController {

		
	private Repository rep;

	/** Constructors */

	public ParticipantController(Repository rep) {
		this.rep = rep;
		//this.PatientList = rep.getPatientList();
		
	}

	/** Getters */
	public List<Participant> getParticipantsList() {
		return rep.getParticipantsList();
	}
    public int getMaxPointsReceived(){
    	int maximalPoints=-1;
    	List<Participant> participantsList = rep.getParticipantsList();		
		
		for (int i = 0; i < participantsList.size(); i++) 
			if (participantsList.get(i).getPoints() >maximalPoints)
				maximalPoints = participantsList.get(i).getPoints(); 
		
    	return maximalPoints;
    }
	public List<Participant> getParticipantsWithMaxPoints() {
		List<Participant> participantsList = rep.getParticipantsList();
		List<Participant> participantsListSamePoints = new ArrayList<Participant>();;
		int pointsMax = getMaxPointsReceived();
		
		for (int i = 0; i < participantsList.size(); i++) 
			if (participantsList.get(i).getPoints() == pointsMax)
				participantsListSamePoints.add(participantsList.get(i));			
		
		return participantsListSamePoints;
	}

	public int getNumberOfParticipantWithMaxPointsReceived(){
		int noParticipantsMaxPoints, i,posMax;		
		List<Participant> participantsList = rep.getParticipantsList();		
		i=0;noParticipantsMaxPoints=0;posMax=0;	
		while (i<participantsList.size()){					
			if (participantsList.get(i).getPoints()> participantsList.get(posMax).getPoints()){
				posMax=i;	
				noParticipantsMaxPoints=1;
			}
			else
				if (participantsList.get(posMax).getPoints() == participantsList.get(i).getPoints())				
					noParticipantsMaxPoints++;
			i++;
		}
		return noParticipantsMaxPoints;
	}	
	
	public void addParticipant(Participant p) {
		
		rep.addNewParticipant(p);
		
	}

	


}
