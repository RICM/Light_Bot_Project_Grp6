package action;

import exception.MouvementEx;
import map.abstr_Case;
import robot.*;

public interface int_Action {
	
	//
	public static void execute (Robot r) throws MouvementEx{};
	
	//
	public static boolean isPossible(Robot r,abstr_Case c){
		return false;
	};
	
}
