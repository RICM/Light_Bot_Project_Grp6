package observer.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import View.Jeu;
import View.Menu;
import View.Niveaux;
import couleur.Couleur;
import exception.ActionEx;
import exception.MouvementEx;
import exception.UnreachableCase;
import observable.action.Activate;
import observable.action.Break_r;
import observable.action.Call_P1;
import observable.action.Call_P2;
import observable.action.Flashback;
import observable.action.Jump;
import observable.action.MoveForward;
import observable.action.Notify_r;
import observable.action.Remember;
import observable.action.TurnLeft;
import observable.action.TurnRIght;
import observable.action.Wait_r;
import observable.action.int_Action;
import observable.action_list.Sequence_List;
import observable.map.Illuminated_Case;
import observable.map.Terrain;
import observable.map.World;
import observable.map.abstr_Case;
import observable.robot.Dumb_bot;
import observable.robot.Orientation.orientation;
import observable.robot.Robot;
import observable.robot.abstr_Robot;
import observer.int_Observer;
import parser.parserJSON;

public class Controller implements int_Observer {

	private Jeu jeu;
	private int current_robot;
	private int current_terrain;
	private int current_program;
	private int overflow;
	private boolean runnable;
	private boolean isRunning = false;
	private boolean isPaused = false;
	private boolean savedPrerun = false;
	protected int cpt = 1;
	protected static String typeRobot[]={"pingouin_GRIS","requin_GRIS"};

	@Override
	public void update(Object obj){
		switch (obj.getClass().getSimpleName()){
		case "Robot" :
			System.out.println("Mouvement détécté");
			this.setNotificationUpdatedRobot((Robot)obj);
			break;
		case "Dumb_bot" :
			System.out.println("Mouvement détécté");
			this.setNotificationUpdatedRobot((Dumb_bot)obj);
			break;
		case "Terrain" :
			this.setNotificationUpdatedTerrain((Terrain)obj);
			break;
		case "Sequence_List" :
			this.setNotificationUpdatedCurrentProgramList((Sequence_List)obj);
			break;
		case "Execution_list" :
			this.runnable = World.currentWorld.isOneRobotActive();
			break;
		case "Illuminated_Case" :
			System.out.println("JE SUIS ALLUME");
			this.setNotificationUpdateCase();
			break;
		case "Ordonnanceur" :
			//this.runnable = false;
			break;
		case "abstr_Case" :
			System.out.println("Controlleur a recu notification d'une abstr_Case");
			this.setNotificationUpdateCase();
		case "Event_Case" :
			System.out.println("Controlleur a recu notification d'une Event_Case");
			this.setNotificationUpdateCase();
		default:
			break;
		}
	}

	public void setNotificationUpdateCase() {
		this.jeu.setNotificationDrawForTime();
	}

	public void setNotificationUpdatedCurrentProgramList(Sequence_List seq){
		System.out.println(seq.toString());
		System.out.println(this.jeu.toString());
		this.jeu.updateSequenceList(seq);
	}

	public void addJeu(Jeu ajeu){
		this.jeu = ajeu;
	}
	private void setNotificationUpdatedTerrain(Terrain obj) {
		this.getNotificationUpdatedTerrain(obj);
	}

	private void getNotificationUpdatedTerrain(Terrain obj) {
		this.jeu.setNotificationDrawForTime();
	}

	public Controller(){
		this.current_robot = 0;
		this.current_terrain = 0;
		this.current_program = 0;
		this.runnable = true;
		World.currentWorld.addObserver(this);
	}
	/**
	 * Receive a notification from model
	 */
	public void setNotification(){

	}

	public void setNotificationGoOn(){
		this.getNotificationRewind();
	}

