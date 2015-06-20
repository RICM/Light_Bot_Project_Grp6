package observable.map;

import java.util.ArrayList;

import couleur.Couleur;
import exception.UnreachableCase;
import observer.int_Observer;

public class Event_Case extends abstr_Case{

	private abstr_Case to_add;
	private Coordonnees dest_add;
	private boolean status;
	private abstr_Case replaced;//la case remplacée

	private ArrayList<int_Observer> listObserver = new ArrayList<int_Observer>();

	public Event_Case(int h, Couleur col, Coordonnees pos, abstr_Case type,boolean stat){
		this.set_coordonnees(pos);
		this.set_couleur(col);
		this.set_hauteur(h);
		this.setDest_add(type.get_coordonnees());
		this.setTo_add(type);//la case qui remplace
		this.setStatus(stat);
		this.addObserver(World.currentWorld.getFirstObserver());
	}

	public abstr_Case getTo_add() {
		return this.to_add;
	}
	public void setTo_add(abstr_Case to_add) {
		this.to_add = to_add;
	}
	public Coordonnees getDest_add() {
		return this.dest_add;
	}
	public void setDest_add(Coordonnees dest_add) {
		this.dest_add = dest_add;
	}
	public boolean getStatus() {
		return this.status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public void init(){
		try {
			this.setReplaced(World.currentWorld.get_case(this.dest_add));
		} catch (UnreachableCase e) {
			System.out.println("this World is corrupted");
			e.printStackTrace();
		}
	}

	public void refresh(){
		if (this.status){
			World.currentWorld.set_Case(this.dest_add, this.to_add);
			System.out.println("Refreshing replacing case");
			System.out.println(this.to_add);
			this.notifyObserver();
		}
		else{
			World.currentWorld.set_Case(this.dest_add, this.replaced);
			System.out.println("Refreshing normal case");
			System.out.println(this.dest_add);
		}

	}
	public abstr_Case getReplaced(){
		return this.replaced;
	}

	public void setReplaced(abstr_Case replaced) {
		this.replaced = replaced;
	}

	@Override
	public abstr_Case Clone() {
		Event_Case temp = new Event_Case(this.get_hauteur(),this.get_couleur(),this.get_coordonnees().
				Clone(),this.getTo_add().Clone(), this.getStatus());
		System.out.println("Cloning Event Case : "+this.listObserver.get(0));
		temp.addObserver(this.listObserver.get(0));
		return temp;
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
		System.out.println("Je suis dans le notify d'une case event");
		System.out.println("Liste des observateur de l'event case : "+this.listObserver.size());
		for(int_Observer obs : this.listObserver)
			obs.update(this);
	}

	/**
	 * notify depuis la case qui remplace l'autre
	 */
	public void notifyObserverfrom() {
		if (this.status){
			this.to_add.notifyObserver();
		}
		else{
			this.replaced.notifyObserver();
		}

	}

}
