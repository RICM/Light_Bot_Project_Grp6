package robot;
import couleur.Couleur;
import exception.UnreachableCase;
import action.*;
import action_list.*;
import map.abstr_Case;
import exception.MouvementEx;
import exception.ActionEx;

public class Robot {
	
	private Possible_List allowed_actions;
	private Sequence_List user_actions = new Sequence_List();
	private abstr_Case current_case;
	private Orientation.orientation current_orientation;
	private Couleur color;
	
	public Robot(abstr_Case initCase, Possible_List Dallowed_actions){
		current_orientation = Orientation.orientation.LEFT;
		current_case = initCase;
		allowed_actions = Dallowed_actions;
	}
	
	public void run() throws MouvementEx, UnreachableCase{
		user_actions.execute(this);
	}
	
	public void add_Action_Allowed_Actions(int_Action act){
		allowed_actions.addActionToList(act);
	}
	
	public void add_Action_User_Actions(int_Action act) throws ActionEx{
		boolean added = false;
		//System.out.println("trying to add : "+act.getClass().getCanonicalName());
		String str = act.getClass().getCanonicalName();
		for(int i =0; i<allowed_actions.size(); i++){
			if(str.equals(allowed_actions.get_name(i))){
				user_actions.addActionToList(act);
				added = true;
				break;
			}
		}
		if(!added){
			throw new ActionEx("impossible d'ajouter");
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
	
	public Couleur get_couleur(){
		return this.color;
	}
	public void set_couleur(Couleur new_color){
		this.color = new_color;
	}
	public void printPosition(){
		System.out.println("position x : "+current_case.get_coordonnees().get_x());
		System.out.println("position y : "+current_case.get_coordonnees().get_y());
	}
	
	public Possible_List get_possibles(){
		return this.allowed_actions;
	}
	
	public void print_allowed_act(){
		System.out.println(allowed_actions.toString());
	}
}
