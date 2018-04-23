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
	private boardCells[][] b = new boardCells[10][10];
	private ArrayList<ships> ships = new ArrayList<ships>();

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
		
		this.ships = initateShips();
	}
	
	public ArrayList<ships> initateShips() {
		ArrayList<ships> temp = new ArrayList<ships>();

		ships airCraft = new ships(5);
		ships battleShip1 = new ships(4);
		ships battleShip2 = new ships(4);
		ships desstroyers1 = new ships(3);
		ships desstroyers2 = new ships(3);
		ships patrol1 = new ships(2);
		ships patrol2 = new ships(2);
		ships patrol3 = new ships(2);

		temp.add(airCraft);
		temp.add(desstroyers1);
		temp.add(desstroyers2);
		temp.add(battleShip1);
		temp.add(battleShip2);
		temp.add(patrol1);
		temp.add(patrol2);
		temp.add(patrol3);

		return temp;
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

	public boolean buttonHit(int x, int y) {
		
		this.b[x][y].setText("2");
		this.b[x][y].getStyleClass().add("hit");
		ships temp = this.b[x][y].getShip();
		temp.hit();
		if (!temp.alive()) {
			removeShip(this.ships.indexOf(temp));
			System.out.println("You sunk a ship");
			return true;
		}
		
		return false;
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
}