	public void getNotificationRewindAndRUn(){
		if (!this.isRunning){
			this.isRunning = true;
			this.getNotificationRewind();
			this.getNotificationRun();
		}
	}
	/**
	 * Receive a notification from view to run program
	 */
	public void getNotificationRun(){
		boolean programm_vide = false;
		World.currentWorld.setAllRobotsActive();
		if (this.isPaused){
			this.setNotificationGoOn();
		}
		if (!this.isRunning){
			this.isRunning = true;
			this.overflow = 0;
			//if (World.currentWorld.get_ordonnanceur().size() == 0)
			// RESET L'ORDO
			World.currentWorld.prerun();
			this.savedPrerun = true;
			this.runnable = World.currentWorld.isOneRobotActive();
			System.out.println("contenu de run " + World.currentWorld.get_robot(0).get_run());
			System.out.println("statut du robot " + World.currentWorld.get_robot(0).get_activable());
			System.out.println("nobmre de robot dans le monde : "+World.currentWorld.get_liste_robot().length);
			System.out.println("nombre de robot dans l'ordo : "+World.currentWorld.get_ordonnanceur().getNumberRobots());
			while (this.overflow<200 && this.isRunning && this.runnable && !(World.currentWorld.is_cleared())){
				System.out.println("Dans le while du controller");
				try {
					System.out.println("While getNotificationRun");
					World.currentWorld.exec();
					World.currentWorld.get_ordonnanceur().increment_ind();
					this.overflow++;
				} catch (MouvementEx e) {
					this.runnable = false;
					//this.jeu.draw_popup("Vous ne pouvez pas effectuer le prochaine mouvement !");
				} catch (UnreachableCase e) {
					this.runnable = false;
					//this.jeu.draw_popup("Vous venez de vous manger une segfault!! La case visée ne peut etre atteinte");
				} catch (ActionEx e) {
					this.runnable = false;
					//this.jeu.draw_popup("Une erreur est survenue lors de l'execution de l'actions");
				} catch (IndexOutOfBoundsException ex){
					//this.jeu.draw_popup("Monsieur, vous devez rentrer au moins une action pour pouvoir exécuter le programme !");
					this.runnable = false;
					programm_vide = true;
				}
				try {
					Thread.sleep(0);
					System.out.println("B I : "+this.overflow);
				}catch(Exception ex){
					System.out.println(ex.getMessage());
				}
			}
			this.isRunning = false;
			World.currentWorld.get_ordonnanceur().removeRobots();
			if (programm_vide){
				World.currentWorld.get_ordonnanceur().removeRobots();
				System.out.println("NOMBRE DE ROBOT DANS ORDO A LA FIN : "+World.currentWorld.get_ordonnanceur().getNumberRobots());
				System.out.println("sortie de la boucle de RUN FOREST RUN");
				this.resetRobotAfterGame();
				this.getNotificationRewind();
			}
			if (this.overflow > 150){
				World.currentWorld.get_ordonnanceur().removeRobots();
				System.out.println("NOMBRE DE ROBOT DANS ORDO A LA FIN : "+World.currentWorld.get_ordonnanceur().getNumberRobots());
				System.out.println("sortie de la boucle de RUN FOREST RUN");
				System.out.println("Boucle infinie détéctée");
				this.resetRobotAfterGame();
				this.overflow = 0;
			}
			if (World.currentWorld.is_cleared()){
				World.currentWorld.get_ordonnanceur().removeRobots();
				System.out.println("NOMBRE DE ROBOT DANS ORDO A LA FIN : "+World.currentWorld.get_ordonnanceur().getNumberRobots());
				System.out.println("sortie de la boucle de RUN FOREST RUN");
				this.resetRobotAfterGame();
				this.getNotificationVictory();
			}
		}
		else{
			System.out.println("On ne peut pas relancer le robot sans redemarrer le niveau");
		}
	}

	public void resetRobotAfterGame(){
		this.current_robot = 0;
		this.current_program = 0;
		this.current_terrain = 0;
	}

	public void getNotificationAddToRobotList(int_Action act){
		/**
		 * Receive a notification from view to add an action to the robot list action
		 */
		abstr_Robot rob = World.currentWorld.get_robot(this.current_robot);
		try {
			rob.add_Action_User_Actions(act);
		} catch (ActionEx e) {
			this.jeu.draw_popup("Vous avez essayez d'ajouter une action interdite!\nGros malin va!");
		}
	}

