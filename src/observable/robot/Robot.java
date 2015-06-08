package observable.robot;
import java.util.ArrayList;

import couleur.Couleur;
import observable.int_Observable;
import observable.action.*;
import observable.action_list.*;
import observable.map.abstr_Case;
import observer.int_Observer;
import exception.UnreachableCase;
import exception.MouvementEx;
import exception.ActionEx;

public class Robot implements int_Observable{
	
	private Possible_List allowed_actions;
	private Sequence_List user_actions = new Sequence_List();
	private abstr_Case current_case;
	private Orientation.orientation current_orientation;
	private Couleur color;
	
	private ArrayList<int_Observer> listObserver = new ArrayList<int_Observer>(); 
	
	public Robot(abstr_Case initCase, Possible_List Dallowed_actions){
		current_orientation = Orientation.orientation.LEFT;
		current_case = initCase;
		allowed_actions = Dallowed_actions;
		notifyObserver();
	}
	
	public void run() throws MouvementEx, UnreachableCase{
		user_actions.execute(this);
	}
	
	public void add_Action_Allowed_Actions(int_Action act){
		allowed_actions.addActionToList(act);
		notifyObserver();
	}
	
	public void add_Action_User_Actions(int_Action act) throws ActionEx{
		boolean added = false;
		//System.out.println("trying to add : "+act.getClass().getCanonicalName());
		String str = act.getClass().getCanonicalName();
		for(int i =0; i<allowed_actions.size(); i++){
			if(str.equals(allowed_actions.get_name(i))){
				user_actions.addActionToList(act);
				added = true;
				notifyObserver();
				break;
			}
		}
		if(!added){
			throw new ActionEx("impossible d'ajouter");
		}
	}
	
	public void remove_Action_User_Actions(int_Action act){
		user_actions.removeActionFromList(act);
		notifyObserver();
	}
	
	public abstr_Case getCurrent_Case(){
		return current_case;
	}
	
	public void setCurrent_Case(abstr_Case c){
		this.current_case = c;
		notifyObserver();
	}
	
	public Orientation.orientation getOrientation(){
		return current_orientation;
	}
	
	public void setOrientation(Orientation.orientation or){
		current_orientation = or;
		notifyObserver();
	}
	
	public Couleur get_couleur(){
		return this.color;
	}
	public void set_couleur(Couleur new_color){
		this.color = new_color;
		notifyObserver();
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
	
	@Override
	public void addObserver(int_Observer obs) {
		this.listObserver.add(obs);
	}
	@Override
	public void removeObserver() {
		listObserver = new ArrayList<int_Observer>();
		
	}
	@Override
	public void notifyObserver() {
		for(int_Observer obs : listObserver)
		      obs.update(this);
	}
}
