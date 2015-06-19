package observable.robot;

import java.util.ArrayList;

import couleur.Couleur;
import exception.ActionEx;
import observable.action.int_Action;
import observable.action_list.Possible_List;
import observable.action_list.Sequence_List;
import observable.map.abstr_Case;
import observable.robot.Orientation.orientation;
import observer.int_Observer;

public class Dumb_bot extends abstr_Robot {

	private boolean mainChangeAllowed;
	private boolean P1ChangeAllowed;
	private boolean P2ChangeAllowed;


	/**
	 * permet de créer un dumb_bot dont le main n'est pas modifiable
	 * @param case_init case de depart
	 * @param or_init orientation de depart
	 * @param allowed_init actions autorisée pour P1 et P2
	 * @param controller pour le controller
	 * @param Fixed_main sequence d'action constituant le main fixe : @require size<=12
	 * @param taille_P1 taille max de P1
	 * @param taille_P2 taille max de P2
	 */
	public Dumb_bot(abstr_Case case_init, orientation or_init, Possible_List allowed_init, Couleur color,
			int_Observer controller, Sequence_List Fixed_main, int taille_P1, int taille_P2){
		this.allowed_actions = allowed_init;
		this.set_couleur(color);
		this.setCurrent_Case(case_init);
		this.setOrientation(or_init);
		this.set_tailleP1(taille_P1);
		this.set_tailleP2(taille_P2);
		this.listObserver.add(controller);
		this.user_actions = Fixed_main;
		this.set_tailleMain(Fixed_main.size());
		this.P1 = new Sequence_List(controller);
		this.P2 = new Sequence_List(controller);
		this.mainChangeAllowed = false;
		this.P1ChangeAllowed = true;
		this.P2ChangeAllowed = true;
		this.user_actions.addObserver(controller);
	}

	/**
	 * similaire au precedent, ici le P1 est fixé
	 * @param case_init
	 * @param or_init
	 * @param allowed_init
	 * @param controller
	 * @param taille_Main
	 * @param Fixed_P1
	 * @param taille_P2
	 */
	public Dumb_bot(abstr_Case case_init, orientation or_init, Possible_List allowed_init, Couleur color,
			int_Observer controller,  int taille_Main, Sequence_List Fixed_P1, int taille_P2){
		this.allowed_actions = allowed_init;
		this.setCurrent_Case(case_init);
		this.setOrientation(or_init);
		this.set_couleur(color);
		this.set_tailleMain(taille_Main);
		this.set_tailleP2(taille_P2);
		this.listObserver.add(controller);
		this.P1 = Fixed_P1;
		this.set_tailleP1(Fixed_P1.size());
		this.user_actions = new Sequence_List(controller);
		this.P2 = new Sequence_List(controller);
		this.mainChangeAllowed = true;
		this.P1ChangeAllowed = false;
		this.P2ChangeAllowed = true;
		this.P1.addObserver(controller);
	}


