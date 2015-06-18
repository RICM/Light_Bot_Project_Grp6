package observable.action;

import java.util.ArrayList;

import couleur.Couleur;
import exception.ActionEx;
import exception.MouvementEx;
import exception.UnreachableCase;
import observable.map.World;
import observable.map.abstr_Case;
import observable.robot.Position;
import observable.robot.abstr_Robot;
import observer.int_Observer;

public class Flashback implements int_Action {

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

	public static Flashback flashback(){
		return new Flashback();
	}

	private Flashback(){
		this.color = Couleur.GRIS;
	}

	public static Flashback flashback(Couleur col){
		return new Flashback(col);
	}

	private Flashback(Couleur col){
		this.color = col;
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
	/**
	 * ramène le robot au dernier emplacement mémorisé si l'emplacement existe et est libre
	 */
	public void execute(abstr_Robot r) throws MouvementEx, UnreachableCase,
	ActionEx {
		if (this.isPossible(r,null)){
			Position pos_dest = r.get_last_pos();
			abstr_Case dest = World.currentWorld.get_case(pos_dest.get_coordonnees());
			if(World.currentWorld.isOccupied(dest)){
				throw new MouvementEx("destination occupee");
			}
			else{
				r.setCurrent_Case(dest);
				r.setOrientation(pos_dest.get_orientation());
				r.set_couleur(pos_dest.get_couleur());
			}
		}
		else{
			System.out.println("Pas de position en mémoire");
		}

	}

	@Override
	public boolean isPossible(abstr_Robot r, abstr_Case c) {
		return ((r.get_size_pos()>0) &&
				(r.get_couleur().equals(this.color) || this.color.equals(Couleur.GRIS)));
	}

	@Override
	public int_Action Clone() {
		Flashback temp;
		temp = flashback(this.color);
		return temp;
	}

}