	public void getNotificationRemoveToRobotList(int position){
		/**
		 * Receive a notification from view to remove an action to the robot list
		 */
		abstr_Robot rob = World.currentWorld.get_robot(this.current_robot);
		if (!rob.getClass().getSimpleName().equals("Dumb_bot")){

			switch (this.current_program){
			case 0 :
				if (position < rob.get_Main().size())
					rob.get_Main().removeIndice(position);
				break;
			case 1 :
				if (position < rob.get_P1().size())
					rob.get_P1().removeIndice(position);
				break;
			case 2 :
				if (position < rob.get_P2().size())
					rob.get_P2().removeIndice(position);
				break;
			}
			System.out.println("P1 "+World.currentWorld.get_robot(0).get_P1());
		}
		/**
		 * La vue aura besoin d'avoir un hashmap de boutons|actions
		 */
	}

	public void getNotificationChangeRobot(int robotCurrent){
		/**
		 * Receive a notification from view to change robot to robot[i] from current
		 */
		robotCurrent++;
		System.out.println("zev "+World.currentWorld.number_robots());
		if (robotCurrent >= World.currentWorld.number_robots()){
			robotCurrent=0;
		}
		this.current_robot = robotCurrent;
		this.jeu.updateRobot(robotCurrent);
	}

	public void getNotificationInitRobot(){
		/**
		 * Receive a notification from view to init robot
		 */
		this.jeu.updateRobot(this.current_robot);
	}

	public void getNotificationDisplayWorld(){
		/**
		 * Receive a notification from view to change the view and display all "terrain"
		 */
	}


	public void setNotificationGetListeOrdo(){
		/**
		 * Receive a notification from view to get liste ordo
		 */
		if(Niveaux.getTheme().equals("D")){
			this.getNotificationGetListeOrdo();
		}
	}

	public void getNotificationGetListeOrdo(){
		ArrayList<String> listeOrdo = new ArrayList<String>();
		for (abstr_Robot robOrdo : World.currentWorld.get_ordonnanceur().getListeRobotOrdonnanceur()){
			int i=0;
			for(abstr_Robot robList : World.currentWorld.get_liste_robot()){
				if(robOrdo == robList){
					listeOrdo.add(typeRobot[i]);
				}
				i++;
			}
		}
		this.jeu.updateDrawListeOrdo(listeOrdo);
	}

	public void getNotificationDisplayTerrain(int i){
		/**
		 * Receive a notification from view to change the view and display the "terrain" i
		 */
		this.current_terrain = i;
		this.setNotificationDisplayTerrain();
	}

	public void setNotificationDisplayTerrain(){
		/**
		 * Send a notification to view to display the current "terrain"
		 */
		try {
			this.jeu.display_terrain(World.currentWorld.get_terrain(this.current_terrain));
		} catch (IOException e) {
			this.jeu.draw_popup("Une erreur s'est produite durant la création du terrain de jeu!");
		}
	}

	public void setNotificationDisplayWorld(){
		/**
		 * Send a notification to view to display the world
		 */
		this.jeu.display_world(World.currentWorld);
	}

	public void setNotificationUpdatedRobot(abstr_Robot rob){
		//if(rob.getCurrent_Case().isVoisine(rob.getPrevious_Case()))
		//System.out.println("");
		this.jeu.setNotificationDrawForTime();
	}

	public void getNotificationUpdatedRobot(abstr_Robot rob){
		this.setNotificationUpdatedRobot(rob);
	}

