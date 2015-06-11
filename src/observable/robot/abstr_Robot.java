package observable.robot;

import java.util.ArrayList;
import java.util.LinkedList;

import observable.map.abstr_Case;
import observable.action.int_Action;
import observable.action_list.Execution_list;
import observable.action_list.Possible_List;
import observable.action_list.Sequence_List;
import observer.int_Observer;
import couleur.Couleur;
import exception.ActionEx;
import exception.MouvementEx;
import exception.UnreachableCase;

public abstract class abstr_Robot {
	private Possible_List allowed_actions;
	private Sequence_List user_actions = new Sequence_List();
	private Sequence_List P1 = new Sequence_List();
	private Sequence_List P2 = new Sequence_List();
	private int nbActionMain, nbActionP1, nbActionP2;
	private abstr_Case current_case;	
	private Orientation.orientation current_orientation;
	private Couleur color;
	private Execution_list order_exec = new Execution_list();
	private ArrayList<int_Observer> listObserver = new ArrayList<int_Observer>();
	private boolean activable = true;
	private LinkedList<Position> rollback = new LinkedList<Position>();
	
	public void run(){
		order_exec.addFirst(user_actions);
		
	}
	
	public void execute() throws MouvementEx, UnreachableCase, ActionEx{
		order_exec.run(this);
	}
	
	public void add_Action_Allowed_Actions(int_Action act){
		allowed_actions.addActionToList(act);
	}
	public void add_Action_User_Actions(int_Action act) throws ActionEx{
		boolean added = false;
		//System.out.println("trying to add : "+act.getClass().getCanonicalName());
		String str = act.getClass().getSimpleName();
		if (user_actions.size() < this.get_tailleMain()){
			for(int i =0; i<allowed_actions.size(); i++){
				if(str.equals(allowed_actions.get_name(i))){
					user_actions.addActionToList(act);
					added = true;
					break;
				}
			}
		}
		if(!added){
			throw new ActionEx("impossible d'ajouter");
		}
	}
	public void add_Action_User_ActionsP1(int_Action act) throws ActionEx{
		boolean added = false;
		//System.out.println("trying to add : "+act.getClass().getCanonicalName());
		String str = act.getClass().getSimpleName();
		if (P1.size() < this.get_tailleP1()){
			for(int i =0; i<allowed_actions.size(); i++){
				if(str.equals(allowed_actions.get_name(i))){
					P1.addActionToList(act);
					added = true;
					break;
				}
			}
		}
		if(!added){
			throw new ActionEx("impossible d'ajouter");
		}
	}
	public void add_Action_User_ActionsP2(int_Action act) throws ActionEx{
		boolean added = false;
		//System.out.println("trying to add : "+act.getClass().getCanonicalName());
		String str = act.getClass().getSimpleName();
		if (P2.size() < this.get_tailleP2()){
			for(int i =0; i<allowed_actions.size(); i++){
				if(str.equals(allowed_actions.get_name(i))){
					P2.addActionToList(act);
					added = true;
					break;
				}
			}
		}
		if(!added){
			throw new ActionEx("impossible d'ajouter");
		}
	}
	public void add_execute(Sequence_List to_add){
		this.order_exec.addFirst(to_add);
	}
	
	public void remove_Action_User_Actions(int_Action twas_too_long){
		this.user_actions.removeActionFromList(twas_too_long);
	}
	
	public void remove_Action_P1(int_Action twas_too_long){
		this.P1.removeActionFromList(twas_too_long);
	}
	
	public void remove_Action_P2(int_Action twas_too_long){
		this.P2.removeActionFromList(twas_too_long);
	}
	
	public void printPosition(){
		System.out.println("position x : "+current_case.get_coordonnees().get_x());
		System.out.println("position y : "+current_case.get_coordonnees().get_y());
	}
	
	//getteurs et setteurs
	
	public Orientation.orientation getOrientation(){
		return current_orientation;
	}
	
	public void setOrientation(Orientation.orientation or){
		this.current_orientation = or;
	}
	
	public Couleur get_couleur(){
		return this.color;
	}
	
	public void set_couleur(Couleur new_color){
		this.color = new_color;
	}
	
	public abstr_Case getCurrent_Case(){
		return current_case;
	}
	
	public void setCurrent_Case(abstr_Case c){
		this.current_case = c;
	}
	
	public Possible_List get_possible(){
		return this.allowed_actions;
	}
	
	public void set_possible(Possible_List allowed){
		this.allowed_actions = allowed;
	}
	public int get_tailleMain(){
		return this.nbActionMain;
	}
	
	public void set_tailleMain(int new_t){
		this.nbActionMain = new_t;
	}
	
	public int get_tailleP1(){
		return this.nbActionP1;
	}
	public void set_tailleP1(int new_t){
		this.nbActionP1 = new_t;
	}
	public Sequence_List get_P1(){
		return this.P1;
	}
	
	public int get_tailleP2(){
		return this.nbActionP2;
	}
	public void set_tailleP2(int new_t){
		this.nbActionP2 = new_t;
	}
	public Sequence_List get_P2(){
		return this.P2;
	}
	
	public void addObserver(int_Observer obs) {
		this.listObserver.add(obs);
	}
	public void removeObserver() {
		listObserver = new ArrayList<int_Observer>();
		
	}
	public void notifyObserver() {
		for(int_Observer obs : listObserver)
		      obs.update(this);
	}
	/**
	 * ajoute la osition actuelle du robot en debut de liste des positions
	 */
	public void store_position(){
		Position pos = new Position(this.current_case.get_coordonnees(), this.getOrientation());
		rollback.addFirst(pos);
	}
	
	/**
	 * 
	 * @return la taille de la liste de position mémorisée
	 */
	public int get_size_pos(){
		return rollback.size();
	}
	/**
	 * 
	 * @return la dernière positionmémorisée
	 * @throws ActionEx
	 */
	public Position get_last_pos() throws ActionEx{
		if (this.rollback.size()>0){
			return this.rollback.removeFirst();
		}
		else{
			throw new ActionEx("Pas de position en memoire");
		}
	}

	public boolean get_activable(){
		return activable;
	}
	
	public void set_activable( boolean activ){
		this.activable = activ;
	}
}
	
