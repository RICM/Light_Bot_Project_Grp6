package action;

import map.abstr_Case;
import robot.Orientation;
import robot.Robot;

public class TurnRIght implements int_Action{

	public static TurnRIght turn_right(){
		return new TurnRIght();
	}
	
	private TurnRIght(){
		
	}
	
	public void execute(Robot r) {
		switch (r.getOrientation()) {  
    		case  TOP :
    			r.setOrientation(Orientation.orientation.RIGHT);
    			break;
    		case  BOT :
    			r.setOrientation(Orientation.orientation.LEFT);
    			break;
    		case  LEFT :
    			r.setOrientation(Orientation.orientation.TOP);
    			break;
    		case  RIGHT :
    			r.setOrientation(Orientation.orientation.BOT);
    			break;
		}
	}

	public boolean isPossible(Robot r, abstr_Case c) {
		return true;
	}

}
