package observable.action_list;

import java.util.ArrayList;
import java.util.LinkedList;

import observable.int_Observable;
import observable.action.int_Action;
import observer.int_Observer;

public class Sequence_List implements int_Action_List, int_Observable{

	private LinkedList<int_Action> actions_list_seq = new LinkedList<int_Action>();

	private ArrayList<int_Observer> listObserver = new ArrayList<int_Observer>();

	@Override
	public void addActionToList(int_Action act){
		this.actions_list_seq.add(act);
		this.notifyObserver();
	}

	public void addActionSubSequence(int_Action act){
		this.actions_list_seq.add(act);
	}

	public void removeActionFromList(int_Action act){
		this.actions_list_seq.remove(act);
		this.notifyObserver();
	}

	public LinkedList<int_Action> getListActions(){
		return this.actions_list_seq;
	}

	public void removeIndice(int i){
		this.actions_list_seq.remove(i);
	}

	public int_Action removeFirst(){
		this.notifyObserver();
		return this.actions_list_seq.removeFirst();
	}

	/*	public void execute (Robot r) throws MouvementEx,UnreachableCase{
		int size = actions_list_seq.size();
		for (int i = 0 ; i < size ; i++ ){
			int_Action act;
			act = actions_list_seq.get(i);
			act.execute(r);
		}
	}*/
	public int size(){
		return this.actions_list_seq.size();
	}

	@Override
	public boolean isPresent(int_Action act) {
		return this.actions_list_seq.contains(act);
	}

	public int_Action get(int i){
		return this.actions_list_seq.get(i);
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
		//System.out.println(this.listObserver.toString());
		for(int_Observer obs : this.listObserver)
			obs.update(this);
	}

	public Sequence_List(int_Observer acontroller){
		this.listObserver.add(acontroller);
	}


	@Override
	public String toString(){
		String str = new String();
		for (int i = 0; i<this.actions_list_seq.size(); i++){
			str = str+" , "+this.actions_list_seq.get(i).getClass().getSimpleName();
		}
		return str;
	}

}
