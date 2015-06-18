package observable.action;

import java.util.ArrayList;

import couleur.Couleur;
import exception.ActionEx;
import exception.MouvementEx;
import exception.UnreachableCase;
import observable.map.abstr_Case;
import observable.robot.abstr_Robot;
import observer.int_Observer;

public class Remember implements int_Action {

	private ArrayList<int_Observer> listObserver = new ArrayList<int_Observer>();

	private Couleur color;
	@Override
	public Couleur getColor() {
		return this.color;
	}
	@Override
	public void setColor(Couleur color) {
		this.color = color;
	}
	public static Remember remember(){
		return new Remember();
	}
	public static Remember remember(Couleur col){
		return new Remember(col);
	}
	private Remember(){
		this.color = Couleur.GRIS;
	}
	private Remember(Couleur col){
		this.color = col;
	}

	@Override
	/**
	 * ajoute la position actuelle dans la memoire de position du robot
	 */
	public void execute(abstr_Robot r) throws MouvementEx, UnreachableCase,
	ActionEx {
		if(this.isPossible(r, null)){
			r.store_position();
			r.setVoid();
		}

	}

	@Override
	public boolean isPossible(abstr_Robot r, abstr_Case c) {
		return (r.get_couleur().equals(this.color) || this.color.equals(Couleur.GRIS));
	}


	@Override
	public void addObserver(int_Observer obs) {
		this.listObserver.add(obs);
	}
	@Override
	public void removeObserver() {
		this.listObserver = new ArrayList<int_Observer>();

	}
	@Override
	public void notifyObserver() {
		for(int_Observer obs : this.listObserver)
			obs.update(this);
	}
	@Override
	public int_Action Clone() {
		int_Action temp = new Remember(this.getColor());
		return temp;
	}
}
