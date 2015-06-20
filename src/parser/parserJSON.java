package parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
import observable.action_list.Possible_List;
import observable.action_list.Sequence_List;
import observable.map.Coordonnees;
import observable.map.Destination_Case;
import observable.map.Empty_Case;
import observable.map.Event_Case;
import observable.map.Illuminated_Case;
import observable.map.Normal_Case;
import observable.map.Painted_Case;
import observable.map.Teleporter_Case;
import observable.map.Terrain;
import observable.map.World;
import observable.map.abstr_Case;
import observable.robot.Dumb_bot;
import observable.robot.Orientation;
import observable.robot.Orientation.orientation;
import observable.robot.Robot;
import observable.robot.abstr_Robot;
import observer.int_Observer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import couleur.Couleur;
import exception.ActionEx;
import exception.UnreachableCase;



public class parserJSON {

	private static final String filePath = new File("").getAbsolutePath()+"/json/";

	public static parserJSON currentparser = new parserJSON();

	public void lecture (int_Observer acontroller,String s){
		try {
			//reader
			FileReader reader = new FileReader(filePath+s+".json");
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);


			if ((String)jsonObject.get("ordo_modif") == "true" ){
				World.currentWorld.get_ordonnanceur().setModifiable(true);
			}else{
				World.currentWorld.get_ordonnanceur().setModifiable(false);
			}

			Number nb_terrain_prime = (Number) jsonObject.get("nb_terrain");
			System.out.println(nb_terrain_prime);
			int nb_terrain = nb_terrain_prime.intValue();
			System.out.println(nb_terrain);
			JSONArray terrains= (JSONArray) jsonObject.get("terrains");

			Number nb_robot_prime = (Number) jsonObject.get("nb_robot");
			int nb_robot = nb_robot_prime.intValue();

			Terrain terrainlist[] = new Terrain[nb_terrain];
			abstr_Robot robotlist[] = new abstr_Robot[nb_robot];


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

						//                                              Coordonnees coor = new Coordonnees(x, y, i);
						//                                              Number hauteur = (Number) ObcaseCurrent.get("hauteur");
						//
						//                                              Couleur coul = this.parserCouleur((String)ObcaseCurrent.get("couleur"));
						abstr_Case carre = this.parserCase(ObcaseCurrent,x,y,i,acontroller);
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

				//String Scouleur = (String) Obcouleur.get("couleur");
				Couleur cool = this.parserCouleur((String) Obcouleur.get("couleur"));

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

				for (int a = 0; a < actions.size() ; a++){
					int_Action act = null;
					switch ((String)actions.get(a)){
					case "Notify":
						act = Notify_r.notify_r();
						break;

					case "Wait":
						act = Wait_r.wait_r();
						break;

					case "Break":
						act = Break_r.break_r();
						break;

					case "Flashback":
						act = Flashback.flashback();
						break;

					case "":
						break;

					case "Remember":
						act = Remember.remember();
						break;

					case "P1":
						act = Call_P1.call_p1(acontroller);
						break;

					case "P2":
						act = Call_P2.call_p2(acontroller);
						break;

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
				//Robot robert =null;
				switch ((String) Obtyperob.get("type_robot")){
				case "Robot":
					JSONObject Obmaxmain = (JSONObject) robotCurrent.get(5);
					//System.out.println(Obmaxmain);
					Number nummain = (Number) Obmaxmain.get("max_main");
					int main = nummain.intValue();

					JSONObject Obmaxp1 = (JSONObject) robotCurrent.get(6);
					//System.out.println(Obmaxp1);
					Number nump1 = (Number) Obmaxp1.get("max_p1");
					int p1 = nump1.intValue();

					JSONObject Obmaxp2 = (JSONObject) robotCurrent.get(7);
					//System.out.println(Obmaxp2);
					Number nump2 = (Number) Obmaxp2.get("max_p2");
					int p2 = nump2.intValue();
					Robot robert = new Robot(robotcase, actionlist, o, main, p1, p2, acontroller, cool);
					robotlist[i]=robert;
					break;

				case "Dumb1":
					JSONObject Obmaind1 = (JSONObject) robotCurrent.get(5);
					Sequence_List d1main = this.parserListDUM(Obmaind1, acontroller,"liste_main");

					JSONObject Obmaxp1d1 = (JSONObject) robotCurrent.get(6);
					//System.out.println(Obmaxp1d1);
					Number nump1d1 = (Number) Obmaxp1d1.get("max_p1");
					System.out.println("max taille p1: "+nump1d1.intValue());
					int d1p1 = nump1d1.intValue();

					JSONObject Obmaxp2d1 = (JSONObject) robotCurrent.get(7);
					//System.out.println(Obmaxp2d1);
					Number nump2d1 = (Number) Obmaxp2d1.get("max_p2");
					System.out.println("max taille p2: "+nump2d1.intValue());
					int d1p2 = nump2d1.intValue();

					Dumb_bot robert_d1 = new Dumb_bot(robotcase, o, actionlist, cool, acontroller, d1main, d1p1, d1p2);
					robotlist[i]=robert_d1;
					break;

				case "Dumb2":

					JSONObject Obmaind2 = (JSONObject) robotCurrent.get(5);
					//System.out.println(Obmaind2);
					Number nummaind2 = (Number) Obmaind2.get("max_main");
					System.out.println("max taille main: "+nummaind2.intValue());
					int d2main = nummaind2.intValue();

					JSONObject Obmaxp1d2 = (JSONObject) robotCurrent.get(6);
					Sequence_List d2p1 = this.parserListDUM(Obmaxp1d2, acontroller, "liste_p1");

					JSONObject Obmaxp2d2 = (JSONObject) robotCurrent.get(7);
					//System.out.println(Obmaxp2d2);
					Number nump2d2 = (Number) Obmaxp2d2.get("max_p2");
					System.out.println("max taille p2: "+nump2d2.intValue());
					int d2p2 = nump2d2.intValue();
					Dumb_bot robert_d2 = new Dumb_bot(robotcase, o, actionlist, cool, acontroller, d2main, d2p1, d2p2);
					robotlist[i]=robert_d2;
					break;

				case "Dumb3":

					JSONObject Obmaind3 = (JSONObject) robotCurrent.get(5);
					Sequence_List d3main = this.parserListDUM(Obmaind3, acontroller,"liste_main");

					JSONObject Obmaxp1d3 = (JSONObject) robotCurrent.get(6);
					Sequence_List d3p1 = this.parserListDUM(Obmaxp1d3, acontroller, "liste_p1");


					JSONObject Obmaxp2d3 = (JSONObject) robotCurrent.get(7);
					//System.out.println(Obmaxp2d3);
					Number nump2d3 = (Number) Obmaxp2d3.get("max_p2");
					System.out.println("max taille p2: "+nump2d3.intValue());
					int d3p2 = nump2d3.intValue();

					Dumb_bot robert_d3 = new Dumb_bot(robotcase, o, actionlist, cool, acontroller, d3main, d3p1, d3p2);
					robotlist[i]=robert_d3;
					break;

				case "Dumb4":

					JSONObject Obmaind4 = (JSONObject) robotCurrent.get(5);
					//System.out.println(Obmaind4);
					Number nummaind4 = (Number) Obmaind4.get("max_main");
					System.out.println("max taille main: "+nummaind4.intValue());
					int d4main = nummaind4.intValue();

					JSONObject Obmaxp1d4 = (JSONObject) robotCurrent.get(6);
					Sequence_List d4p1 = this.parserListDUM(Obmaxp1d4, acontroller, "liste_p1");

					JSONObject Obmaxp2d4 = (JSONObject) robotCurrent.get(5);
					Sequence_List d4p2 = this.parserListDUM(Obmaxp2d4, acontroller,"liste_p2");

					Dumb_bot robert_d4 = new Dumb_bot(robotcase, o, actionlist, cool, acontroller, d4main, d4p1, d4p2);
					robotlist[i]=robert_d4;
					break;

				case "Dumb5":

					JSONObject Obmaind5 = (JSONObject) robotCurrent.get(5);
					Sequence_List d5main = this.parserListDUM(Obmaind5, acontroller,"liste_main");

					JSONObject Obmaxp1d5 = (JSONObject) robotCurrent.get(6);
					Sequence_List d5p1 = this.parserListDUM(Obmaxp1d5, acontroller, "liste_p1");

					JSONObject Obmaxp2d5 = (JSONObject) robotCurrent.get(5);
					Sequence_List d5p2 = this.parserListDUM(Obmaxp2d5, acontroller,"liste_p2");

					Dumb_bot robert_d5 = new Dumb_bot(robotcase, o, actionlist, cool, acontroller, d5main, d5p1, d5p2);
					robotlist[i]=robert_d5;
					break;



				default:
					break;
				}

			}

			World.currentWorld.set_liste_robot(robotlist);
			World.currentWorld.set_liste_terrain(terrainlist);
			World.currentWorld.store_status();
			World.currentWorld.init_World();
			//                      try {
			//                              World.currentWorld.store_status();
			//                      } catch (ActionEx e) {
			//                              // TODO Auto-generated catch block
			//                              e.printStackTrace();
			//                      }

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
		} catch (ActionEx e) {
			e.printStackTrace();
		}
	}


