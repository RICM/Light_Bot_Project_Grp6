package observable.map;

import couleur.Couleur;
import exception.UnreachableCase;

public class Event_Case extends abstr_Case{

	private abstr_Case to_add;
	private Coordonnees dest_add;
	private boolean status;
	private abstr_Case replaced;

	public Event_Case(int h, Couleur col, Coordonnees pos, Coordonnees dest, abstr_Case type,boolean stat){
		this.set_coordonnees(pos);
		this.set_couleur(col);
		this.set_hauteur(h);
		this.setDest_add(dest);
		this.setTo_add(type);
		this.setStatus(stat);
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
		}
		else{
			World.currentWorld.set_Case(this.dest_add, this.replaced);
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
		return new Event_Case(this.get_hauteur(),this.get_couleur(),this.get_coordonnees().
				Clone(),this.getDest_add().Clone(),this.getTo_add().Clone(), this.getStatus());
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
