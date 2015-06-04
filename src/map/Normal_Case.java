package map;
import Couleur.*;

public class Normal_Case extends abstr_Case {
	
	public Normal_Case(int h, Couleur color, Coordonnees cordo){
		this.couleur = color;
		this.coordonnees = cordo;
		this.hauteur = h;
	}

}
