package action;

import map.abstr_Case;
import robot.Robot;

public class Jump implements int_Action{
	
	public static Jump jump(){
		return new Jump();
	}
	
	private Jump(){
		
	}

	public void execute(Robot r) {
		// TODO Auto-generated method stub
		
	}

	public boolean isPossible(Robot r, abstr_Case c) {
		// TODO Auto-generated method stub
		return false;
	}

}
