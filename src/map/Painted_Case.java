package map;
import Couleur.*;

public class Painted_Case extends abstr_Case {

	
		public Painted_Case(int h, Couleur color, Coordonnees cordo){
			this.couleur = color;
			this.coordonnees = cordo;
			this.hauteur = h;
		}
		
}
