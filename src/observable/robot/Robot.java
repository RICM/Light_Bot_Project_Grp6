package observable.robot;
import java.util.ArrayList;

import couleur.Couleur;
import exception.UnreachableCase;
import observable.int_Observable;
import observable.action.int_Action;
import observable.action_list.Possible_List;
import observable.action_list.Sequence_List;
import observable.map.World;
import observable.map.abstr_Case;
import observer.int_Observer;

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

	public Robot(abstr_Case initCase, Possible_List allowed_actions, Orientation.orientation or,
			int tailleM, int tailleP1, int tailleP2, int_Observer controller, Couleur col){
		this.setOrientation(or);
		this.setCurrent_Case(initCase);
		this.set_possible(allowed_actions);
		this.set_tailleMain(tailleM);
		this.set_tailleP1(tailleP1);
		this.set_tailleP2(tailleP2);
		this.set_couleur(col);
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
		this.notifyObserver();
	}

	@Override
	public Couleur get_couleur(){
		return this.color;
	}

	@Override
	public void set_couleur(Couleur new_color){
		this.color = new_color;
		this.notifyObserver();
	}

	@Override
	public abstr_Case getCurrent_Case(){
		return this.current_case;
	}

	@Override
	public void setCurrent_Case(abstr_Case c){
		this.prev_case = this.current_case;
		this.current_case = c;
		this.notifyObserver();
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

	@Override
	public void setFromPosition(Position new_pos) throws UnreachableCase{
		this.setCurrent_Case(World.currentWorld.get_case(new_pos.get_coordonnees()));
		this.set_couleur(new_pos.get_couleur());
		this.setOrientation(new_pos.get_orientation());
		this.notifyObserver();
	}

}
