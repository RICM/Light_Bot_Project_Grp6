import observer.controller.Controller;
import parser.parserJSON;
import View.Menu;


public class Game {

	public static void main (String[] args){
		Controller acontroller = new Controller();
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
		//Possible_List list = new Possible_List();
		/** Ajouts des actions de base **/
		//list.addActionToList(MoveForward.move_forward());
		//list.addActionToList(Jump.jump());
		//list.addActionToList(TurnRIght.turn_right());
		//list.addActionToList(Activate.activate());
		//list.addActionToList(TurnLeft.turn_left());
		//System.out.println("Liste permise : ");
		//System.out.println(list.toString());

		/** Définition du world **/


		/** Définition du terrain **/
		/*Terrain terrain_test = new Terrain(6,6);
		abstr_Case initRob;

		for (int i = 0; i < 6; i++){
			for (int j = 0; j < 6; j++){
				Couleur color = Couleur.GRIS;
				if((i+j)%3 == 0){
					color = Couleur.VERT;
				}
				else if ((i+j)%3==1){
					color = Couleur.ROUGE;
				}
				Coordonnees cord = new Coordonnees(i,j,0);
				Painted_Case carre = new Painted_Case(0,color, cord);
				terrain_test.add_case(i, j, carre);
			}
		}
		Couleur color = Couleur.VERT;
		Coordonnees cord = new Coordonnees(2,1,0);
		Illuminated_Case carre = new Illuminated_Case(1,color, cord);
		terrain_test.add_case(2, 1, carre);

		Coordonnees cord2 = new Coordonnees(2,3,0);
		Teleporter_Case carre2 = new Teleporter_Case(2,color, cord2, cord);
		terrain_test.add_case(2, 3, carre2);
		try {*/
		/*initRob = terrain_test.get_case(0, 0);
			Robot robert = new Robot(initRob, list, acontroller);
			Robot robotlist[] = new Robot[1];
			Terrain terrainlist[] = new Terrain[1];
			robotlist[0] = robert;
			terrainlist[0] = terrain_test;
			World.currentWorld.set_liste_terrain(terrainlist);
			World.currentWorld.set_liste_robot(robotlist);
			World.currentWorld.basic_print_world();*/
		parserJSON.currentparser.lecture(acontroller);
		//robert.print_allowed_act();
		/*robert.add_Action_User_Actions(Activate.activate());
				robert.add_Action_User_Actions(MoveForward.move_forward(Couleur.ROUGE));
				robert.add_Action_User_Actions(TurnRIght.turn_right());
				robert.add_Action_User_Actions(Activate.activate());
				robert.add_Action_User_Actions(MoveForward.move_forward());
				robert.add_Action_User_Actions(MoveForward.move_forward());
				robert.add_Action_User_Actions(TurnRIght.turn_right());
				robert.add_Action_User_Actions(MoveForward.move_forward());
				robert.add_Action_User_Actions(Activate.activate());
				robert.add_Action_User_Actions(TurnLeft.turn_left(Couleur.ROUGE));
				robert.add_Action_User_Actions(Activate.activate());
				//System.out.println("Position initiale : ");
				//robert.printPosition();
				Robot roblist[] = new Robot[1];
				roblist[0] = robert;
				Terrain terrlist[] = new Terrain[1];
				terrlist[0] = terrain_test;
				World.currentWorld.set_liste_robot(roblist);
				World.currentWorld.set_liste_terrain(terrlist);*/
		parserJSON.currentparser.lecture(acontroller);
		//robert.run();
		//System.out.println("Position finale : ");
		//robert.printPosition();

		//Jeu game = new Jeu(0,null);

		Menu game = new Menu(acontroller);
		//game.setVisible(true);
		//game.setLocationRelativeTo(null);




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
