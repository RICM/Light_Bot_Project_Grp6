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
		if(Run_List.size()>0){
			if(Run_List.getFirst().size() > 0){
				int_Action temp = Run_List.getFirst().removeFirst();
				temp.execute(r);
			}
			else{
				Run_List.removeFirst();
				this.run(r);
			}
		}
		else{
			throw new ActionEx("Liste robot vide");
		}
	}
}
