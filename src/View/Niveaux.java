package View;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;

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

import observer.controller.Controller;

public class Niveaux {

	protected Texture texture_Background = new Texture();
	protected Sprite sprite_Background = new Sprite();
	protected Texture texture_btnNext = new Texture();
	protected Sprite sprite_btnNext= new Sprite();
	protected Texture texture_btnPrec = new Texture();
	protected Sprite sprite_btnPrec= new Sprite();
	protected Texture texture_btnLevel = new Texture();
	protected Sprite sprite_btnLevel = new Sprite();

	protected Texture texture_btnFond = new Texture();
	protected Sprite sprite_btnFond = new Sprite();
	protected Texture texture_btnHome = new Texture();
	protected Sprite sprite_btnHome = new Sprite();
	protected Texture texture_btnSound = new Texture();
	protected Sprite sprite_btnSound = new Sprite();

	protected HashMap<Sprite,String> listSprite = new HashMap<Sprite, String>();
	protected int level = 1;
	protected static final int WIDTH = 1200;
	protected static final int HEIGHT = 700;
	protected static Controller controller;
	protected static String theme;
	protected Font f = new Font();
	protected boolean first_round = true;

	protected static int indiceInterface=-40;
	protected static boolean deroule = false;
	protected static boolean renroule = false;


	/**
	 * Constructeur
	 * @param acontroller
	 * @param current_theme le theme choisi par l'utilisateur: T(tuto), D(dual), C(conditions), P(pointeurs)
	 */
	public Niveaux(Controller acontroller,String current_theme){
		this.setTheme(current_theme);
		controller = acontroller;

		try {
			this.texture_Background.loadFromFile(Paths.get("Images/selectLvl/back.jpg"));
			this.texture_btnNext.loadFromFile(Paths.get("Images/selectLvl/next100x100.png"));
			this.texture_btnPrec.loadFromFile(Paths.get("Images/selectLvl/prec100x100.png"));
			this.texture_btnSound.loadFromFile(Paths.get("Images/selectLvl/Sound.png"));
			this.texture_btnLevel.loadFromFile(Paths.get("Images/selectLvl/level.png"));
			this.f.loadFromFile(Paths.get("Fonts/BRUSHSCI.ttf"));
			this.texture_btnFond.loadFromFile(Paths.get("Images/Jeu/BoutonsInterface/Interface_Fond.png"));
			this.texture_btnHome.loadFromFile(Paths.get("Images/Jeu/BoutonsInterface/Interface_Home.png"));
			this.texture_btnSound.loadFromFile(Paths.get("Images/Jeu/BoutonsInterface/Interface_Sound.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Menu.reset_cam();
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

	/**
	 * setters
	 * @param acontroller le controlleur
	 */
	public void addController(Controller acontroller){
		controller = acontroller;
	}

	/**
	 * Detecte les entrees claviers et souris
	 */
	public void processEvent(){
		for(Event e : Menu.app.pollEvents()){
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
				//System.out.println(pos.x+" "+pos.y);
				this.btnClick(click);
			}

			Vector2i pos = Mouse.getPosition(Menu.app);
			float x = pos.x;
			float y = pos.y;
			Iterator<Sprite> keySetIterator = this.listSprite.keySet().iterator();
			while(keySetIterator.hasNext()){

				Sprite action = keySetIterator.next();
				FloatRect rect = action.getGlobalBounds();

				if(this.listSprite.get(action).equals("Fond")){
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
	 * @param pos Coordonnees du clique souris
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
				}
				else if(this.listSprite.get(s).equals("Next")){
					this.level++;

					int nb_max;
					if (this.theme.equals("D")){
						nb_max =7;
					} else if (this.theme.equals("C")){
						nb_max=5;
					}else{
						nb_max =4;
					}
					if(this.level>nb_max)
						this.level = nb_max;
				}
				else if(this.listSprite.get(s).equals("Home")){
					new Theme(controller);
				}
				else if(this.listSprite.get(s).equals("Sound")){
					if(Menu.IsPlaying){
						Menu.song.pause();
						Menu.IsPlaying = false;
					}else{
						Menu.song.play();
						Menu.IsPlaying = true;
					}
					break;
				}
				else if(this.listSprite.get(s).equals("Level")){
					String lvl = this.getTheme()+this.level;
					controller.getLevel(controller,lvl);
					new Jeu(this.level, controller);
				}
			}
		}
	}

	/**
	 * Affiche le background
	 */
	private void displayBackground() {
		this.sprite_Background.setTexture(this.texture_Background);
		this.sprite_Background.setScale(0.7f, 0.7f);
		Menu.app.draw(this.sprite_Background);
	}

	/**
	 * Affiche les boutons
	 */
	protected void displayBtn() {
		//Affichage bouton next
		this.sprite_btnNext.setTexture(this.texture_btnNext);
		this.sprite_btnNext.setPosition(Niveaux.WIDTH-250,700/2);
		Menu.app.draw(this.sprite_btnNext);

		//Affichage bouton prec
		this.sprite_btnPrec.setTexture(this.texture_btnPrec);
		this.sprite_btnPrec.setPosition(150,700/2);
		Menu.app.draw(this.sprite_btnPrec);

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
		this.sprite_btnFond.setPosition(WIDTH/2-100,indiceInterface);
		Menu.app.draw(this.sprite_btnFond);

		//Affichage bouton Son
		this.sprite_btnSound.setTexture(this.texture_btnSound);
		this.sprite_btnSound.setPosition(WIDTH/2-100+50,indiceInterface);
		Menu.app.draw(this.sprite_btnSound);

		//Affichage bouton Home
		this.sprite_btnHome.setTexture(this.texture_btnHome);
		this.sprite_btnHome.setPosition(WIDTH/2-100+100,indiceInterface);
		Menu.app.draw(this.sprite_btnHome);


		//Affichage bouton Jouer
		this.sprite_btnLevel.setTexture(this.texture_btnLevel);
		this.sprite_btnLevel.setPosition(Niveaux.WIDTH/2-330,700/2+210);
		Menu.app.draw(this.sprite_btnLevel);



		Text t = new Text(""+this.level,this.f,72);
		t.setColor(Color.BLACK);
		t.setPosition(Niveaux.WIDTH/2+18, 350);
		Menu.app.draw(t);

		if(this.first_round){
			this.listSprite.put(this.sprite_btnPrec,"Previous");
			this.listSprite.put(this.sprite_btnNext,"Next");
			this.listSprite.put(this.sprite_btnFond,"Fond");
			this.listSprite.put(this.sprite_btnHome,"Home");
			this.listSprite.put(this.sprite_btnSound,"Sound");
			this.listSprite.put(this.sprite_btnLevel, "Level");
			this.first_round = false;
		}

	}

	public static String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

}
