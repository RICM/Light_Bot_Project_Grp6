package map;
import couleur.Couleur;

public class Normal_Case extends abstr_Case {
	
	public Normal_Case(int h, Coordonnees cordo){
		this.couleur = Couleur.GRIS;
		this.coordonnees = cordo;
		this.hauteur = h;
	}
}
