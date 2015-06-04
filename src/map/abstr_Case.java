package map;
import Couleur.*;

public abstract class abstr_Case {
	public int hauteur;
	public Couleur couleur;
	public Coordonnees coordonnees;
	
	public Couleur get_couleur(){
		return this.couleur;
	}
	
	public int get_hauteur(){
		return this.hauteur;
	}
	
	public Coordonnees get_coordonnees(){
		return this.coordonnees;
	}
}
