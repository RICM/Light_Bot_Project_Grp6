package robot;
import action.*;
import action_list.*;
import map.abstr_Case;

import exception.MouvementEx;
import exception.ActionEx;

public class Robot {
	
	private Possible_List allowed_actions = new Possible_List();
	private Sequence_List user_actions = new Sequence_List();
	private abstr_Case current_case;
	private Orientation.orientation current_orientation;
	
	public Robot(abstr_Case initCase, Possible_List allowed_actions){
		current_orientation = Orientation.orientation.TOP;
		this.allowed_actions = allowed_actions;
		current_case = initCase;
	}
	
	public void run() throws MouvementEx{
		user_actions.execute(this);
	}
	
	public void add_Action_Allowed_Actions(int_Action act){
		allowed_actions.addActionToList(act);
	}
	
	public void add_Action_User_Actions(int_Action act) throws ActionEx {
		if (allowed_actions.isPresent(act)){
			user_actions.addActionToList(act);
		}
		else{
			throw new ActionEx("Action non autorisée");
		}
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
	
	public void printPosition(){
		System.out.println("position x : "+current_case.get_coordonnees().get_x());
		System.out.println("position y : "+current_case.get_coordonnees().get_y());
	}
	
	
	
	
	

}
