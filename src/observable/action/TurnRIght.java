package observable.action;

import java.util.ArrayList;

import couleur.Couleur;
import observable.int_Observable;
import observable.map.World;
import observable.map.abstr_Case;
import observable.robot.Orientation;
import observable.robot.abstr_Robot;
import observer.int_Observer;

public class TurnRIght implements int_Action, int_Observable{

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
	/**
	 * tourne le robot vers la droit (cycle : TOP,RIGHT,BOTTOM,LEFT)
	 */
	@Override
	public void execute(abstr_Robot r) {
		if(this.isPossible(r,r.getCurrent_Case())){
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
		}
		World.currentWorld.basic_print_world();
	}

	@Override
	public boolean isPossible(abstr_Robot r, abstr_Case c) {
		return (this.color.equals(Couleur.GRIS) || r.get_couleur().equals(this.color));
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
		int_Action temp = new TurnRIght(this.getColor());
		return temp;
	}

}
