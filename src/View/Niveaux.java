package View;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

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
	protected ArrayList<Sprite> listSprite = new ArrayList<Sprite>();
	protected int level = 1;
	protected static final int WIDTH = 1200;
	protected static final int HEIGHT = 700;


	public Niveaux(){

		while(Menu.app.isOpen()){
			this.processEvent();
			this.displayBackground();
			this.displayBtn();
			Menu.app.display();
			//System.out.println(Mouse.getPosition().x + " " + Mouse.getPosition().y);
		}
	}

	/**
	 * Détecte les entrées claviers et souris
	 */
	public void processEvent(){
		Menu.app.setKeyRepeatEnabled(false);
		for(Event e : Menu.app.pollEvents()){

			if(e.type == Type.CLOSED){
				Menu.app.close();
			}


			if (e.type == Event.Type.MOUSE_BUTTON_PRESSED) {
				e.asMouseEvent();
				Vector2i pos = Mouse.getPosition(Menu.app);
				Vector2f click = Menu.app.mapPixelToCoords(pos);
				System.out.println(pos.x+" "+pos.y);
				this.btnClick(click);
			}

		}
	}

	/**
	 * Regarde si le clique passé en paramètre est sur l'un des boutons
	 * @param pos Coordonnées du clique souris
	 */
	private void btnClick(Vector2f pos) {
		float x = pos.x;
		float y = pos.y;
		int cpt = 0;

		for(Sprite s : this.listSprite){
			FloatRect rect = s.getGlobalBounds();
			if(x>=rect.left && x<=rect.left+rect.width &&
					y>=rect.top && y<=rect.top+rect.height){
				if(cpt == 0){
					this.level--;
					if(this.level<1)
						this.level = 1;
				}
				else if(cpt == 1){
					this.level++;
				}
				else if(cpt == 2){
					new Menu();
				}
				else if(cpt == 3){
					new Jeu(0);
				}
			}
			cpt++;

		}

	}

	/**
	 * Affiche le background
	 */
	private void displayBackground() {
		try {
			this.texture_Background.loadFromFile(Paths.get("selectLvl/back.jpg"));
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
			this.texture_btnNext.loadFromFile(Paths.get("selectLvl/next100x100.png"));
			this.sprite_btnNext.setTexture(this.texture_btnNext);
			this.sprite_btnNext.setPosition(Niveaux.WIDTH-250,700/2);
			Menu.app.draw(this.sprite_btnNext);

			//Affichage bouton prec
			this.texture_btnPrec.loadFromFile(Paths.get("selectLvl/prec100x100.png"));
			this.sprite_btnPrec.setTexture(this.texture_btnPrec);
			this.sprite_btnPrec.setPosition(150,700/2);
			Menu.app.draw(this.sprite_btnPrec);

			//Affichage bouton menu
			this.texture_btnMenu.loadFromFile(Paths.get("selectLvl/menu.png"));
			this.sprite_btnMenu.setTexture(this.texture_btnMenu);
			this.sprite_btnMenu.setPosition(30,5);
			Menu.app.draw(this.sprite_btnMenu);

			//Affichage bouton Jouer
			this.texture_btnLevel.loadFromFile(Paths.get("selectLvl/level.png"));
			this.sprite_btnLevel.setTexture(this.texture_btnLevel);
			this.sprite_btnLevel.setPosition(Niveaux.WIDTH/2-330,700/2+210);
			Menu.app.draw(this.sprite_btnLevel);

			Font f = new Font();
			f.loadFromFile(Paths.get("Fonts/BRUSHSCI.ttf"));
			Text t = new Text(""+this.level,f,72);
			t.setColor(Color.BLACK);
			t.setPosition(Niveaux.WIDTH/2+18, 350);
			Menu.app.draw(t);

			this.listSprite.add(this.sprite_btnPrec);
			this.listSprite.add(this.sprite_btnNext);
			this.listSprite.add(this.sprite_btnMenu);
			this.listSprite.add(this.sprite_btnLevel);


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
