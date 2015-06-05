package map;

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
			for (int y = 0; y < terrain[i].length; y++){
				boolean caserobot = false;
				for (int j = 0; j < robotList.length; j++){
					if (robotList[j].getCurrent_Case().coordonnees.equals(terrain[i][y].coordonnees)){
						caserobot = true;
					}
				}
				if (!caserobot){
					System.out.print("\tO\t");
				}else{
					System.out.print("\tX\t");
				}
			}
			System.out.println("");
		}
	}
	
	public abstr_Case[][] get_terrain(){
		return this.terrain;
	}
}