	public int_Action getNotificationAddActionToUserList(String str, Couleur color){
		try{
			System.out.println("current_program : "+ this.current_program);
			switch (str){
			case "TurnRIght" :
				if (this.current_program == 2){
					TurnRIght action = TurnRIght.turn_right(color);
					World.currentWorld.get_robot(this.current_robot).add_Action_User_ActionsP2(action);
					return action;
				}else if (this.current_program == 1){
					TurnRIght action = TurnRIght.turn_right(color);
					World.currentWorld.get_robot(this.current_robot).add_Action_User_ActionsP1(action);
					return action;
				}else{
					TurnRIght action = TurnRIght.turn_right(color);
					World.currentWorld.get_robot(this.current_robot).add_Action_User_Actions(action);
					return action;
				}
			case "TurnLeft" :
				if (this.current_program == 2){
					TurnLeft action = TurnLeft.turn_left(color);
					World.currentWorld.get_robot(this.current_robot).add_Action_User_ActionsP2(action);
					return action;
				}else if (this.current_program == 1){
					TurnLeft action = TurnLeft.turn_left(color);
					World.currentWorld.get_robot(this.current_robot).add_Action_User_ActionsP1(action);
					return action;
				}else{
					TurnLeft action = TurnLeft.turn_left(color);
					World.currentWorld.get_robot(this.current_robot).add_Action_User_Actions(action);
					return action;
				}
			case "MoveForward" :
				if (this.current_program == 2){
					MoveForward action = MoveForward.move_forward(color);
					World.currentWorld.get_robot(this.current_robot).add_Action_User_ActionsP2(action);
					return action;
				}else if (this.current_program == 1){
					MoveForward action = MoveForward.move_forward(color);
					World.currentWorld.get_robot(this.current_robot).add_Action_User_ActionsP1(action);
					return action;
				}else{
					MoveForward action = MoveForward.move_forward(color);
					World.currentWorld.get_robot(this.current_robot).add_Action_User_Actions(action);
					return action;
				}
			case "Activate" :
				if (this.current_program == 2){
					Activate action = Activate.activate(color);
					World.currentWorld.get_robot(this.current_robot).add_Action_User_ActionsP2(action);
					return action;
				}else if (this.current_program == 1){
					Activate action = Activate.activate(color);
					World.currentWorld.get_robot(this.current_robot).add_Action_User_ActionsP1(action);
					return action;
				}else{
					Activate action = Activate.activate(color);
					World.currentWorld.get_robot(this.current_robot).add_Action_User_Actions(action);
					return action;
				}
			case "Jump" :
				if (this.current_program == 2){
					Jump action = Jump.jump(color);
					World.currentWorld.get_robot(this.current_robot).add_Action_User_ActionsP2(action);
					return action;
				}else if (this.current_program == 1){
					Jump action = Jump.jump(color);
					World.currentWorld.get_robot(this.current_robot).add_Action_User_ActionsP1(action);
					return action;
				}else{
					Jump action = Jump.jump(color);
					World.currentWorld.get_robot(this.current_robot).add_Action_User_Actions(action);
					return action;
				}
			case "Call_P1" :
				if (this.current_program == 2){
					Call_P1 action = Call_P1.call_p1(color, this);
					World.currentWorld.get_robot(this.current_robot).add_Action_User_ActionsP2(action);
					return action;
				}else if (this.current_program == 1){
					Call_P1 action = Call_P1.call_p1(color, this);
					World.currentWorld.get_robot(this.current_robot).add_Action_User_ActionsP1(action);
					return action;
				}else{
					Call_P1 action = Call_P1.call_p1(color, this);
					World.currentWorld.get_robot(this.current_robot).add_Action_User_Actions(action);
					return action;
				}
			case "Call_P2" :
				if (this.current_program == 2){
					Call_P2 action = Call_P2.call_p2(color, this);
					World.currentWorld.get_robot(this.current_robot).add_Action_User_ActionsP2(action);
					return action;
				}else if (this.current_program == 1){
					Call_P2 action = Call_P2.call_p2(color, this);
					World.currentWorld.get_robot(this.current_robot).add_Action_User_ActionsP1(action);
					return action;
				}else{
					Call_P2 action = Call_P2.call_p2(color, this);
					World.currentWorld.get_robot(this.current_robot).add_Action_User_Actions(action);
					return action;
				}
			case "Remember" :
				if (this.current_program == 2){
					Remember action = Remember.remember(color);
					World.currentWorld.get_robot(this.current_robot).add_Action_User_ActionsP2(action);
					return action;
				}else if (this.current_program == 1){
					Remember action = Remember.remember(color);
					World.currentWorld.get_robot(this.current_robot).add_Action_User_ActionsP1(action);
					return action;
				}else{
					Remember action = Remember.remember(color);
					World.currentWorld.get_robot(this.current_robot).add_Action_User_Actions(action);
					return action;
				}
			case "Flashback" :
				if (this.current_program == 2){
					Flashback action = Flashback.flashback(color);
					World.currentWorld.get_robot(this.current_robot).add_Action_User_ActionsP2(action);
					return action;
				}else if (this.current_program == 1){
					Flashback action = Flashback.flashback(color);
					World.currentWorld.get_robot(this.current_robot).add_Action_User_ActionsP1(action);
					return action;
				}else{
					Flashback action = Flashback.flashback(color);
					World.currentWorld.get_robot(this.current_robot).add_Action_User_Actions(action);
					return action;
				}
			case "Notify_r" :
				if (this.current_program == 2){
					Notify_r action = Notify_r.notify_r(color);
					World.currentWorld.get_robot(this.current_robot).add_Action_User_ActionsP2(action);
					return action;
				}else if (this.current_program == 1){
					Notify_r action = Notify_r.notify_r(color);
					World.currentWorld.get_robot(this.current_robot).add_Action_User_ActionsP1(action);
					return action;
				}else{
					Notify_r action = Notify_r.notify_r(color);
					World.currentWorld.get_robot(this.current_robot).add_Action_User_Actions(action);
					return action;
				}
			case "Break_r" :
				if (this.current_program == 2){
					Break_r action = Break_r.break_r(color);
					World.currentWorld.get_robot(this.current_robot).add_Action_User_ActionsP2(action);
					return action;
				}else if (this.current_program == 1){
					Break_r action = Break_r.break_r(color);
					World.currentWorld.get_robot(this.current_robot).add_Action_User_ActionsP1(action);
					return action;
				}else{
					Break_r action = Break_r.break_r(color);
					World.currentWorld.get_robot(this.current_robot).add_Action_User_Actions(action);
					return action;
				}
			case "Wait_r" :
				if (this.current_program == 2){
					Wait_r action = Wait_r.wait_r(color);
					World.currentWorld.get_robot(this.current_robot).add_Action_User_ActionsP2(action);
					return action;
				}else if (this.current_program == 1){
					Wait_r action = Wait_r.wait_r(color);
					World.currentWorld.get_robot(this.current_robot).add_Action_User_ActionsP1(action);
					return action;
				}else{
					Wait_r action = Wait_r.wait_r(color);
					World.currentWorld.get_robot(this.current_robot).add_Action_User_Actions(action);
					return action;
				}
			default :
				return null;
			}
		}catch(ActionEx ex){
			return null;
		}
	}

