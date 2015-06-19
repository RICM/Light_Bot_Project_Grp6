package View;


import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import observable.action.int_Action;
import observable.action_list.Sequence_List;
import observable.map.Terrain;
import observable.map.World;
import observable.robot.Orientation.orientation;
import observable.robot.abstr_Robot;
import observer.controller.Controller;

import org.jsfml.audio.Music;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.Event.Type;

import couleur.Couleur;


public class Jeu {

	protected static Controller controller;
	protected static HashMap<String,Texture> textureCase = new HashMap<String,Texture>();
	protected static HashMap<String,Texture> textureRobot = new HashMap<String,Texture>();
	protected static HashMap<String,Texture> textureBackground = new HashMap<String,Texture>();
	protected static HashMap<String,Texture> textureBouton = new HashMap<String,Texture>();
	protected static HashMap<String,Texture> textureBoutonInterface = new HashMap<String,Texture>();
	protected Texture maTextureMenu = new Texture();

	protected static int identificateur_robot = 0;
	protected static String typeBackground[]=  {"Main","P1","P2"};
	protected static String typeCouleur [] = {"_ROUGE","_VERT","_GRIS"};
	protected static String typeOrientation [] = {"_BOT","_LEFT","_RIGHT","_TOP"};
	protected static String typeBoutonInterfaceMenu [] = {"Fond","Sound","Home"};
	protected static String typeBouton [] = {"Activate","Break_r", "Call_P1","Call_P2", "Flashback", "Jump", "MoveForward", "Notify_r", "Pause", "Remember","tache","TurnLeft", "TurnRIght"};
	protected static String typeCase [] = {"Normal_Case","Painted_Case_ROUGE", "Painted_Case_VERT","Teleporter_Case", "Illuminated_Case_Active", "Illuminated_Case_Inactive", "Event_Case", "Empty_Case", "Destination_Case", "Empile_Case"};
	protected static String typeRobot[]={"pingouin_GRIS","requin_GRIS"};
	protected static String typeBoutonInterface[]={"pingouin_GRIS","play","stop","rewind"};

	protected Sprite monSprite = new Sprite();
	protected Sprite monSpriteActionChoisie = new Sprite();
	protected Sprite monSpriteBackground = new Sprite();
	protected Sprite monSpritePerso = new Sprite();
	protected Sprite monSpriteMenu = new Sprite();

	protected static HashMap<String, Sprite> liste_sprite = new HashMap<String, Sprite>();
	protected static HashMap<String, Sprite> liste_background = new HashMap<String, Sprite>();
	protected int level;



	protected static final int NB_MAX_CASE = 10;
	protected static final int HAUTEUR_CASE = 26;
	protected static final int LARGEUR_CASE = 120;
	protected static final int TAILLE_MAX_MAIN = 12;
	protected static final int TAILLE_MAX_P1 = 8;
	protected static final int TAILLE_MAX_P2 = 8;

	protected static int indiceInterface=-40;
	protected static boolean deroule = false;
	protected static boolean renroule = false;
	protected static boolean IsPlaying = true;
	protected boolean FirstLoop = true;
	protected static Couleur couleur_active = Couleur.GRIS;
	protected static float x_whale = -600;
	protected static int y_whale = Menu.getHeight()/2-200;
	protected static String activate = "Main";
	//protected static ArrayList<Sprite> liste_background = new ArrayList<Sprite>();
	protected static LinkedList<Sprite> liste_main = new LinkedList<Sprite>();
	protected static LinkedList<Sprite> liste_P1 = new LinkedList<Sprite>();
	protected static LinkedList<Sprite> liste_P2 = new LinkedList<Sprite>();

	protected static LinkedList<int_Action> liste_main_actions = new LinkedList<int_Action>();
	protected static LinkedList<int_Action> liste_P1_actions = new LinkedList<int_Action>();
	protected static LinkedList<int_Action> liste_P2_actions = new LinkedList<int_Action>();


	public void changeRobotActionsDisplay(abstr_Robot robot){
		liste_main_actions = robot.get_Main().getListActions();
		liste_P1_actions = robot.get_P1().getListActions();
		liste_P2_actions = robot.get_P2().getListActions();
		//		liste_main = new LinkedList<Sprite>();
		//		liste_P1 = new LinkedList<Sprite>();
		//		liste_P2 = new LinkedList<Sprite>();
		this.setSpritesListesActions();
	}

