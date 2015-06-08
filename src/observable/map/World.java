package observable.map;
import java.util.ArrayList;

import observable.int_Observable;
import observable.robot.Robot;
import observer.int_Observer;

public class World implements int_Observable {
	
	private ArrayList<int_Observer> listObserver = new ArrayList<int_Observer>(); 

	private World(){
	}

	public static World currentWorld = new World();

	private Terrain[] liste_terrain;
	private Robot[]   liste_robot;

	public Terrain get_terrain(int n){
		return liste_terrain[n];
	}

	public Robot get_robot(int n){
		return liste_robot[n];
	}

	Terrain[] get_liste_terrain(){
		return  liste_terrain;
	}

	public void set_liste_terrain(Terrain[] new_liste){
		liste_terrain = new_liste;
		notifyObserver();
	}

	Robot[] get_liste_robot(){
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
}