	public void setNotificationSwitchProgram(String newCurrent) {
		switch (newCurrent) {
		case "Main" : this.current_program = 0;break;
		case "P1" : this.current_program = 1;break;
		default : this.current_program = 2;break;
		}
		System.out.println("switched program to : "+this.current_program);
	}



	public void getNotificationRewind(){
		try {
			World.currentWorld.rewind_status();
			System.out.println("Rewind : "+World.currentWorld.get_robot(0).getCurrent_Case().get_coordonnees().get_x()
					+" , "+World.currentWorld.get_robot(0).getCurrent_Case().get_coordonnees().get_y());
			System.out.println("Liste actions main : "+World.currentWorld.get_robot(0).get_Main().getListActions().toString());
			System.out.println(World.currentWorld.get_robot(0).get_run().toString());
			this.isRunning = false;
		} catch (UnreachableCase e) {
			this.jeu.draw_popup("D�sol�, une erreur inattendue s'est produite");
		} catch (ActionEx e) {
			this.jeu.draw_popup("D�sol�, une erreur inattendue s'est produite");
		}
	}

	public void setNotificationDrawButton() {
		LinkedList<String> toReturn = new LinkedList<String>();
		for (int_Action act : World.currentWorld.get_robot(this.current_robot).get_possible().get()){
			toReturn.add(act.getClass().getSimpleName());
		}
		this.jeu.updateDrawBouton(toReturn);
	}


