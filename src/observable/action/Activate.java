package observable.action;
import java.util.ArrayList;

import couleur.Couleur;
import exception.ActionEx;
import exception.MouvementEx;
import exception.UnreachableCase;
import observable.int_Observable;
import observable.map.Coordonnees;
import observable.map.Event_Case;
import observable.map.Illuminated_Case;
import observable.map.Teleporter_Case;
import observable.map.World;
import observable.map.abstr_Case;
import observable.robot.abstr_Robot;
import observer.int_Observer;

public class Activate implements int_Action, int_Observable{

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
	public static Activate activate(){
		return new Activate();
	}
	public static Activate activate(Couleur color){
		return new Activate(color);
	}
	private Activate(){
		this.color = Couleur.GRIS;
	}
	private Activate(Couleur col){
		this.color = col;
	}
	@Override
	/**
	 * si la case actuele est allumable, change l'état de celle ci
	 * si c'est un teleporteur, l'utilise
	 * si c'est une case peinet, r prend a couleur de celle ci
	 */
	public void execute(abstr_Robot r) throws MouvementEx, UnreachableCase, ActionEx {
		System.out.println("Ich bin in activate");
		abstr_Case cprime = r.getCurrent_Case();
		if (this.isPossible(r,cprime)){
			if(cprime.getClass().getSimpleName().equals("Teleporter_Case")){
				Coordonnees next = ((Teleporter_Case)cprime).get_destination();
				if(!World.currentWorld.isOccupied(World.currentWorld.get_case(next))){
					abstr_Case dest = World.currentWorld.get_case(((Teleporter_Case)cprime).get_destination());
					if (dest.getClass().getSimpleName().equals("Empty_Case")){
						throw new ActionEx("Null pointer");
					}
					else{
						r.setCurrent_Case(dest);
					}
				}
				else {
					throw new ActionEx("la case destination est occupée");
				}
			}
			else if(cprime.getClass().getSimpleName().equals("Illuminated_Case")){
				System.out.println("Ich bin in activate : là ou tu veux aller");
				((Illuminated_Case)cprime).set_active(!((Illuminated_Case)cprime).get_active());
				if (((Illuminated_Case)cprime).get_active()){
					World.currentWorld.increment_allume();
				}
				else {
					World.currentWorld.decrement_allume();
				}

			}
			else if(cprime.getClass().getSimpleName().equals("Event_Case")){
				((Event_Case)cprime).setStatus(!((Event_Case)cprime).getStatus());
				((Event_Case)cprime).notifyObserverfrom();
			}
			else {
				r.set_couleur(cprime.get_couleur());
			}
		}
		else{
			System.out.println("Impossible de faire ça");
		}
	}

	@Override
	/**
	 * verifie que la case est une case utilisable
	 */
	public boolean isPossible(abstr_Robot r, abstr_Case c) {
		return (((c.getClass().getSimpleName().equals("Teleporter_Case")
				|| c.getClass().getSimpleName().equals("Painted_Case"))
				|| c.getClass().getSimpleName().equals("Illuminated_Case")
				|| c.getClass().getSimpleName().equals("Event_Case"))

				&&(this.color.equals(r.get_couleur())||
						this.color.equals(Couleur.GRIS))) ;
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
		Activate temp = activate(this.getColor());
		return temp;
	}
}

