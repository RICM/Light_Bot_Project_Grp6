package observable.action;

import java.util.ArrayList;

import couleur.Couleur;
import exception.MouvementEx;
import observable.int_Observable;
import observable.map.World;
import observable.map.abstr_Case;
import observable.robot.Orientation;
import observable.robot.abstr_Robot;
import observer.int_Observer;

public class TurnLeft implements int_Action, int_Observable{

	private ArrayList<int_Observer> listObserver = new ArrayList<int_Observer>();

	private Couleur color;
	@Override
	public Couleur getColor() {
		return this.color;
	}
	@Override
	public void setColor(Couleur color) {
		this.color = color;
	}
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
	/**
	 * tourne le robot vers la gauche (cycle : UP,LEFT,BOTTOM,RIGHT)
	 */
	@Override
	public void execute(abstr_Robot r) throws MouvementEx {
		if(this.isPossible(r,r.getCurrent_Case())){
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
			this.notifyObserver();
		}
		r.setVoid();
		World.currentWorld.basic_print_world();
		System.out.println("Couleur de l'action : "+this.color.toString());
	}


	@Override
	public boolean isPossible(abstr_Robot r, abstr_Case c) {
		return (Couleur.GRIS.equals(this.color) || r.get_couleur().equals(this.color));
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
		for(int_Observer obs : this.listObserver)
			obs.update(this);
	}
	@Override
	public int_Action Clone() {
		int_Action temp = new TurnLeft(this.getColor());
		return temp;
	}
}
