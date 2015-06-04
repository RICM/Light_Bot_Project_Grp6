package map;
import map.Terrain;
import robot.Robot;;

public class World {
	private Terrain[] liste_terrain;
	private Robot[]   liste_robot;
	
	
	Terrain[] get_liste_terrain(){
		return  liste_terrain;
	}
	
	void set_liste_terrain(Terrain[] new_liste){
		liste_terrain = new_liste;
	}
	
	Robot[] get_liste_robot(){
		return  liste_robot;
	}
	
	void set_liste_robot(Robot[] new_liste){
		liste_robot = new_liste;
	}
}
