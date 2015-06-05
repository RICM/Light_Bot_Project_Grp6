import java.awt.EventQueue;

import map.Coordonnees;
import map.Normal_Case;
import map.Terrain;
import map.World;
import map.abstr_Case;
import robot.Robot;
import action.Jump;
import action.LightCase;
import action.MoveForward;
import action_list.Possible_List;
import exception.ActionEx;
import exception.MouvementEx;
import exception.UnreachableCase;
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
		
		/** Définition de la liste des actions permises au robot **/
		Possible_List list = new Possible_List();
		/** Ajouts des actions de base **/
		list.addActionToList(MoveForward.move_forward());
		list.addActionToList(Jump.jump());
		//System.out.println("Liste permise : ");
		//System.out.println(list.toString());

		/** Définition du robot **/


		/** Définition du terrain **/
		Terrain terrain_test = new Terrain(4,4);
		abstr_Case initRob;
		for (int i = 0; i < 4; i++){
			for (int j = 0; j < 4; j++){
				Coordonnees cord = new Coordonnees(i,j,0);
				Normal_Case carre = new Normal_Case(0,cord);
				terrain_test.add_case(i, j, carre);
			}
		}
		try {
			initRob = terrain_test.get_case(3, 3);
			Robot robert = new Robot(initRob, list);
			//robert.print_allowed_act();
			try{
				robert.add_Action_User_Actions(MoveForward.move_forward());
				robert.add_Action_User_Actions(MoveForward.move_forward());
				robert.add_Action_User_Actions(MoveForward.move_forward());
				robert.add_Action_User_Actions(MoveForward.move_forward());
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
			}catch (ActionEx ex){
				System.out.println(ex.getMessage());
			}
		} catch (UnreachableCase e1) {
			System.out.println(e1.getMessage());
		}

		
		
		
		/*EventQueue.invokeLater(new Runnable() {
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
		});*/
	}
}
