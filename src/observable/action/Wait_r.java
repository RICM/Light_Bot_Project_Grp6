package observable.action;

import java.util.ArrayList;

import observable.map.abstr_Case;
import observable.robot.abstr_Robot;
import observer.int_Observer;
import couleur.Couleur;
import exception.ActionEx;
import exception.MouvementEx;
import exception.UnreachableCase;

public class Wait_r implements int_Action {


	private ArrayList<int_Observer> listObserver = new ArrayList<int_Observer>();

	private Couleur color;
	private Wait_r(Couleur col) {
		this.color = col;
	}

	private Wait_r(){
		this.color = Couleur.GRIS;
	}

	public  static Wait_r wait_r(){
		return new Wait_r();
	}

	public  static Wait_r wait_r(Couleur col){
		return new Wait_r(col);
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
	public void execute(abstr_Robot r) throws MouvementEx, UnreachableCase,
	ActionEx {
		if(this.isPossible(r,null))
			r.set_activable(false);

	}

	@Override
	public boolean isPossible(abstr_Robot r, abstr_Case c) {
		return (r.get_couleur().equals(this.color) || this.color.equals(Couleur.GRIS));
	}

	@Override
	public Couleur getColor() {
		return this.color;
	}
	@Override
	public void setColor(Couleur color) {
		this.color = color;
	}


}
