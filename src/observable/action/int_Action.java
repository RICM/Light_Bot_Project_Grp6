package observable.action;

import couleur.Couleur;
import observable.int_Observable;
import observable.map.abstr_Case;
import observable.robot.*;
import exception.MouvementEx;
import exception.UnreachableCase;

public interface int_Action extends int_Observable {
	
	//
	public void execute (Robot r) throws MouvementEx, UnreachableCase;
	
	//
	public boolean isPossible(Robot r,abstr_Case c);
	
	
}
