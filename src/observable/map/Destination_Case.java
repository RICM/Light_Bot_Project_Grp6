package observable.map;
import couleur.Couleur;

public class Destination_Case extends abstr_Case {


	public Destination_Case(int h, Couleur Color, Coordonnees cord, Coordonnees destination){
		this.set_couleur(Color);
		this.set_coordonnees(cord);
		this.set_hauteur(h);
	}


}
