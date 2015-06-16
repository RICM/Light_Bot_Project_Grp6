package observable.action_list;

import java.util.ArrayList;
import java.util.LinkedList;

import observable.int_Observable;
import observable.action.int_Action;
import observable.robot.abstr_Robot;
import observer.int_Observer;
import exception.ActionEx;
import exception.MouvementEx;
import exception.UnreachableCase;

public class Execution_list implements int_Observable{

	private LinkedList<Sequence_List> Run_List = new LinkedList<Sequence_List>();

	private ArrayList<int_Observer> listObserver = new ArrayList<int_Observer>();

	public void add(Sequence_List next){
		this.Run_List.add(next);
	}
	public void addFirst(Sequence_List new_h){
		this.Run_List.addFirst(new_h);
	}
	public void initFirst(Sequence_List main){
		Sequence_List temp = new Sequence_List(null);
		for(int i = 0; i < main.size(); i++){
			temp.addActionToList(main.get(i));
		}
		this.addFirst(temp);
	}

	public void removeFirst(){
		this.Run_List.removeFirst();
	}

	public void run(abstr_Robot r) throws MouvementEx, UnreachableCase, ActionEx{
		if(this.Run_List.size()>0){
			System.out.println("taille run list : "+this.Run_List.size());
			if(this.Run_List.getFirst().size() > 0){
				int_Action temp = this.Run_List.getFirst().removeFirst();
				temp.execute(r);
			}
			else{
				this.Run_List.removeFirst();
				this.run(r);
			}
		}
		else{
			System.out.println("J'AI FINIS TA RACE");
			this.notifyObserver();
			//throw new ActionEx("Liste robot vide");
		}
	}

	@Override
	public String toString(){
		String str = new String();
		for (int i = 0; i < this.Run_List.size(); i++){
			str = str+" | "+this.Run_List.get(i).toString();
		}
		return str;
	}
	public void clear() {
		for(int i = 0; i< this.Run_List.size(); i++){
			this.Run_List.removeFirst();
		}
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

	public int size(){
		return this.Run_List.size();
	}
}
