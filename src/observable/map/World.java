package observable.map;
import java.util.ArrayList;

import Ordonnanceur.Ordonnanceur;
import exception.ActionEx;
import exception.MouvementEx;
import exception.UnreachableCase;
import observable.int_Observable;
import observable.robot.Position;
import observable.robot.abstr_Robot;
import observer.int_Observer;

public class World implements int_Observable {

	private ArrayList<int_Observer> listObserver = new ArrayList<int_Observer>();

	public int_Observer getFirstObserver(){
		return this.listObserver.get(0);
	}

	private World(){
	}

	public static World currentWorld = new World();

	private Terrain[] liste_terrain;
	private abstr_Robot[]   liste_robot;

	private int nb_case_allumable;
	private int nb_case_allumees;

	private Terrain[] save_terr;
	private Position[] save_robot;

	private Ordonnanceur ordo = new Ordonnanceur();

	public Ordonnanceur get_ordonnanceur(){
		return this.ordo;
	}

	public void resetOrdonanceur(){
		this.ordo = new Ordonnanceur();
	}

	public void set_ordonnanceur(Ordonnanceur new_ord){
		this.ordo = new_ord;
	}

	public void prerun(){
		try {
			this.init_World();
		} catch (UnreachableCase e) {
			System.out.println("this shouldn't have heppened, WTH was done");
			e.printStackTrace();
		}
		//this.ordo = new Ordonnanceur();
		System.out.println("Taille de la liste de robot dans le monde : "+this.liste_robot.length);
		for(int i =0; i< this.liste_robot.length;i++){
			this.liste_robot[i].run();
			//	System.out.println("taille de la liste " + this.liste_robot[i].get_run().size());
		}
		System.out.println("nb cases a allumer : " + this.nb_case_allumable + "et allumé" + this.nb_case_allumees);
	}

	public void exec() throws MouvementEx, UnreachableCase, ActionEx, IndexOutOfBoundsException{
		System.out.println("Je suis dans exec");
		if (this.ordo.isReady()){
			System.out.println("Ich bin in ordo");
			this.ordo.execute_next();
		}
	}

	public Terrain get_terrain(int n){
		return this.liste_terrain[n];
	}

	public abstr_Robot get_robot(int n){
		return this.liste_robot[n];
	}

	Terrain[] get_liste_terrain(){
		return  this.liste_terrain;
	}

	public void set_liste_terrain(Terrain[] new_liste){
		this.liste_terrain = new_liste;
		this.notifyObserver();
	}

	public abstr_Robot[] get_liste_robot(){
		return  this.liste_robot;
	}

	public void set_liste_robot(abstr_Robot[] new_liste){
		this.liste_robot = new_liste;
		this.notifyObserver();
	}

	public void basic_print_world(){
		for (int i = 0; i < this.liste_terrain.length; i++){
			this.liste_terrain[i].print_basic_terrain(this.liste_robot);
		}
	}

	public int number_robots(){
		return this.liste_robot.length;
	}

	@Override
	public void addObserver(int_Observer obs) {
		this.listObserver.add(obs);
	}
	@Override
	public void removeObserver() {
		this.listObserver = new ArrayList<int_Observer>();

	}
	@Override
	public void notifyObserver() {
		for(int_Observer obs : this.listObserver)
			obs.update(this);
	}
	public void set_save_terr(Terrain[] temp){
		this.save_terr = temp;
	}

	/**
	 *
	 * @param coor
	 * @return la case au coordonnees defini par coor
	 * @throws UnreachableCase si la case n'existe pas
	 */
	public abstr_Case get_case(Coordonnees coor) throws UnreachableCase {
		return this.liste_terrain[coor.get_n()].get_case(coor.get_x(), coor.get_y());
	}


	/**
	 *
	 * @param dest
	 * @return true si la case dest est occupée par un robot
	 * @throws UnreachableCase
	 */
	public boolean isOccupied(abstr_Case dest) throws UnreachableCase{
		boolean res = false;
		for(int i = 0; i<this.liste_robot.length; i++){
			if(this.liste_robot[i].getCurrent_Case().get_coordonnees().equals(dest)){
				res = true;
			}
		}
		return res;
	}
	/**
	 *
	 * @return true ssi toutes les cases sont allumées
	 */
	public boolean is_cleared(){
		if(this.nb_case_allumable == this.nb_case_allumees){
			return true;
		}
		return false;
	}

