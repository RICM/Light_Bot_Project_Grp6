package observable.action;

import java.util.ArrayList;

import couleur.Couleur;
import exception.ActionEx;
import exception.MouvementEx;
import exception.UnreachableCase;
import observable.int_Observable;
import observable.action_list.Sequence_List;
import observable.map.abstr_Case;
import observable.robot.abstr_Robot;
import observer.int_Observer;

public class Call_P2 implements int_Action, int_Observable{
	
	private Couleur color;
	
	private ArrayList<int_Observer> listObserver = new ArrayList<int_Observer>(); 
	
	public static Call_P2 call_p2(){
		return new Call_P2();
	}
	private Call_P2(){
		this.color = Couleur.GRIS;
	}
	
	public static Call_P2 call_p2(Couleur col){
		return new Call_P2(col);
	}
	private Call_P2(Couleur col){
		this.color = col;
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

	@Override
	public void execute(abstr_Robot r) throws MouvementEx, UnreachableCase, ActionEx {
		if (isPossible(r,r.getCurrent_Case())){
			Sequence_List temp = new Sequence_List();
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
