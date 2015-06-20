package Ordonnanceur;

import java.util.ArrayList;
import java.util.LinkedList;

import exception.ActionEx;
import exception.MouvementEx;
import exception.UnreachableCase;
import observable.int_Observable;
import observable.robot.abstr_Robot;
import observer.int_Observer;

public class Ordonnanceur implements int_Observable {

	//creation d'une liste de Robot qui permet de déterminer leurs ordres de passages
	private LinkedList<abstr_Robot> list_robot = new LinkedList<abstr_Robot>();
	private LinkedList<abstr_Robot> list_robot_save = new LinkedList<abstr_Robot>();
	// creation d'un tableau d'objet
	private ArrayList<int_Observer> controller = new ArrayList<int_Observer>();
	private int ind_ex = 0;
	private boolean ready = true;
	private boolean modifiable = false;

	public LinkedList<abstr_Robot> getListeRobotOrdonnanceur(){
		return this.list_robot;
	}

	public boolean getModifiable(){
		return this.modifiable;
	}

	public void setModifiable(boolean b){
		this.modifiable = b;
	}
	public int getNumberRobots(){
		return this.list_robot.size();
	}

	public boolean isReady() {
		return this.ready;
	}

	public int size(){
		return this.list_robot.size();
	}
	public void setReady(boolean ready) {
		this.notifyObserver();
		this.ready = ready;
	}


	/* end of Getters & setters */


	// Ajout d'un robot en queue de linkedlist.
	public void addRobot(abstr_Robot r){
		this.list_robot.add(r);
	}

	//Suppression du premier élément de la linkedlist.
	public void removeFirst(){
		this.list_robot.removeFirst();
	}

	// Suppression de l'élément d'indice "indice" de la linkedlist.
	public void removeRobot(int i){
		this.list_robot.remove(i);
	}

	//Verifie que le robot d'indice 0 de la liste soit activable puis l'execute
	public void executeFirstAction() throws MouvementEx, UnreachableCase, ActionEx{
		this.ind_ex = 0;
		if(this.list_robot.get(this.ind_ex).get_activable()){
			this.list_robot.get(this.ind_ex).execute();
		}
		this.increment_ind();
		this.ready = true;
	}

	//Incrémente ind_ex en verifiant que ind_ex ne dépasse pas la taille de la liste de robot.
	public void increment_ind() {
		this.ind_ex++;
		if (this.ind_ex >= this.list_robot.size()){
			this.ind_ex = 0;
		}

	}

	public boolean removeRobotFromOrdo(abstr_Robot rob){
		return this.list_robot.remove(rob);
	}

	//Si le robot d'indice ind_ex est activable, il est execute.
	public void execute_next() throws MouvementEx, UnreachableCase, ActionEx, IndexOutOfBoundsException{
		System.out.println("ORDO LIST : "+this.list_robot);
		abstr_Robot robot;
		robot = this.list_robot.get(this.ind_ex);
		System.out.println("CURRENT ROBOT : "+robot.getClass().getSimpleName()+robot.toString());
		System.out.println("ROBOT IS ACTIVABLE ?"+robot.get_activable());
		if(robot.get_activable()){
			this.ready = false;
			this.list_robot.get(this.ind_ex).execute();
		}
		//this.increment_ind();
	}

	public int getNombreRobotsSave(){
		return this.list_robot_save.size();
	}

	//Execute tous les robots de la linkedlist.
	public void execute() throws MouvementEx, UnreachableCase, ActionEx{
		for (int i =0 ; i < this.list_robot.size() ; i++){
			System.out.println("taille list exec rob : " + this.list_robot.get(i).get_run().size());
			if (this.list_robot.get(i).get_activable()){
				this.setReady(false);
				this.list_robot.get(i).execute();
			}
		}
	}

	// Supprime tous les robots de la linkedlist
	public void removeRobots() {
		System.out.println("TAILLE DE LA LISTE DE ROBOT DE L'ORDO : "+this.list_robot.size());
		int taille_liste = this.list_robot.size();
		for (int i = 0; i < taille_liste; i++){
			System.out.println("REMOVE ROBOT DE L'ORDO : "+i);
			this.removeRobot(0);
		}
		this.ready = true;
	}

	@Override
	//Ajoute un controleur
	public void addObserver(int_Observer obs) {
		this.controller.add(obs);
	}
	@Override
	//supprime un controleur
	public void removeObserver() {
		this.controller = new ArrayList<int_Observer>();

	}

	@Override
	//Notifie le controleur
	public void notifyObserver() {
		for(int_Observer obs : this.controller)
			obs.update(this);
	}

	public void clearOrdoSave(){
		for (int i = 0; i < this.list_robot_save.size(); i++){
			this.list_robot_save.remove(i);
		}
	}

	public void restoreOrdo() {
		for (abstr_Robot rob : this.list_robot_save){
			this.list_robot.add(rob);
		}
	}

	public void clearSaveOrdo(){
		for (int i = 0; i < this.list_robot_save.size(); i++)
			this.list_robot_save.remove(i);
	}

	public void saveOrdoOnceCleared(){
		for (abstr_Robot rob : this.list_robot){
			this.list_robot_save.add(rob);
		}
	}

	public void saveOrdo(){
		this.clearSaveOrdo();
		this.saveOrdoOnceCleared();
	}


}
