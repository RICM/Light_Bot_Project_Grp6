package observer.controller;

import observable.action.int_Action;
import observable.grahique.Jeu;
import observable.map.Terrain;
import observable.map.World;
import observable.robot.Robot;
import observable.robot.abstr_Robot;
import exception.ActionEx;
import exception.MouvementEx;
import exception.UnreachableCase;
import observer.int_Observer;
public class Controller implements int_Observer {
	
	private Jeu jeu;
	private int current_robot;
	private int current_terrain;
	
	public void update(Object obj){
		switch (obj.getClass().getSimpleName()){
			case "Robot" :
				setNotificationUpdatedRobot((Robot)obj);
				break;
			case "Terrain" :
				setNotificationUpdatedTerrain((Terrain)obj);
				break;
			default:
				break;
		}
	}
	
	private void setNotificationUpdatedTerrain(Terrain obj) {
		getNotificationUpdatedTerrain(obj);
	}

	private void getNotificationUpdatedTerrain(Terrain obj) {
		jeu.display_terrain(obj);
	}

	public Controller(){
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
	
	public void setNotificationUpdatedRobot(abstr_Robot rob){
		/**
		 * Send a notification to view to display the robot
		 */
		jeu.display_robot(rob);
	}
	
	public void getNotificationUpdatedRobot(abstr_Robot rob){
		setNotificationUpdatedRobot(rob);
	}
	
	
}