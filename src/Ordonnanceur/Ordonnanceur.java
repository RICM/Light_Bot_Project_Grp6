package Ordonnanceur;

import java.util.LinkedList;

import observable.robot.abstr_Robot;
import exception.ActionEx;
import exception.MouvementEx;
import exception.UnreachableCase;

public class Ordonnanceur {
	private LinkedList<abstr_Robot> list_robot = new LinkedList<abstr_Robot>();

	public void addRobot(abstr_Robot r){
		this.list_robot.add(r);
	}

	public void removeFirst(abstr_Robot r){
		this.list_robot.removeFirst();
	}

	public void removeRobot(int indice){
		this.list_robot.remove(indice);
	}

	public void execute() throws MouvementEx, UnreachableCase, ActionEx{
		for (int i =0 ; i < this.list_robot.size() ; i++){
			if (this.list_robot.get(i).get_activable()){
				this.list_robot.get(i).execute();
			}
		}
	}
}
