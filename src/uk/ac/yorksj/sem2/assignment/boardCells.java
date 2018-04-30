package uk.ac.yorksj.sem2.assignment;

import javafx.scene.control.Button;

public class boardCells extends Button {
	
	private ships ship = null;
	
	public void setShip(ships s) {
		this.ship = s;
	}

	
	public ships getShip() {
		return this.ship;
	}
}
