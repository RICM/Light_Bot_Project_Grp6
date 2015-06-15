package observable.robot;

import java.util.ArrayList;
import java.util.LinkedList;

import observable.action.int_Action;
import observable.action_list.Execution_list;
import observable.action_list.Possible_List;
import observable.action_list.Sequence_List;
import observable.map.World;
import observable.map.abstr_Case;
import observer.int_Observer;
import couleur.Couleur;
import exception.ActionEx;
import exception.MouvementEx;
import exception.UnreachableCase;

public abstract class abstr_Robot {
	protected ArrayList<int_Observer> listObserver = new ArrayList<int_Observer>();
	protected Possible_List allowed_actions;
	protected Sequence_List user_actions;
	protected Sequence_List P1;
	protected Sequence_List P2;
	protected int nbActionMain, nbActionP1, nbActionP2;
	protected abstr_Case current_case;
	protected Orientation.orientation current_orientation;
	protected Couleur color;
	protected Execution_list order_exec = new Execution_list();

	protected boolean activable = true;
	protected LinkedList<Position> rollback = new LinkedList<Position>();


	/**
	 * initialise la liste d'execution du robot avec le contenu de main
	 */
	public void run(){
		this.order_exec.addFirst(this.user_actions);

	}
	/**
	 * execute la première action de la liste
	 * @throws MouvementEx
	 * @throws UnreachableCase
	 * @throws ActionEx
	 */
	public void execute() throws MouvementEx, UnreachableCase, ActionEx{
		this.order_exec.run(this);
	}

	public void add_Action_Allowed_Actions(int_Action act){
		this.allowed_actions.addActionToList(act);
	}
	public void add_Action_User_Actions(int_Action act) throws ActionEx{
		boolean added = false;
		//System.out.println("trying to add : "+act.getClass().getCanonicalName());
		String str = act.getClass().getSimpleName();
		if (this.user_actions.size() < this.get_tailleMain()){
			for(int i =0; i<this.allowed_actions.size(); i++){
				if(str.equals(this.allowed_actions.get_name(i))){
					this.user_actions.addActionToList(act);
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
		if (this.P1.size() < this.get_tailleP1()){
			for(int i =0; i<this.allowed_actions.size(); i++){
				if(str.equals(this.allowed_actions.get_name(i))){
					this.P1.addActionToList(act);
					System.out.println("Action ajoutée au robot");
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
		if (this.P2.size() < this.get_tailleP2()){
			for(int i =0; i<this.allowed_actions.size(); i++){
				if(str.equals(this.allowed_actions.get_name(i))){
					this.P2.addActionToList(act);
					System.out.println("Action ajoutée au robot");
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
		System.out.println("position x : "+this.current_case.get_coordonnees().get_x());
		System.out.println("position y : "+this.current_case.get_coordonnees().get_y());
	}

	//getteurs et setteurs

	public Orientation.orientation getOrientation(){
		return this.current_orientation;
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
		return this.current_case;
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
	public Sequence_List get_Main(){
		return this.user_actions;
	}


	public int get_tailleMain(){
		return this.nbActionP1;
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
		this.listObserver = new ArrayList<int_Observer>();

	}
	public void notifyObserver() {
		for(int_Observer obs : this.listObserver)
			obs.update(this);
	}
	/**
	 * ajoute la osition actuelle du robot en debut de liste des positions
	 */
	public void store_position(){
		Position pos = new Position(this.current_case.get_coordonnees(), this.getOrientation(), this.color);
		this.rollback.addFirst(pos);
	}

	/**
	 *
	 * @return la taille de la liste de position mémorisée
	 */
	public int get_size_pos(){
		return this.rollback.size();
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
		return this.activable;
	}

	public void set_activable( boolean activ){
		this.activable = activ;
	}

	public void reset_exec(){
		this.order_exec.clear();
	}

	public void setFromPosition(Position new_pos) throws UnreachableCase{
		this.setCurrent_Case(World.currentWorld.get_case(new_pos.get_coordonnees()));
		this.set_couleur(new_pos.get_couleur());
		this.setOrientation(new_pos.get_orientation());
	}

	public void removeFirstRunable(){
		this.order_exec.removeFirst();
	}

}

