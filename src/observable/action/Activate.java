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
	public void execute(abstr_Robot r) throws MouvementEx, UnreachableCase {
	abstr_Case cprime = r.getCurrent_Case();
	if (isPossible(r,cprime)){
		if(cprime.getClass().getSimpleName().equals("Teleporter_Case")){
			r.setCurrent_Case(World.currentWorld.get_case(((Teleporter_Case)cprime).get_destination()));
			notifyObserver();
		}
		else if(cprime.getClass().getSimpleName().equals("Illiminated_Case")){
			((Illuminated_Case)cprime).set_active(!((Illuminated_Case)cprime).get_active());
			if (((Illuminated_Case)cprime).get_active()){
				World.currentWorld.increment_allume();
			}
			else {
				World.currentWorld.decrement_allume();
			}
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
		return (((c.getClass().getSimpleName().equals("Teleporter_Case")
					|| c.getClass().getSimpleName().equals("Painted_Case"))
					|| c.getClass().getSimpleName().equals("Illiminated_Case"))
				&&(color.equals(r.get_couleur())||
					color.equals(Couleur.GRIS))) ;
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

