package observable.action;
import java.util.ArrayList;

import couleur.Couleur;
import observable.map.*;
import observable.robot.*;
import exception.MouvementEx;
import exception.UnreachableCase;
import observable.int_Observable;
import observer.int_Observer;



public class MoveForward implements int_Action, int_Observable{
	
	private ArrayList<int_Observer> listObserver = new ArrayList<int_Observer>(); 
	
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
	
	public void execute(abstr_Robot r) throws MouvementEx, UnreachableCase{
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
			notifyObserver();
		}
		
	}
	
	public boolean isPossible(abstr_Robot r, abstr_Case c){
		return ((color.equals(Couleur.GRIS) || color.equals(r.get_couleur())) 
				&& (r.getCurrent_Case().get_hauteur() == c.get_hauteur()));
	}
	
	@Override
	public void addObserver(int_Observer obs) {
		this.listObserver.add(obs);
	}
	@Override
	public void removeObserver() {
		listObserver = new ArrayList<int_Observer>();
		
	}
	@Override
	public void notifyObserver() {
		for(int_Observer obs : listObserver)
		      obs.update(this);
	}
}
