package observable.action;

import java.util.ArrayList;

import couleur.Couleur;
import exception.ActionEx;
import exception.MouvementEx;
import exception.UnreachableCase;
import observable.int_Observable;
import observable.action_list.Sequence_List;
import observable.map.World;
import observable.map.abstr_Case;
import observable.robot.abstr_Robot;
import observer.int_Observer;

public class Call_P1 implements int_Action, int_Observable{

	private Couleur color;

	@Override
	public Couleur getColor() {
		return this.color;
	}
	@Override
	public void setColor(Couleur color) {
		this.color = color;
	}

	private ArrayList<int_Observer> listObserver = new ArrayList<int_Observer>();

	public static Call_P1 call_p1(int_Observer controller){
		return new Call_P1(controller);
	}
	private Call_P1(int_Observer controller){
		this.color = Couleur.GRIS;
		this.listObserver.add(controller);
	}

	public static Call_P1 call_p1(Couleur col, int_Observer controller){
		return new Call_P1(col, controller);
	}
	private Call_P1(Couleur col, int_Observer controller){
		this.color = col;
		this.listObserver.add(controller);
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
	/**
	 * ajoute la procédure P1 a la liste d'execution du robot r
	 */
	public void execute(abstr_Robot r) throws MouvementEx, UnreachableCase, ActionEx {
		if (this.isPossible(r,r.getCurrent_Case())){
			Sequence_List temp = new Sequence_List(this.listObserver.get(0));
			for (int i =0; i<r.get_P1().size(); i++){
				temp.addActionToList(r.get_P1().get(i));
			}
			r.add_execute(temp);
			World.currentWorld.get_ordonnanceur().setReady(true);
		}
		else{
			throw new ActionEx("impossible d'executer ");
		}

	}

	@Override
	public boolean isPossible(abstr_Robot r, abstr_Case c) {
		return (r.get_couleur().equals(this.color) || r.get_couleur().equals(Couleur.GRIS));
	}

}
