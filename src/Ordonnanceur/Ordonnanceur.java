package Ordonnanceur;

import java.util.LinkedList;

import exception.ActionEx;
import exception.MouvementEx;
import exception.UnreachableCase;
import observable.robot.abstr_Robot;

public class Ordonnanceur {
	private LinkedList<abstr_Robot> list_robot = new LinkedList<abstr_Robot>();
	private int ind_ex = 0;
	private boolean ready = true;

	public void addRobot(abstr_Robot r){
		this.list_robot.add(r);
	}

	public void removeFirst(abstr_Robot r){
		this.list_robot.removeFirst();
	}

	public void removeRobot(int indice){
		this.list_robot.remove(indice);
	}
	public void executeFirstAction() throws MouvementEx, UnreachableCase, ActionEx{
		this.ind_ex = 0;
		if(this.list_robot.get(this.ind_ex).get_activable()){
			this.list_robot.get(this.ind_ex).execute();
		}
		this.increment_ind();
		this.ready = true;
	}

	private void increment_ind() {
		this.ind_ex++;
		if (this.ind_ex >= this.list_robot.size()){
			this.ind_ex = 0;
		}

	}

	public void execute_next() throws MouvementEx, UnreachableCase, ActionEx{
		System.out.println("Ich bin in execute-next");
		if(this.list_robot.get(this.ind_ex).get_activable()){
			this.ready = false;
			this.list_robot.get(this.ind_ex).execute();
		}
		this.increment_ind();
	}

	public void execute() throws MouvementEx, UnreachableCase, ActionEx{
		for (int i =0 ; i < this.list_robot.size() ; i++){
			System.out.println("taille list exec rob : " + this.list_robot.get(i).get_run().size());
			if (this.list_robot.get(i).get_activable()){
				this.setReady(false);
				this.list_robot.get(i).execute();
			}
		}
	}

	public boolean isReady() {
		return this.ready;
	}

	public int size(){
		return this.list_robot.size();
	}
	public void setReady(boolean ready) {
		System.out.println("boolean mis a vrai");
		this.ready = ready;
	}
}
