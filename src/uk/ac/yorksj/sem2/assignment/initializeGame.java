package uk.ac.yorksj.sem2.assignment;

import java.util.ArrayList;
import java.util.Random;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class initializeGame extends Application {
	private boolean playerTurn = false;
	private board player;
	private board enemy;
	private ArrayList<ships> playerShips = new ArrayList<ships>();
	private ArrayList<ships> enemyShips = new ArrayList<ships>();
	private int shipCount = 0;
	private ships s1 = new ships(4);
	private boolean game = true;

	public static void main(String[] args) {
		launch(args);
	}
	
	//TODO  re-write board to accept ships list

	public void start(Stage s) throws Exception {
		Group root = new Group();
		root.getChildren().add(getGripPane());
		Scene scene = new Scene(root, 750, 700);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		s.setScene(scene);
		s.show();
		s.setTitle("BattleShip Game");
		playerShips = initateShips();
		enemyShips = initateShips();
		initiateGame();

	}
	

	public GridPane getGripPane() {
		GridPane pane = new GridPane();
		enemy = new board(false, e -> {
			if (playerTurn) {
				Button btn = (Button) e.getSource();
				 if (hitAndMiss(enemy, btn)) {
					 playerTurn = false;
					 enemyTurn();
				 }
				 
				 
			}
			/*
			 * if ((btn.getText().equals("0")) || (btn.getText().equals("2"))) {
			 * System.out.println("REUTRN"); return; } if (btn.getText().equals("1")) {
			 * System.out.println("2"); btn.setText("2"); } else { System.out.println("0");
			 * btn.setText("0"); } playerTurn = false; }
			 */

		});
		pane.add(enemy.getBoard(), 0, 0);

		player = new board(true, e -> {

			Button btn = (Button) e.getSource();
			if (playerTurn) {
				if (shipCount < playerShips.size()) {
					playerShips.get(shipCount).setNorth(e.getButton() == MouseButton.PRIMARY);
					if (player.placeShip(playerShips.get(shipCount), GridPane.getColumnIndex(btn),
							GridPane.getRowIndex(btn), playerShips.get(shipCount).getNorth(), playerTurn)) {
						shipCount++;
					}
					;
				} else
				{}
			}
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

	public boolean hitAndMiss(board bord, Button btn) {
		int tempX = GridPane.getColumnIndex(btn);
		int tempY = GridPane.getRowIndex(btn);
		if (btn.getText().equals("0") || btn.getText().equals("2")) {
			return false;
		} else if (btn.getText().equals("1")) {
			 if (bord.buttonHit(tempX, tempY)) {
				 
				 
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
		System.out.println(shipsCount);
		while (shipsCount < enemyShips.size()) {
			// System.out.println("test: " + shipsCount); // test purpose
			x = rd.nextInt(10);
			y = rd.nextInt(10);
			// System.out.println("X: " + x + ", Y: " + y);
			// btn = (Button) enemy.getButtonLocation(x, y);
			if (enemy.placeShip(enemyShips.get(shipsCount), x, y, true, playerTurn)) {
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
		
		if (hitAndMiss(player,  (Button) player.getButtonLocation(x, y))){
			playerTurn = true;
		}
		else 
			enemyTurn();
	}

}
