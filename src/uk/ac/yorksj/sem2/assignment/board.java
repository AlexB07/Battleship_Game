package uk.ac.yorksj.sem2.assignment;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class board extends Parent {

	private boolean player;
	private GridPane pane = new GridPane();
	private boardCells[][] btns = new boardCells[initializeGame.getSetting(0)][initializeGame.getSetting(0)];
	private ArrayList<ships> ships = new ArrayList<ships>();

	public board(boolean who, EventHandler<MouseEvent> handle) {
		this.player = who;

		for (int y = 0; y < btns.length; y++) {

			for (int x = 0; x < btns.length; x++) {
				btns[x][y] = new boardCells();
				btns[x][y].setMinSize(30, 30);
				btns[x][y].setOnMouseClicked(handle);
				btns[x][y].getStyleClass().add("background");
				pane.add(btns[x][y], x, y);
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

	public boolean placeShip(ships ship, int x, int y, boolean north, int boardSize) {
		int tempX = x;
		int tempY = y;
		ship.setNorth(north);

		if (validPlaceShips(ship, tempX, tempY, boardSize)) {
			if (ship.getNorth()) {
				for (int i = 0; i < ship.getLength(); i++) {
					updateButton(tempX, tempY + i, this.getPlayer(), ship);

				}
				return true;
			} else
				for (int i = 0; i < ship.getLength(); i++) {
					updateButton(tempX + i, tempY, this.getPlayer(), ship);
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

				if (this.btns[x][y + i].getText().equals("1")) {
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
				if (this.btns[x + i][y].getText().equals("1")) {
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
		this.btns[x][y].setText("0");
		this.btns[x][y].getStyleClass().add("miss");
	}

	public boolean buttonHit(int x, int y, boolean player) {
		String playerName = "";
		if (player) {
			new Audio("hit.mp3");
			playerName = "[ENEMY]";
		} else
			playerName = "[PLAYER]";

		this.btns[x][y].setText("2");
		this.btns[x][y].getStyleClass().add("hit");
		ships temp = this.btns[x][y].getShip();
		temp.hit();
		if (!temp.isAlive()) {
			removeShip(this.ships.indexOf(temp));
			// sink.start();
			initializeGame
					.appendChatBox(playerName + " You've sunk my battleship. Ships left: " + this.ships.size() + "\n");
			return true;
		}

		return true;
	}

	public void updateButton(int x, int y, boolean player, ships ship) {
		this.btns[x][y].setText("1");
		if (player) {
			this.btns[x][y].getStyleClass().clear();
			this.btns[x][y].getStyleClass().add("playerShip");
		}
		this.btns[x][y].setShip(ship);
	}

	public Object getButtonLocation(int x, int y) {
		return this.btns[x][y];
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
		return this.btns[x][y].getText();
	}
}
