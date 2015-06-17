package observable.map;
import java.util.ArrayList;

import couleur.Couleur;
import observer.int_Observer;

public class Teleporter_Case extends abstr_Case {
	private Coordonnees dest;
	private ArrayList<int_Observer> listObserver = new ArrayList<int_Observer>();

	public Teleporter_Case(int h, Couleur Color, Coordonnees cord, Coordonnees destination){
		this.set_couleur(Color);
		this.set_coordonnees(cord);
		this.set_hauteur(h);
		this.dest = destination;
		this.addObserver(World.currentWorld.getFirstObserver());
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
