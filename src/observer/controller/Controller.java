package observer.controller;

import java.io.IOException;

import observable.action.Activate;
import observable.action.Call_P1;
import observable.action.Call_P2;
import observable.action.Jump;
import observable.action.MoveForward;
import observable.action.TurnLeft;
import observable.action.TurnRIght;
import observable.action.int_Action;
import observable.action_list.Sequence_List;
import observable.map.Terrain;
import observable.map.World;
import observable.robot.Robot;
import observable.robot.abstr_Robot;
import observer.int_Observer;
import View.Jeu;
import couleur.Couleur;
import exception.ActionEx;
import exception.MouvementEx;
import exception.UnreachableCase;
public class Controller implements int_Observer {

	private Jeu jeu;
	private int current_robot;
	private int current_terrain;
	private int current_program;
	private boolean runnable;

	@Override
	public void update(Object obj){
		switch (obj.getClass().getSimpleName()){
		case "Robot" :
			this.setNotificationUpdatedRobot((Robot)obj);
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
		default:
			break;
		}
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
		try {
			this.jeu.display_terrain(obj);
		} catch (IOException e) {
			this.jeu.draw_popup("Désolé une erreur est survenue lors de la création du terrain");
		}
	}

	public Controller(){
		this.current_robot = 0;
		this.current_terrain = 0;
		this.current_program = 0;
		this.runnable = true;
	}

	public void setNotification(){
		/**
		 * Receive a notification from model
		 */
	}

	public void getNotificationRun(){
		/**
		 * Receive a notification from view to run program
		 */
		for(int j = 0; j < World.currentWorld.get_liste_robot().length;j++){
			World.currentWorld.get_ordonnanceur().addRobot(World.currentWorld.get_robot(j));
		}
		World.currentWorld.prerun();
		while (this.runnable && !(World.currentWorld.is_cleared())){
			try {
				World.currentWorld.exec();
			} catch (MouvementEx e) {
				this.jeu.draw_popup("Vous ne pouvez pas effectuer le prochaine mouvement !");
			} catch (UnreachableCase e) {
				this.jeu.draw_popup("Vous venez de vous manger une segfault!! La case visée ne peut etre atteinte");
			} catch (ActionEx e) {
				this.jeu.draw_popup("Une erreur est survenue lors de l'execution de l'actions");
			}
		}
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
		/**
		 * La vue aura besoin d'avoir un hashmap de boutons|actions
		 */
	}

	public void getNotificationChangeRobot(int i){
		/**
		 * Receive a notification from view to change robot to robot[i] from current
		 */
		if (World.currentWorld.number_robots()!=1)
			this.current_robot = i;
		this.jeu.updateRobotListAction(World.currentWorld.get_robot(this.current_robot));
	}

	public void getNotificationDisplayWorld(){
		/**
		 * Receive a notification from view to change the view and display all "terrain"
		 */
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
		/**
		 * Send a notification to view to display the robot
		 */
		//this.jeu.display_robot(rob);
	}

	public void getNotificationUpdatedRobot(abstr_Robot rob){
		this.setNotificationUpdatedRobot(rob);
	}

	public int_Action getNotificationAddActionToUserList(String str, Couleur color){
		try{
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
					World.currentWorld.get_robot(this.current_robot).add_Action_User_Actions(action);;
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
			default :
				return null;
			}
		}catch(ActionEx ex){
			return null;
		}
	}

	public void setNotificationSwitchProgram(int cmp) {
		this.current_program = cmp;
		System.out.println("switched program to : "+cmp);
	}

	public void getNotificationRewind(){
		try {
			World.currentWorld.rewind_status();
		} catch (UnreachableCase e) {
			this.jeu.draw_popup("Désolé, une erreur inattendue s'est produite");
		} catch (ActionEx e) {
			this.jeu.draw_popup("Désolé, une erreur inattendue s'est produite");
		}
	}
}
