package observable.map;

import java.util.ArrayList;

import observer.int_Observer;

public class Empty_Case extends abstr_Case {
	public Empty_Case(){
		this.addObserver(World.currentWorld.getFirstObserver());
	}

	private ArrayList<int_Observer> listObserver = new ArrayList<int_Observer>();

	@Override
	public abstr_Case Clone() {
		return new Empty_Case();
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
}
