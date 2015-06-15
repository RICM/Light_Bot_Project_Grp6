package View;


import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import observable.action.Activate;
import observable.action.Jump;
import observable.action.MoveForward;
import observable.action.TurnLeft;
import observable.action.TurnRIght;
import observable.action.int_Action;
import observable.map.Illuminated_Case;
import observable.map.Terrain;
import observable.map.World;
import observable.map.abstr_Case;
import observable.robot.Orientation;
import observable.robot.abstr_Robot;

import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.Event.Type;

import exception.ActionEx;
import exception.MouvementEx;
import exception.UnreachableCase;

public class Jeu {

	protected Texture maTexture = new Texture();
	protected Sprite monSprite = new Sprite();
	protected Texture maTextureBouton = new Texture();
	protected Sprite monSpriteBouton = new Sprite();
	protected Texture maTextureBackground = new Texture();
	protected Sprite monSpriteBackground = new Sprite();
	protected Texture maTexturePerso = new Texture();
	protected Sprite monSpritePerso = new Sprite();
	protected static HashMap<Sprite,String> liste_sprite = new HashMap<Sprite, String>();
	protected int level;
	protected static World w = World.currentWorld;
	protected static abstr_Robot r = Jeu.w.get_robot(0);
	protected Terrain t = Jeu.w.get_terrain(this.level);
	protected abstr_Case[][] cases = this.t.get_terrain();
	protected int width_case = 80;
	protected int height_case = 50;
	protected static final int NB_MAX_CASE = 10;
	protected int indice_tele=0;
	protected static float x_whale = -600;
	protected static int y_whale = Menu.HEIGHT/2-200;
	protected static String activate = "Main";
	protected static ArrayList<Sprite> liste_background = new ArrayList<Sprite>();

	public Jeu(int lvl){
		Menu.reset_cam();
		this.level = lvl;
		while(Menu.app.isOpen()){
			Menu.app.clear();
			this.drawBackground();
			this.drawGrilleISO();
			this.draw_bouton();
			this.draw_procedure();
			this.processEvent();
			Menu.app.display();
			System.out.println("Robot list : "+r.get_P1().toString());
			if(World.currentWorld.is_cleared()){
				//				JOptionPane.showMessageDialog(null, "Fin");
				//				Menu.app.close();
			}
		}
	}

