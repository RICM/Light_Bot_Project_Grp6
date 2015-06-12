package observable.robot;

import observable.map.Coordonnees;
import observable.robot.Orientation.orientation;
import couleur.Couleur;

public class Position {
	private orientation or;
	private Coordonnees coor;
	private Couleur color;

	public Position(Coordonnees cor, orientation dir, Couleur col){
		this.coor = new Coordonnees(cor.get_x(), cor.get_y(), cor.get_n());
		this.or = dir;
		this.color= col;
	}

	public Coordonnees get_coordonnees(){
		return this.coor;
	}

	public orientation get_orientation(){
		return this.or;
	}

	public Couleur get_couleur(){
		return this.color;
	}

}
