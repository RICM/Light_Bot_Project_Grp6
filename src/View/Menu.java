package View;

import java.io.IOException;
import java.nio.file.Paths;

import observer.controller.Controller;

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

public class Menu {

	protected static RenderWindow app = new RenderWindow(new VideoMode(Niveaux.WIDTH, Niveaux.HEIGHT),"Lightbot");
	protected Texture texture_Background = new Texture();
	protected Sprite sprite_Background = new Sprite();
	protected Texture texture_Play= new Texture();
	protected Sprite sprite_Play = new Sprite();
	protected static View camera = new View();
	protected static final int WIDTH = 1200;
	protected static final int HEIGHT = 700;
	protected static Controller controller;
	protected Music song = new Music();

	public Menu(Controller acontroller){
		controller = acontroller;
		Menu.reset_cam();
		try {
			this.song.openFromFile(Paths.get("Song/theme.ogg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//this.song.play();
		this.song.setLoop(true);
		//while(Menu.app.isOpen()){
		Menu.app.clear();
		this.displayBackground();
		this.displayBtn();
		Menu.app.display();
		this.processEvent();
		//}
	}

	public void addController(Controller acontroller){
		controller = acontroller;
	}

	/**
	 * Affiche le bouton Jouer
	 */
	private void displayBtn() {
		// TODO Auto-generated method stub
		try {
			this.texture_Play.loadFromFile(Paths.get("Images/menu/play.png"));
			this.sprite_Play = new Sprite();
			this.sprite_Play.setTexture(this.texture_Play);
			this.sprite_Play.setPosition(Menu.app.getSize().x/2-75, Menu.app.getSize().y/2-75);
			Menu.app.draw(this.sprite_Play);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Affiche le background
	 */
	private void displayBackground() {
		// TODO Auto-generated method stub
		try {
			this.texture_Background.loadFromFile(Paths.get("Images/menu/back1.png"));
			this.sprite_Background.setTexture(this.texture_Background);

			Menu.app.draw(this.sprite_Background);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * D�tecte les entr�es claviers et souris
	 */
	private void processEvent() {
		// TODO Auto-generated method stub
		Menu.app.setKeyRepeatEnabled(false);
		Event e = Menu.app.waitEvent();
		//for(Event e : Menu.app.pollEvents()){

		if(e.type == Type.CLOSED){
			Menu.app.close();
		}
		else if(e.type == Event.Type.RESIZED){
			Menu.reset_cam();
			Menu.app.clear();
			this.displayBackground();
			this.displayBtn();
			Menu.app.display();
			this.processEvent();
		}

		//		if (Keyboard.isKeyPressed(Key.DOWN)){
		//			Menu.camera.zoom(0.5f);
		//			Menu.app.setView(Menu.camera);
		//		}
		//
		//		if (Keyboard.isKeyPressed(Key.UP)){
		//			Menu.camera.zoom(2f);
		//			Menu.app.setView(Menu.camera);
		//		}
		//
		//		if (Keyboard.isKeyPressed(Key.RIGHT)){
		//			Menu.camera.rotate(-45);;
		//			Menu.app.setView(Menu.camera);
		//		}
		//
		//		if (Keyboard.isKeyPressed(Key.LEFT)){
		//			Menu.camera.rotate(45);;
		//			Menu.app.setView(Menu.camera);
		//		}

		else if (e.type == Event.Type.MOUSE_BUTTON_RELEASED) {
			e.asMouseEvent();
			Vector2i pos = Mouse.getPosition(Menu.app);
			Vector2f poss = Menu.app.mapPixelToCoords(pos);
			System.out.println(poss.x+" "+poss.y);
			this.btnClick(poss);
		}
		else
			this.processEvent();

		//		}
	}

	/**
	 *	R�initialise la cam�ra
	 */
	protected static void reset_cam() {
		int x = Menu.app.getSize().x;
		int y = Menu.app.getSize().y;
		Menu.camera.setSize(x, y);
		Menu.camera.setCenter(x/2, y/2);
		Menu.app.setView(Menu.camera);
	}

	/**
	 * Regarde si le clique pass� en param�tre est sur le bouton Jouer
	 * @param pos Coordonn�es du clique souris
	 */
	private void btnClick(Vector2f pos) {
		// TODO Auto-generated method stub
		float x = pos.x;
		float y = pos.y;
		FloatRect rect = this.sprite_Play.getGlobalBounds();
		if(x>=rect.left && x<=rect.left+rect.width &&
				y>=rect.top && y<=rect.top+rect.height){
			Theme niv = new Theme(controller);
		}
		else
			this.processEvent();
	}
}
