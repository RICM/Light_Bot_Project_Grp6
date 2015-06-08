package observable;

import observer.int_Observer;

public interface int_Observable {
	
	public void addObserver(int_Observer obs);
	public void removeObserver();
	public void notifyObserver();

}
