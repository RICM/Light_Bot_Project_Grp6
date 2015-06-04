package action;
import robot;



public class MoveForward implements int_Action{
	
	public void execute(Robot r){
		abstr_Case c_prime;
		Coordonnees pos = r.getCurrent_case().getCoordonnes();
		switch (r.getOrientation()) {  
	      
        case  TOP : c_prime = Partie.PARTIE.get_world().liste_terrain[pos.getn()].getCase(pos.getx(),pos.gety()-1);
        case  BOT : c_prime = Partie.PARTIE.get_world().liste_terrain[pos.getn()].getCase(pos.getx(),pos.gety()+1);;
        case  LEFT : c_prime = Partie.PARTIE.get_world().liste_terrain[pos.getn()].getCase(pos.getx()-1,pos.gety());
        case  RIGHT : c_prime = Partie.PARTIE.get_world().liste_terrain[pos.getn()].getCase(pos.getx()+1,pos.gety());
        default :  break;
		if (! is_possible(r,c_prime)){
			throw (new Exception("impossible d'avancer"));
		} else {
			r.setCurrent_Case(c_prime);
		}
		
	}
	
	public boolean isPossible(Robot r, abstr_Case c){
		return (r.getCurrent_Case().get_hauteur() == c.get_hauteur);

	}
}
