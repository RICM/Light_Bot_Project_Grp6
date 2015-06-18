package observable.map;

import java.util.ArrayList;

import couleur.Couleur;
import exception.UnreachableCase;
import observable.int_Observable;
import observable.robot.abstr_Robot;
import observer.int_Observer;

public class Terrain implements int_Observable{

	private ArrayList<int_Observer> listObserver = new ArrayList<int_Observer>();

	public Terrain(int x, int y){
		this.terrain = new abstr_Case[y][x];
		this.notifyObserver();
	}

	private abstr_Case[][] terrain;

	public void add_case(int x, int y, abstr_Case c){
		this.terrain[y][x] = c;
		this.notifyObserver();
	}

	public abstr_Case get_case (int x, int y) throws UnreachableCase{
		//System.out.println(x+" , "+y);
		if (y >= 0 && y < this.terrain.length){
			if (x >= 0 && x < this.terrain[y].length){
				return this.terrain[y][x];
			}
		}
		throw new UnreachableCase("Case is unreachable");
	}

	public void print_basic_terrain(abstr_Robot[] liste_robot){
		for (int i = 0; i < this.terrain.length; i++){
			String horizontal_separator = " ";
			for (int y = 0; y < this.terrain[i].length; y++){
				horizontal_separator = horizontal_separator + "________________";
			}
			System.out.println(horizontal_separator);
			for (int y = 0; y < this.terrain[i].length; y++){
				boolean caserobot = false;
				for (int j = 0; j < liste_robot.length; j++){
					abstr_Case temp = this.terrain[i][y];
					if(!temp.getClass().getSimpleName().equals("Empty_Case")){
						if (liste_robot[j].getCurrent_Case().get_coordonnees().equals(this.terrain[i][y].get_coordonnees())){
							caserobot = true;
						}
					}
				}
				if (y == 0){
					System.out.print("|");
				}
				if (!caserobot){
					System.out.print("\t");
					switch (this.terrain[i][y].getClass().getSimpleName()){
					case "Painted_Case":
						System.out.print("P");
						break;
					case "Empty_Case":
						System.out.print("E");
						break;
					case "Normal_Case":
						System.out.print("N");
						break;
					case "Illuminated_Case":
						System.out.print("I");
						break;
					case "Teleporter_Case":
						System.out.print("T");
						break;
					}
					if(this.terrain[i][y].get_couleur()==Couleur.VERT){
						System.out.print("B");
					}
					else if(this.terrain[i][y].get_couleur()==Couleur.ROUGE){
						System.out.print("J");
					}
					System.out.print("\t");

				}else{
					switch (liste_robot[0].getOrientation()){
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
		for (int a = 0; a < this.terrain[this.terrain.length-1].length; a++){
			horizontal_separator = horizontal_separator + "________________";
		}
		System.out.println(horizontal_separator);
		if (World.currentWorld.is_cleared()) {

			System.out.println("le jeu est cleared");
		}
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
		this.listObserver = new ArrayList<int_Observer>();

	}
	@Override
	public void notifyObserver() {
		for(int_Observer obs : this.listObserver)
			obs.update(this);
	}

	@SuppressWarnings("unchecked")
	public Terrain Clone() throws UnreachableCase{
		Terrain to_return = new Terrain(this.terrain[this.terrain.length-1].length,this.terrain.length);
		int i,j;
		j = this.terrain.length;
		i = this.terrain[j-1].length;
		for(int k = 0; k<j; k++){
			for(int l = 0;l<i ; l++){
				System.out.println("aaaaaaaaaaaaaaa");
				to_return.add_case(l, k, this.get_case(l, k).Clone());
				System.out.println(this.get_case(l, k).getClass().getSimpleName());
				if (this.get_case(l, k).getClass().getSimpleName().equals("Illuminated_Case")){
					System.out.println("aaaaaaaaaaaaaaa");
					System.out.println("CASE ILLUMINEE : "+((Illuminated_Case)to_return.get_case(l, k)).get_active());
				}
			}
		}
		to_return.listObserver=(ArrayList<int_Observer>) this.listObserver.clone();
		return to_return;
	}

	public void set_case(abstr_Case temp, int i, int j) {
		this.terrain[j][i]=temp;
	}
}