	public Sequence_List parserListDUM(JSONObject o, int_Observer c,String s){
		//System.out.println("EEEEEEEEEEEEEE"+s);
		JSONArray lmain = (JSONArray) o.get(s);
		Sequence_List sequencelist = new Sequence_List(c);

		for (int m = 0; m < lmain.size(); m++){
			int_Action act = null;
			switch ((String)lmain.get(m)){
			case "Notify":
				act = Notify_r.notify_r();
				break;

			case "Wait":
				act = Wait_r.wait_r();
				break;

			case "Break":
				act = Break_r.break_r();
				break;

			case "Flashback":
				act = Flashback.flashback();
				break;

			case "":
				break;

			case "Remember":
				act = Remember.remember();
				break;

			case "P1":
				act = Call_P1.call_p1(c);
				break;

			case "P2":
				act = Call_P2.call_p2(c);
				break;

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
			sequencelist.addActionSubSequence(act);
		}

		return sequencelist;
	}

	public Couleur parserCouleur(String s){
		Couleur coul;
		switch(s){
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
		return coul;
	}

	public abstr_Case parserCase(JSONObject o, int x, int y, int i,int_Observer c){

		//System.out.println("DDDDDDDDDDDDD");

		Coordonnees coor = new Coordonnees(x, y, i);
		Number hauteur = (Number) o.get("hauteur");

		Couleur coul = this.parserCouleur((String)o.get("couleur"));

		abstr_Case carre = null;
		switch ((String) o.get("type_case")) { //Class.forName((String)ObcaseCurrent.get("type_case")).
		case "case_event":
			boolean s = false;
			switch ((String)o.get("stat")){
			case "true" :
				s = true;
				break;

			case "false" :
				s = false;
				break;
			default: break;
			}
			//System.out.println("CCCCCCCCCCC"+o.get("case"));
			abstr_Case carretype = this.parserCaseEvent((JSONObject)o.get("case"),c);
			carre = new Event_Case(hauteur.intValue(), coul, coor, carretype, s);
			carre.addObserver(c);
			break;

		case "case_destination" :
			carre = new Destination_Case(hauteur.intValue(), coul, coor);
			System.out.println("terrain"+(i+1)+" case: "+"coordX "+carre.get_coordonnees().get_x()+" coordY "+carre.get_coordonnees().get_y()+" hauteur "+carre.get_hauteur());
			break;

		case "case_painted" :
			carre = new Painted_Case(hauteur.intValue(), coul, coor);
			System.out.println("terrain"+(i+1)+" case: "+"coordX "+carre.get_coordonnees().get_x()+" coordY "+carre.get_coordonnees().get_y()+" hauteur "+carre.get_hauteur());
			break;

		case "case_teleporter" :
			Number destX = (Number)  o.get("destX");
			Number destY = (Number)  o.get("destY");
			Number indice = (Number) o.get("indice");
			Coordonnees dest = new Coordonnees(destX.intValue(), destY.intValue(),indice.intValue());
			//new Teleporter_Case(h, Color, cord, destination)
			carre = new Teleporter_Case(hauteur.intValue(), coul, coor, dest);
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
		return carre;

	}

	public abstr_Case parserCaseEvent (JSONObject o,int_Observer c){

		Couleur coul = this.parserCouleur((String) o.get("couleur"));
		//System.out.println("BBBBBBBBB"+o.get("coorX"));
		Number coorX = (Number)o.get("coorX");
		int x = coorX.intValue();

		Number coorY = (Number)o.get("coorY");
		//System.out.println("AAAAAAAAA"+o.get("coorY"));
		int y = coorY.intValue();

		Number indice = (Number)o.get("indice");
		int i = indice.intValue();

		Coordonnees coor = new Coordonnees(x, y, i);

		Number hauteur = (Number)o.get("hauteur");
		int h = hauteur.intValue();

		abstr_Case carre = null;
		switch ((String) o.get("type_case")){

		case "case_event":

			boolean s = false;
			switch ((String)o.get("stat")){
			case "true" :
				s = true;
				break;

			case "false" :
				s = false;
				break;
			default: break;
			}

			carre = new Event_Case(h, coul, coor,this.parserCaseEvent((JSONObject)o.get("case"),c), s);
			carre.addObserver(c);
			break;

		case "case_destination" :
			carre = new Destination_Case(h,coul,coor);
			System.out.println("terrain"+(i+1)+" case: "+"coordX "+carre.get_coordonnees().get_x()+" coordY "+carre.get_coordonnees().get_y()+" hauteur "+carre.get_hauteur());
			break;

		case "case_painted" :
			carre = new Painted_Case(h, coul, coor);
			System.out.println("terrain"+(i+1)+" case: "+"coordX "+carre.get_coordonnees().get_x()+" coordY "+carre.get_coordonnees().get_y()+" hauteur "+carre.get_hauteur());
			break;

		case "case_teleporter" :
			Number destX = (Number)  o.get("destX");
			Number destY = (Number)  o.get("destY");
			Number indicedest = (Number) o.get("indice");
			Coordonnees dest = new Coordonnees(destX.intValue(), destY.intValue(),indicedest.intValue());
			//new Teleporter_Case(h, Color, cord, destination)
			carre = new Teleporter_Case(hauteur.intValue(), coul, coor, dest);
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
		return carre;
	}

}