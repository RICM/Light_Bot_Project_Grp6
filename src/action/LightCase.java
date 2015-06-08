package action;

import couleur.Couleur;
import exception.MouvementEx;
import map.*;
import robot.Robot;

public class LightCase implements int_Action{
	
	public static LightCase light_case(){
		return new LightCase();
	}
	
	private LightCase(){
		
	}

	public void execute(Robot r) throws MouvementEx {
		Illuminated_Case Case;
		if(isPossible(r,r.getCurrent_Case())){
				Case = (Illuminated_Case)r.getCurrent_Case();
				Case.set_active(!Case.get_active());
		}
		else{
			throw (new MouvementEx("impossible d'allumer cette case"));
		}
		
	}

	public boolean isPossible(Robot r, abstr_Case c) {
		return ((c.getClass().getCanonicalName() == "map.Illuminated_Case") && (c.get_couleur() ==Couleur.GRIS || c.get_couleur()==r.get_couleur()));
	}

}
