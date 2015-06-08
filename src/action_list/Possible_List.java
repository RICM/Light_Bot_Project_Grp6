package action_list;

import java.util.LinkedList;
import exception.UnreachableCase;

import robot.Robot;
import action.int_Action;
import exception.MouvementEx;

public class Possible_List implements int_Action_List{
	
	private LinkedList<int_Action> allowed_actions = new LinkedList<int_Action>();
	
	public void addActionToList(int_Action act){
		//System.out.println("trying to add : "+act.getClass().getCanonicalName());
		allowed_actions.add(act);
		if (allowed_actions.contains(act)){
			//System.out.println("added !");
			//System.out.println(toString());
		}
	}
	
	public void removeActionFromList(int_Action act){
		allowed_actions.remove(act);
	}
	
	public boolean isPresent(int_Action act){
		return this.allowed_actions.contains(act);
	}
	
	public void execute (Robot r) throws MouvementEx, UnreachableCase{
		int size = allowed_actions.size();
		for (int i = 0 ; i < size ; i++ ){
			int_Action act;
			act = allowed_actions.get(i);
			act.execute(r);
		}
	}
	
	public int_Action pickAction (int index){
		int_Action ax = allowed_actions.get(index);
		allowed_actions.remove(index);
		return ax;
	}
	
	public String toString(){
		return allowed_actions.toString();
	}
	public int size(){
		return allowed_actions.size();
	}
	public String get_name(int i){
		return allowed_actions.get(i).getClass().getCanonicalName();
	}
	
	public LinkedList<int_Action> get(){
		return this.allowed_actions;
	}
}
