package uk.ac.yorksj.sem2.assignment;

public class ships {

	public int ship;
	private int health;

	public ships(int type) {
		this.ship = type;
		this.health = type;
	}

	public int getHealth() {
		return this.health;
	}

	public int hit() {
		return this.health--;
	}

}
