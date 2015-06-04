package robot;
import action.int_Action;
import map.abstr_Case;

import java.util.LinkedList;

public class Robot {
	
	private LinkedList<int_Action> allowed_actions;
	private LinkedList<int_Action> user_actions;
	private abstr_Case current_case;
	private Orientation.orientation current_orientation;
	
	public Robot(){
	
	}
	
	public void run(){
		for(int i = 0; i < user_actions.size(); i++){
			//user_actions.get(i).execute(this);
		}
	}
	
	public void add_Action_Allowed_Actions(int_Action act){
		allowed_actions.add(act);
	}
	
	public void add_Action_User_Actions(int_Action act){
		user_actions.add(act);
	}
	
	public abstr_Case getCurrent_Case(){
		return current_case;
	}
	
	public void setCurrent_Case(abstr_Case c){
		this.current_case = c;
	}
	
	public Orientation.orientation getOrientation(){
		return current_orientation;
	}
	
	public void setOrientation(Orientation.orientation or){
		current_orientation = or;
	}
	
	
	
	
	
	

}
