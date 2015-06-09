package observable.action_list;

import java.util.LinkedList;

import exception.ActionEx;
import exception.MouvementEx;
import exception.UnreachableCase;
import observable.robot.abstr_Robot;
import observable.action.int_Action;

public class Execution_list {

	private LinkedList<Sequence_List> Run_List = new LinkedList<Sequence_List>();
	
	public void add(Sequence_List next){
		Run_List.add(next);
	}
	public void addFirst(Sequence_List new_h){
		Run_List.addFirst(new_h);
	}

	public void run(abstr_Robot r) throws MouvementEx, UnreachableCase, ActionEx{
		while(Run_List.size()>0){
			while(Run_List.getFirst().size() > 0){
				Run_List.getFirst().removeFirst().execute(r);
			}
			Run_List.removeFirst();
		}
	}
}