import java.awt.EventQueue;

import map.Coordonnees;
import map.Normal_Case;
import map.Terrain;
import map.World;
import map.abstr_Case;
import robot.Robot;
import action.Jump;
import action.MoveForward;
import action_list.Possible_List;
import exception.ActionEx;
import exception.MouvementEx;
import grahique.Menu2;


public class Game {

	public static void main (String[] args){
		/**
		 * un robot
		 * tableau de robot
		 * cases
		 * tableu de cases
		 * actions possibles robot
		 * 3*MF
		 * orientation vers la droite
		 * setWorld
		 */

		/** Définition du terrain **/
		Terrain terrain_test = new Terrain(4,4);
		abstr_Case initRob;
		for (int i = 0; i < 4; i++){
			for (int j = 0; j < 4; j++){
				Coordonnees cord = new Coordonnees(i,j, i+j);
				Normal_Case carre = new Normal_Case(0,cord);
				System.out.println(carre.coordonnees.get_x());
				terrain_test.add_case(i, j, carre);
			}
		}
		initRob = terrain_test.get_case(3, 3);

		/** Définition de la liste des actions permises au robot **/
		Possible_List list = new Possible_List();
		/** Ajouts des actions de base **/
		list.addActionToList(MoveForward.move_forward());
		list.addActionToList(MoveForward.move_forward());
		list.addActionToList(Jump.jump());

		/** Définition du robot **/

		Robot robert = new Robot(initRob, list);
		try{
			robert.add_Action_User_Actions(MoveForward.move_forward());
			robert.add_Action_User_Actions(MoveForward.move_forward());
			robert.add_Action_User_Actions(MoveForward.move_forward());
		}catch (ActionEx e){
			System.out.println(e.getMessage());
		}
		System.out.println("Position initiale : ");
		robert.printPosition();
		Robot roblist[] = new Robot[1];
		roblist[0] = robert;
		Terrain terrlist[] = new Terrain[1];
		terrlist[0] = terrain_test;
		World.currentWorld.set_liste_robot(roblist);
		World.currentWorld.set_liste_terrain(terrlist);
		try{
			robert.run();
			System.out.println("Position finale : ");
			robert.printPosition();
		}catch(MouvementEx e){
			System.out.println(e.getMessage());
		}
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Menu2 frame = new Menu2();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
