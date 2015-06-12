package observable.action_list;

import java.util.ArrayList;
import java.util.LinkedList;

import observable.int_Observable;
import observable.action.int_Action;
import observer.int_Observer;

public class Possible_List implements int_Action_List, int_Observable{

	private LinkedList<int_Action> allowed_actions = new LinkedList<int_Action>();

	private ArrayList<int_Observer> listObserver = new ArrayList<int_Observer>();

	@Override
	public void addActionToList(int_Action act){
		boolean present= false;
		for (int i = 0; i<this.allowed_actions.size();i++){
			if (this.allowed_actions.get(i).getClass().getSimpleName().equals(act.getClass().getSimpleName())){
				present = true;
			}
		}
		if (!present){
			this.allowed_actions.add(act);
			this.notifyObserver();
		}
	}

	public void removeActionFromList(int_Action act){
		this.allowed_actions.remove(act);
		this.notifyObserver();
	}

	@Override
	public boolean isPresent(int_Action act){
		boolean present= false;
		for (int i = 0; i<this.allowed_actions.size();i++){
			if (this.allowed_actions.get(i).getClass().getSimpleName().equals(act.getClass().getSimpleName())){
				present = true;
			}
		}
		return present;
	}

	/*public void execute (Robot r) throws MouvementEx, UnreachableCase, ActionEx{
		int size = allowed_actions.size();
		for (int i = 0 ; i < size ; i++ ){
			int_Action act;
			act = allowed_actions.get(i);
			act.execute(r);
		}
	}*/

	public int_Action pickAction (int index){
		int_Action ax = this.allowed_actions.get(index);
		this.allowed_actions.remove(index);
		this.notifyObserver();
		return ax;
	}

	@Override
	public String toString(){
		return this.allowed_actions.toString();
	}
	public int size(){
		return this.allowed_actions.size();
	}
	public String get_name(int i){
		return this.allowed_actions.get(i).getClass().getSimpleName();
	}

	public LinkedList<int_Action> get(){
		return this.allowed_actions;
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
}
