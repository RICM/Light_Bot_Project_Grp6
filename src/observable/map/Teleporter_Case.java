package observable.map;
import couleur.Couleur;

public class Teleporter_Case extends abstr_Case {
	private Coordonnees dest;

	public Teleporter_Case(int h, Couleur Color, Coordonnees cord, Coordonnees destination){
		this.set_couleur(Color);
		this.set_coordonnees(cord);
		this.set_hauteur(h);
		this.dest = destination;
	}

	public Coordonnees get_destination(){
		return this.dest;
	}

	public void set_destination(Coordonnees new_dest){
		this.dest = new_dest;
	}

	@Override
	public abstr_Case Clone() {
		return new Teleporter_Case(this.get_hauteur(), this.get_couleur(), this.get_coordonnees().Clone(), this.dest.Clone());
	}
}
