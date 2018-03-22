package uk.ac.yorksj.sem2.assignment;

import java.util.ArrayList;
import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class initializeGame extends Application {
	private board player, enemy;
	private ArrayList<ships> playerShips = new ArrayList<ships>();
	private ArrayList<ships> enemyShips = new ArrayList<ships>();
	private int shipCount = 0;
	 private ships s1 = new ships(4);

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage s) throws Exception {
		s.setTitle("BattleShip Game");
		playerShips = initateShips();
		enemyShips = initateShips();
		initiateGame();
		Group root = new Group();
		root.getChildren().add(getGripPane());
		Scene scene = new Scene(root, 750, 700);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		s.setScene(scene);
		s.show();

	}

	public GridPane getGripPane() {
		GridPane pane = new GridPane();
		enemy = new board(false, e -> {

		});
		pane.add(enemy.getBoard(), 0, 0);

		player = new board(true, e -> {
			Button btn = (Button) e.getSource();

			if (shipCount < playerShips.size()) {
				playerShips.get(shipCount).setNorth(e.getButton() == MouseButton.PRIMARY);
				if (player.placeShip(playerShips.get(shipCount), GridPane.getColumnIndex(btn),
						GridPane.getRowIndex(btn), playerShips.get(shipCount).getNorth())) {
					shipCount++;
				}
				;
			} else
				System.out.println("You have placed all ships"); // test

		});
		pane.add(player.getBoard(), 0, 1);
		pane.setVgap(35);

		return pane;
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

	public void initiateGame() {
		placeEnemyShips();
	}

	public void placeEnemyShips() {
		int shipsCount = 0;
		int x = 0;
		int y = 0;
		Random rd = new Random();
		System.out.println(shipsCount);
		while (shipsCount < enemyShips.size()) {
			System.out.println("test: " + shipsCount);
			x = rd.nextInt(10);
			y = rd.nextInt(10);
			// btn = (Button) enemy.getButtonLocation(x, y);
			if (enemy.placeShip(s1, x, y, true)) {
				shipsCount++;
			}

		}

	}

}
