package observable.map;

import couleur.Couleur;

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
		this.notifyObserver();
	}

	public boolean get_active(){
		return this.active;
	}

	@Override
	public abstr_Case Clone() {
		Illuminated_Case to_return = new Illuminated_Case(this.get_hauteur(),this.get_couleur(),this.get_coordonnees().Clone());
		to_return.set_active(this.active);
		return to_return;
	}

}