	public void setNotificationDrawControle() {
		this.jeu.updateDrawControle();
	}


	public void setNotificationDrawBoutonOrdonnance() {
		this.jeu.updateDrawBoutonOrdonnance();
	}


	public void setNotificationDrawGrilleISO(){
		int taille_abs =  World.currentWorld.get_terrain(0).get_terrain()[0].length;
		int taille_ord =  World.currentWorld.get_terrain(0).get_terrain().length;
		this.jeu.updateDrawGrilleISO(taille_abs, taille_ord);
	}

	public void setNotificationDrawISO(int X, int Y){
		int taille_abs =  World.currentWorld.get_terrain(0).get_terrain()[0].length;
		int taille_ord =  World.currentWorld.get_terrain(0).get_terrain().length;
		int PosX = Menu.getWidth()/2 +59*(Y+X)-taille_abs*60;
		int PosY = Menu.getHeight()/2 +18*(Y-X)-taille_ord*18+200;
		int XRob;
		int YRob;
		abstr_Case Ma_case;

		try {
			Ma_case = World.currentWorld.get_terrain(0).get_case(X,Y);
			int hauteur_max =  Ma_case.get_hauteur();
			String class_name = Ma_case.getClass().getSimpleName();
			String info_suppl = "";

			if(class_name.equals("Painted_Case")){
				info_suppl = "_" + Ma_case.get_couleur().toString();
			}
			if(class_name.equals("Illuminated_Case")){
				if (((Illuminated_Case)Ma_case).get_active()){
					info_suppl = "_Active";
				}else{
					info_suppl = "_Inactive";
				}
			}
			this.jeu.updateDrawISO(PosX, PosY, hauteur_max, class_name, info_suppl );

			abstr_Robot [] listeRobot = World.currentWorld.get_liste_robot();
			int i=0;
			for(abstr_Robot robotCurrent : listeRobot){
				if(this.getCpt()<8 && robotCurrent.getPrevious_Case()!=null){
					XRob = robotCurrent.getPrevious_Case().get_coordonnees().get_x();
					YRob = robotCurrent.getPrevious_Case().get_coordonnees().get_y();
				}
				else{
					XRob = robotCurrent.getCurrent_Case().get_coordonnees().get_x();
					YRob = robotCurrent.getCurrent_Case().get_coordonnees().get_y();
				}
				//Si le pingouin est sur cette case, alors on l'affiche à la hauteur maximale de celle-ci
				if ((XRob == X) && (YRob == Y)){
					this.setNotificationDrawPerso(robotCurrent, i);
				}
				i++;
			}
		} catch (UnreachableCase e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setNotificationDrawPerso(abstr_Robot robot, int num_robot){
		abstr_Case Ma_Case = robot.getCurrent_Case();
		abstr_Case Ma_Case_Prev = robot.getPrevious_Case();
		int X = Ma_Case.get_coordonnees().get_x();
		int Y = Ma_Case.get_coordonnees().get_y();
		int taille_abs =  World.currentWorld.get_terrain(0).get_terrain()[0].length;
		int taille_ord =  World.currentWorld.get_terrain(0).get_terrain().length;
		int PosX = Menu.getWidth()/2 +59*(Y+X)-taille_abs*60;
		int PosY = Menu.getHeight()/2 +18*(Y-X)-taille_ord*18+200;
		if(Ma_Case_Prev != null){
			int X2 = Ma_Case_Prev.get_coordonnees().get_x();
			int Y2 = Ma_Case_Prev.get_coordonnees().get_y();
			int PosX2 = Menu.getWidth()/2 +59*(Y2+X2)-taille_abs*60;
			int PosY2 = Menu.getHeight()/2 +18*(Y2-X2)-taille_ord*18+200;
			System.out.println(PosY2+" "+PosY);
			PosX = PosX2 + ((PosX-PosX2)/10)*this.cpt;
			PosY = PosY2 + ((PosY-PosY2)/10)*this.cpt;

			this.cpt++;
			if (this.cpt>10){
				this.cpt=1;
				robot.setPrevious_Case(null);
			}
		}
		int H = Ma_Case.get_hauteur();
		orientation orientation = robot.getOrientation();
		Couleur couleur = robot.get_couleur();
		this.jeu.updateDrawPerso(PosX, PosY, H, orientation, couleur, num_robot);
	}


	public void setNotificationDrawAllProcedure() {
		String name_proc;
		int nombre_bouton_max;
		LinkedList<String> class_name;
		LinkedList<String> color_name;
		int i = 0;
		switch(i){
		case 0 :
			class_name = new LinkedList<String>();
			color_name = new LinkedList<String>();
			name_proc = "Main";
			nombre_bouton_max = World.currentWorld.get_robot(this.current_robot).get_tailleMain();
			for(int_Action act :  World.currentWorld.get_robot(this.current_robot).get_Main().getListActions()){
				class_name.add(act.getClass().getSimpleName());
				color_name.add(act.getColor().name());
			}
			this.jeu.updateDrawProcedure(name_proc, nombre_bouton_max, class_name, color_name);
			i++;
			//break;
		case 1 :
			class_name = new LinkedList<String>();
			color_name = new LinkedList<String>();
			name_proc = "P1";
			nombre_bouton_max = World.currentWorld.get_robot(this.current_robot).get_tailleP1();
			for(int_Action act :  World.currentWorld.get_robot(this.current_robot).get_P1().getListActions()){
				class_name.add(act.getClass().getSimpleName());
				color_name.add(act.getColor().name());
			}
			this.jeu.updateDrawProcedure(name_proc, nombre_bouton_max, class_name, color_name);
			i++;
			//break;
		case 2 :
			class_name = new LinkedList<String>();
			color_name = new LinkedList<String>();
			name_proc = "P2";
			nombre_bouton_max = World.currentWorld.get_robot(this.current_robot).get_tailleP2();
			for(int_Action act :  World.currentWorld.get_robot(this.current_robot).get_P2().getListActions()){
				class_name.add(act.getClass().getSimpleName());
				color_name.add(act.getColor().name());
			}
			this.jeu.updateDrawProcedure(name_proc, nombre_bouton_max, class_name, color_name);

			i++;
			break;
		}
	}


	public void setNotificationUpdatedRobotMouvement() {
		System.out.println("envoie de la notification");
		World.currentWorld.get_ordonnanceur().setReady(true);
	}
	public void getLevel(Controller controller, String level){
		parserJSON.currentparser.lecture(controller,level);
	}

	public void getNotificationVictory(){
		this.isRunning = false;
		this.jeu.victory();

	}

	public void getNotificationStopRun(){
		this.runnable = false;
		this.isRunning = false;
		this.isPaused = true;
	}

	public int getCpt() {
		return this.cpt;
	}

	public void getNotificationAddToOrdonnanceurList(int robot){
		World.currentWorld.get_ordonnanceur().addRobot(World.currentWorld.get_robot(robot));
	}

	public void getNotificationRemoveToOrdonnanceurList(int robot){
		World.currentWorld.get_ordonnanceur().removeRobot(robot);
	}

	public void getNotificationSizeOrdonnanceurList(){
		this.jeu.updateSizeOrdonnanceur(World.currentWorld.get_ordonnanceur().size());
	}
}

