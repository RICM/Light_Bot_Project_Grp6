package observable.map;
import java.util.ArrayList;

import couleur.Couleur;
import observable.int_Observable;
import observer.int_Observer;

public abstract class abstr_Case implements int_Observable{
	private int hauteur;
	private Couleur couleur;
	private Coordonnees coordonnees;

	protected ArrayList<int_Observer> listObserver = new ArrayList<int_Observer>();

	public Couleur get_couleur(){
		return this.couleur;
	}
	public void set_couleur(Couleur color){
		this.couleur = color;
		this.notifyObserver();
	}

	public void set_hauteur(int haut){
		this.hauteur = haut;
		this.notifyObserver();
	}

	public void set_coordonnees(Coordonnees coordo){
		this.coordonnees = coordo;
		this.notifyObserver();
	}
	public int get_hauteur(){
		return this.hauteur;
	}

	public Coordonnees get_coordonnees(){
		return this.coordonnees;
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
	public abstract abstr_Case Clone();
}
