package map;

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
	
	public abstr_Case[][] get_terrain(){
		return this.terrain;
	}
}
