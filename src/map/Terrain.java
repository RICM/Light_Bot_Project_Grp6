package map;

import robot.Orientation;
import robot.Robot;
import exception.UnreachableCase;

public class Terrain {

	public Terrain(int x, int y){
		this.terrain = new abstr_Case[x][y];
	}
	
	private abstr_Case[][] terrain;
	
	public void add_case(int x, int y, abstr_Case c){
		terrain[x][y] = c;
	}
	
	public abstr_Case get_case (int x, int y) throws UnreachableCase{
		//System.out.println(x+" , "+y);
		if (x >= 0 && x < terrain.length){
			if (y >= 0 && y < terrain[x].length){
				return terrain[x][y];
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
					System.out.print("\t\t");
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
}