	/**
	 * permet de créer un dumb_bot dont le main et P1 ne sont pas modifiable (P1 et P2 interchangeable donc 1 seul constru pour les 2)
	 * @param case_init case de depart
	 * @param or_init orientation de depart
	 * @param allowed_init actions autorisée pour P1 et P2
	 * @param controller pour le controller
	 * @param Fixed_main sequence d'action constituant le main fixe : @require size<=12
	 * @param Fixed_P1 similaire a Fixed main pour P1
	 * @param taille_P2 taille max de P2
	 */
	public Dumb_bot(abstr_Case case_init, orientation or_init, Possible_List allowed_init, Couleur color,
			int_Observer controller, Sequence_List Fixed_main, Sequence_List Fixed_P1, int taille_P2){
		this.allowed_actions = allowed_init;
		this.set_couleur(color);
		this.setCurrent_Case(case_init);
		this.setOrientation(or_init);
		this.set_tailleP2(taille_P2);
		this.listObserver.add(controller);
		this.user_actions = Fixed_main;
		this.user_actions.addObserver(controller);
		this.set_tailleMain(Fixed_main.size());
		this.P1 = Fixed_P1;
		this.set_tailleP1(Fixed_P1.size());
		this.P1.addObserver(controller);
		this.P2 = new Sequence_List(controller);
		this.mainChangeAllowed = false;
		this.P1ChangeAllowed = false;
		this.P2ChangeAllowed = true;

	}
	/**
	 * similaire au precedent mais ici c'est le main qui reste libre
	 * @param case_init
	 * @param or_init
	 * @param allowed_init
	 * @param controller
	 * @param taille_Main
	 * @param Fixed_P1
	 * @param Fixed_P2
	 */
	public Dumb_bot(abstr_Case case_init, orientation or_init, Possible_List allowed_init, Couleur color,
			int_Observer controller, int taille_Main, Sequence_List Fixed_P1,  Sequence_List Fixed_P2){
		this.allowed_actions = allowed_init;
		this.setCurrent_Case(case_init);
		this.setOrientation(or_init);
		this.set_couleur(color);
		this.set_tailleMain(taille_Main);
		this.listObserver.add(controller);
		this.P2 = Fixed_P2;
		this.set_tailleP2(Fixed_P2.size());
		this.P2.addObserver(controller);
		this.P1 = Fixed_P1;
		this.set_tailleP1(Fixed_P1.size());
		this.P1.addObserver(controller);
		this.P2 = new Sequence_List(controller);
		this.mainChangeAllowed = true;
		this.P1ChangeAllowed = false;
		this.P2ChangeAllowed = false;
	}


	public Dumb_bot(abstr_Case case_init, orientation or_init, Possible_List allowed_init, Couleur color,
			int_Observer controller, Sequence_List Fixed_Main, Sequence_List Fixed_P1,  Sequence_List Fixed_P2){
		this.allowed_actions = allowed_init;
		this.setCurrent_Case(case_init);
		this.setOrientation(or_init);
		this.set_couleur(color);
		this.listObserver.add(controller);
		this.user_actions = Fixed_Main;
		this.set_tailleMain(Fixed_Main.size());
		this.user_actions.addObserver(controller);
		this.P2 = Fixed_P2;
		this.set_tailleP1(Fixed_P1.size());
		this.P2.addObserver(controller);
		this.P1 = Fixed_P1;
		this.set_tailleP2(Fixed_P2.size());
		this.P1.addObserver(controller);
		this.P2 = new Sequence_List(controller);
		this.mainChangeAllowed = false;
		this.P1ChangeAllowed = false;
		this.P2ChangeAllowed = false;
	}

	@Override
	public void add_Action_User_Actions(int_Action act) throws ActionEx{
		boolean added = false;
		//System.out.println("trying to add : "+act.getClass().getCanonicalName());
		String str = act.getClass().getSimpleName();
		if (this.mainChangeAllowed && this.user_actions.size() < this.get_tailleMain()){
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
		if (this.P1ChangeAllowed && this.P1.size() < this.get_tailleP1()){
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
		if (this.P2ChangeAllowed && this.P2.size() < this.get_tailleP2()){
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
	public void remove_Action_User_Actions(int_Action twas_too_long) throws ActionEx {
		if(this.mainChangeAllowed){
			this.user_actions.removeActionFromList(twas_too_long);
		}
		else throw new ActionEx("Impossible de retirer une action");
	}

	@Override
	public void remove_Action_P1(int_Action twas_too_long) throws ActionEx{
		if (this.P1ChangeAllowed){
			this.user_actions.removeActionFromList(twas_too_long);
		}
		else throw new ActionEx("Impossible de retirer une action");
	}

	@Override
	public void remove_Action_P2(int_Action twas_too_long) throws ActionEx{
		if (this.P2ChangeAllowed){
			this.user_actions.removeActionFromList(twas_too_long);

		}
		else throw new ActionEx("Impossible de retirer une action");
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
	public void setCurrent_Case(abstr_Case c){
		this.prev_case = this.current_case;
		this.current_case = c;
		this.notifyObserver();
	}



}
