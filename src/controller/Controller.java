package controller;

import exception.ActionEx;
import exception.MouvementEx;
import exception.UnreachableCase;
import robot.Robot;
import action.int_Action;
import map.World;
import grahique.Jeu;

public class Controller {
	
	private Jeu jeu;
	private int current_robot;
	private int current_terrain;
	
	public Controller(Jeu jeu){
		this.jeu = jeu;
		current_robot = 0;
		current_terrain = 0;
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
		int robotN = World.currentWorld.number_robots();
		for (int i = 0; i < robotN; i++){
			try {
				World.currentWorld.get_robot(i).run();
			} catch (MouvementEx e) {
				jeu.draw_popup("Ce mouvement ne vous est pas permis!");
				e.printStackTrace();
			} catch (UnreachableCase e) {
				jeu.draw_popup("Segmentation Fault!\nVous êtes sortis du terrain! ");
			}
		}
	}
	
	public void getNotificationAddToRobotList(int_Action act){
		/**
		 * Receive a notification from view to add an action to the robot list action
		 */
		Robot rob = World.currentWorld.get_robot(current_robot); 
		try {
			rob.add_Action_User_Actions(act);
		} catch (ActionEx e) {
			jeu.draw_popup("Vous avez essayez d'ajouter une action interdite!\nGros malin va!");
		}
	}
	
	public void getNotificationRemoveToRobotList(int_Action act){
		/**
		 * Receive a notification from view to remove an action to the robot list
		 */
		Robot rob = World.currentWorld.get_robot(current_robot);
		rob.remove_Action_User_Actions(act);
	}
	
	public void getNotificationChangeRobot(int i){
		/**
		 * Receive a notification from view to change robot to robot[i] from current
		 */
		current_robot = i;
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
		current_terrain = i;
		setNotificationDisplayTerrain();
	}
	
	public void setNotificationDisplayTerrain(){
		/**
		 * Send a notification to view to display the current "terrain"
		 */
		jeu.display_terrain(World.currentWorld.get_terrain(current_terrain));
	}
	
	public void setNotificationDisplayWorld(){
		/**
		 * Send a notification to view to display the world
		 */
		jeu.display_world(World.currentWorld);
	}
}
