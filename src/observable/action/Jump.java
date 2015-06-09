package observable.action;
import java.util.ArrayList;

import couleur.Couleur;
import observable.map.*;
import observable.robot.Robot;
import observable.robot.abstr_Robot;
import exception.MouvementEx;
import exception.UnreachableCase;
import observable.int_Observable;
import observer.int_Observer;

public class Jump implements int_Action, int_Observable{
	
	private Couleur color;

	private ArrayList<int_Observer> listObserver = new ArrayList<int_Observer>(); 
	
	public static Jump jump(){
		return new Jump();
	}
	public static Jump jump(Couleur col){
		return new Jump(col);
	}
	private Jump(Couleur col){
		this.color = col;
	}
	private Jump(){
		this.color = Couleur.GRIS;
	}

	public void execute(abstr_Robot r) throws MouvementEx,UnreachableCase {
		
		abstr_Case c_prime = null;
		Coordonnees pos = r.getCurrent_Case().get_coordonnees();
		switch (r.getOrientation()) {  
	    	case  TOP :
	    		c_prime = World.currentWorld.get_terrain(pos.get_n()).get_case(pos.get_x(),pos.get_y()-1);
	    		break;
	    	case  BOT :
	    		c_prime = World.currentWorld.get_terrain(pos.get_n()).get_case(pos.get_x(),pos.get_y()+1);;
	    		break;
	    	case  LEFT :
	    		c_prime = World.currentWorld.get_terrain(pos.get_n()).get_case(pos.get_x()-1,pos.get_y());
	    		break;
	    	case  RIGHT :
	    		c_prime = World.currentWorld.get_terrain(pos.get_n()).get_case(pos.get_x()+1,pos.get_y());
	    		break;
		}
		
		if (! isPossible(r,c_prime)){
			throw (new MouvementEx("impossible de sauter"));//saut sur place
		} else {
			r.setCurrent_Case(c_prime);
			notifyObserver();
		}
		
	}
	// true == on peut sauter et avancer
	//false == on saute sur place
	public boolean isPossible(abstr_Robot r, abstr_Case c) {
		return ((r.get_couleur().equals(color) || Couleur.GRIS.equals(color)) 
				&& (r.getCurrent_Case().get_hauteur()+1 == c.get_hauteur()) 
					|| (c.get_hauteur() < r.getCurrent_Case().get_hauteur()));
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
