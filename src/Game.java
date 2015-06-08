import java.awt.EventQueue;

import map.Coordonnees;
import map.Normal_Case;
import map.Painted_Case;
import map.Teleporter_Case;
import map.Terrain;
import map.World;
import map.abstr_Case;
import robot.Robot;
import action.Activate;
import action.Jump;
import action.LightCase;
import action.MoveForward;
import action.TurnRIght;
import action_list.Possible_List;
import couleur.Couleur;
import exception.ActionEx;
import exception.MouvementEx;
import exception.UnreachableCase;
import grahique.Jeu;


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
		list.addActionToList(TurnRIght.turn_right());
		list.addActionToList(Activate.activate());
		list.addActionToList(LightCase.light_case());
		//System.out.println("Liste permise : ");
		//System.out.println(list.toString());

		/** Définition du world **/


		/** Définition du terrain **/
		Terrain terrain_test = new Terrain(7,6);
		abstr_Case initRob;
	
		for (int i = 0; i < 7; i++){
			for (int j = 0; j < 6; j++){
				Couleur color = Couleur.GRIS;
				if(i+j%3 == 0){
					color = Couleur.JAUNE;
				}
				else if (i+j%3==1){
					color = Couleur.BLEU;
				}
				Coordonnees cord = new Coordonnees(i,j,0);
				Painted_Case carre = new Painted_Case(0,color, cord);
				terrain_test.add_case(i, j, carre);
			}
		}
		Coordonnees cord = new Coordonnees(2,2,0);
		try {
			Teleporter_Case test_tele = new Teleporter_Case(0,Couleur.GRIS,cord, terrain_test.get_case(1, 1));
			terrain_test.add_case(2, 2, test_tele);
		} catch (UnreachableCase e2) {
			System.out.println("case innacessible");
		}
		try {
			initRob = terrain_test.get_case(3, 3);
			Robot robert = new Robot(initRob, list);
			Robot robotlist[] = new Robot[1];
			Terrain terrainlist[] = new Terrain[1];
			robotlist[0] = robert;
			terrainlist[0] = terrain_test;
			World.currentWorld.set_liste_terrain(terrainlist);
			World.currentWorld.set_liste_robot(robotlist);
			World.currentWorld.basic_print_world();
			//robert.print_allowed_act();
			try{
				robert.add_Action_User_Actions(MoveForward.move_forward());
				robert.add_Action_User_Actions(TurnRIght.turn_right());

				robert.add_Action_User_Actions(MoveForward.move_forward());
				robert.add_Action_User_Actions(Activate.activate());
				//System.out.println("Position initiale : ");
				//robert.printPosition();
				Robot roblist[] = new Robot[1];
				roblist[0] = robert;
				Terrain terrlist[] = new Terrain[1];
				terrlist[0] = terrain_test;
				World.currentWorld.set_liste_robot(roblist);
				World.currentWorld.set_liste_terrain(terrlist);
				try{
					robert.run();
					//System.out.println("Position finale : ");
					//robert.printPosition();
				}catch(MouvementEx e){
					System.out.println(e.getMessage());
				}
			}catch (ActionEx ex){
				System.out.println(ex.getMessage());
			}
		} catch (UnreachableCase e1) {
			System.out.println(e1.getMessage());
		}
		
		Jeu game = new Jeu(0);




		/*EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Jeu game = new Jeu(0);
				} catch (Exception e) {				NOT WORKING ON MACOSX
					e.printStackTrace();
				}
			}
		});*/
	}
}
