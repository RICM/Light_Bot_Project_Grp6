package observable.map;
import java.util.ArrayList;

import exception.UnreachableCase;
import observable.int_Observable;
import observable.robot.Robot;
import observable.robot.abstr_Robot;
import observer.int_Observer;

public class World implements int_Observable {
	
	private ArrayList<int_Observer> listObserver = new ArrayList<int_Observer>(); 

	private World(){
	}

	public static World currentWorld = new World();

	private Terrain[] liste_terrain;
	private abstr_Robot[]   liste_robot;
	
	private int nb_case_allumable;
	private int nb_case_allumees;

	
	public Terrain get_terrain(int n){
		return liste_terrain[n];
	}

	public abstr_Robot get_robot(int n){
		return liste_robot[n];
	}

	Terrain[] get_liste_terrain(){
		return  liste_terrain;
	}

	public void set_liste_terrain(Terrain[] new_liste){
		liste_terrain = new_liste;
		notifyObserver();
	}

	abstr_Robot[] get_liste_robot(){
		return  liste_robot;
	}

	public void set_liste_robot(Robot[] new_liste){
		liste_robot = new_liste;
		notifyObserver();
	}
	
	public void basic_print_world(){
		for (int i = 0; i < liste_terrain.length; i++){
			liste_terrain[i].print_basic_terrain(liste_robot);
		}
	}
	
	public int number_robots(){
		return liste_robot.length;
	}
	
	@Override
	public void addObserver(int_Observer obs) {
		this.listObserver.add(obs);
	}
	@Override
	public void removeObserver() {
		listObserver = new ArrayList<int_Observer>();
		
	}
	@Override
	public void notifyObserver() {
		for(int_Observer obs : listObserver)
		      obs.update(this);
	}
	/**
	 * 
	 * @param coor
	 * @return la case au coordonnees defini par coor
	 * @throws UnreachableCase si la case n'existe pas
	 */
	public abstr_Case get_case(Coordonnees coor) throws UnreachableCase {
		return liste_terrain[coor.get_n()].get_case(coor.get_x(), coor.get_y());
	}
	
	public boolean isOccupied(abstr_Case dest) throws UnreachableCase{
		boolean res = false;
		for(int i = 0; i<liste_robot.length; i++){
			if(liste_robot[i].getCurrent_Case().get_coordonnees().equals(dest)){
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
		return this.nb_case_allumable == this.nb_case_allumees;
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
	public void refresh_allumable() throws UnreachableCase{
		this.nb_case_allumable = 0;
		this.nb_case_allumees = 0;
		for(int i = 0; i<liste_terrain.length; i++){
			for(int j = 0; j<liste_terrain[i].get_terrain().length;j++ ){
				for(int k = 0; k<liste_terrain[i].get_terrain()[j].length; k++){
					if (liste_terrain[i].get_case(i, j).getClass().getSimpleName().equals("Illuminated_Case")){
						this.nb_case_allumable++;
						if (((Illuminated_Case)liste_terrain[i].get_case(i, j)).get_active()){
							this.nb_case_allumees++;
						}
					}
				}
			}
		}
	}	
}
