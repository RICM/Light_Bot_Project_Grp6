package action;

import map.World;
import couleur.Couleur;
import map.abstr_Case;
import robot.Orientation;
import robot.Robot;

public class TurnRIght implements int_Action{
	
	private Couleur color;
	public static TurnRIght turn_right(){
		return new TurnRIght();
	}
	public static TurnRIght turn_right(Couleur col){
		return new TurnRIght(col);
	}
	private TurnRIght(){
		this.color = Couleur.GRIS;
	}
	private TurnRIght(Couleur col){
		this.color = col;
	}
	
	public void execute(Robot r) {
		switch (r.getOrientation()) {  
    		case  TOP :
    			r.setOrientation(Orientation.orientation.RIGHT);
    			break;
    		case  BOT :
    			r.setOrientation(Orientation.orientation.LEFT);
    			break;
    		case  LEFT :
    			r.setOrientation(Orientation.orientation.TOP);
    			break;
    		case  RIGHT :
    			r.setOrientation(Orientation.orientation.BOT);
    			break;
		}
		World.currentWorld.basic_print_world();
	}

	public boolean isPossible(Robot r, abstr_Case c) {
		return (color == Couleur.GRIS || r.get_couleur() == color);
	}

}
