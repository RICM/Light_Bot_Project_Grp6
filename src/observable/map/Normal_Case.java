package observable.map;
import java.util.ArrayList;

import couleur.Couleur;
import observer.int_Observer;

public class Normal_Case extends abstr_Case {

	private ArrayList<int_Observer> listObserver = new ArrayList<int_Observer>();

	public Normal_Case(int h, Coordonnees cordo){
		this.set_couleur(Couleur.GRIS);
		this.set_coordonnees(cordo);
		this.set_hauteur(h);
		this.addObserver(World.currentWorld.getFirstObserver());
	}

	@Override
	public abstr_Case Clone() {
		return new Normal_Case(this.get_hauteur(),this.get_coordonnees().Clone());
	}

	@Override
	public void addObserver(int_Observer obs) {
		this.listObserver.add(obs);
	}
	@Override
	public void removeObserver() {
		this.listObserver = new ArrayList<int_Observer>();

	}
	@Override
	public void notifyObserver() {
		for(int_Observer obs : this.listObserver)
			obs.update(this);
	}
}
