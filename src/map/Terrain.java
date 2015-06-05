package map;

public class Terrain {

	private abstr_Case[][] terrain;
	
	public void add_case(int x, int y, abstr_Case c){
		terrain[x][y] = c;
	}
	
	public abstr_Case get_case (int x, int y){
		return terrain[x][y];
	}
	
}
