package observable.grahique;


import java.awt.Frame;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JOptionPane;

import observable.action.TurnLeft;
import observable.action.TurnRIght;
import observable.map.Empty_Case;
import observable.map.Normal_Case;
import observable.map.Painted_Case;
import observable.map.Terrain;
import observable.map.World;
import observable.map.abstr_Case;
import observable.robot.Orientation;
import observable.robot.abstr_Robot;

import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.Mouse;
import org.jsfml.window.VideoMode;
import org.jsfml.window.WindowStyle;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.Event.Type;

import observable.robot.Orientation;
import observable.robot.Robot;
import observable.robot.abstr_Robot;
import observable.action.MoveForward;
import observable.action.TurnLeft;
import observable.action.TurnRIght;
import couleur.Couleur;
import exception.ActionEx;
import exception.MouvementEx;
import exception.UnreachableCase;

public class Jeu {

	public static RenderWindow app = new RenderWindow(new VideoMode(1200, 700),"Lightbot",WindowStyle.CLOSE);
	public Texture maTexture = new Texture();
	public Sprite monSprite = new Sprite();
	public Texture maTextureBackground = new Texture();
	public Sprite monSpriteBackground = new Sprite();
	public Texture maTexturePerso = new Texture();
	public Sprite monSpritePerso = new Sprite();
	public ArrayList<Texture> liste_text = new ArrayList<Texture>();
	public static HashMap<Sprite,Orientation.orientation> liste_sprite = new HashMap<Sprite, Orientation.orientation>();
	private int level;
	private Frame f;

	private static World w = World.currentWorld;
	private static abstr_Robot r = w.get_robot(0);
	private Terrain t = w.get_terrain(this.level);
	private abstr_Case[][] cases = this.t.get_terrain();
	private int width_case = 80;
	private int height_case = 50;
	private static final int NB_MAX_CASE = 10;

