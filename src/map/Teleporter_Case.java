package map;
import couleur.*;

public class Teleporter_Case extends abstr_Case {
	private abstr_Case dest;
	
	public Teleporter_Case(int h, Couleur Color, Coordonnees cord, abstr_Case destination){
		this.set_couleur(Color);
		this.set_coordonnees(cord);
		this.set_hauteur(h);
		this.dest = destination;
	}
	
	public abstr_Case get_destination(){
		return this.dest;
	}
	
	public void set_destination(abstr_Case new_dest){
		this.dest = new_dest;
	}
}
