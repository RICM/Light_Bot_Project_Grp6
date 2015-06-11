package View;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.Mouse;
import org.jsfml.window.Mouse.Button;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.Event.Type;

public class Menu {

	protected static RenderWindow app = new RenderWindow(new VideoMode(Niveaux.WIDTH, Niveaux.HEIGHT),"Lightbot");
	protected Texture texture_Background = new Texture();
	protected Sprite sprite_Background = new Sprite();
	protected Texture texture_Play= new Texture();
	protected Sprite sprite_Play = new Sprite();
	protected View camera = new View();
	protected static final int WIDTH = 1200;
	protected static final int HEIGHT = 700;

	public Menu(){
		this.camera.setSize(new Vector2f(WIDTH, HEIGHT));
		this.camera.setCenter(WIDTH/2, HEIGHT/2);
		Menu.app.setView(this.camera);
		while(Menu.app.isOpen()){
			Menu.app.clear();
			this.processEvent();
			this.displayBackground();
			this.displayBtn();
			Menu.app.display();
		}
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
	 * Détecte les entrées claviers et souris
	 */
	private void processEvent() {
		// TODO Auto-generated method stub
		Menu.app.setKeyRepeatEnabled(false);
		for(Event e : Menu.app.pollEvents()){

			if(e.type == Type.CLOSED){
				Menu.app.close();
			}
			if(e.type == Event.Type.RESIZED){
				int x = Menu.app.getSize().x;
				int y = Menu.app.getSize().y;
				this.camera.setSize(x, y);
				this.camera.setCenter(x/2, y/2);
				Menu.app.setView(this.camera);
			}

			if (Keyboard.isKeyPressed(Key.DOWN)){
				this.camera.zoom(0.5f);
				Menu.app.setView(this.camera);
			}

			if (Keyboard.isKeyPressed(Key.UP)){
				this.camera.zoom(2f);
				Menu.app.setView(this.camera);
			}

			if (Keyboard.isKeyPressed(Key.RIGHT)){
				this.camera.rotate(-45);;
				Menu.app.setView(this.camera);
			}

			if (Keyboard.isKeyPressed(Key.LEFT)){
				this.camera.rotate(45);;
				Menu.app.setView(this.camera);
			}

			if (e.type == Event.Type.MOUSE_BUTTON_PRESSED && Mouse.isButtonPressed(Button.LEFT)) {
				e.asMouseEvent();
				Vector2i pos = Mouse.getPosition(Menu.app);
				Vector2f poss = Menu.app.mapPixelToCoords(pos);
				System.out.println(poss.x+" "+poss.y);
				this.btnClick(poss);
			}

		}
	}

	/**
	 * Regarde si le clique passé en paramètre est sur le bouton Jouer
	 * @param pos Coordonnées du clique souris
	 */
	private void btnClick(Vector2f pos) {
		// TODO Auto-generated method stub
		float x = pos.x;
		float y = pos.y;
		FloatRect rect = this.sprite_Play.getGlobalBounds();
		if(x>=rect.left && x<=rect.left+rect.width &&
				y>=rect.top && y<=rect.top+rect.height){
			new Niveaux();
		}
	}
}
