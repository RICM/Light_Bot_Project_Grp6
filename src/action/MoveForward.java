package action;
import couleur.Couleur;
import map.*;
import robot.*;
import exception.MouvementEx;
import exception.UnreachableCase;



public class MoveForward implements int_Action{
	
	public static MoveForward move_forward(){
		return new MoveForward();
	}
	
	private MoveForward(){
		
	}
	
	public void execute(Robot r) throws MouvementEx, UnreachableCase{
		abstr_Case c_prime = null;
		int i,j;
		Coordonnees pos = r.getCurrent_Case().get_coordonnees();
		//System.out.println("Robert est en : "+pos.get_x()+" , "+pos.get_y()+" tourné vers : "+r.getOrientation().toString());
		switch (r.getOrientation()) {  
	    	case  TOP :
	    		i = pos.get_x();
	    		j = pos.get_y()-1;
	    		//System.out.println("prochaine pos : "+i+" , "+j);
	    		c_prime = World.currentWorld.get_terrain(pos.get_n()).get_case(pos.get_x(),pos.get_y()-1);
	    		break;
	    	case  BOT :
	    		i = pos.get_x();
	    		j = pos.get_y()+1;
	    		//System.out.println("prochaine pos : "+i+" , "+j);
	    		c_prime = World.currentWorld.get_terrain(pos.get_n()).get_case(pos.get_x(),pos.get_y()+1);
	    		break;
	    	case  LEFT :
	    		i = pos.get_x()-1;
	    		j = pos.get_y();	    		
	    		//System.out.println("prochaine pos : "+i+" , "+j);
	    		c_prime = World.currentWorld.get_terrain(pos.get_n()).get_case(pos.get_x()-1,pos.get_y());
	    		break;
	    	case  RIGHT :
	    		i = pos.get_x()+1;
	    		j = pos.get_y();
	    		//System.out.println("prochaine pos : "+i+" , "+j);
	    		c_prime = World.currentWorld.get_terrain(pos.get_n()).get_case(pos.get_x()+1,pos.get_y());
	    		break;
		}
		
		if (! isPossible(r,c_prime)){
			//System.out.println("impossible d'avancer bitch");
			throw (new MouvementEx("impossible d'avancer"));
		} else {
			r.setCurrent_Case(c_prime);
			World.currentWorld.basic_print_world();
		}
		
	}
	
	public boolean isPossible(Robot r, abstr_Case c){
		return ((c.get_couleur() == Couleur.GRIS || c.get_couleur() == r.get_couleur()) && (r.getCurrent_Case().get_hauteur() == c.get_hauteur()));
	}
}
