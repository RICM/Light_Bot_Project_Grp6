package parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import observable.action.Activate;
import observable.action.Jump;
import observable.action.MoveForward;
import observable.action.TurnLeft;
import observable.action.TurnRIght;
import observable.action.int_Action;
import observable.action_list.Possible_List;
import observable.map.Coordonnees;
import observable.map.Empty_Case;
import observable.map.Illuminated_Case;
import observable.map.Normal_Case;
import observable.map.Painted_Case;
import observable.map.Teleporter_Case;
import observable.map.Terrain;
import observable.map.World;
import observable.map.abstr_Case;
import observable.robot.Orientation;
import observable.robot.Orientation.orientation;
import observable.robot.Robot;
import observer.int_Observer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import couleur.Couleur;
import exception.UnreachableCase;



public class parserJSON {

	private static final String filePath = new File("").getAbsolutePath()+"/essaie.json";

	public static parserJSON currentparser = new parserJSON();

	public void lecture (int_Observer acontroller){
		try {
			//reader
			FileReader reader = new FileReader(filePath);
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);


			Number nb_terrain_prime = (Number) jsonObject.get("nb_terrain");
			System.out.println(nb_terrain_prime);
			int nb_terrain = nb_terrain_prime.intValue();
			System.out.println(nb_terrain);
			JSONArray terrains= (JSONArray) jsonObject.get("terrains");

			Number nb_robot_prime = (Number) jsonObject.get("nb_robot");
			int nb_robot = nb_robot_prime.intValue();

			Terrain terrainlist[] = new Terrain[nb_terrain];
			Robot robotlist[] = new Robot[nb_robot];


			for(int i=0; i < nb_terrain ; i++){
				JSONObject ObterrainCurrent =  (JSONObject) terrains.get(i);
				JSONArray terrainCurrent = (JSONArray) ObterrainCurrent.get(("terrain"+(i+1)));

				//System.out.println(terrainCurrent);
				//System.out.println(terrainCurrent.get(1));

				JSONObject ObnumY = (JSONObject) terrainCurrent.get(0);
				//System.out.println(ObnumY);
				Number numY = (Number) ObnumY.get("nb_case_hauteur");
				//System.out.println(numY);
				int dimY = numY.intValue();

				JSONObject ObnumX = (JSONObject) terrainCurrent.get(1);
				//System.out.println(ObnumX);
				Number numX = (Number) ObnumX.get("nb_case_largeur");
				//System.out.println(numX);
				int dimX = numX.intValue();

				terrainlist[i] = new Terrain(dimX,dimY);

				//System.out.println(terrainCurrent.get(2));
				JSONObject Obtabligne = (JSONObject) terrainCurrent.get(2);
				JSONArray lignes = (JSONArray) Obtabligne.get("lignes");
				//System.out.println(lignes);

				for(int y = 0; y < dimY; y++ ){
					//test taille ligne
					JSONObject Obtabcase = (JSONObject) lignes.get(y);
					//System.out.println(Obtabcase);
					JSONArray ligneCurrent = (JSONArray) Obtabcase.get("ligne"+(y+1));
					System.out.println(ligneCurrent);
					for (int x =0; x < dimX; x++){
						JSONObject ObcaseCurrent = (JSONObject) ligneCurrent.get(x);
						//System.out.println(ObcaseCurrent);
						Coordonnees coor = new Coordonnees(x, y, i);
						Number hauteur = (Number) ObcaseCurrent.get("hauteur");

						Couleur coul;
						switch((String)ObcaseCurrent.get("couleur")){
						case "VERT" :
							coul = Couleur.VERT;
							break;

						case "ROUGE" :
							coul = Couleur.ROUGE;
							break;

						case "GRIS" :
						default :
							coul = Couleur.GRIS;
							break;
						}

						abstr_Case carre = null;
						switch ((String) ObcaseCurrent.get("type_case")) { //Class.forName((String)ObcaseCurrent.get("type_case")).
						case "case_painted" :
							carre = new Painted_Case(hauteur.intValue(), coul, coor);
							System.out.println("terrain"+(i+1)+" case: "+"coordX "+carre.get_coordonnees().get_x()+" coordY "+carre.get_coordonnees().get_y()+" hauteur "+carre.get_hauteur());
							break;

						case "case_teleporter" :
							Number destX = (Number)  ObcaseCurrent.get("destX");
							Number destY = (Number)  ObcaseCurrent.get("destY");
							Number indice = (Number) ObcaseCurrent.get("indice");
							//new Teleporter_Case(h, Color, cord, destination)
							carre = new Teleporter_Case(hauteur.intValue(), coul, coor, new Coordonnees(destX.intValue(), destY.intValue(), indice.intValue()));
							//System.out.println("terrain"+i+" case: "+"coordX "+carre.get_coordonnees().get_x()+" coordY "+carre.get_coordonnees().get_y()+" hauteur "+carre.get_hauteur());
							break;

						case "case_normale" :
							carre = new Normal_Case(hauteur.intValue(),coor);
							System.out.println("terrain"+(i+1)+" case: "+"coordX "+carre.get_coordonnees().get_x()+" coordY "+carre.get_coordonnees().get_y()+" hauteur "+carre.get_hauteur());
							break;

						case "case_empty" :
							carre = new Empty_Case();
							//System.out.println("terrain"+(i+1)+" case: "+"coordX "+carre.get_coordonnees().get_x()+" coordY "+carre.get_coordonnees().get_y()+" hauteur "+carre.get_hauteur());
							break;

						case "case_illuminated" :
							carre = new Illuminated_Case(hauteur.intValue(), coul, coor);
							System.out.println("terrain"+(i+1)+" case: "+"coordX "+carre.get_coordonnees().get_x()+" coordY "+carre.get_coordonnees().get_y()+" hauteur "+carre.get_hauteur());
							break;

						default :
							carre = new Normal_Case(hauteur.intValue(),coor);
							System.out.println("terrain"+(i+1)+" case: "+"coordX "+carre.get_coordonnees().get_x()+" coordY "+carre.get_coordonnees().get_y()+" hauteur "+carre.get_hauteur());
							break;
						}

						terrainlist[i].add_case(x, y, carre);
					}

				}
			}

			System.out.println("nb de terrain: "+terrainlist.length);
			System.out.println("nb de robot: "+nb_robot);
			//System.out.println(terrainlist[0].get_terrain());
			//System.out.println(terrainlist[1].get_terrain().length);
			//World.currentWorld.set_liste_terrain(terrainlist);

			JSONArray robots= (JSONArray) jsonObject.get("robots");

			for (int i=0; i < nb_robot ; i++){
				JSONObject ObrobotCurrent =  (JSONObject) robots.get(i);
				JSONArray robotCurrent = (JSONArray) ObrobotCurrent.get(("robot"+(i+1)));
				System.out.println(robotCurrent);
				JSONObject Obcouleur = (JSONObject) robotCurrent.get(1);
				//System.out.println(Obcouleur);

				String Scouleur = (String) Obcouleur.get("couleur");
				Couleur cool;
				switch ((String) Obcouleur.get("couleur")){
				case "VERT" :
					cool = Couleur.VERT;
					break;

				case "ROUGE" :
					cool = Couleur.ROUGE;
					break;

				case "GRIS" :
					cool = Couleur.GRIS;
					break;

				default :
					cool = Couleur.GRIS;
					break;
				}

				JSONObject ObcoorX = (JSONObject) robotCurrent.get(2);
				//System.out.println(ObcoorX);
				Number numrobX = (Number) ObcoorX.get("coorX");
				System.out.println("robX: "+numrobX);
				int robX = numrobX.intValue();

				JSONObject ObcoorY = (JSONObject) robotCurrent.get(3);
				//System.out.println(ObcoorY);
				Number numrobY = (Number) ObcoorY.get("coorY");
				System.out.println("robY: "+numrobY);
				int robY = numrobY.intValue();

				JSONObject Obindice = (JSONObject) robotCurrent.get(4);
				//System.out.println(Obindice);
				Number numindice = (Number) Obindice.get("indice_tableau");
				System.out.println("indice robot: "+numindice.intValue());
				int indice = numindice.intValue();

				//System.out.println(terrainlist[indice].get_case(robX, robY));
				abstr_Case robotcase =  terrainlist[indice].get_case(robX, robY);

				JSONObject Obmaxmain = (JSONObject) robotCurrent.get(5);
				//System.out.println(Obmaxmain);
				Number nummain = (Number) Obmaxmain.get("max_main");
				System.out.println("max taille du main: "+nummain.intValue());
				int main = nummain.intValue();

				JSONObject Obmaxp1 = (JSONObject) robotCurrent.get(6);
				//System.out.println(Obmaxp1);
				Number nump1 = (Number) Obmaxp1.get("max_p1");
				System.out.println("max taille p1: "+nump1.intValue());
				int p1 = nump1.intValue();

				JSONObject Obmaxp2 = (JSONObject) robotCurrent.get(7);
				//System.out.println(Obmaxp2);
				Number nump2 = (Number) Obmaxp2.get("max_p2");
				System.out.println("max taille p2: "+nump2.intValue());
				int p2 = nump2.intValue();;

				JSONObject orient = (JSONObject) robotCurrent.get(8);
				orientation o= null;
				switch ((String) orient.get("orientation")){
				case "left":
					o = Orientation.orientation.LEFT;
					break;

				case "right":
					o = Orientation.orientation.RIGHT;
					break;

				case "top":
					o = Orientation.orientation.TOP;
					break;

				case "bot":
					o = Orientation.orientation.BOT;
					break;
				default :
					o = Orientation.orientation.LEFT;
					break;
				}


				JSONObject Obactions = (JSONObject) robotCurrent.get(9);
				JSONArray actions = (JSONArray) Obactions.get("actions");
				Possible_List actionlist = new Possible_List();
				//System.out.println(actions);

				for (int a = 0; a < actions.size() ; a++){
					//System.out.println(actions.get(a));
					int_Action act = null;
					switch ((String)actions.get(a)){
					case "Activate":
						act = Activate.activate();
						break;
					case "Jump":
						act = Jump.jump();
						break;

					case "MoveForward":
						act = MoveForward.move_forward();
						break;

					case "TurnLeft":
						act = TurnLeft.turn_left();
						break;

					case "TurnRight":
						act = TurnRIght.turn_right();
						break;

					default :
						break;
					}
					actionlist.addActionToList(act);
				}

				JSONObject Obtyperob = (JSONObject) robotCurrent.get(0);
				Robot robert =null;
				switch ((String) Obtyperob.get("type_robot")){
				case "Robot":
					robert = new Robot(robotcase, actionlist, o, main, p1, p2, acontroller);
					break;
				default:
					break;
				}
				robotlist[i]=robert;
			}
			World.currentWorld.set_liste_robot(robotlist);
			World.currentWorld.set_liste_terrain(terrainlist);
		}
		catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ParseException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		} catch (UnreachableCase e) {
			e.printStackTrace();
		}
	}
}



