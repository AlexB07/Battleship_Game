package uk.ac.yorksj.sem2.assignment;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class board extends Parent {

	private boolean player;
	private GridPane pane = new GridPane();
	private boardCells[][] b = new boardCells[initializeGame.getSetting(0)][initializeGame.getSetting(0)];
	private ArrayList<ships> ships = new ArrayList<ships>();

	public board(boolean who, EventHandler<MouseEvent> handle) {
		this.player = who;

		for (int y = 0; y < b.length; y++) {

			for (int x = 0; x < b.length; x++) {
				b[x][y] = new boardCells();
				b[x][y].setMinSize(30, 30);
				b[x][y].setOnMouseClicked(handle);
				b[x][y].getStyleClass().add("background");
				pane.add(b[x][y], x, y);
			}
		}

		this.ships = initateShips();
	}

	public boolean getPlayer() {
		return this.player;
	}

	public ArrayList<ships> initateShips() {
		ArrayList<ships> temp = new ArrayList<ships>();
		// aircraft ships
		for (int i = 0; i < initializeGame.getSetting(1); i++) {
			temp.add(new ships(5));
		}

		// battleship
		for (int i = 0; i < initializeGame.getSetting(2); i++) {
			temp.add(new ships(4));
		}

		// destroyer ship
		for (int i = 0; i < initializeGame.getSetting(3); i++) {
			temp.add(new ships(3));
		}

		// patrol Ships
		for (int i = 0; i < initializeGame.getSetting(4); i++) {
			temp.add(new ships(2));
		}
		return temp;
	}

	public boolean placeShip(ships ship, int x, int y, boolean north, boolean pl, int boardSize) {
		int tempX = x;
		int tempY = y;
		ship.setNorth(north);

		if (validPlaceShips(ship, tempX, tempY, boardSize)) {
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

	public boolean validPlaceShips(ships ship, int x, int y, int boardSize) {

		if (ship.getNorth()) {

			for (int i = 0; i < ship.getLength(); i++) {

				// Test for in board
				if (!(x >= 0 && x <= boardSize - 1 && y + i >= 0 && y + i <= boardSize - 1)) {
					return false;
				}

				if (this.b[x][y + i].getText().equals("1")) {
					return false;
				}

			}

		} else {

			for (int i = 0; i < ship.getLength(); i++) {

				// Test for in board
				if (!(x + i >= 0 && x + i <= boardSize - 1 && y >= 0 && y <= boardSize - 1)) {
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

	public void buttonMiss(int x, int y, boolean player) {
		if (player) {
			new Audio("miss.mp3");
		}
		this.b[x][y].setText("0");
		this.b[x][y].getStyleClass().add("miss");
	}

	public boolean buttonHit(int x, int y, boolean player) {
		if (player) {
			new Audio("hit.mp3");
		}
		this.b[x][y].setText("2");
		this.b[x][y].getStyleClass().add("hit");
		ships temp = this.b[x][y].getShip();
		temp.hit();
		if (!temp.alive()) {
			removeShip(this.ships.indexOf(temp));
			// sink.start();
			System.out.println("You sunk a ship");
			return false;
		}

		return true;
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

	public int getShipSize() {
		return this.ships.size();
	}

	public ArrayList<ships> getships() {
		return this.ships;
	}

	public void removeShip(int i) {
		this.ships.remove(i);
	}

	public String checkButton(int x, int y) {
		return this.b[x][y].getText();
	}
}
