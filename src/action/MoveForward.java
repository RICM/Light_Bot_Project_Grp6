package action;
import couleur.Couleur;
import map.*;
import robot.*;
import exception.MouvementEx;
import exception.UnreachableCase;



public class MoveForward implements int_Action{
	
	private Couleur color;
	public static MoveForward move_forward(){
		return new MoveForward();
	}
	public static MoveForward move_forward(Couleur col){
		return new MoveForward(col);
	}
	private MoveForward(){
		this.color = Couleur.GRIS;
	}
	private MoveForward(Couleur col){
		this.color = col;
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
	    		System.out.println("prochaine pos : "+i+" , "+j);
	    		break;
	    	case  BOT :
	    		i = pos.get_x();
	    		j = pos.get_y()+1;
	    		System.out.println("prochaine pos : "+i+" , "+j);
	    		break;
	    	case  LEFT :
	    		i = pos.get_x()-1;
	    		j = pos.get_y();	    		
	    		System.out.println("prochaine pos : "+i+" , "+j);
	    		break;
	    	case  RIGHT :
	    		i = pos.get_x()+1;
	    		j = pos.get_y();
	    		System.out.println("prochaine pos : "+i+" , "+j);
	    		break;
	    	default :
	    		i = pos.get_x();
	    		j = pos.get_y();
	    		break;
		}
		c_prime = World.currentWorld.get_terrain(pos.get_n()).get_case(i,j);
		System.out.println("couleur de l'action : "+color.toString());
		if (! isPossible(r,c_prime)){
			//System.out.println("impossible d'avancer bitch");
			throw (new MouvementEx("impossible d'avancer"));
		} else {
			r.setCurrent_Case(c_prime);
			World.currentWorld.basic_print_world();
		}
		
	}
	
	public boolean isPossible(Robot r, abstr_Case c){
		return ((color.equals(Couleur.GRIS) || color.equals(r.get_couleur())) 
				&& (r.getCurrent_Case().get_hauteur() == c.get_hauteur()));
	}
}