	/**
	 * D�tecte les entr�es claviers et souris
	 */
	public void processEvent(){
		Menu.app.setKeyRepeatEnabled(false);

		for(Event e : Menu.app.pollEvents()){
			if(e.type == Type.CLOSED){
				Texture te = new Texture();
				Sprite sp = new Sprite();
				try {
					te.loadFromFile(Paths.get("Images/Jeu/gif/images_fixes/whale.png"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				sp.setTexture(te);
				while(Jeu.x_whale < 1200){
					sp.setPosition(Jeu.x_whale,Jeu.y_whale);
					this.drawBackground();
					if(x_whale < Menu.WIDTH/3-200)
						this.drawGrilleISO();
					this.draw_bouton();
					Menu.app.draw(sp);
					Menu.app.display();
					Jeu.x_whale += 20;
					Menu.app.clear();
				}
				Menu.app.close();
			}

			if(e.type == Event.Type.RESIZED){
				Menu.reset_cam();
			}
			if (Keyboard.isKeyPressed(Key.LEFT)){
				Jeu.r.setOrientation(Orientation.orientation.LEFT);
				try {
					MoveForward.move_forward().execute(Jeu.r);
				} catch (MouvementEx e1) {
					System.out.println(e1.getMessage());
				} catch (UnreachableCase e1) {
					System.out.println(e1.getMessage());
				}
			}
			if (Keyboard.isKeyPressed(Key.RIGHT)){
				Jeu.r.setOrientation(Orientation.orientation.RIGHT);
				try {
					MoveForward.move_forward().execute(Jeu.r);
				} catch (MouvementEx e1) {
					System.out.println(e1.getMessage());
				} catch (UnreachableCase e1) {
					System.out.println(e1.getMessage());
				}
			}
			if (Keyboard.isKeyPressed(Key.SPACE)){

			}
			if (Keyboard.isKeyPressed(Key.UP)){
				Jeu.r.setOrientation(Orientation.orientation.TOP);
				try {
					MoveForward.move_forward().execute(Jeu.r);
				} catch (MouvementEx e1) {
					System.out.println(e1.getMessage());
				} catch (UnreachableCase e1) {
					System.out.println(e1.getMessage());
				}
			}
			if (Keyboard.isKeyPressed(Key.DOWN)){
				Jeu.r.setOrientation(Orientation.orientation.BOT);
				try {
					MoveForward.move_forward().execute(Jeu.r);
				} catch (MouvementEx e1) {
					System.out.println(e1.getMessage());
				} catch (UnreachableCase e1) {
					System.out.println(e1.getMessage());
				}
			}

			if (Keyboard.isKeyPressed(Key.SPACE)){
				Jeu.r.run();
			}

			if (e.type == Event.Type.MOUSE_BUTTON_RELEASED ) {
				e.asMouseEvent();
				Vector2i pos = Mouse.getPosition(Menu.app);
				Vector2f click = Menu.app.mapPixelToCoords(pos);
				Jeu.detect_move(click);
			}

		}
	}

	/**
	 *
	 * @param pos Coordonn�es du clique souris
	 */
	private static void detect_move(Vector2f pos) {
		float x = pos.x;
		float y = pos.y;
		Iterator<Sprite> keySetIterator = Jeu.liste_sprite.keySet().iterator();
		while(keySetIterator.hasNext()){
			Sprite s = keySetIterator.next();
			FloatRect rect = s.getGlobalBounds();
			if(x>=rect.left && x<=rect.left+rect.width &&
					y>=rect.top && y<=rect.top+rect.height){
				if(Jeu.liste_sprite.get(s).equals("MoveForward")){
					//ajoute l'action dans la liste d'action du robot en fonction du panneau activé
					try {
						switch(activate){
						case ("Main") : r.add_Action_User_Actions(MoveForward.move_forward());break;
						case ("P1") : r.add_Action_User_ActionsP1(MoveForward.move_forward());break;
						case ("P2") : r.add_Action_User_ActionsP2(MoveForward.move_forward());break;
						}
					}catch (ActionEx e) {
						e.printStackTrace();
					}
					try {
						MoveForward.move_forward().execute(Jeu.r);
					} catch (MouvementEx e1) {
						System.out.println(e1.getMessage());
					} catch (UnreachableCase e1) {
						System.out.println(e1.getMessage());
					}
					break;
				}
				else if(Jeu.liste_sprite.get(s).equals("TurnRIght")){
					//ajoute l'action dans la liste d'action du robot en fonction du panneau activé
					try {
						switch(activate){
						case ("Main") : r.add_Action_User_Actions(TurnRIght.turn_right());break;
						case ("P1") : r.add_Action_User_ActionsP1(TurnRIght.turn_right());break;
						case ("P2") : r.add_Action_User_ActionsP2(TurnRIght.turn_right());break;
						}
					}catch (ActionEx e) {
						e.printStackTrace();
					}
					switch(r.getOrientation()){
					case TOP : Jeu.r.setOrientation(Orientation.orientation.RIGHT);break;
					case RIGHT:Jeu.r.setOrientation(Orientation.orientation.BOT);break;
					case BOT : Jeu.r.setOrientation(Orientation.orientation.LEFT);break;
					case LEFT: Jeu.r.setOrientation(Orientation.orientation.TOP);break;
					}
					break;
				}
				else if(Jeu.liste_sprite.get(s).equals("TurnLeft")){
					//ajoute l'action dans la liste d'action du robot en fonction du panneau activé
					try {
						switch(activate){
						case ("Main") : r.add_Action_User_Actions(TurnLeft.turn_left());break;
						case ("P1") : r.add_Action_User_ActionsP1(TurnLeft.turn_left());break;
						case ("P2") : r.add_Action_User_ActionsP2(TurnLeft.turn_left());break;
						}
					}catch (ActionEx e) {
						e.printStackTrace();
					}
					switch(r.getOrientation()){
					case TOP : Jeu.r.setOrientation(Orientation.orientation.LEFT);break;
					case RIGHT:Jeu.r.setOrientation(Orientation.orientation.TOP);break;
					case BOT : Jeu.r.setOrientation(Orientation.orientation.RIGHT);break;
					case LEFT: Jeu.r.setOrientation(Orientation.orientation.BOT);break;
					}
					break;
				}
				else if(Jeu.liste_sprite.get(s).equals("Activate")){
					//ajoute l'action dans la liste d'action du robot en fonction du panneau activé
					try {
						switch(activate){
						case ("Main") : r.add_Action_User_Actions(Activate.activate());break;
						case ("P1") : r.add_Action_User_ActionsP1(Activate.activate());break;
						case ("P2") : r.add_Action_User_ActionsP2(Activate.activate());break;
						}
					}catch (ActionEx e) {
						e.printStackTrace();
					}
					System.out.println("Allumer");
					try {
						Activate.activate().execute(r);
					} catch (MouvementEx e) {
						e.printStackTrace();
					} catch (UnreachableCase e) {
						e.printStackTrace();
					} catch (ActionEx e) {
						e.printStackTrace();
					}
					break;
				}
				else if(Jeu.liste_sprite.get(s).equals("Jump"))
					//ajoute l'action dans la liste d'action du robot en fonction du panneau activé
					try {
						switch(activate){
						case ("Main") : r.add_Action_User_Actions(Jump.jump());break;
						case ("P1") : r.add_Action_User_ActionsP1(Jump.jump());break;
						case ("P2") : r.add_Action_User_ActionsP2(Jump.jump());break;
						}
					}catch (ActionEx e) {
						e.printStackTrace();
					}
				System.out.println("Sauter");
				try {
					Jump.jump().execute(r);
				} catch (MouvementEx e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnreachableCase e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}
		int cmp=0;
		String background[]=  {"Main","P1","P2"};
		for(Sprite s: liste_background){
			FloatRect rect = s.getGlobalBounds();
			if(x>=rect.left && x<=rect.left+rect.width &&
					y>=rect.top && y<=rect.top+rect.height){
				activate=background[cmp];
				System.out.println("activate: "+activate);
				break;
			}
			cmp++;
		}

	}


	public void draw_popup(String msg){
		JOptionPane.showMessageDialog(null, msg);
	}



	/**
	 *	Affiche le personnage
	 * @param rob le personnage a afficher
	 * @param X la position en x du personnage
	 * @param Y la position en y du personnage
	 */
	public void drawPerso(abstr_Robot rob){
		try {
			abstr_Case c = rob.getCurrent_Case();
			int X = c.get_coordonnees().get_x();
			int Y = c.get_coordonnees().get_y();
			int taille_abs =  World.currentWorld.get_terrain(0).get_terrain()[0].length;
			int taille_ord =  World.currentWorld.get_terrain(0).get_terrain().length;
			int PosX = Menu.WIDTH/2 +59*(Y+X)-taille_abs*60-180;
			int PosY = Menu.HEIGHT/2 +18*(Y-X)-taille_ord*18+100;

			switch(rob.getOrientation()){
			case BOT : this.maTexturePerso.loadFromFile(Paths.get("Images/Jeu/gif/images_fixes/3.png"));break;
			case LEFT:	this.maTexturePerso.loadFromFile(Paths.get("Images/Jeu/gif/images_fixes/15.png"));break;
			case RIGHT:this.maTexturePerso.loadFromFile(Paths.get("Images/Jeu/gif/images_fixes/7.png"));break;
			case TOP : this.maTexturePerso.loadFromFile(Paths.get("Images/Jeu/gif/images_fixes/11.png"));break;
			}
			this.monSpritePerso.setTexture(this.maTexturePerso);
			this.monSpritePerso.setPosition(PosX+30+10,PosY-26*c.get_hauteur()+25);
			Menu.app.draw(this.monSpritePerso);

		} catch (IOException e) {
			System.out.println(e.getMessage());
		} // on charge la texture qui se trouve dans notre dossier assets
	}

	/**
	 * Affiche les boutons
	 */
	public void draw_bouton(){
		LinkedList<int_Action> actions = r.get_possible().get();
		int i =0;
		try {
			for(int_Action a : actions){
				Texture maTextureBouton = new Texture();
				Sprite monSpriteBouton = new Sprite();
				maTextureBouton.loadFromFile(Paths.get("Images/Jeu/bouton/"+a.getClass().getSimpleName()+".png"));
				monSpriteBouton.setTexture(maTextureBouton);
				monSpriteBouton.setPosition(10+100*i,610);
				Jeu.liste_sprite.put(monSpriteBouton,a.getClass().getSimpleName());
				Menu.app.draw(monSpriteBouton);
				i++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	public void draw_procedure(){
		try {
			String background[]=  {"Main","P1","P2"};
			int nombre_bouton[] = {r.get_tailleMain(),r.get_tailleP1(),r.get_tailleP2()};
			int nombre_bouton_ajoute[] = {r.get_Main().size(),r.get_P1().size(),r.get_P2().size()};
			int posY[] = {7, 290, 494};
			int y;
			int num=0;
			int pos_bouton[] = {44,324,530};
			for(String type_background : background){
				Sprite monSpriteBackground = new Sprite();
				if(type_background==activate){
					this.maTexture.loadFromFile(Paths.get("Images/Jeu/background/Fond_"+type_background+"_Activate.png"));
				}
				else{
					this.maTexture.loadFromFile(Paths.get("Images/Jeu/background/Fond_"+type_background+".png"));
				}
				monSpriteBackground.setPosition(881, posY[num]);
				monSpriteBackground.setTexture(this.maTexture);
				liste_background.add(monSpriteBackground);
				//System.out.println("monSpriteBackground.toString(): "+monSpriteBackground.toString());
				Menu.app.draw(monSpriteBackground);
				y=0;
				for(int x=0; x<nombre_bouton[num];x++){
					this.maTexture = new Texture();
					if(x%4==0){
						y++;
					}
					Sprite monSprite = new Sprite();

					if (x<nombre_bouton_ajoute[num]){
						switch(num){
						case 0 : this.maTexture.loadFromFile(Paths.get("Images/Jeu/bouton/"+r.get_Main().get(x).getClass().getSimpleName()+".png"));break;
						case 1 : this.maTexture.loadFromFile(Paths.get("Images/Jeu/bouton/"+r.get_P1().get(x).getClass().getSimpleName()+".png"));break;
						case 2 : this.maTexture.loadFromFile(Paths.get("Images/Jeu/bouton/"+r.get_P2().get(x).getClass().getSimpleName()+".png"));break;
						}
					}else{
						this.maTexture.loadFromFile(Paths.get("Images/Jeu/background/Fond_Bouton.png"));
					}
					monSprite.setPosition(884+(x%4*(70+4)), pos_bouton[num] + (y-1)*(70+10));
					monSprite.setTexture(this.maTexture);
					Menu.app.draw(monSprite);
				}
				num++;
			}
			//emplacement des boutons



		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}


	}


	/**
	 * Associe une image avec la case en fonction de son type
	 * @param cases
	 */
	public void typeCases(abstr_Case cases){
		try{
			switch(cases.getClass().getSimpleName()){
			case("Normal_Case") : this.maTexture.loadFromFile(Paths.get("Images/Jeu/Cases/Square_normal.png"));break;
			case("Destination_Case") : this.maTexture.loadFromFile(Paths.get("Images/Jeu/Cases/Square_destination.png"));break;
			case("Empty_Case") : this.maTexture.loadFromFile(Paths.get("Images/Jeu/Cases/Square_vide.png"));break;
			case("Painted_Case") :
				switch(cases.get_couleur()){
				case VERT : this.maTexture.loadFromFile(Paths.get("Images/Jeu/Cases/Square_vert.png"));break;
				case GRIS : this.maTexture.loadFromFile(Paths.get("Images/Jeu/Cases/Square_normal.png"));break;
				case ROUGE : this.maTexture.loadFromFile(Paths.get("Images/Jeu/Cases/Square_rouge.png"));break;
				}break;
			case("Teleporter_Case") : this.maTexture.loadFromFile(Paths.get("Images/Jeu/Cases/case_teleporteur/Case_pointeur_"+ this.indice_tele+".png"));
			this.indice_tele++;
			if( this.indice_tele>=9){
				this.indice_tele=0;
			}break;
			case("Illuminated_Case") :
				if(((Illuminated_Case) cases).get_active()){
					this.maTexture.loadFromFile(Paths.get("Images/Jeu/Cases/Square_allume.png"));
				}else{
					this.maTexture.loadFromFile(Paths.get("Images/Jeu/Cases/Square_allumable2.png"));
				}break;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void display_world(World world){

	}

	public void display_terrain(Terrain terrain) throws IOException{

	}

	public void drawBackground(){
		try {
			this.maTextureBackground.loadFromFile(Paths.get("Images/Jeu/background2.jpg"));
			this.monSpriteBackground.setTexture(this.maTextureBackground);
			Menu.app.draw(this.monSpriteBackground);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * Affiche le terrain
	 */
	public void drawGrilleISO(){
		int Xtemp,Ytemp;
		int taille_abs =  World.currentWorld.get_terrain(0).get_terrain()[0].length;
		int taille_ord =  World.currentWorld.get_terrain(0).get_terrain().length;
		//
		//		System.out.println("***************");
		for(int X=taille_abs-1;X>=0;X--){
			Ytemp=0;//taille_ord-1;
			//	System.out.println("X = "+X);
			for(Xtemp=X;Xtemp<taille_abs;Xtemp++){
				if(Ytemp<0){
					break;
				}
				//	System.out.println("(Xtemp,Ytemp) = ("+Xtemp+","+Ytemp+")");
				this.affichageISO(Xtemp,Ytemp);
				Ytemp++;
			}
		}
		for(int Y=1;Y<taille_ord;Y++){//1 car on a géré le cas 0 avec X juste au dessus
			Xtemp=0;
			//		System.out.println("Y = "+Y);
			for(Ytemp=Y;Ytemp<taille_ord;Ytemp++){
				if(Xtemp>=taille_abs){
					break;
				}
				this.affichageISO(Xtemp,Ytemp);
				//		System.out.println("(Xtemp,Ytemp) = ("+Xtemp+","+Ytemp+")");
				Xtemp++;
			}
		}
	}

	/**
	 * Si la hauteur de la case est > 1, affiche toutes les cases en dessous
	 * @param X coordonn�es x de la case
	 * @param Y coordonn�es y de la case
	 */
	public void affichageISO(int X, int Y){

		try {
			abstr_Case Ma_case = World.currentWorld.get_terrain(0).get_case(X,Y);
			int hauteur_max = Ma_case.get_hauteur();
			int taille_abs =  World.currentWorld.get_terrain(0).get_terrain()[0].length;
			int taille_ord =  World.currentWorld.get_terrain(0).get_terrain().length;
			int PosX = Menu.WIDTH/2 +59*(Y+X)-taille_abs*60-180;
			int PosY = Menu.HEIGHT/2 +18*(Y-X)-taille_ord*18+100;

			for(int hauteur=1; hauteur<hauteur_max;hauteur++){
				try {
					this.maTexture.loadFromFile(Paths.get("Images/Jeu/Cases/Square_empile.png"));
					this.monSprite.setTexture(this.maTexture);
					this.monSprite.setPosition(PosX,PosY-26*hauteur);
					Menu.app.draw(this.monSprite);
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
			this.typeCases(Ma_case);
			this.monSprite.setTexture(this.maTexture);
			this.monSprite.setPosition(PosX,PosY-26*hauteur_max);
			Menu.app.draw(this.monSprite);
			abstr_Robot [] rob = w.get_liste_robot();
			for(int i = 0;i<rob.length;i++){
				int x = rob[i].getCurrent_Case().get_coordonnees().get_x();
				int y = rob[i].getCurrent_Case().get_coordonnees().get_y();
				//Si le pingouin est sur cette case, alors on l'affiche à la hauteur maximale de celle-ci
				if ((x == X) && (y == Y)){
					this.drawPerso(rob[i]);
				}
			}

		} catch (UnreachableCase e1) {
			System.out.println(e1.getMessage());
		}
	}

}
