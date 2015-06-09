package observable.action;
import java.util.ArrayList;

import couleur.Couleur;
import observable.map.*;
import observable.robot.Robot;
import observable.robot.abstr_Robot;
import exception.MouvementEx;
import observable.int_Observable;
import observer.int_Observer;

public class Activate implements int_Action, int_Observable{
	
	private ArrayList<int_Observer> listObserver = new ArrayList<int_Observer>(); 
	
	private Couleur color;
	public static Activate activate(){
		return new Activate();
	}
	public static Activate activate(Couleur color){
		return new Activate(color);
	}
	private Activate(){
		this.color = Couleur.GRIS;
	}
	private Activate(Couleur col){
		this.color = col;
	}
	@Override
	public void execute(abstr_Robot r) throws MouvementEx {
	abstr_Case cprime = r.getCurrent_Case();
	if (isPossible(r,cprime)){
		if(cprime.getClass().getCanonicalName().equals("map.Teleporter_Case")){
			r.setCurrent_Case(((Teleporter_Case)cprime).get_destination());
			notifyObserver();
		}
		else {
			r.set_couleur(cprime.get_couleur());
			notifyObserver();
		}
	}
	else{
		throw (new MouvementEx("impossible à utiliser"));
		}
	}

	@Override
	public boolean isPossible(abstr_Robot r, abstr_Case c) {
		return (((c.getClass().getCanonicalName().equals("observable.map.Teleporter_Case")&&
					(color.equals(r.get_couleur())||
						color.equals(Couleur.GRIS))) 
				|| c.getClass().getCanonicalName().equals("observable.map.Painted_Case")));
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

