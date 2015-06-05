package action;

import map.abstr_Case;
import robot.Orientation;
import robot.Robot;

public class TurnLeft implements int_Action{

	public static void execute(Robot r) {
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


	public static boolean isPossible(Robot r, abstr_Case c) {
		return true;
	}

}