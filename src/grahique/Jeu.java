package grahique;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.Event.Type;

public class Jeu {

	public static RenderWindow app = new RenderWindow(new VideoMode(1200, 700),"Lightbot");
	public Texture maTexture = new Texture();
	public Sprite monSprite = new Sprite();
	public Texture maTextureBackground = new Texture();
	public Sprite monSpriteBackground = new Sprite();
	public Texture maTexturePerso = new Texture();
	public Sprite monSpritePerso = new Sprite();
	public static int x = 200;
	public static int y = 50;
	private final Menu2 frame;

	public static void processEvent(){
		app.setKeyRepeatEnabled(false);
		for(Event e : app.pollEvents()){
			if(e.type == Type.CLOSED){
				app.close();
			}

			if (Keyboard.isKeyPressed(Key.LEFT)){
				if(x>200)
					x -= 100;
			}
			if (Keyboard.isKeyPressed(Key.RIGHT)){
				if(x<700)
					x += 100;
			}
			if (Keyboard.isKeyPressed(Key.UP)){
				if(y>0)
					y -= 50;
			}
			if (Keyboard.isKeyPressed(Key.DOWN)){
				if(y<250)
					y += 50;
			}



		}
	}

	public void drawGrille(){
		try {
			maTextureBackground.loadFromFile(Paths.get("background.jpg"));
			monSpriteBackground.setTexture(maTextureBackground);
			app.draw(monSpriteBackground);
			for(int i=0;i<10;i++){
				for(int j=0; j<10;j++){
					maTexture.loadFromFile(Paths.get("square.png"));
					monSprite.setTexture(maTexture);
					monSprite.setPosition(80+75*j, 80+45*i);
					app.draw(monSprite);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} // on charge la texture qui se trouve dans notre dossier assets
	}

	public void drawPerso(){
		try {
			maTexturePerso.loadFromFile(Paths.get("perso.gif"));
			monSpritePerso.setTexture(maTexturePerso);
			monSpritePerso.setPosition(x+40*3, y+25*4);
			app.draw(monSpritePerso);


		} catch (IOException e) {
			e.printStackTrace();
		} // on charge la texture qui se trouve dans notre dossier assets
	}

	public Jeu(Menu2 fenetre){
		this.frame=fenetre;
		this.frame.setVisible(false);
		while(app.isOpen()){
			processEvent();
			drawGrille();
			drawPerso();
			app.display();
			//System.out.println(Mouse.getPosition().x + " " + Mouse.getPosition().y);
		}
	}


}
