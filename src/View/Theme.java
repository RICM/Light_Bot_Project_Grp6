package View;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;

import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.Event.Type;

import observer.controller.Controller;

public class Theme {

	protected static Controller controller;
	protected Texture texture_btnMenu = new Texture();
	protected Texture texture_Background = new Texture();

	protected Sprite sprite_btnMenu = new Sprite();
	protected Sprite sprite_Background = new Sprite();
	protected Sprite spriteTab[] = {new Sprite(),new Sprite(),new Sprite(),new Sprite()};

	protected Texture texture_btnFond = new Texture();
	protected Sprite sprite_btnFond = new Sprite();
	protected Texture texture_btnCredit = new Texture();
	protected Sprite sprite_btnCredit = new Sprite();
	protected Texture texture_btnCreditFond = new Texture();
	protected Sprite sprite_btnCreditFond = new Sprite();
	protected Texture texture_btnSound = new Texture();
	protected Sprite sprite_btnSound = new Sprite();

	protected String tab [] = {"Tutoriels","Conditions","Pointeurs","Dual_mode"};
	protected Texture texture_Theme [] = new Texture[4];

	protected HashMap<String,Sprite> listSprite = new HashMap<String,Sprite>();


	protected static int indiceInterface=-40;
	protected static boolean deroule = false;
	protected static boolean renroule = false;
	protected boolean first_round = true;
	protected boolean afficheCredit = false;

	public Theme(Controller acontroller){
		controller = acontroller;
		Menu.reset_cam();
		try {
			this.texture_Background.loadFromFile(Paths.get("Images/selectLvl/back.jpg"));
			this.texture_btnMenu.loadFromFile(Paths.get("Images/selectLvl/Back.png"));
			this.texture_btnFond.loadFromFile(Paths.get("Images/Jeu/BoutonsInterface/Interface_Fond.png"));
			this.texture_btnCredit.loadFromFile(Paths.get("Images/selectLvl/Credit.png"));
			this.texture_btnCreditFond.loadFromFile(Paths.get("Images/selectLvl/CreditFond.png"));
			this.texture_btnSound.loadFromFile(Paths.get("Images/Jeu/BoutonsInterface/Interface_Sound.png"));
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
			if(!this.afficheCredit){
				this.displayTheme();
			}else{
				for(int i = 0;i<this.tab.length;i++){
					this.listSprite.remove(this.tab[i]);
				}
			}
			Menu.app.display();
			this.processEvent();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Detecte les entrees claviers et souris
	 */
	protected void processEvent() {
		for(Event e : Menu.app.pollEvents()){
			if(e.type == Type.CLOSED){
				Menu.app.close();
			}

			if(e.type == Event.Type.RESIZED){
				Menu.reset_cam();
			}

			if (e.type == Event.Type.MOUSE_BUTTON_RELEASED) {
				Vector2i pos = Mouse.getPosition(Menu.app);
				Vector2f click = Menu.app.mapPixelToCoords(pos);
				e.asMouseEvent();
				this.btnClick(click);
			}

			Vector2i pos = Mouse.getPosition(Menu.app);
			float x = pos.x;
			float y = pos.y;
			Iterator<String> keySetIterator = this.listSprite.keySet().iterator();
			while(keySetIterator.hasNext()){

				String action = keySetIterator.next();
				FloatRect rect = this.listSprite.get(action).getGlobalBounds();

				if(action.equals("Fond")){
					if(x>=rect.left && x<=rect.left+rect.width && y>=rect.top && y<=rect.top+rect.height){
						deroule = true;
						break;
					}else{
						renroule = true;
						break;
					}
				}
			}
		}

	}


	/**
	 * Regarde si le clique en parametre est sur l'un des boutons
	 * @param pos : Coordonnees du clique souris
	 */
	private void btnClick(Vector2f pos) {
		float x = pos.x;
		float y = pos.y;
		Iterator<String> keySetIterator = this.listSprite.keySet().iterator();
		while(keySetIterator.hasNext()){
			String s = keySetIterator.next();
			FloatRect rect = this.listSprite.get(s).getGlobalBounds();
			if(x>=rect.left && x<=rect.left+rect.width &&
					y>=rect.top && y<=rect.top+rect.height){

				if(s.equals("Menu")){
					new Menu(controller);
					break;
				}
				else if(s.equals("Tutoriels")){
					new Niveaux(controller,"T");
					break;
				}
				else if(s.equals("Conditions")){
					new Niveaux(controller,"C");
					break;
				}
				else if(s.equals("Dual_mode")){
					new Niveaux(controller,"D");
					break;
				}
				else if(s.equals("Pointeurs")){
					new Niveaux(controller,"P");
					break;
				}
				else if(s.equals("Credit")){
					this.afficheCredit = true;
				}
				else if(s.equals("Sound")){
					if(Menu.IsPlaying){
						Menu.song.pause();
						Menu.IsPlaying = false;
					}else{
						Menu.song.play();
						Menu.IsPlaying = true;
					}
					break;
				}
				else if(s.equals("CreditFond")){
					this.afficheCredit = false;
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

		if(deroule){
			if(indiceInterface>=0){
				deroule = false;
			}else{
				indiceInterface +=2;
			}
		}
		if(renroule && !deroule){
			if(indiceInterface>-40){
				indiceInterface -=2;
			}else{
				renroule = false;
			}
		}

		//Affichage bouton Fond
		this.sprite_btnFond.setTexture(this.texture_btnFond);
		this.sprite_btnFond.setPosition( Menu.getWidth()/2-100,indiceInterface);
		Menu.app.draw(this.sprite_btnFond);

		//Affichage bouton Son
		this.sprite_btnSound.setTexture(this.texture_btnSound);
		this.sprite_btnSound.setPosition( Menu.getWidth()/2-100+50,indiceInterface);
		Menu.app.draw(this.sprite_btnSound);

		//Affichage bouton Credit
		this.sprite_btnCredit.setTexture(this.texture_btnCredit);
		this.sprite_btnCredit.setPosition( Menu.getWidth()/2-100+100,indiceInterface);
		Menu.app.draw(this.sprite_btnCredit);

		//Affichage bouton Credit
		if(this.afficheCredit){
			this.sprite_btnCreditFond.setTexture(this.texture_btnCreditFond);
			this.sprite_btnCreditFond.setPosition( 350, 50);
			Menu.app.draw(this.sprite_btnCreditFond);
		}

		if(this.first_round){
			this.listSprite.put("Fond",this.sprite_btnFond);
			this.listSprite.put("Credit",this.sprite_btnCredit);
			this.listSprite.put("Sound",this.sprite_btnSound);
			this.listSprite.put("CreditFond",this.sprite_btnCreditFond);
			this.first_round = false;
		}
	}

	/**
	 * Affiche les themes possibles
	 */
	protected void displayTheme() {
		for(int i = 0;i<this.tab.length;i++){
			this.spriteTab[i].setTexture(this.texture_Theme[i]);
			this.spriteTab[i].setPosition(250+(150+50)*i,350);
			this.listSprite.putIfAbsent(this.tab[i],this.spriteTab[i]);
			Menu.app.draw(this.spriteTab[i]);
		}
	}

}
