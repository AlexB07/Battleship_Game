package uk.ac.yorksj.sem2.assignment;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class board extends Parent {

	private boolean player;
	private GridPane pane = new GridPane();
	private boardCells[][] b = new boardCells[10][10];

	public board(boolean who, EventHandler<MouseEvent> handle) {
		this.player = who;
		int size = 9;

		for (int y = 0; y <= size; y++) {

			for (int x = 0; x <= size; x++) {
				b[x][y] = new boardCells(); 
				b[x][y].setMinSize(30, 30);
				b[x][y].setOnMouseClicked(handle);
				b[x][y].getStyleClass().add("background");
				pane.add(b[x][y], x, y);
				// Button btn = b[x][y];
				// Player board, eg. placing ships
			}
		}
	}

	public boolean placeShip(ships ship, int x, int y, boolean north, boolean pl) {
		int tempX = x;
		int tempY = y;
		ship.setNorth(north);

		if (validPlaceShips(ship, tempX, tempY)) {
			if (ship.getNorth()) {
				for (int i = 0; i < ship.getLength(); i++) {
					getButton(tempX, tempY + i, pl, ship);
					
				}
				return true;
			} else
				for (int i = 0; i < ship.getLength(); i++) {
					getButton(tempX + i, tempY, pl, ship);
				}
			return true;
		} else
			return false;
	}

	public boolean validPlaceShips(ships ship, int x, int y) {

		if (ship.getNorth()) {

			for (int i = 0; i < ship.getLength(); i++) {

				// Test for in board
				if (!(x >= 0 && x <= 9 && y + i >= 0 && y + i <= 9)) {
					return false;
				}

				if (this.b[x][y + i].getText().equals("1")) {
					return false;
				}

			}

		} else {

			for (int i = 0; i < ship.getLength(); i++) {

				// Test for in board
				if (!(x + i >= 0 && x + i <= 9 && y >= 0 && y <= 9)) {
					return false;
				}
				// Test to see space is empty
				if (this.b[x + i][y].getText().equals("1")) {
					return false;
				}

			}
		}
		return true;
	}

	public GridPane getBoard() {
		return this.pane;
	}

	public void buttonMiss(int x, int y) {
		this.b[x][y].setText("0");
		this.b[x][y].getStyleClass().add("miss");
	}

	public void buttonHit(int x, int y) {
		
		this.b[x][y].setText("2");
		this.b[x][y].getStyleClass().add("hit");
		ships temp = this.b[x][y].getShip();
		temp.hit();
		if (!temp.alive()) {
			System.out.println("You have sunk the enemy ship");
		}
	}
	

	public void getButton(int x, int y, boolean player, ships ship) {
		this.b[x][y].setText("1");
		if (player) {
			this.b[x][y].getStyleClass().clear();
			this.b[x][y].getStyleClass().add("playerShip");
			System.out.println("playerShip");
		}
		this.b[x][y].setShip(ship);
	}

	public Object getButtonLocation(int x, int y) {
		return this.b[x][y];
	}
}
