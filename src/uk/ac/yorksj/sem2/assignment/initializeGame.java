package uk.ac.yorksj.sem2.assignment;



import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class initializeGame extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage s) throws Exception {
		s.setTitle("BattleShip Game");

		Group root = new Group();
		root.getChildren().add(getGripPane());
		Scene scene = new Scene(root, 750, 700);
		s.setScene(scene);
		s.show();

	}

	public GridPane getGripPane() {
		GridPane pane = new GridPane();
		board enemy = new board(false);
		pane.add(enemy.getBoard(), 0, 0);
		
		board player = new board(true);
		pane.add(player.getBoard(), 0,1);
		pane.setVgap(35);
		
		return pane;
	}

	public void initiateGame() {
		//place enemy ships
	}

}
