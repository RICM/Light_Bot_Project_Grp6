package action_list;

import action.int_Action;

public interface int_Action_List {
	
	public boolean isPresent(int_Action act);
	
	public void addActionToList(int_Action act);
	
	public String toString();

}
