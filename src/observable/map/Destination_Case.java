package observable.map;
import java.util.ArrayList;

import couleur.Couleur;
import observer.int_Observer;

public class Destination_Case extends abstr_Case {

	private ArrayList<int_Observer> listObserver = new ArrayList<int_Observer>();

	public Destination_Case(int h, Couleur Color, Coordonnees cord){
		this.set_couleur(Color);
		this.set_coordonnees(cord);
		this.set_hauteur(h);
		this.addObserver(World.currentWorld.getFirstObserver());
	}

	@Override
	public Destination_Case Clone(){
		return new Destination_Case(this.get_hauteur(), this.get_couleur(),this.get_coordonnees().Clone());
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
