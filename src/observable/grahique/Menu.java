package observable.grahique;

import java.io.IOException;
import java.nio.file.Paths;

import javax.swing.JOptionPane;

import org.jsfml.graphics.ConstView;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Mouse;
import org.jsfml.window.Mouse.Button;
import org.jsfml.window.VideoMode;
import org.jsfml.window.WindowStyle;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.Event.Type;

public class Menu {

	protected static RenderWindow app = new RenderWindow(new VideoMode(Niveaux.WIDTH, Niveaux.HEIGHT),"Lightbot");
	protected Texture texture_Background = new Texture();
	protected Sprite sprite_Background = new Sprite();
	protected Texture texture_Play= new Texture();
	protected Sprite sprite_Play = new Sprite();
//	protected View camera = new View();
	protected static final int WIDTH = 1200;
	protected static final int HEIGHT = 700;

	public Menu(){
//		Menu.app.setView(camera);
		while(Menu.app.isOpen()){
			this.processEvent();
			this.displayBackground();
			this.displayBtn();
			Menu.app.display();
		}
	}

	private void displayBtn() {
		// TODO Auto-generated method stub
		try {
			this.texture_Play.loadFromFile(Paths.get("menu/play.png"));
			sprite_Play = new Sprite();
			this.sprite_Play.setTexture(this.texture_Play);
			this.sprite_Play.setPosition(Menu.WIDTH/2-75, Menu.HEIGHT/2-75);
			Menu.app.draw(this.sprite_Play);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void displayBackground() {
		// TODO Auto-generated method stub
		try {
			this.texture_Background.loadFromFile(Paths.get("menu/back1.png"));
			this.sprite_Background.setTexture(this.texture_Background);

			Menu.app.draw(this.sprite_Background);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void processEvent() {
		// TODO Auto-generated method stub
		Menu.app.setKeyRepeatEnabled(false);
		for(Event e : Menu.app.pollEvents()){

			if(e.type == Type.CLOSED){
				Menu.app.close();
			}
			if(e.type == Event.Type.RESIZED){
				
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

	private void btnClick(Vector2f pos) {
		// TODO Auto-generated method stub
		float x = pos.x;
		float y = pos.y;
		FloatRect rect = this.sprite_Play.getGlobalBounds();
		System.out.println("Sprite "+rect.left+" "+rect.width);
		if(x>=rect.left && x<=rect.left+rect.width &&
				y>=rect.top && y<=rect.top+rect.height){
			Niveaux niv = new Niveaux();
		}
	}
}
