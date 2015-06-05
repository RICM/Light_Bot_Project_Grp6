package action;

import exception.MouvementEx;
import map.*;
import robot.Robot;

public class LightCase implements int_Action{

	public static void execute(Robot r) throws MouvementEx {
		Illuminated_Case Case;
		if(isPossible(r,r.getCurrent_Case())){
				Case = (Illuminated_Case)r.getCurrent_Case();
				Case.set_active(!Case.get_active());
		}
		else{
			throw (new MouvementEx("impossible d'allumer cette case"));
		}
		
	}

	public static boolean isPossible(Robot r, abstr_Case c) {
		if(c instanceof Illuminated_Case){
			return true;
		}
		else{
			return false;
		}
	}

}
