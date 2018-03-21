package uk.ac.yorksj.sem2.assignment;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class initializeGame extends Application {
	private board player;
	private ships s1 = new ships(4, false);

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage s) throws Exception {
		s.setTitle("BattleShip Game");

		Group root = new Group();
		root.getChildren().add(getGripPane());
		Scene scene = new Scene(root, 750, 700);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		s.setScene(scene);
		s.show();

	}

	public GridPane getGripPane() {
		GridPane pane = new GridPane();
		board enemy = new board(false, e -> {

		});
		pane.add(enemy.getBoard(), 0, 0);

		player = new board(true, e -> {

			player.placeShip(s1, e.getSource(), e.getButton() == MouseButton.PRIMARY);

		});
		pane.add(player.getBoard(), 0, 1);
		pane.setVgap(35);

		return pane;
	}

	public void initiateGame() {
		// place enemy ships
	}

}
