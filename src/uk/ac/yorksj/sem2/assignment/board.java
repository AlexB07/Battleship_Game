package uk.ac.yorksj.sem2.assignment;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class board extends Parent {

	private boolean player;
	private GridPane pane = new GridPane();
	private Button[][] b = new Button[10][10];
	private boolean vert = false;

	public board(boolean who, EventHandler<MouseEvent> handle) {
		this.player = who;
		int size = 9;

		for (int y = 0; y <= size; y++) {

			for (int x = 0; x <= size; x++) {
				b[x][y] = new Button();
				b[x][y].setMinSize(30, 30);
				b[x][y].setOnMouseClicked(handle);
				pane.add(b[x][y], x, y);
				// Button btn = b[x][y];
				// Player board, eg. placing ships
			}
		}
	}
	
	public void placeShip(ships ship, Object btn, boolean north) {
		int tempX = GridPane.getColumnIndex((Node) btn);
		int tempY = GridPane.getRowIndex((Node) btn);
		
		ship.setNorth(north);
		if (ship.getNorth()) {
			for (int i = 0; i < ship.getLength(); i++) {
				getButton(tempX, tempY + i);
			}
		}else 
			for (int i = 0; i < ship.getLength(); i++) {
				getButton(tempX + i, tempY);
			}
		
	}
	
	

	public GridPane getBoard() {
		return this.pane;
	}

	public void getButton(int x, int y) {
		this.b[x][y].setText("1");
	}
}
