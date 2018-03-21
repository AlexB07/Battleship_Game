package uk.ac.yorksj.sem2.assignment;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class board extends Parent {

	private boolean player;
	private GridPane pane = new GridPane();
	private Button[][] b = new Button[10][10];
	private boolean vert = false;

	public board(boolean who) {
		this.player = who;
		int size = 9;

		for (int y = 0; y <= size; y++) {

			for (int x = 0; x <= size; x++) {
				b[x][y] = new Button();
				b[x][y].setMinSize(30, 30);
				pane.add(b[x][y], x, y);
				Button btn = b[x][y];
				// Player board, eg. placing ships

				if (this.player == true) {
					btn.setOnMouseClicked(new EventHandler<MouseEvent>() {					
						int tempX = GridPane.getColumnIndex(btn);
						int tempY = GridPane.getRowIndex(btn);

						public void handle(MouseEvent e) {
							// TODO place ships
							// set vertical ships
							if (e.getButton().equals(MouseButton.PRIMARY)) {
								for (int i = 0; i < 3; i++) {
									getButton(tempX, tempY + i);
								}
							}
						}
					});
				} else {

					// Enemy Board, eg. attacking ships
					btn.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							// TODO attack enemy board
						}
					});

				}
			}
		}
	}

	public GridPane getBoard() {
		return this.pane;
	}

	public void getButton(int x, int y) {
		this.b[x][y].setText("1");
	}
}
