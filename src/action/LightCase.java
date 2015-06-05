package action;

import map.abstr_Case;
import robot.Robot;

public class LightCase implements int_Action{
	
	public static LightCase light_case(){
		return new LightCase();
	}
	
	private LightCase(){
		
	}

	public void execute(Robot r) {
		// TODO Auto-generated method stub
		
	}

	public boolean isPossible(Robot r, abstr_Case c) {
		// TODO Auto-generated method stub
		return false;
	}


}
