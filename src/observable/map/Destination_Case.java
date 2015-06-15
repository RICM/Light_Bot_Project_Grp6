package observable.map;
import couleur.Couleur;

public class Destination_Case extends abstr_Case {


	public Destination_Case(int h, Couleur Color, Coordonnees cord){
		this.set_couleur(Color);
		this.set_coordonnees(cord);
		this.set_hauteur(h);
	}

	@Override
	public Destination_Case Clone(){
		return new Destination_Case(this.get_hauteur(), this.get_couleur(),this.get_coordonnees().Clone());
	}
}
