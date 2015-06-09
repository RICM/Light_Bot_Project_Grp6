package observable.action;

import java.util.ArrayList;

import couleur.Couleur;
import observable.map.*;
import observable.robot.Robot;
import observable.robot.abstr_Robot;
import observer.int_Observer;
import exception.MouvementEx;
import observable.int_Observable;

public class LightCase implements int_Action, int_Observable{
	
	private ArrayList<int_Observer> listObserver = new ArrayList<int_Observer>();
	
	Couleur color;
	public static LightCase light_case(){
		return new LightCase();
	}
	public static LightCase light_case(Couleur col){
		return new LightCase(col);
	}
	private LightCase(){
		this.color = Couleur.GRIS;
	}
	private LightCase(Couleur col){
		this.color = col;
	}
	
	
	public void execute(abstr_Robot r) throws MouvementEx {
		Illuminated_Case Case;
		if(isPossible(r,r.getCurrent_Case())){
				Case = (Illuminated_Case)r.getCurrent_Case();
				Case.set_active(!Case.get_active());
				notifyObserver();
		}
		else{
			throw (new MouvementEx("impossible d'allumer cette case"));
		}
		
	}

	public boolean isPossible(abstr_Robot r, abstr_Case c) {
		return ((c.getClass().getCanonicalName() == "observable.map.Illuminated_Case") 
				&& (color.equals(Couleur.GRIS) || color.equals(r.get_couleur())));
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
