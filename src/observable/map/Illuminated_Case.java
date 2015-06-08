package observable.map;

import couleur.*;

public class Illuminated_Case extends abstr_Case {
	
	private boolean active;
	
	public Illuminated_Case(int h, Couleur color, Coordonnees cordo){
		this.set_couleur(color);
		this.set_coordonnees(cordo);
		this.set_hauteur(h);
		this.active = false;
	}
	
	public void set_active(boolean act){
		this.active = act;
		notifyObserver();
	}
	
	public boolean get_active(){
		return this.active;
	}
	
}
