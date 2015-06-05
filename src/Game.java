import grahique.Menu2;
import map.Coordonnees;
import map.Normal_Case;
import map.abstr_Case;
import action_list.Possible_List;

import java.awt.EventQueue;
import java.util.LinkedList;

import action.int_Action;
import map.Terrain;


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
		/**EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu2 frame = new Menu2();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});**/
		
		/** Définition du terrain **/
		Terrain terrain_test = new Terrain();
		abstr_Case initRob;
		for (int i = 0; i < 4; i++){
			for (int j = 0; i < 4; i++){
				Coordonnees cord = new Coordonnees(i,j, i+j);
				Normal_Case carré = new Normal_Case(0,cord);
				terrain_test.add_case(i, j, carré);
			}	
		}
		initRob = terrain_test.get_case(3, 3);
		
		/** Définition de la liste des actions permises au robot **/
		Possible_List = new Possible_List();
		
		
		/** Définition du robot **/
		
		Robot robert = new Robot(initRob, LinkedList<int_Action> allowed_actions)
		}
}
