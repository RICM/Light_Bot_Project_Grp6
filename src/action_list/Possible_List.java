package action_list;

import java.util.LinkedList;

import robot.Robot;
import action.int_Action;
import exception.MouvementEx;

public class Possible_List implements int_Action_List{
	
	private LinkedList<int_Action> allowed_actions = new LinkedList<int_Action>();
	
	public void addActionToList(int_Action act){
		allowed_actions.add(act);
	}
	
	public void removeActionFromList(int_Action act){
		allowed_actions.remove(act);
	}
	
	public boolean isPresent(int_Action act){
		return this.allowed_actions.contains(act);
	}
	
	public void execute (Robot r) throws MouvementEx{
		int size = allowed_actions.size();
		for (int i = 0 ; i < size ; i++ ){
			int_Action act;
			act = allowed_actions.get(i);
			act.execute(r);
		}
	}

}
