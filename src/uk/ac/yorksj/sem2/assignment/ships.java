package uk.ac.yorksj.sem2.assignment;

public class ships {

	public int ship;
	private int health;
	private boolean north;
	public int[][] pos;

	public ships(int type) {
		this.ship = type;
		this.health = type;
		this.north = false;
		pos = new int[type][type];
	}

	public int getHealth() {
		return this.health;
	}

	public int hit() {
		return this.health--;
	}
	
	public boolean getNorth() {
		return this.north;
	}
	
	public int getLength() {
		return this.ship;
	}

	public void setNorth(boolean north) {
		this.north = north; 
	}
	
	
}
