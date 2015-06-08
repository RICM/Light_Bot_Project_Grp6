package action;

import map.World;
import couleur.Couleur;
import exception.MouvementEx;
import map.abstr_Case;
import robot.Orientation;
import robot.Robot;

public class TurnLeft implements int_Action{
	
	private Couleur color;
	public static TurnLeft turn_left(){
		return new TurnLeft();
	}
	public static TurnLeft turn_left(Couleur col){
		return new TurnLeft(col);
	}
	private TurnLeft(){
		this.color = Couleur.GRIS;
	}
	private TurnLeft(Couleur col){
		this.color = col;
	}
	
	public void execute(Robot r) throws MouvementEx {
		if(isPossible(r,r.getCurrent_Case())){
		switch (r.getOrientation()) {  
    		case  TOP :
    			r.setOrientation(Orientation.orientation.LEFT);
    			break;
    		case  BOT :
    			r.setOrientation(Orientation.orientation.RIGHT);
    			break;
    		case  LEFT :
    			r.setOrientation(Orientation.orientation.BOT);
    			break;
    		case  RIGHT :
    			r.setOrientation(Orientation.orientation.TOP);
    			break;
		}
		}
		else{
			throw (new MouvementEx("impossible de tourner"));
		}
		World.currentWorld.basic_print_world();
		System.out.println("Couleur de l'action : "+color.toString());
	}


	public boolean isPossible(Robot r, abstr_Case c) {
		return (Couleur.GRIS.equals(color) || r.get_couleur().equals(color));
	}

}
