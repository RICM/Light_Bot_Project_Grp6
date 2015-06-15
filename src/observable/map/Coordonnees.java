package observable.map;

public class Coordonnees {
	private int x;
	private int y;
	private int n;


	/**
	 *
	 * @param coordx
	 * @param coordy
	 * @param identificateur
	 */
	public Coordonnees(int coordx, int coordy, int identificateur){
		this.x = coordx;
		this.y = coordy;
		this.n = identificateur;
	}

	public boolean equals(Coordonnees toTest){
		return (this.x == toTest.get_x() &&
				this.y == toTest.get_y() &&
				this.n == toTest.get_n());
	}

	public void set_x(int coordx){
		this.x = coordx;
	}

	public void set_y(int coordy){
		this.y = coordy;
	}

	public void set_n(int coordn){
		this.n = coordn;
	}

	public int get_x(){
		return this.x;
	}

	public int get_y(){
		return this.y;
	}

	public int get_n(){
		return this.n;
	}

	public Coordonnees Clone(){
		return new Coordonnees(this.x,this.y,this.n);
	}
}
