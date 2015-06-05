package action_list;

import java.util.LinkedList;

import robot.Robot;
import action.int_Action;
import exception.MouvementEx;

public class Sequence_List implements int_Action_List {

private LinkedList<int_Action> actions_list_seq = new LinkedList<int_Action>();
	
	public void addActionToList(int_Action act){
		actions_list_seq.add(act);
	}
	
	public void removeActionFromList(int_Action act){
		actions_list_seq.remove(act);
	}
	
	public void execute (Robot r) throws MouvementEx{
		int size = actions_list_seq.size();
		for (int i = 0 ; i < size ; i++ ){
			int_Action act;
			act = actions_list_seq.get(i);
			act.execute(r);
		}
	}
	
}
