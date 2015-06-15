package observable.action_list;

import java.util.LinkedList;

import observable.action.int_Action;
import observable.robot.abstr_Robot;
import exception.ActionEx;
import exception.MouvementEx;
import exception.UnreachableCase;

public class Execution_list {

	private LinkedList<Sequence_List> Run_List = new LinkedList<Sequence_List>();

	public void add(Sequence_List next){
		this.Run_List.add(next);
	}
	public void addFirst(Sequence_List new_h){
		this.Run_List.addFirst(new_h);
	}

	public void run(abstr_Robot r) throws MouvementEx, UnreachableCase, ActionEx{
		if(this.Run_List.size()>0){
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
			throw new ActionEx("Liste robot vide");
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
}
