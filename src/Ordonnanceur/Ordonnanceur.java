package Ordonnanceur;

import java.util.LinkedList;

import exception.ActionEx;
import exception.MouvementEx;
import exception.UnreachableCase;
import observable.robot.abstr_Robot;

public class Ordonnanceur {
	private LinkedList<abstr_Robot> list_robot = new LinkedList<abstr_Robot>();
	
	public void addRobot(abstr_Robot r){
		list_robot.add(r);
	}

	public void removeFirst(abstr_Robot r){
		list_robot.removeFirst();
	}
	
	public void removeRobot(int indice){
		list_robot.remove(indice);
	}
	
	public void execute() throws MouvementEx, UnreachableCase, ActionEx{
		for (int i =0 ; i < list_robot.size() ; i++){
			list_robot.get(i).execute();
		}
	}
}
