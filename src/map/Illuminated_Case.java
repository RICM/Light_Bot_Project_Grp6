package map;

import Couleur.*;

public class Illuminated_Case extends abstr_Case {
	
	private boolean active;
	
	public Illuminated_Case(int h, Couleur color, Coordonnees cordo){
		this.hauteur = h;
		this.couleur = color;
		this.coordonnees = cordo;
		this.active = false;
	}
	
	public void set_active(boolean act){
		this.active = act;
	}
	
	public boolean get_active(){
		return this.active;
	}
	
}
