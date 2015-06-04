package action;

import exception.MouvementEx;
import map.abstr_Case;
import robot.*;

public interface int_Action {
	
	//
	public void execute (Robot r) throws MouvementEx;
	
	//
	public boolean isPossible(Robot r,abstr_Case c);
	
}
