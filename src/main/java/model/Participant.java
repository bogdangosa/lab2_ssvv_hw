
package model;

public class Participant {
	private String id;
	private String name;
	private int points;
	
	/** Constructors */
	
	public Participant() {};
	
	public Participant(String idP, String nameP, int pointsP) 
	{
			this.id = idP;
			this.name = nameP;
			this.points= pointsP;
	}
	
	/** Getters and setters */
	public String getId() {
		return this.toString();
	}
	public void setId(String idP) {
		id = idP;
	}
	public String getName() {
		return name;
	}
	public void setName(String nameP) {
		name = nameP;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int pointsP) {
		points = pointsP;
	}
	/** Others */
	public String toString() {
	    return id + ", " + name + "," + points;
	
	}
}