	public void setSpritesListesActions(){
		for (int i = 0; i<liste_main_actions.size(); i++){
			/*
			 * TO BE DEVELOPPED
			 */
		}
	}

	protected static HashMap<Sprite,int_Action> liste_sprite_Action = new HashMap<Sprite, int_Action>();


	public void addController(Controller acontroller){
		controller = acontroller;
		controller.addJeu(this);
	}

	public Jeu(int lvl, Controller acontroller){
		this.addController(acontroller);
		Menu.reset_cam();
		this.level = lvl;
		this.initTextureCase();
		this.initTextureRobot();
		this.initTextureBackground();
		this.initTextureBouton();
		this.initTextureBoutonInterface();
		try {
			this.maTextureMenu.loadFromFile(Paths.get("Images/Jeu/Backgrounds/win.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while(Menu.app.isOpen()){
			Menu.app.clear();
			this.drawBackground();
			controller.setNotificationDrawGrilleISO();
			controller.setNotificationDrawButton();
			controller.setNotificationDrawControle();
			controller.setNotificationDrawAllProcedure();
			Menu.app.display();
			this.processEvent(false);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(World.currentWorld.is_cleared()){
				//				JOptionPane.showMessageDialog(null, "Fin");
				//				Menu.app.close();
			}
		}
	}

	/**
	 * D�tecte les entr�es claviers et souris
	 */
	public void processEvent(boolean finish){
		Menu.app.setKeyRepeatEnabled(false);
		//Event e = Menu.app.waitEvent();
		for(Event e : Menu.app.pollEvents()){
			if(e.type == Type.CLOSED){
				Texture te = new Texture();
				Sprite sp = new Sprite();
				Music song_close = new Music();
				try {
					te.loadFromFile(Paths.get("Images/Jeu/gif/images_fixes/whale.png"));
					song_close.openFromFile(Paths.get("Song/close.ogg"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				song_close.play();
				sp.setTexture(te);
				while(Jeu.x_whale < 1200){
					sp.setPosition(Jeu.x_whale,Jeu.y_whale);
					this.drawBackground();
					if(x_whale < Menu.getWidth()/3-200)
						controller.setNotificationDrawGrilleISO();
					controller.setNotificationDrawButton();
					Menu.app.draw(sp);
					Menu.app.display();
					Jeu.x_whale += 20;
					Menu.app.clear();
				}
				Menu.app.close();

			}

			if(e.type == Event.Type.RESIZED){
				Menu.reset_cam();
			}

			if (e.type == Event.Type.MOUSE_BUTTON_RELEASED ) {
				e.asMouseEvent();
				Vector2i pos = Mouse.getPosition(Menu.app);
				Vector2f click = Menu.app.mapPixelToCoords(pos);
				if(finish == false){
					Jeu.detect_move(click);
					this.delete_button(e,click);
				}
				else
					this.retourMenu(e,click);
			}

			Vector2i pos = Mouse.getPosition(Menu.app);
			float x = pos.x;
			float y = pos.y;
			Iterator<String> keySetIterator = Jeu.liste_sprite.keySet().iterator();
			while(keySetIterator.hasNext()){

				String action = keySetIterator.next();
				FloatRect rect = liste_sprite.get(action).getGlobalBounds();

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

	public void retourMenu(Event e, Vector2f click) {
		FloatRect rect = this.monSpriteMenu.getGlobalBounds();
		float x = click.x;
		float y = click.y;
		if(x>=rect.left && x<=rect.left+rect.width && y>=rect.top && y<=rect.top+rect.height){
			System.out.println("test");
			new Theme(controller);
		}

	}

	public void updateRobot(int new_robot){
		identificateur_robot = new_robot;
		typeBoutonInterface[0] = typeRobot[identificateur_robot];
		System.out.println(identificateur_robot);
	}
	/**
	 *
	 * @param pos Coordonn�es du clique souris
	 */
	private static void detect_move(Vector2f pos) {
		float x = pos.x;
		float y = pos.y;
		Iterator<String> keySetIterator = Jeu.liste_sprite.keySet().iterator();
		while(keySetIterator.hasNext()){

			String action = keySetIterator.next();
			String actionPossible[]={"MoveForward","TurnRIght","TurnLeft","Activate","Jump","Call_P1","Call_P2","Remember","Flashback","Break_r","Notify_r","Wait_r"};
			FloatRect rect = liste_sprite.get(action).getGlobalBounds();

			if(x>=rect.left && x<=rect.left+rect.width && y>=rect.top && y<=rect.top+rect.height){

				for(String actionCurrent : actionPossible){
					if(action.equals(actionCurrent)){
						int_Action actionToAdd = controller.getNotificationAddActionToUserList(action, couleur_active);
						if (actionToAdd != null){
							liste_sprite_Action.put(liste_sprite.get(action), actionToAdd);
						}
						break;
					}
				}

				if((action.equals("pingouin_GRIS")) && (identificateur_robot == 0)){
					System.out.println("pingouin_blanc -> requin_blanc");
					controller.getNotificationChangeRobot(identificateur_robot);
					break;
				}
				else if((action.equals("requin_GRIS")) && (identificateur_robot == 1)){
					System.out.println("requin_blanc -> pingouin_blanc");
					controller.getNotificationChangeRobot(identificateur_robot);
					break;
				}
				else if(action.equals("_GRIS")){
					System.out.println("couleur GRIS");
					couleur_active =  Couleur.GRIS;
					break;
				}
				else if(action.equals("_ROUGE")){
					System.out.println("couleur ROUGE");
					couleur_active =  Couleur.ROUGE;
					break;
				}
				else if(action.equals("_VERT")){
					System.out.println("couleur VERT");
					couleur_active =  Couleur.VERT;
					break;
				}
				else if(action.equals("play")){
					System.out.println("play");
					controller.getNotificationRewindAndRUn();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
				else if(action.equals("rewind")){
					System.out.println("rewind");
					controller.getNotificationRewind();
					break;
				}
				else if(action.equals("stop")){
					System.out.println("stop");
					controller.getNotificationStopRun();
					break;
				}
				else if(action.equals("Home")){
					System.out.println("Home");
					new Theme(controller);
					break;
				}
				else if(action.equals("Sound")){
					System.out.println("Sound");
					if(IsPlaying){
						Menu.song.pause();
						IsPlaying = false;
					}else{
						Menu.song.play();
						IsPlaying = true;
					}
					break;
				}
			}
		}

		//Definis la fenetre active : Main, P1 ou P2

		keySetIterator = Jeu.liste_background.keySet().iterator();
		while(keySetIterator.hasNext()){

			String backgroundCurrent = keySetIterator.next();
			FloatRect rect = liste_background.get(backgroundCurrent).getGlobalBounds();
			if(x>=rect.left && x<=rect.left+rect.width && y>=rect.top && y<=rect.top+rect.height){
				activate = backgroundCurrent;
				System.out.println("Active :"+ activate);
				controller.setNotificationSwitchProgram(backgroundCurrent);
				break;
			}
		}
	}


	public void delete_button(Event e, Vector2f click){
		float x = click.x;
		float y = click.y;
		System.out.println(activate);
		if(activate.equals("Main"))
			remove_action_liste(liste_main,x,y);
		else if(activate.equals("P1")){
			remove_action_liste(liste_P1,x,y);
		}
		else if(activate.equals("P2")){
			remove_action_liste(liste_P2,x,y);
		}
	}

	public static void remove_action_liste(LinkedList<Sprite> list_remove, float x, float y){
		int compteur=0;
		for(Sprite s: list_remove){
			FloatRect rect = s.getGlobalBounds();
			if(x>=rect.left && x<=rect.left+rect.width && y>=rect.top && y<=rect.top+rect.height){
				if(list_remove.get(compteur)!=null){
					controller.getNotificationRemoveToRobotList(compteur);
				}
				break;
			}
			compteur++;
		}
	}


	public static void draw_popup(String msg){
		JOptionPane.showMessageDialog(null, msg);
	}



	/**
	 *	Affiche le personnage
	 * @param rob le personnage a afficher
	 * @param X la position en x du personnage
	 * @param Y la position en y du personnage
	 */
	public void updateDrawPerso(int X, int Y, int H, orientation O, Couleur C,int Robot){
		Texture textureTemp;

		String to_get = Robot+"_"+O.toString()+"_"+C.toString();
		textureTemp = textureRobot.get(to_get);

		this.monSpritePerso.setTexture(textureTemp);
		this.monSpritePerso.setPosition((float) ((X+30+10)*0.8),(float) (0.8*(Y-HAUTEUR_CASE*H+25)));
		this.monSpritePerso.setScale(0.9f,0.9f);
		Menu.app.draw(this.monSpritePerso);
	}

	/**
	 * Affiche les boutons
	 */
	public void updateDrawBouton(LinkedList<String> actionsAutorises){

		int i =0;
		for(String actionCurrent : actionsAutorises){

			String to_get = actionCurrent +"_"+couleur_active;

			Texture textureTemp = textureBouton.get(to_get);
			Sprite monSpriteBouton = liste_sprite.get(actionCurrent);
			monSpriteBouton.setTexture(textureTemp);
			monSpriteBouton.setPosition(10+80*i,610);
			Jeu.liste_sprite.replace(actionCurrent,monSpriteBouton);//(actionCurrent,monSpriteBouton);
			Menu.app.draw(monSpriteBouton);
			i++;
		}
	}


	/*
	 * Affiche bouton play,retour etc
	 *
	 *
	 */
	public void updateDrawControle(){
		Texture textureTemp;
		int i = 0;
		for(String boutonCurrent : typeBoutonInterface){

			textureTemp = textureBoutonInterface.get(boutonCurrent);
			Sprite monSpriteBouton = liste_sprite.get(boutonCurrent);
			monSpriteBouton.setTexture(textureTemp);
			monSpriteBouton.setPosition(10,10+80*i);
			Jeu.liste_sprite.replace(boutonCurrent,monSpriteBouton);
			Menu.app.draw(monSpriteBouton);
			i++;
		}

		i = 0;
		for(String couleurCurrent : typeCouleur){

			textureTemp = textureBoutonInterface.get("tache"+couleurCurrent);
			Sprite monSpriteBouton = liste_sprite.get(couleurCurrent);
			monSpriteBouton.setTexture(textureTemp);
			monSpriteBouton.setPosition(700+50*i,540);
			Jeu.liste_sprite.replace(couleurCurrent,monSpriteBouton);
			Menu.app.draw(monSpriteBouton);
			i++;
		}

		i = 0;
		for(String boutonMenuCurrent : typeBoutonInterfaceMenu){

			textureTemp = textureBoutonInterface.get(boutonMenuCurrent);
			Sprite monSpriteBouton = liste_sprite.get(boutonMenuCurrent);
			monSpriteBouton.setTexture(textureTemp);
			if(deroule){
				if(indiceInterface>=0){
					deroule = false;
				}else{
					indiceInterface ++;
				}
			}
			if(renroule && !deroule){
				if(indiceInterface>-40){
					indiceInterface --;
				}else{
					renroule = false;
				}
			}
			monSpriteBouton.setPosition(400+50*i,indiceInterface);
			Jeu.liste_sprite.replace(boutonMenuCurrent,monSpriteBouton);
			Menu.app.draw(monSpriteBouton);
			i++;
		}
	}

	public void updateDrawProcedure(String name_proc, int nombre_bouton_max, LinkedList<String> class_name, LinkedList<String> color_name ){
		/**
		 * A tester
		 */
		int posY;
		String to_get;

		switch (name_proc){
		case "Main" :	posY = 7;	break;
		case "P1" :		posY = 290;	break;
		default :		posY = 494;	break;
		}

		Texture textureTemp;
		if(name_proc==activate){ 	to_get = name_proc + "_Activate";}
		else{						to_get = name_proc;}
		if(nombre_bouton_max>0){
			textureTemp = textureBackground.get(to_get);
			Sprite monSpriteBackground = liste_background.get(name_proc);
			monSpriteBackground.setPosition(881, posY);
			monSpriteBackground.setTexture(textureTemp);
			liste_background.replace(name_proc, monSpriteBackground);
			Menu.app.draw(monSpriteBackground);

			int y=0;
			for(int x=0; x<nombre_bouton_max;x++){
				if(x%4==0){	y++;}

				if (x<class_name.size()){
					to_get = class_name.get(x)+"_"+color_name.get(x);
				}
				else{
					to_get = "Fond_Bouton";
				}

				textureTemp = textureBouton.get(to_get);
				this.monSpriteActionChoisie = new Sprite();
				this.monSpriteActionChoisie.setPosition(884+(x%4*(70+4)), (posY+37) + (y-1)*(70+10));
				this.monSpriteActionChoisie.setTexture(textureTemp);
				Menu.app.draw(this.monSpriteActionChoisie);

				if(name_proc=="Main" && liste_main.size()<nombre_bouton_max){
					liste_main.add(this.monSpriteActionChoisie);
				}
				else if(name_proc=="P1" && liste_P1.size()<nombre_bouton_max)
				{
					liste_P1.add(this.monSpriteActionChoisie);
				}
				else if(name_proc=="P2" && liste_P2.size()<nombre_bouton_max)
				{
					liste_P2.add(this.monSpriteActionChoisie);
				}
			}
		}
	}





	/**
	 *  Chargement de toutes les textures avant l'execution du while avant de limiter la demande de ressource
	 */

	public void initTextureCase(){
		try{
			Texture textureTemp;
			for(String caseCurrent : typeCase){
				textureTemp = new Texture();
				textureTemp.loadFromFile(Paths.get("Images/Jeu/Cases/"+caseCurrent+".png"));
				textureCase.put(caseCurrent,textureTemp);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void initTextureBouton(){
		try{
			Texture textureTemp;
			for(String boutonCurrent : typeBouton){
				for(String couleurCurrent : typeCouleur){
					textureTemp = new Texture();
					textureTemp.loadFromFile(Paths.get("Images/Jeu/Boutons/"+boutonCurrent+couleurCurrent+".png"));
					textureBouton.put(boutonCurrent+couleurCurrent,textureTemp);

					Sprite monSpriteBouton = new Sprite();
					liste_sprite.put(boutonCurrent, monSpriteBouton);
				}
			}
			textureTemp = new Texture();
			textureTemp.loadFromFile(Paths.get("Images/Jeu/Backgrounds/Fond_Bouton.png"));
			textureBouton.put("Fond_Bouton",textureTemp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void initTextureBoutonInterface(){
		try{
			Texture textureTemp;
			Sprite monSpriteBouton;
			for(String boutonCurrent : typeBoutonInterface){
				textureTemp = new Texture();
				textureTemp.loadFromFile(Paths.get("Images/Jeu/Boutons/"+boutonCurrent+".png"));
				textureBoutonInterface.put(boutonCurrent,textureTemp);

				monSpriteBouton = new Sprite();
				liste_sprite.put(boutonCurrent, monSpriteBouton);
			}
			textureTemp = new Texture();
			textureTemp.loadFromFile(Paths.get("Images/Jeu/Boutons/requin_GRIS.png"));
			textureBoutonInterface.put("requin_GRIS",textureTemp);

			monSpriteBouton = new Sprite();
			liste_sprite.put("requin_GRIS", monSpriteBouton);

			for(String couleurCurrent : typeCouleur){
				textureTemp = new Texture();
				textureTemp.loadFromFile(Paths.get("Images/Jeu/Boutons/tache"+couleurCurrent+".png"));
				textureBoutonInterface.put("tache"+couleurCurrent,textureTemp);

				monSpriteBouton = new Sprite();
				liste_sprite.put(couleurCurrent, monSpriteBouton);
			}
			for(String boutonCurrent : typeBoutonInterfaceMenu){
				textureTemp = new Texture();
				textureTemp.loadFromFile(Paths.get("Images/Jeu/BoutonsInterface/Interface_"+boutonCurrent+".png"));
				textureBoutonInterface.put(boutonCurrent,textureTemp);

				monSpriteBouton = new Sprite();
				liste_sprite.put(boutonCurrent, monSpriteBouton);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void initTextureRobot(){
		try {
			int i = 0;
			Texture textureTemp;
			for(String robotCurrent : typeRobot){
				for(String orientationCurrent : typeOrientation){
					for(String couleurCurrent : typeCouleur){
						textureTemp = new Texture();
						textureTemp.loadFromFile(Paths.get("Images/Jeu/Robots/"+i+orientationCurrent+couleurCurrent+".png"));
						textureRobot.put(i+orientationCurrent+couleurCurrent,textureTemp);
					}
				}
				i++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void initTextureBackground(){
		try {
			Texture textureTemp;
			Sprite monSpriteBackground;
			for(String backgroundCurrent : typeBackground){
				textureTemp = new Texture();
				textureTemp.loadFromFile(Paths.get("Images/Jeu/Backgrounds/Fond_"+backgroundCurrent+".png"));
				textureBackground.put(backgroundCurrent,textureTemp);

				monSpriteBackground = new Sprite();
				liste_background.put(backgroundCurrent, monSpriteBackground);

				textureTemp = new Texture();
				textureTemp.loadFromFile(Paths.get("Images/Jeu/Backgrounds/Fond_"+backgroundCurrent+"_Activate.png"));
				textureBackground.put(backgroundCurrent+"_Activate",textureTemp);
			}

			textureTemp = new Texture();
			textureTemp.loadFromFile(Paths.get("Images/Jeu/Backgrounds/Background_General.jpg"));
			textureBackground.put("Background_General",textureTemp);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void initTextureBoutonChoisis(){

		for(int i=0; i<TAILLE_MAX_MAIN;i++){
			Sprite monSprite = new Sprite();
			liste_main.add(monSprite);
		}
		for(int i=0; i<TAILLE_MAX_P1;i++){
			Sprite monSprite = new Sprite();
			liste_P1.add(monSprite);
		}
		for(int i=0; i<TAILLE_MAX_P2;i++){
			Sprite monSprite = new Sprite();
			liste_P2.add(monSprite);
		}
	}


	public void display_world(World world){

	}

	public void display_terrain(Terrain terrain) throws IOException{

	}

	public void drawBackground(){
		Texture textureTemp;
		textureTemp = textureBackground.get("Background_General");
		this.monSpriteBackground.setTexture(textureTemp);
		Menu.app.draw(this.monSpriteBackground);
	}


	/**
	 * Affiche le terrain
	 */
	public void updateDrawGrilleISO(int taille_abs, int taille_ord ){
		int Xtemp,Ytemp;

		for(int X=taille_abs-1;X>=0;X--){
			Ytemp=0;
			for(Xtemp=X;Xtemp<taille_abs;Xtemp++){
				if(Ytemp<0){
					break;
				}
				//this.affichageISO(Xtemp,Ytemp);
				controller.setNotificationDrawISO(Xtemp,Ytemp);
				Ytemp++;
			}
		}
		for(int Y=1;Y<taille_ord;Y++){//1 car on a géré le cas 0 avec X juste au dessus
			Xtemp=0;
			for(Ytemp=Y;Ytemp<taille_ord;Ytemp++){
				if(Xtemp>=taille_abs){
					break;
				}
				//this.affichageISO(Xtemp,Ytemp);
				controller.setNotificationDrawISO(Xtemp,Ytemp);
				Xtemp++;
			}
		}
	}

	/**
	 * Affichage des cases
	 * @param X
	 * @param Y
	 */
	public void updateDrawISO(int X, int Y, int H, String class_name, String info_suppl){

		String to_get;
		Texture textureTemp;

		for(int hauteur=0; hauteur<=H;hauteur++){
			to_get = class_name + info_suppl;

			textureTemp = textureCase.get(to_get);
			this.monSprite.setTexture(textureTemp);
			this.monSprite.setScale(0.8f, 0.8f);
			this.monSprite.setPosition((float) (X*0.8),(float) ((Y-HAUTEUR_CASE*hauteur)*0.8));
			Menu.app.draw(this.monSprite);
		}



	}

	public void setNotificationDrawForTime(){
		long temps_depart = System.currentTimeMillis();
		long duree = 300; // en millisecondes
		while((System.currentTimeMillis() - temps_depart ) < duree )
		{
			System.out.println("in while******"+controller.getCpt());
			Menu.app.clear();
			this.drawBackground();
			controller.setNotificationDrawGrilleISO();
			controller.setNotificationDrawButton();
			controller.setNotificationDrawControle();
			controller.setNotificationDrawAllProcedure();
			Menu.app.display();
			this.processEvent(false);
			if(World.currentWorld.is_cleared()){
				//				JOptionPane.showMessageDialog(null, "Fin");
				//				Menu.app.close();
			}
		}
		System.out.println("sortie du while");
		controller.setNotificationUpdatedRobotMouvement();
	}

	public void updateSequenceList(Sequence_List seq) {
		System.out.println("j'ai updaté la sequence display");
	}

	public void victory() {
		//JOptionPane.showMessageDialog(null, "Win");
		try {
			this.maTextureMenu.loadFromFile(Paths.get("Images/Jeu/Backgrounds/win.png"));
			this.monSpriteMenu.setTexture(this.maTextureMenu);
			this.monSpriteMenu.setPosition(Menu.getWidth()/2-200, Menu.getHeight()/2-75);
			Menu.app.draw(this.monSpriteMenu);
			Menu.app.display();
			while(Menu.app.isOpen())
			{
				this.processEvent(true);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected static void processEventVictory() {
		// TODO Auto-generated method stub

	}

}
