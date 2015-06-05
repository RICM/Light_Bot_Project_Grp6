package map;
import robot.Robot;

public class World {

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
	}

	Robot[] get_liste_robot(){
		return  liste_robot;
	}

	public void set_liste_robot(Robot[] new_liste){
		liste_robot = new_liste;
	}
	
	public void basic_print_world(){
		for (int i = 0; i < liste_terrain.length; i++){
			liste_terrain[i].print_basic_terrain(liste_robot);
		}
	}
}