	public static void processEvent(){
		app.setKeyRepeatEnabled(false);
		for(Event e : app.pollEvents()){
			if(e.type == Type.CLOSED){
				app.close();
				Menu2 game = new Menu2();
				game.setVisible(true);
				game.setLocationRelativeTo(null);
				app.close();
			}

			if (Keyboard.isKeyPressed(Key.LEFT)){
				r.setOrientation(Orientation.orientation.LEFT);
				try {
					MoveForward.move_forward().execute(r);
				} catch (MouvementEx e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnreachableCase e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if (Keyboard.isKeyPressed(Key.RIGHT)){
				r.setOrientation(Orientation.orientation.RIGHT);
				try {
					MoveForward.move_forward().execute(r);
				} catch (MouvementEx e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnreachableCase e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if (Keyboard.isKeyPressed(Key.UP)){
				r.setOrientation(Orientation.orientation.TOP);
				try {
					MoveForward.move_forward().execute(r);
				} catch (MouvementEx e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnreachableCase e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if (Keyboard.isKeyPressed(Key.DOWN)){
				r.setOrientation(Orientation.orientation.BOT);
				try {
					MoveForward.move_forward().execute(r);
				} catch (MouvementEx e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnreachableCase e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			if (e.type == Event.Type.MOUSE_BUTTON_PRESSED) {
				e.asMouseEvent();
				Vector2i pos = Mouse.getPosition(app);
				System.out.println(pos.x+" "+pos.y);
				try {
					detect_move(pos);
				} catch (ActionEx e1) {
					e1.printStackTrace();
				}
			}

		}
	}

	private static void detect_move(Vector2i pos) throws ActionEx {
		int x = pos.x;
		int y = pos.y;
		Iterator<Sprite> keySetIterator = liste_sprite.keySet().iterator();
		while(keySetIterator.hasNext()){
			Sprite s = keySetIterator.next();
			FloatRect rect = s.getGlobalBounds();
			if(x>=rect.left && x<=rect.left+rect.width &&
					y>=rect.top && y<=rect.top+rect.height){
				if(liste_sprite.get(s) == Orientation.orientation.LEFT)
					r.add_Action_User_Actions(TurnLeft.turn_left());
				else if(liste_sprite.get(s) == Orientation.orientation.RIGHT)
					r.add_Action_User_Actions(TurnRIght.turn_right());
			}
		}
	}

	public void draw_popup(String msg){
		JOptionPane.showMessageDialog(null, msg);
	}

	public void drawGrille(){
		try {
			this.maTextureBackground.loadFromFile(Paths.get("background.jpg"));
			this.monSpriteBackground.setTexture(this.maTextureBackground);
			app.draw(this.monSpriteBackground);
			for(int i=0;i<cases.length;i++){
				for(int j=0; j<cases[i].length;j++){
					typeCases(cases[i][j]);

					monSprite.setTexture(maTexture);
					monSprite.setPosition(80+(width_case-5)*(j+((NB_MAX_CASE-cases[i].length)/2)), 80+(height_case-5)*(i+((NB_MAX_CASE-cases.length)/2)));
					app.draw(monSprite);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} // on charge la texture qui se trouve dans notre dossier assets
	}

	public void drawPerso(int X, int Y){
		try {
			if(r.getOrientation() == Orientation.orientation.BOT)
				maTexturePerso.loadFromFile(Paths.get("gif/images_fixes/3.png"));
			else if(r.getOrientation() == Orientation.orientation.LEFT)
				maTexturePerso.loadFromFile(Paths.get("gif/images_fixes/15.png"));
			else if(r.getOrientation() == Orientation.orientation.RIGHT)
				maTexturePerso.loadFromFile(Paths.get("gif/images_fixes/7.png"));
			else if(r.getOrientation() == Orientation.orientation.TOP)
				maTexturePerso.loadFromFile(Paths.get("gif/images_fixes/11.png"));

			this.monSpritePerso.setTexture(this.maTexturePerso);
			this.monSpritePerso.setPosition(X,Y);
			app.draw(this.monSpritePerso);


		} catch (IOException e) {
			e.printStackTrace();
		} // on charge la texture qui se trouve dans notre dossier assets
	}
	public void draw_bouton(){
		try {
			for(int i=0;i<3;i++){
				Texture maTextureBouton = new Texture();
				Sprite monSpriteBouton = new Sprite();
				if(i==0){
					maTextureBouton.loadFromFile(Paths.get("bouton/rota_gauche.png"));
					liste_sprite.put(monSpriteBouton,Orientation.orientation.LEFT);
				}
				else if(i==1){
					maTextureBouton.loadFromFile(Paths.get("bouton/rota_droite.png"));
					liste_sprite.put(monSpriteBouton,Orientation.orientation.RIGHT);
				}
				else{
					maTextureBouton.loadFromFile(Paths.get("bouton/tout_droit.png"));
					liste_sprite.put(monSpriteBouton,null);
				}
				monSpriteBouton.setTexture(maTextureBouton);
				monSpriteBouton.setPosition(100+100*i,550);
				app.draw(monSpriteBouton);
				this.liste_text.add(maTextureBouton);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Jeu(int lvl,Frame frame){
		this.level = lvl;
		this.f = frame;
		this.f.setVisible(false);
		while(app.isOpen()){
			processEvent();
			this.drawGrilleISO();
			//drawPerso();
			this.draw_bouton();
			app.display();
			//System.out.println(Mouse.getPosition().x + " " + Mouse.getPosition().y);
		}
	}

	public void typeCases(abstr_Case cases){
		try{
			if(cases instanceof Normal_Case){
				this.maTexture.loadFromFile(Paths.get("Cases/Square_allumé.png"));
			}
			else if(cases instanceof Empty_Case){
				this.maTexture.loadFromFile(Paths.get("Cases/Square_vide.png"));
			}
			else if(cases instanceof Painted_Case){
				if(cases.get_couleur()== Couleur.VERT)
					maTexture.loadFromFile(Paths.get("Cases/Square_vert.png"));
				else if(cases.get_couleur()== Couleur.GRIS)
					maTexture.loadFromFile(Paths.get("Cases/Square_normal.png"));
				else if(cases.get_couleur()== Couleur.ROUGE)
					maTexture.loadFromFile(Paths.get("Cases/Square_rouge.png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} // on charge la texture qui se trouve dans notre dossier assets


	}

	public void drawGrilleISO(){
		try{
			this.maTextureBackground.loadFromFile(Paths.get("background.jpg"));
			this.monSpriteBackground.setTexture(this.maTextureBackground);
			app.draw(this.monSpriteBackground);

			int Xtemp,Ytemp;
			int taille_abs =  World.currentWorld.get_terrain(0).get_terrain()[0].length;
			int taille_ord =  World.currentWorld.get_terrain(0).get_terrain().length;

			System.out.println("***************");
			for(int X=taille_abs-1;X>=0;X--){
				Ytemp=0;//taille_ord-1;
				System.out.println("X = "+X);
				for(Xtemp=X;Xtemp<taille_abs;Xtemp++){
					if(Ytemp<0){
						break;
					}
					System.out.println("(Xtemp,Ytemp) = ("+Xtemp+","+Ytemp+")");
					this.affichageISO(Xtemp,Ytemp);
					Ytemp++;
				}
			}
			for(int Y=1;Y<taille_ord;Y++){//-2 car on a géré le cas 0 avec X juste au dessus
				Xtemp=0;
				System.out.println("Y = "+Y);
				for(Ytemp=Y;Ytemp<taille_ord;Ytemp++){
					if(Xtemp>=taille_abs){
						break;
					}
					this.affichageISO(Xtemp,Ytemp);
					System.out.println("(Xtemp,Ytemp) = ("+Xtemp+","+Ytemp+")");
					Xtemp++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} // on charge la texture qui se trouve dans notre dossier assets
	}

	public void affichageISO(int X, int Y){

		try {
			abstr_Case Ma_case = World.currentWorld.get_terrain(0).get_case(X,Y);
			int x = r.getCurrent_Case().get_coordonnees().get_x();
			int y = r.getCurrent_Case().get_coordonnees().get_y();
			int hauteur_max = Ma_case.get_hauteur();
			int taille_abs =  World.currentWorld.get_terrain(0).get_terrain()[0].length;
			int taille_ord =  World.currentWorld.get_terrain(0).get_terrain().length;
			int PosX = app.getSize().x/2 +59*(Y+X)-taille_abs*60;
			int PosY = app.getSize().y/2 +18*(Y-X)-taille_ord*18;
			
			for(int hauteur=1; hauteur<hauteur_max;hauteur++){
				try {
					this.maTexture.loadFromFile(Paths.get("Cases/Square_normal.png"));
					this.monSprite.setTexture(this.maTexture);
					this.monSprite.setPosition(PosX,PosY-26*hauteur);
					app.draw(this.monSprite);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			this.typeCases(Ma_case);
			this.monSprite.setTexture(this.maTexture);
			this.monSprite.setPosition(PosX,PosY-26*hauteur_max);
			app.draw(this.monSprite);
			
			//Si le pingouin est sur cette case, alors on l'affiche à la hauteur maximale de celle-ci
			if ((x == X) && (y == Y)){
				drawPerso(PosX+30+10,PosY+25);
			}
			
		} catch (UnreachableCase e1) {
			e1.printStackTrace();
		}
	}

}
