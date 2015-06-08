package observable.map;

import java.util.ArrayList;

import couleur.Couleur;
import observable.robot.Orientation;
import observable.robot.Robot;
import exception.UnreachableCase;
import observable.int_Observable;
import observer.int_Observer;

public class Terrain implements int_Observable{

	private ArrayList<int_Observer> listObserver = new ArrayList<int_Observer>(); 
	
	public Terrain(int x, int y){
		this.terrain = new abstr_Case[y][x];
		notifyObserver();
	}
	
	private abstr_Case[][] terrain;
	
	public void add_case(int x, int y, abstr_Case c){
		terrain[y][x] = c;
		notifyObserver();
	}
	
	public abstr_Case get_case (int x, int y) throws UnreachableCase{
		//System.out.println(x+" , "+y);
		if (y >= 0 && y < terrain.length){
			if (x >= 0 && x < terrain[x].length){
				return terrain[y][x];
			}
		}
		throw new UnreachableCase("Case is unreachable");
	}
	
	public void print_basic_terrain(Robot[] robotList){
		for (int i = 0; i < terrain.length; i++){
			String horizontal_separator = " ";
			for (int y = 0; y < terrain[i].length; y++){
				horizontal_separator = horizontal_separator + "________________";
			}
			System.out.println(horizontal_separator);
			for (int y = 0; y < terrain[i].length; y++){
				boolean caserobot = false;
				for (int j = 0; j < robotList.length; j++){
					if (robotList[j].getCurrent_Case().get_coordonnees().equals(terrain[i][y].get_coordonnees())){
						caserobot = true;
					}
				}
				if (y == 0){
					System.out.print("|");
				}
				if (!caserobot){
					System.out.print("\t");
					switch (terrain[i][y].getClass().getCanonicalName()){
						case "map.Painted_Case":
							System.out.print("P");
							break;
						case "map.Normal_Case":
							System.out.print("N");
							break;
						case "map.Illuminated_Case":
							System.out.print("I");
							break;
						case "map.Teleporter_Case":
							System.out.print("T");
							break;
					}
					if(terrain[i][y].get_couleur()==Couleur.BLEU){
						System.out.print("B");
					}
					else if(terrain[i][y].get_couleur()==Couleur.JAUNE){
						System.out.print("J");
					}
					System.out.print("\t");
					
				}else{
					switch (robotList[0].getOrientation()){
					case TOP : 
						System.out.print("\t^");
						break;
					case RIGHT :
						System.out.print("\t>");
						break;
					case LEFT:
						System.out.print("\t<");
						break;
					case BOT:
						System.out.print("\tv");
						break;
					}
					System.out.print("ROBERT\t");
				}
				System.out.print("|");
			}
			System.out.println("");
		}
		String horizontal_separator = " ";
		for (int a = 0; a < terrain[terrain.length-1].length; a++){
			horizontal_separator = horizontal_separator + "________________";
		}
		System.out.println(horizontal_separator);
	}
	
	public abstr_Case[][] get_terrain(){
		return this.terrain;
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
}
