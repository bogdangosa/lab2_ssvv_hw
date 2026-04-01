package Lecture02MaxPointsParticipantsMV;

import controller.ParticipantController;
import repository.Repository;
import ui.ParticipantUI;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        String participantsFile = "FileParticipantsMaxAll.txt";

        Repository repo = new Repository(participantsFile);
        ParticipantController ctrl = new ParticipantController(repo);

        ParticipantUI console = new ParticipantUI(ctrl);
        console.Run();
    }
}
