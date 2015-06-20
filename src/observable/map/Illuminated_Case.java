package observable.map;

import java.util.ArrayList;

import couleur.Couleur;
import observer.int_Observer;

public class Illuminated_Case extends abstr_Case {

	//list d'obs
	private boolean active;

	private ArrayList<int_Observer> listObserver = new ArrayList<int_Observer>();

	public Illuminated_Case(int h, Couleur color, Coordonnees cordo){
		this.set_couleur(color);
		this.set_coordonnees(cordo);
		this.set_hauteur(h);
		this.active = false;
		this.addObserver(World.currentWorld.getFirstObserver());
	}

	public void set_active(boolean act){
		if (this.listObserver.isEmpty())
			//System.out.println("T'es trop fort mec t'as compris que j'avais pas d'observeur");
			this.active = act;
		this.notifyObserver();
	}

	public void preset_active (boolean bool){
		this.active=bool;
	}

	public boolean get_active(){
		return this.active;
	}

	@Override
	public abstr_Case Clone() {
		Illuminated_Case to_return = new Illuminated_Case(this.get_hauteur(),this.get_couleur(),this.get_coordonnees().Clone());
		to_return.preset_active(this.active);
		to_return.addObserver(this.listObserver.get(0));
		return to_return;
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