	/**
	 * Increment nb_case_allumés
	 */
	public void increment_allume(){
		this.nb_case_allumees++;
	}

	/**
	 * decrement nb_case_allumés
	 */
	public void decrement_allume(){
		this.nb_case_allumees--;
	}

	/**
	 * @return void, set nb_case allumables et allumées au bonne valeurs
	 * @throws UnreachableCase
	 */
	public void init_World() throws UnreachableCase{
		this.nb_case_allumable = 0;
		this.nb_case_allumees = 0;
		for(int i = 0; i<this.liste_terrain.length; i++){
			for(int j = 0; j<this.liste_terrain[i].get_terrain().length;j++ ){
				for(int k = 0; k<this.liste_terrain[i].get_terrain()[j].length; k++){
					abstr_Case temp = this.save_terr[i].get_case(k, j).Clone();
					System.out.println(temp);
					System.out.println(this.save_terr[i].get_case(k,j));
					this.liste_terrain[i].set_case(temp,k,j);
					//	System.out.println(" type de la case " +this.liste_terrain[i].get_case(j, k).getClass().getSimpleName() + "et coordonnees" + j + "   "+ k );
					if (this.liste_terrain[i].get_case(k, j).getClass().getSimpleName().equals("Event_Case")){
						((Event_Case)this.liste_terrain[i].get_case(k, j)).init();
						((Event_Case)this.liste_terrain[i].get_case(k, j)).refresh();
					}
					if (this.liste_terrain[i].get_case(k, j).getClass().getSimpleName().equals("Illuminated_Case")){
						System.out.println("CASE ILLUMINEE SAVE : "+((Illuminated_Case)this.save_terr[i].get_case(k, j)).get_active());
						System.out.println("CASE ILLUMINEE : "+((Illuminated_Case)this.liste_terrain[i].get_case(k, j)).get_active());
						this.nb_case_allumable++;
						if (((Illuminated_Case)this.liste_terrain[i].get_case(k, j)).get_active()){
							this.nb_case_allumees++;
						}
					}
				}
			}
		}
	}

	public void store_status() throws UnreachableCase, ActionEx{
		System.out.println("je me sauvegarde");
		int size_t = World.currentWorld.get_liste_terrain().length;
		Terrain temp[] = new Terrain[size_t];
		World.currentWorld.save_robot = new Position[World.currentWorld.liste_robot.length];
		for(int i =0; i < this.liste_terrain.length; i++){
			temp[i] = World.currentWorld.get_terrain(i).Clone();
			World.currentWorld.set_save_terr(temp);
		}
		for(int i =0; i < this.liste_robot.length; i++){
			World.currentWorld.liste_robot[i].store_position();
			World.currentWorld.save_robot[i] = World.currentWorld.liste_robot[i].get_last_pos();
		}
		System.out.println("j'ai fini de me sauvegarder : world");
	}

	public boolean isOneRobotActive(){
		for (int i = 0; i < currentWorld.liste_robot.length; i ++){
			if (currentWorld.liste_robot[i].get_run().size() != 0){
				System.out.println("True True True");
				return true;
			}
		}
		return false;
	}


	//ICI LA ICI//

	public void rewind_status() throws UnreachableCase, ActionEx{
		for(int i =0; i < currentWorld.save_terr.length; i++){
			World.currentWorld.liste_terrain[i] = World.currentWorld.save_terr[i].Clone();
		}
		for(int i =0; i < this.liste_robot.length; i++){
			World.currentWorld.liste_robot[i].setFromPosition(World.currentWorld.save_robot[i]);
			World.currentWorld.liste_robot[i].reset_exec();
		}
		World.currentWorld.init_World();
	}

	public void set_Case(Coordonnees dest, abstr_Case replacement){
		this.liste_terrain[dest.get_n()].add_case(dest.get_x(), dest.get_y(), replacement);
	}


}