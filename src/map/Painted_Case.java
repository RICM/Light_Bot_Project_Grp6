package map;
import couleur.*;

public class Painted_Case extends abstr_Case {

	
		public Painted_Case(int h, Couleur color, Coordonnees cordo){
			this.set_couleur(color);
			this.set_coordonnees(cordo);
			this.set_hauteur(h);
		}
		
}
