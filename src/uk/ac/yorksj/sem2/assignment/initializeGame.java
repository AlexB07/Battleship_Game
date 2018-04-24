package uk.ac.yorksj.sem2.assignment;

import java.util.ArrayList;
import java.util.Random;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class initializeGame extends Application {
	private boolean playerTurn = false;
	private board player;
	private board enemy;
	private int shipCount = 0;
	private boolean game = false;
	private Rectangle nextShip = new Rectangle();

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage s) throws Exception {
		Group root = new Group();

		root.getChildren().add(getGripPane());
		// nextShip.getStyleClass().add("playerShip")
		root.getChildren().add(nextShip);
		Scene scene = new Scene(root, 750, 700);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		s.setScene(scene);
		s.show();
		s.setTitle("BattleShip Game");
		initiateGame();

	}

	public GridPane getGripPane() {
		GridPane pane = new GridPane();
		enemy = new board(false, e -> {
			if (game) {
				Button btn = (Button) e.getSource();
				if (hitAndMiss(enemy, btn)) {
					if (enemy.getShipSize() == 0) {
						System.out.println("You WIN!");
						game = false;
					} else {
						playerTurn = false;
						enemyTurn();
					}
				}

			}

		});
		pane.add(enemy.getBoard(), 0, 0);

		player = new board(true, e -> {

			Button btn = (Button) e.getSource();
			if (playerTurn) {

				if (shipCount < player.getShipSize()) {
					player.getships().get(shipCount).setNorth(e.getButton() == MouseButton.PRIMARY);
					if (player.placeShip(player.getships().get(shipCount), GridPane.getColumnIndex(btn),
							GridPane.getRowIndex(btn), player.getships().get(shipCount).getNorth(), playerTurn)) {
						shipCount++;

						if (shipCount >= player.getShipSize()) {
							showCurrentShip(0);
							game = true;
						} else
							showCurrentShip(player.getships().get(shipCount).getLength());
					}

				} else {
					game = true;
				}

			}
		});
		pane.add(player.getBoard(), 0, 1);
		nextShip = new Rectangle(350, 485, 30, (player.getships().get(0).getLength() * 30));
		nextShip.setFill(Color.BLUE);
		nextShip.setStroke(Color.BLACK);
		nextShip.setStrokeWidth(4);
		nextShip.setStrokeType(StrokeType.INSIDE);
		pane.add(nextShip, 2, 3);
		pane.setVgap(35);

		return pane;
	}

	public void showCurrentShip(int length) {
		nextShip.setHeight(length * 30);
	}

	public boolean hitAndMiss(board bord, Button btn) {
		int tempX = GridPane.getColumnIndex(btn);
		int tempY = GridPane.getRowIndex(btn);
		if (btn.getText().equals("0") || btn.getText().equals("2")) {
			return false;
		} else if (btn.getText().equals("1")) {
			if (bord.buttonHit(tempX, tempY)) {
				// Hit battleship message TODO
				// append text
			}
			return true;

		} else
			bord.buttonMiss(tempX, tempY);
		return true;
	}

	public void initiateGame() {
		placeEnemyShips();
		playerTurn = true;
	}

	public void placeEnemyShips() {
		int shipsCount = 0;
		int x = 0;
		int y = 0;
		Random rd = new Random();
		while (shipsCount < enemy.getShipSize()) {
			x = rd.nextInt(10);
			y = rd.nextInt(10);
			if (enemy.placeShip(enemy.getships().get(shipsCount), x, y, true, playerTurn)) {
				shipsCount++;
			}

		}

	}

	public void enemyTurn() {
		int y = 0;
		int x = 0;
		boolean temp = false;
		Random rd = new Random();
		x = rd.nextInt(10);
		y = rd.nextInt(10);

		if (hitAndMiss(player, (Button) player.getButtonLocation(x, y))) {
			playerTurn = true;
		} else {
			enemyTurn();
		}
		if (player.getShipSize() == 0) {
			System.out.println("You LOSE!");
			game = false;
		}
	}

	// SECOND STAGE

	public void secondStage(Stage s) {

		ObservableList<Integer> options = FXCollections.observableArrayList(0, 1, 2, 3, 4, 5);

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		Scene scene = new Scene(grid, 750, 750);
		s.setScene(scene);

		// Add inputs

		Text sceneTitle = new Text("Settings");
		sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(sceneTitle, 0, 0, 2, 1);

		Label gridSize = new Label("Grid Size: ");
		grid.add(gridSize, 0, 1);

		TextField gridSizeField = new TextField();
		grid.add(gridSizeField, 1, 1);

		Label numberOf = new Label("Number Of;");
		numberOf.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
		grid.add(numberOf, 0, 2);

		Label airCraft = new Label("aircraft Ships: ");
		grid.add(airCraft, 0, 3);

		ComboBox<Integer> airCraftField = new ComboBox<>(options);
		grid.add(airCraftField, 1, 3);

		Label battleship = new Label("battleShips: ");
		grid.add(battleship, 0, 4);

		ComboBox<Integer> battleShipsField = new ComboBox<>(options);
		grid.add(battleShipsField, 1, 4);

		Label destroyer = new Label("Destroyer Ships: ");
		grid.add(destroyer, 0, 5);

		ComboBox<Integer> destroyerField = new ComboBox<>(options);
		grid.add(destroyerField, 1, 5);

		Label patrol = new Label("Patrol Ships: ");
		grid.add(patrol, 0, 6);

		ComboBox<Integer> patrolField = new ComboBox<>(options);
		grid.add(patrolField, 1, 6);

		Button submit = new Button("Save Settings");
		grid.add(submit, 1, 7);

		submit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

			}
		});

	}

}
