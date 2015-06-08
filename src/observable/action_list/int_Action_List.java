package observable.action_list;

import observable.int_Observable;
import observable.action.int_Action;

public interface int_Action_List extends int_Observable{
	
	public boolean isPresent(int_Action act);
	
	public void addActionToList(int_Action act);
	
	public String toString();

}
