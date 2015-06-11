package observable.robot;

import observable.map.Coordonnees;
import observable.robot.Orientation.orientation;

public class Position {
	private orientation or;
	private Coordonnees coor;
	
	public Position(Coordonnees cor, orientation dir){
		this.coor = new Coordonnees(cor.get_x(), cor.get_y(), cor.get_n());
		this.or = dir;
	}
	
	public Coordonnees get_coordonnees(){
		return this.coor;
	}
	
	public orientation get_orientation(){
		return this.or;
	}
	
}
