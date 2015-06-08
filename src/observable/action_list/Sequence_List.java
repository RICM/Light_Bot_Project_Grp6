package observable.action_list;

import java.util.LinkedList;

import observable.action.int_Action;
import observable.robot.Robot;
import exception.UnreachableCase;
import exception.MouvementEx;

public class Sequence_List implements int_Action_List {

private LinkedList<int_Action> actions_list_seq = new LinkedList<int_Action>();
	
	public void addActionToList(int_Action act){
		actions_list_seq.add(act);
	}
	
	public void removeActionFromList(int_Action act){
		actions_list_seq.remove(act);
	}
	
	public void execute (Robot r) throws MouvementEx,UnreachableCase{
		int size = actions_list_seq.size();
		for (int i = 0 ; i < size ; i++ ){
			int_Action act;
			act = actions_list_seq.get(i);
			act.execute(r);
		}
	}

	@Override
	public boolean isPresent(int_Action act) {
		return this.actions_list_seq.contains(act);
	}
	
}
