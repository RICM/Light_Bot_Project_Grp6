package observable.action;

import observable.couleur.Couleur;
import observable.map.abstr_Case;
import observable.robot.*;
import exception.MouvementEx;
import exception.UnreachableCase;

public interface int_Action {
	
	//
	public void execute (Robot r) throws MouvementEx, UnreachableCase;
	
	//
	public boolean isPossible(Robot r,abstr_Case c);
	
}
