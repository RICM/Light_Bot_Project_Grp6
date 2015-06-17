package Ordonnanceur;

import java.util.LinkedList;

import observable.robot.abstr_Robot;
import exception.ActionEx;
import exception.MouvementEx;
import exception.UnreachableCase;

public class Ordonnanceur {
	private LinkedList<abstr_Robot> list_robot = new LinkedList<abstr_Robot>();
	private int ind_ex;

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
	}

	private void increment_ind() {
		if (this.ind_ex >= this.list_robot.size()){
			this.ind_ex = 0;
		}
		else{
			this.ind_ex++;
		}

	}

	public void execute_next() throws MouvementEx, UnreachableCase, ActionEx{
		if(this.list_robot.get(this.ind_ex).get_activable()){
			this.list_robot.get(this.ind_ex).execute();
		}
		this.increment_ind();
	}

	public void execute() throws MouvementEx, UnreachableCase, ActionEx{
		for (int i =0 ; i < this.list_robot.size() ; i++){
			System.out.println("taille list exec rob : " + this.list_robot.get(i).get_run().size());
			if (this.list_robot.get(i).get_activable()){
				System.out.println("lala1");
				this.list_robot.get(i).execute();
				System.out.println("lala2");
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
