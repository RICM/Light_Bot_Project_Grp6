package observable.action;

import java.util.ArrayList;

import observable.int_Observable;
import observable.action_list.Sequence_List;
import observable.map.abstr_Case;
import observable.robot.abstr_Robot;
import observer.int_Observer;
import couleur.Couleur;
import exception.ActionEx;
import exception.MouvementEx;
import exception.UnreachableCase;

public class Call_P2 implements int_Action, int_Observable{

	private Couleur color;

	private ArrayList<int_Observer> listObserver = new ArrayList<int_Observer>();

	public static Call_P2 call_p2(int_Observer controller){
		return new Call_P2(controller);
	}
	private Call_P2(int_Observer controller){
		this.listObserver.add(controller);
		this.color = Couleur.GRIS;
	}

	public static Call_P2 call_p2(Couleur col, int_Observer controller){
		return new Call_P2(col, controller);
	}
	private Call_P2(Couleur col, int_Observer controller){
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
	 * ajoute la procédure P2 a la liste d'execution du robot r
	 */
	public void execute(abstr_Robot r) throws MouvementEx, UnreachableCase, ActionEx {
		if (this.isPossible(r,r.getCurrent_Case())){
			Sequence_List temp = new Sequence_List(this.listObserver.get(0));
			for (int i =0; i<r.get_P2().size(); i++){
				temp.addActionToList(r.get_P2().get(i));
			}
			r.add_execute(temp);
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
