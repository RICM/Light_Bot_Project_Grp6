package action;

import couleur.Couleur;
import map.abstr_Case;
import robot.Orientation;
import robot.Robot;

public class TurnLeft implements int_Action{
	
	private Couleur color;
	public static TurnLeft turn_left(){
		return new TurnLeft();
	}
	public static TurnLeft turn_left(Couleur col){
		return new TurnLeft(col);
	}
	private TurnLeft(){
		this.color = Couleur.GRIS;
	}
	private TurnLeft(Couleur col){
		this.color = col;
	}
	
	public void execute(Robot r) {
		switch (r.getOrientation()) {  
    		case  TOP :
    			r.setOrientation(Orientation.orientation.LEFT);
    			break;
    		case  BOT :
    			r.setOrientation(Orientation.orientation.RIGHT);
    			break;
    		case  LEFT :
    			r.setOrientation(Orientation.orientation.BOT);
    			break;
    		case  RIGHT :
    			r.setOrientation(Orientation.orientation.TOP);
    			break;
		}
	}


	public boolean isPossible(Robot r, abstr_Case c) {
		return (color == Couleur.GRIS || r.get_couleur() == color);
	}

}
