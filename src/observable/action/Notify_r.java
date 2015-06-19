package observable.action;

import java.util.ArrayList;

import couleur.Couleur;
import exception.MouvementEx;
import observable.map.World;
import observable.map.abstr_Case;
import observable.robot.abstr_Robot;
import observer.int_Observer;

public class Notify_r implements int_Action {

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
	public static Notify_r notify_r(){
		return new Notify_r();
	}
	public static Notify_r notify_r(Couleur col){
		return new Notify_r(col);
	}
	private Notify_r(){
		this.color = Couleur.GRIS;
	}
	private Notify_r(Couleur col){
		this.color = col;
	}
	/**
	 * tourne le robot vers la gauche (cycle : UP,LEFT,BOTTOM,RIGHT)
	 */
	@Override
	public void execute(abstr_Robot r) throws MouvementEx {
		if (this.isPossible(r, null)){
			for(int i =0; i< World.currentWorld.get_liste_robot().length;i++){
				World.currentWorld.get_robot(i).set_activable(true);
				r.setVoid();
			}
		}
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
		// TODO Auto-generated method stub
		int_Action temp = new Notify_r(this.getColor());
		return temp;
	}
}
