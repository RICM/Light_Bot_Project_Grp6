package action;
import map.*;
import robot.*;



public class MoveForward implements int_Action{
	
	public void execute(Robot r){
		abstr_Case c_prime;
		Coordonnees pos = r.getCurrent_Case().get_coordonnees();
		switch (r.getOrientation()) {  
	      
        case  TOP : c_prime = Partie.PARTIE.get_world().liste_terrain[pos.get_n()].getCase(pos.get_x(),pos.get_y()-1);
        case  BOT : c_prime = Partie.PARTIE.get_world().liste_terrain[pos.get_n()].getCase(pos.get_x(),pos.get_y()+1);;
        case  LEFT : c_prime = Partie.PARTIE.get_world().liste_terrain[pos.get_n()].getCase(pos.get_x()-1,pos.get_y());
        case  RIGHT : c_prime = Partie.PARTIE.get_world().liste_terrain[pos.get_n()].getCase(pos.get_x()+1,pos.get_y());
        default :  break;
		if (! this.isPossible(r,c_prime)){
			throw (new Exception("impossible d'avancer"));
		} else {
			r.setCurrent_Case(c_prime);
		}}
		
	}
	
	public boolean isPossible(Robot r, abstr_Case c){
		return (r.getCurrent_Case().get_hauteur() == c.get_hauteur());

	}
}
