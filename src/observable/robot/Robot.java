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

public class Robot extends abstr_Robot implements int_Observable {
	
	
	public Robot(abstr_Case initCase, Possible_List allowed_actions){
		setOrientation(Orientation.orientation.LEFT);
		setCurrent_Case(initCase);
		set_possible(allowed_actions);
		set_tailleMain(12);
		set_tailleP1(8);
		set_tailleP2(8);
	}
	public Robot(abstr_Case initCase, Possible_List allowed_actions, int tailleM, int TailleP1, int tailleP2){
		setOrientation(Orientation.orientation.LEFT);
		setCurrent_Case(initCase);
		set_possible(allowed_actions);
		set_tailleMain(12);
		set_tailleP1(8);
		set_tailleP2(8);
	}
	
	public Robot(abstr_Case initCase, Possible_List allowed_actions, Orientation.orientation or, int tailleM, int TailleP1, int tailleP2){
		setOrientation(or);
		setCurrent_Case(initCase);
		set_possible(allowed_actions);
		set_tailleMain(12);
		set_tailleP1(8);
		set_tailleP2(8);
	}
	
	public void print_allowed_act(){
		System.out.println(this.get_possible().toString());
	}
}
