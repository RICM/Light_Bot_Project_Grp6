package View;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;

import observer.controller.Controller;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.Event.Type;

public class Niveaux {

	protected Texture texture_Background = new Texture();
	protected Sprite sprite_Background = new Sprite();
	protected Texture texture_btnNext = new Texture();
	protected Sprite sprite_btnNext= new Sprite();
	protected Texture texture_btnPrec = new Texture();
	protected Sprite sprite_btnPrec= new Sprite();
	protected Texture texture_btnMenu = new Texture();
	protected Sprite sprite_btnMenu= new Sprite();
	protected Texture texture_btnLevel = new Texture();
	protected Sprite sprite_btnLevel = new Sprite();
	protected HashMap<Sprite,String> listSprite = new HashMap<Sprite, String>();
	protected int level = 1;
	protected static final int WIDTH = 1200;
	protected static final int HEIGHT = 700;
	protected static Controller controller;
	protected String theme;


	public Niveaux(Controller acontroller,String current_theme){
		this.theme = current_theme;
		controller = acontroller;
		Menu.reset_cam();
		this.displayBackground();
		this.displayBtn();
		Menu.app.display();
		while(Menu.app.isOpen()){
			this.processEvent();
			//System.out.println(Mouse.getPosition().x + " " + Mouse.getPosition().y);
		}
	}

	public void addController(Controller acontroller){
		controller = acontroller;
	}

	/**
	 * D�tecte les entr�es claviers et souris
	 */
	public void processEvent(){
		Event e = Menu.app.waitEvent();
		//		for(e : Menu.app.pollEvents()){

		if(e.type == Type.CLOSED){
			Menu.app.close();
		}


		if(e.type == Event.Type.RESIZED){
			Menu.reset_cam();
		}

		if (e.type == Event.Type.MOUSE_BUTTON_RELEASED) {
			e.asMouseEvent();
			Vector2i pos = Mouse.getPosition(Menu.app);
			Vector2f click = Menu.app.mapPixelToCoords(pos);
			System.out.println(pos.x+" "+pos.y);
			this.btnClick(click);
		}
		//		}
	}

	/**
	 * Regarde si le clique pass� en param�tre est sur l'un des boutons
	 * @param pos Coordonn�es du clique souris
	 */
	private void btnClick(Vector2f pos) {
		float x = pos.x;
		float y = pos.y;
		Iterator<Sprite> keySetIterator = this.listSprite.keySet().iterator();
		while(keySetIterator.hasNext()){
			Sprite s = keySetIterator.next();
			FloatRect rect = s.getGlobalBounds();
			if(x>=rect.left && x<=rect.left+rect.width &&
					y>=rect.top && y<=rect.top+rect.height){
				if(this.listSprite.get(s).equals("Previous")){
					this.level--;
					if(this.level<1)
						this.level = 1;
					this.displayBackground();
					this.displayBtn();
					Menu.app.display();
				}
				else if(this.listSprite.get(s).equals("Next")){
					this.level++;
					if(this.level>4)
						this.level = 4;
					this.displayBackground();
					this.displayBtn();
					Menu.app.display();
				}
				else if(this.listSprite.get(s).equals("Menu")){
					new Theme(controller);
				}
				else if(this.listSprite.get(s).equals("Level")){
					String lvl = this.theme+this.level;
					Jeu jeu = new Jeu(this.level, controller);
				}
			}
		}
	}

	/**
	 * Affiche le background
	 */
	private void displayBackground() {
		try {
			this.texture_Background.loadFromFile(Paths.get("Images/selectLvl/back.jpg"));
			this.sprite_Background.setTexture(this.texture_Background);
			this.sprite_Background.setScale(0.7f, 0.7f);
			Menu.app.draw(this.sprite_Background);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Affiche les boutons
	 */
	protected void displayBtn() {
		try {
			//Affichage bouton next
			this.texture_btnNext.loadFromFile(Paths.get("Images/selectLvl/next100x100.png"));
			this.sprite_btnNext.setTexture(this.texture_btnNext);
			this.sprite_btnNext.setPosition(Niveaux.WIDTH-250,700/2);
			Menu.app.draw(this.sprite_btnNext);

			//Affichage bouton prec
			this.texture_btnPrec.loadFromFile(Paths.get("Images/selectLvl/prec100x100.png"));
			this.sprite_btnPrec.setTexture(this.texture_btnPrec);
			this.sprite_btnPrec.setPosition(150,700/2);
			Menu.app.draw(this.sprite_btnPrec);

			//Affichage bouton Back
			this.texture_btnMenu.loadFromFile(Paths.get("Images/selectLvl/Back.png"));
			this.sprite_btnMenu.setTexture(this.texture_btnMenu);
			this.sprite_btnMenu.setPosition(30,5);
			Menu.app.draw(this.sprite_btnMenu);

			//Affichage bouton Jouer
			this.texture_btnLevel.loadFromFile(Paths.get("Images/selectLvl/level.png"));
			this.sprite_btnLevel.setTexture(this.texture_btnLevel);
			this.sprite_btnLevel.setPosition(Niveaux.WIDTH/2-330,700/2+210);
			Menu.app.draw(this.sprite_btnLevel);

			Font f = new Font();
			f.loadFromFile(Paths.get("Fonts/BRUSHSCI.ttf"));
			Text t = new Text(""+this.level,f,72);
			t.setColor(Color.BLACK);
			t.setPosition(Niveaux.WIDTH/2+18, 350);
			Menu.app.draw(t);

			this.listSprite.put(this.sprite_btnPrec,"Previous");
			this.listSprite.put(this.sprite_btnNext,"Next");
			this.listSprite.put(this.sprite_btnMenu,"Menu");
			this.listSprite.put(this.sprite_btnLevel, "Level");


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
