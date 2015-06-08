package observable.map;
import couleur.*;

public abstract class abstr_Case {
	private int hauteur;
	private Couleur couleur;
	private Coordonnees coordonnees;
	
	public Couleur get_couleur(){
		return this.couleur;
	}
	public void set_couleur(Couleur color){
			this.couleur = color;
	}
	
	public void set_hauteur(int haut){
		this.hauteur = haut;
	}
	
	public void set_coordonnees(Coordonnees coordo){
		this.coordonnees = coordo;
	}
	public int get_hauteur(){
		return this.hauteur;
	}
	
	public Coordonnees get_coordonnees(){
		return this.coordonnees;
	}
}
