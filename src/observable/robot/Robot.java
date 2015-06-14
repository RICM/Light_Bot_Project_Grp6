package observable.robot;
import observable.int_Observable;
import observable.action_list.Possible_List;
import observable.map.abstr_Case;
import observer.int_Observer;
import couleur.Couleur;

public class Robot extends abstr_Robot implements int_Observable {


	public Robot(abstr_Case initCase, Possible_List allowed_actions, int_Observer controller){
		this.setOrientation(Orientation.orientation.LEFT);
		this.setCurrent_Case(initCase);
		this.set_possible(allowed_actions);
		this.set_tailleMain(12);
		this.set_tailleP1(8);
		this.set_tailleP2(8);
		this.set_couleur(Couleur.GRIS);
		super.listObserver.add(controller);
	}
	public Robot(abstr_Case initCase, Possible_List allowed_actions, int tailleM, int tailleP1, int tailleP2, int_Observer controller){
		this.setOrientation(Orientation.orientation.LEFT);
		this.setCurrent_Case(initCase);
		this.set_possible(allowed_actions);
		this.set_tailleMain(tailleM);
		this.set_tailleP1(tailleP1);
		this.set_tailleP2(tailleP2);
		this.set_couleur(Couleur.GRIS);
		super.listObserver.add(controller);
	}

	public Robot(abstr_Case initCase, Possible_List allowed_actions, Orientation.orientation or, int tailleM, int tailleP1, int tailleP2, int_Observer controller){
		this.setOrientation(or);
		this.setCurrent_Case(initCase);
		this.set_possible(allowed_actions);
		this.set_tailleMain(tailleM);
		this.set_tailleP1(tailleP1);
		this.set_tailleP2(tailleP2);
		this.set_couleur(Couleur.GRIS);
		super.listObserver.add(controller);
	}

	public void print_allowed_act(){
		System.out.println(this.get_possible().toString());
	}
}
