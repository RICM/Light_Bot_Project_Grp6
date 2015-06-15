package observable.robot;
import java.util.ArrayList;

import observable.int_Observable;
import observable.action.int_Action;
import observable.action_list.Possible_List;
import observable.action_list.Sequence_List;
import observable.map.abstr_Case;
import observer.int_Observer;
import couleur.Couleur;
import exception.ActionEx;

public class Robot extends abstr_Robot implements int_Observable {


	public Robot(abstr_Case initCase, Possible_List allowed_actions, int_Observer controller){
		this.setOrientation(Orientation.orientation.LEFT);
		this.setCurrent_Case(initCase);
		this.set_possible(allowed_actions);
		this.set_tailleMain(12);
		this.set_tailleP1(8);
		this.set_tailleP2(8);
		this.set_couleur(Couleur.GRIS);
		this.listObserver.add(controller);
		this.user_actions = new Sequence_List(controller);
		this.P1 = new Sequence_List(controller);
		this.P2 = new Sequence_List(controller);
		for (int_Observer obs : this.listObserver){
			this.order_exec.addObserver(obs);
		}
	}

	public Robot(abstr_Case initCase, Possible_List allowed_actions, int tailleM, int tailleP1, int tailleP2, int_Observer controller){
		this.setOrientation(Orientation.orientation.LEFT);
		this.setCurrent_Case(initCase);
		this.set_possible(allowed_actions);
		this.set_tailleMain(tailleM);
		this.set_tailleP1(tailleP1);
		this.set_tailleP2(tailleP2);
		this.set_couleur(Couleur.GRIS);
		this.listObserver.add(controller);
		this.user_actions = new Sequence_List(controller);
		this.P1 = new Sequence_List(controller);
		this.P2 = new Sequence_List(controller);
		for (int_Observer obs : this.listObserver){
			this.order_exec.addObserver(obs);
		}
	}

	public Robot(abstr_Case initCase, Possible_List allowed_actions, Orientation.orientation or, int tailleM, int tailleP1, int tailleP2, int_Observer controller){
		this.setOrientation(or);
		this.setCurrent_Case(initCase);
		this.set_possible(allowed_actions);
		this.set_tailleMain(tailleM);
		this.set_tailleP1(tailleP1);
		this.set_tailleP2(tailleP2);
		this.set_couleur(Couleur.GRIS);
		this.listObserver.add(controller);
		this.user_actions = new Sequence_List(controller);
		this.P1 = new Sequence_List(controller);
		this.P2 = new Sequence_List(controller);
		for (int_Observer obs : this.listObserver){
			this.order_exec.addObserver(obs);
		}
	}

	public void print_allowed_act(){
		System.out.println(this.get_possible().toString());
	}

	@Override
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

	@Override
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
	@Override
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
	@Override
	public void add_execute(Sequence_List to_add){
		this.order_exec.addFirst(to_add);
	}

	@Override
	public void remove_Action_User_Actions(int_Action twas_too_long){
		this.user_actions.removeActionFromList(twas_too_long);
	}

	@Override
	public void remove_Action_P1(int_Action twas_too_long){
		this.P1.removeActionFromList(twas_too_long);
	}

	@Override
	public void remove_Action_P2(int_Action twas_too_long){
		this.P2.removeActionFromList(twas_too_long);
	}

	@Override
	public void printPosition(){
		System.out.println("position x : "+this.current_case.get_coordonnees().get_x());
		System.out.println("position y : "+this.current_case.get_coordonnees().get_y());
	}

	//getteurs et setteurs

	@Override
	public Orientation.orientation getOrientation(){
		return this.current_orientation;
	}

	@Override
	public void setOrientation(Orientation.orientation or){
		this.current_orientation = or;
	}

	@Override
	public Couleur get_couleur(){
		return this.color;
	}

	@Override
	public void set_couleur(Couleur new_color){
		this.color = new_color;
	}

	@Override
	public abstr_Case getCurrent_Case(){
		return this.current_case;
	}

	@Override
	public void setCurrent_Case(abstr_Case c){
		this.current_case = c;
	}

	@Override
	public Possible_List get_possible(){
		return this.allowed_actions;
	}

	@Override
	public void set_possible(Possible_List allowed){
		this.allowed_actions = allowed;
	}
	@Override
	public Sequence_List get_Main(){
		return this.user_actions;
	}


	@Override
	public int get_tailleMain(){
		return this.nbActionP1;
	}

	@Override
	public void set_tailleMain(int new_t){
		this.nbActionMain = new_t;
	}

	@Override
	public int get_tailleP1(){
		return this.nbActionP1;
	}
	@Override
	public void set_tailleP1(int new_t){
		this.nbActionP1 = new_t;
	}
	@Override
	public Sequence_List get_P1(){
		return this.P1;
	}

	@Override
	public int get_tailleP2(){
		return this.nbActionP2;
	}
	@Override
	public void set_tailleP2(int new_t){
		this.nbActionP2 = new_t;
	}
	@Override
	public Sequence_List get_P2(){
		return this.P2;
	}

	@Override
	public void addObserver(int_Observer obs) {
		this.listObserver.add(obs);
	}
	@Override
	public void removeObserver() {
		this.listObserver = new ArrayList<int_Observer>();

	}
	@Override
	public void notifyObserver() {
		for(int_Observer obs : this.listObserver)
			obs.update(this);
	}
	/**
	 * ajoute la osition actuelle du robot en debut de liste des positions
	 */
	@Override
	public void store_position(){
		Position pos = new Position(this.current_case.get_coordonnees(), this.getOrientation(), this.color);
		this.rollback.addFirst(pos);
	}

	/**
	 *
	 * @return la taille de la liste de position mémorisée
	 */
	@Override
	public int get_size_pos(){
		return this.rollback.size();
	}
	/**
	 *
	 * @return la dernière positionmémorisée
	 * @throws ActionEx
	 */
	@Override
	public Position get_last_pos() throws ActionEx{
		if (this.rollback.size()>0){
			return this.rollback.removeFirst();
		}
		else{
			throw new ActionEx("Pas de position en memoire");
		}
	}

	@Override
	public boolean get_activable(){
		return this.activable;
	}

	@Override
	public void set_activable( boolean activ){
		this.activable = activ;
	}
}
