package observable.map;
import couleur.Couleur;

public class Normal_Case extends abstr_Case {

	public Normal_Case(int h, Coordonnees cordo){
		this.set_couleur(Couleur.GRIS);
		this.set_coordonnees(cordo);
		this.set_hauteur(h);
	}

	@Override
	public abstr_Case Clone() {
		return new Normal_Case(this.get_hauteur(),this.get_coordonnees().Clone());
	}

}
