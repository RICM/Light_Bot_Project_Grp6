package observable.map;
import couleur.Couleur;

public class Painted_Case extends abstr_Case {


	public Painted_Case(int h, Couleur color, Coordonnees cordo){
		this.set_couleur(color);
		this.set_coordonnees(cordo);
		this.set_hauteur(h);
	}

	@Override
	public abstr_Case Clone() {
		return new Painted_Case(this.get_hauteur(),this.get_couleur(),this.get_coordonnees().Clone());
	}

}
