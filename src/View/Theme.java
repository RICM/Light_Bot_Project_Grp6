package View;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;

import observer.controller.Controller;

import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.Event.Type;

public class Theme {

	protected static Controller controller;
	protected Texture texture_btnMenu = new Texture();
	protected Texture texture_Background = new Texture();

	protected Sprite sprite_btnMenu = new Sprite();
	protected Sprite sprite_Background = new Sprite();
	protected Sprite sprite_Theme = new Sprite();

	protected String tab [] = {"Tutoriels","Conditions","Pointeurs","Dual_mode"};
	protected Texture texture_Theme [] = new Texture[4];

	protected HashMap<Sprite,String> listSprite = new HashMap<Sprite, String>();


	public Theme(Controller acontroller){
		controller = acontroller;
		Menu.reset_cam();
		try {
			this.texture_Background.loadFromFile(Paths.get("Images/selectLvl/back.jpg"));
			this.texture_btnMenu.loadFromFile(Paths.get("Images/selectLvl/menu.png"));
			for(int i=0; i<this.tab.length;i++){
				this.texture_Theme[i] = new Texture();
				this.texture_Theme[i].loadFromFile(Paths.get("Images/selectLvl/"+this.tab[i]+".png"));
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		while(Menu.app.isOpen()){
			Menu.app.clear();
			this.displayBackground();
			this.displayBtn();
			this.displayTheme();
			Menu.app.display();
			this.processEvent();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	protected void processEvent() {
		for(Event e : Menu.app.pollEvents()){
			if(e.type == Type.CLOSED){
				System.out.println("test");
				Menu.app.close();
			}

			if(e.type == Event.Type.RESIZED){
				Menu.reset_cam();
			}

			if (e.type == Event.Type.MOUSE_BUTTON_RELEASED) {
				Vector2i pos = Mouse.getPosition(Menu.app);
				Vector2f click = Menu.app.mapPixelToCoords(pos);
				e.asMouseEvent();
				System.out.println(pos.x+" "+pos.y);
				this.btnClick(click);
			}
		}

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

				if(this.listSprite.get(s).equals("Menu")){
					new Menu(controller);
					break;
				}
				else if(this.listSprite.get(s).equals("Tutoriels")){
					new Niveaux(controller,"T");
					break;
				}
				else if(this.listSprite.get(s).equals("Conditions")){
					new Niveaux(controller,"C");
					break;
				}
				else if(this.listSprite.get(s).equals("Dual_mode")){
					new Niveaux(controller,"D");
					break;
				}
				else if(this.listSprite.get(s).equals("Pointeurs")){
					new Niveaux(controller,"P");
					break;
				}
			}
		}
	}

	/**
	 * Affiche le background
	 */
	protected void displayBackground() {
		this.sprite_Background.setTexture(this.texture_Background);
		this.sprite_Background.setScale(0.7f, 0.7f);
		Menu.app.draw(this.sprite_Background);

	}

	/**
	 * Affiche les boutons
	 */
	protected void displayBtn() {
		//Affichage bouton menu
		this.sprite_btnMenu.setTexture(this.texture_btnMenu);
		this.sprite_btnMenu.setPosition(30,5);
		Menu.app.draw(this.sprite_btnMenu);
		this.listSprite.put(this.sprite_btnMenu, "Menu");
	}

	protected void displayTheme() {
		for(int i = 0;i<this.tab.length;i++){
			this.sprite_Theme.setTexture(this.texture_Theme[i]);
			this.sprite_Theme.setPosition(250+(150+50)*i,350);
			this.listSprite.put(this.sprite_Theme,this.tab[i]);
			Menu.app.draw(this.sprite_Theme);
			this.sprite_Theme = new Sprite();
		}
	}

}
