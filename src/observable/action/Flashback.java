package observable.action;

import java.util.ArrayList;

import couleur.Couleur;
import observable.map.World;
import observable.map.abstr_Case;
import observable.robot.Position;
import observable.robot.abstr_Robot;
import observer.int_Observer;
import exception.ActionEx;
import exception.MouvementEx;
import exception.UnreachableCase;

public class Flashback implements int_Action {

	private ArrayList<int_Observer> listObserver = new ArrayList<int_Observer>(); 
	
	private Couleur color;

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
		listObserver = new ArrayList<int_Observer>();
		
	}
	@Override
	public void notifyObserver() {
		for(int_Observer obs : listObserver)
		      obs.update(this);
	}

	@Override
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
			}
		}
		else{
			throw new ActionEx("Pas de position en memoire");
		}
		
	}

	@Override
public boolean isPossible(abstr_Robot r, abstr_Case c) {
		return ((r.get_size_pos()>0) &&
					(r.get_couleur().equals(color) || this.color.equals(Couleur.GRIS)));
	}

}
