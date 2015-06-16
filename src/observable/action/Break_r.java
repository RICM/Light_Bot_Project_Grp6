package observable.action;

import java.util.ArrayList;

import observable.map.abstr_Case;
import observable.robot.abstr_Robot;
import observer.int_Observer;
import couleur.Couleur;
import exception.ActionEx;
import exception.MouvementEx;
import exception.UnreachableCase;

public class Break_r implements int_Action {

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

	public static Break_r break_r(){
		return new Break_r();
	}

	public static Break_r break_r(Couleur col){
		return new Break_r(col);
	}

	private Break_r(){
		this.setColor(Couleur.GRIS);
	}

	private Break_r(Couleur col){
		this.setColor(col);
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
		if (this.isPossible(r,null)){
			r.removeFirstRunable();
		}

	}

	@Override
	public boolean isPossible(abstr_Robot r, abstr_Case c) {
		return this.getColor().equals(r.get_couleur()) || this.getColor().equals(Couleur.GRIS);
	}


}
