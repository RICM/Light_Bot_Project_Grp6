package View;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.audio.Music;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Mouse;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.Event.Type;

import observer.controller.Controller;

public class Menu {

	protected static RenderWindow app = new RenderWindow(new VideoMode(Niveaux.WIDTH, Niveaux.HEIGHT),"Lightbot");
	protected Texture texture_Background = new Texture();
	protected Sprite sprite_Background = new Sprite();
	protected Texture texture_Play= new Texture();
	protected Sprite sprite_Play = new Sprite();
	protected static View camera = new View();
	private static final int WIDTH = 1200;
	private static final int HEIGHT = 700;
	protected static Controller controller;
	protected static Music song = new Music();
	protected static boolean IsPlaying = true;



	public Menu(Controller acontroller){
		app.setVerticalSyncEnabled(true);
		controller = acontroller;
		Menu.app.setVerticalSyncEnabled(true);
		try {
			this.texture_Background.loadFromFile(Paths.get("Images/menu/back1.png"));
			this.texture_Play.loadFromFile(Paths.get("Images/menu/play.png"));
			Menu.song.openFromFile(Paths.get("Song/theme.ogg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Menu.reset_cam();
		//this.song.play();
		this.song.setLoop(true);
		while(Menu.app.isOpen()){
			Menu.app.clear();
			this.displayBackground();
			this.displayBtn();
			Menu.app.display();
			this.processEvent();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void addController(Controller acontroller){
		controller = acontroller;
	}

	/**
	 * Affiche le bouton Jouer
	 */
	private void displayBtn() {
		this.sprite_Play.setTexture(this.texture_Play);
		this.sprite_Play.setPosition(Menu.app.getSize().x/2-75, Menu.app.getSize().y/2-75);
		Menu.app.draw(this.sprite_Play);
	}

	/**
	 * Affiche le background
	 */
	private void displayBackground() {
		this.sprite_Background.setTexture(this.texture_Background);
		Menu.app.draw(this.sprite_Background);
	}

	/**
	 * Detecte les entrees claviers et souris
	 */
	private void processEvent() {
		Menu.app.setKeyRepeatEnabled(false);
		for(Event e : Menu.app.pollEvents()){
			if(e.type == Type.CLOSED){
				//System.out.println("test");
				Menu.app.close();
			}

			else if(e.type == Event.Type.RESIZED){
				Menu.reset_cam();
			}

			else if (e.type == Event.Type.MOUSE_BUTTON_RELEASED) {
				e.asMouseEvent();
				Vector2i pos = Mouse.getPosition(Menu.app);
				//Transforme les coordonnees ecran en coordonnees fenetre
				Vector2f poss = Menu.app.mapPixelToCoords(pos);
				//System.out.println(poss.x+" "+poss.y);
				this.btnClick(poss);
			}


		}
	}

	/**
	 *	Reinitialise la camera
	 */
	protected static void reset_cam() {
		int x = Menu.app.getSize().x;
		int y = Menu.app.getSize().y;
		Menu.camera.setSize(x, y);
		Menu.camera.setCenter(x/2, y/2);
		Menu.app.setView(Menu.camera);
	}

	/**
	 * Regarde si le clique en parametre est sur le bouton Jouer
	 * @param pos : Coordonnees du clique souris
	 */
	private void btnClick(Vector2f pos) {
		float x = pos.x;
		float y = pos.y;
		FloatRect rect = this.sprite_Play.getGlobalBounds();
		if(x>=rect.left && x<=rect.left+rect.width &&
				y>=rect.top && y<=rect.top+rect.height){
			new Theme(controller);
		}
	}

	/****Getters****/
	public static int getWidth() {
		return WIDTH;
	}

	public static int getHeight() {
		return HEIGHT;
	}
}
