package uk.ac.yorksj.sem2.assignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class initializeGame extends Application {
	private boolean playerTurn = false;
	private board player, enemy;
	private int shipCount = 0; // player ship counter
	private boolean game = false;
	private Rectangle nextShip = new Rectangle(); // Player next ship placement
	private static ArrayList<Integer> settings = new ArrayList<Integer>();
	private ArrayList<Integer> dirMemoryX = new ArrayList<Integer>(); // Computer AI moves X co-ordinates
	private ArrayList<Integer> dirMemoryY = new ArrayList<Integer>(); // COmputer AI moves Y co-ordinates
	private Random rd = new Random();
	private static TextArea chatBox;

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage s) throws FileNotFoundException {
		readFile();
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setVgap(10);
		pane.setHgap(10);
		pane.setPadding(new Insets(25, 25, 25, 25));
		Scene scene = new Scene(pane, 750, 700);
		s.setScene(scene);

		Label lblStart = new Label("Welcome To Battleships");
		pane.add(lblStart, 0, 0);
		Button startBtn = new Button("START");
		startBtn.setMaxHeight(Double.MAX_VALUE);
		startBtn.setMaxWidth(Double.MAX_VALUE);
		startBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				s.close();
				// System.out.println(settings.size());
				gameStage(s);

			}
		});
		pane.add(startBtn, 0, 1);
		Button settingsBtn = new Button("Settings");
		settingsBtn.setMaxHeight(Double.MAX_VALUE);
		settingsBtn.setMaxWidth(Double.MAX_VALUE);
		settingsBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				s.close();
				settingStage(s);

			}
		});

		pane.add(settingsBtn, 0, 2);

		Button quitBtn = new Button("Exit");
		quitBtn.setMaxHeight(Double.MAX_VALUE);
		quitBtn.setMaxWidth(Double.MAX_VALUE);
		quitBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to exit?", ButtonType.YES,
						ButtonType.NO);
				alert.showAndWait();

				if (alert.getResult() == ButtonType.YES) {
					System.exit(0);
				}
			}
		});
		pane.add(quitBtn, 0, 3);
		s.show();
		s.setMaximized(false);

	}

	// Updates current next ship display on user interface
	public void showCurrentShip(int length) {
		nextShip.setWidth(length * 30);
	}

	public boolean hitAndMiss(board bord, Button btn) {
		int tempX = GridPane.getColumnIndex(btn);
		int tempY = GridPane.getRowIndex(btn);
		if (btn.getText().equals("0") || btn.getText().equals("2")) {
			return false;
		} else if (btn.getText().equals("1")) {
			if (bord.buttonHit(tempX, tempY, bord.getPlayer() == false)) {
				// System.out.println("hit ");
				if (bord.getPlayer() == true) {
					// System.out.println("Target Mode Activated");
					targetMode(tempX, tempY);

				}

			}
			return true;

		} else
			bord.buttonMiss(tempX, tempY, bord.getPlayer() == false);
		return true;

	}

	public void placeEnemyShips() {

		int shipsCount = 0;
		int x = 0;
		int y = 0;
		int num = 0;
		Random rd = new Random();
		while (shipsCount < enemy.getShipSize()) {
			x = rd.nextInt(settings.get(0));
			y = rd.nextInt(settings.get(0));
			num = rd.nextInt(10);
			if (enemy.placeShip(enemy.getships().get(shipsCount), x, y, num % 2 == 0, settings.get(0))) {
				shipsCount++;
			}

		}
		chatBox.appendText("[ENEMY] I have placed all my ships \n");

	}

	// Computer AI
	public void targetMode(int centerX, int centerY) {
		int x = centerX;
		int y = centerY;
		// System.out.println("added");
		if ((x + 1 < settings.get(0))
				&& ((player.checkButton(x + 1, y).equals("") || (player.checkButton(x + 1, y).equals("1"))))) {
			dirMemoryX.add(x + 1);
			dirMemoryY.add(y);
		}
		if ((y + 1 < settings.get(0))
				&& ((player.checkButton(x, y + 1).equals("") || (player.checkButton(x, y + 1).equals("1"))))) {
			dirMemoryX.add(x);
			dirMemoryY.add(y + 1);
		}
		if ((x - 1 >= 0) && ((player.checkButton(x - 1, y).equals("") || (player.checkButton(x - 1, y).equals("1"))))) {
			dirMemoryX.add(x - 1);
			dirMemoryY.add(y);
		}
		if ((y - 1 >= 0) && ((player.checkButton(x, y - 1).equals("") || (player.checkButton(x, y - 1).equals("1"))))) {
			dirMemoryX.add(x);
			dirMemoryY.add(y - 1);
		}

	}

	public void AIShoot() {
		if (dirMemoryX.size() == 0) {
			randomMove();
		} else if (hitAndMiss(player, (Button) player.getButtonLocation(dirMemoryX.get(0), dirMemoryY.get(0)))) {
			deleteMove();
			playerTurn = true;
		} else
			AIMove();
		if (player.getShipSize() == 0) {
			appendChatBox("[GAME] Computer Wins!! \n" + "[GAME] Player Loses.");
			game = false;
		}
	}

	public void deleteMove() {
		dirMemoryX.remove(0);
		dirMemoryY.remove(0);
	}

	public boolean AIMove() {
		deleteMove();
		if (dirMemoryX.size() == 0) {
			randomMove();
		} else if (hitAndMiss(player, (Button) player.getButtonLocation(dirMemoryX.get(0), dirMemoryY.get(0)))) {
			deleteMove();
			playerTurn = true;
			return true;
		}
		return false;
	}

	public void randomMove() {
		int y = 0;
		int x = 0;

		x = rd.nextInt(settings.get(0));
		y = rd.nextInt(settings.get(0));

		if (hitAndMiss(player, (Button) player.getButtonLocation(x, y))) {
			playerTurn = true;
		} else {
			randomMove();
		}

	}

	// SETTINGS STAGE
	// Read and write file for settings
	public void readFile() throws FileNotFoundException {
		settings = new ArrayList<Integer>();

		String path = "settings.txt";
		try {
			File file = new File(path);
			Scanner fileSc = new Scanner(file);

			while (fileSc.hasNextInt()) {
				settings.add(fileSc.nextInt());
			}

			fileSc.close();
		} catch (FileNotFoundException e) {
			System.err.println("[ERROR] File does not exist");
		}

		if (settings.size() == 0) {
			initSettings();
		}

	}

	public void writeFile() throws FileNotFoundException {
		String path = "settings.txt";
		File file = new File(path);
		PrintWriter pw = new PrintWriter(file);
		for (int i = 0; i < settings.size(); i++) {
			pw.println(settings.get(i));
		}
		pw.close();
	}

	public void settingStage(Stage s) {

		ObservableList<Integer> options = FXCollections.observableArrayList(0, 1, 2, 3, 4, 5);

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		Scene scene = new Scene(grid, 1000, 700);
		s.setScene(scene);

		// Add inputs

		Text sceneTitle = new Text("Settings");
		sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(sceneTitle, 0, 0, 2, 1);

		Label gridSize = new Label("Grid Size: ");
		grid.add(gridSize, 0, 1);

		TextField gridSizeField = new TextField(settings.get(0).toString());
		grid.add(gridSizeField, 1, 1);

		Label numberOf = new Label("Number Of;");
		numberOf.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
		grid.add(numberOf, 0, 2);

		Label airCraft = new Label("aircraft Ships: ");
		grid.add(airCraft, 0, 3);

		ComboBox<Integer> airCraftField = new ComboBox<>(options);
		airCraftField.setValue(settings.get(1));
		grid.add(airCraftField, 1, 3);

		Label battleship = new Label("battleShips: ");
		grid.add(battleship, 0, 4);

		ComboBox<Integer> battleShipsField = new ComboBox<>(options);
		battleShipsField.setValue(settings.get(2));
		grid.add(battleShipsField, 1, 4);

		Label destroyer = new Label("Destroyer Ships: ");
		grid.add(destroyer, 0, 5);

		ComboBox<Integer> destroyerField = new ComboBox<>(options);
		destroyerField.setValue(settings.get(3));
		grid.add(destroyerField, 1, 5);

		Label patrol = new Label("Patrol Ships: ");
		grid.add(patrol, 0, 6);

		ComboBox<Integer> patrolField = new ComboBox<>(options);
		patrolField.setValue(settings.get(4));
		grid.add(patrolField, 1, 6);

		Text message = new Text();
		grid.add(message, 1, 7);

		Button submit = new Button("Save Settings");
		grid.add(submit, 1, 8);

		Text note = new Text("NOTE: Max gridsize is 5 to 15. This will be \n"
				+ "values out of this range, will be set to default value \n" + "or 10. Also numbers only.");
		note.setFill(Color.DARKGREEN);
		grid.add(note, 2, 5);

		submit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				int totalSquares = 0;
				// total boat squares taken up by boats, plus a error margin
				totalSquares = (airCraftField.getValue() * 5) + (battleShipsField.getValue() * 4)
						+ (destroyerField.getValue() * 3) + (patrolField.getValue() * 2) + 10;

				// gridSizeField validation numbers only
				if (!gridSizeField.getText().matches("[0-9]+")) {
					gridSizeField.setText("10");
				}
				if (totalSquares <= Math.pow(Integer.parseInt(gridSizeField.getText()), 2)) {
					settings = new ArrayList<Integer>();
					if ((gridSizeField.getText() == "") || (Integer.parseInt(gridSizeField.getText()) < 5)
							|| (Integer.parseInt(gridSizeField.getText()) > 15)) {
						gridSizeField.setText("10");
					}

					// load settings
					settings.add(Integer.parseInt(gridSizeField.getText()));
					settings.add(airCraftField.getValue());
					settings.add(battleShipsField.getValue());
					settings.add(destroyerField.getValue());
					settings.add(patrolField.getValue());
					try {
						writeFile();
					} catch (FileNotFoundException e1) {
						System.err.println("[ERROR] File Not found!");
					}
					s.hide();
					try {
						start(s);
					} catch (FileNotFoundException e) {
						System.err.println("[ERROR] File Not found!");
					}

				} else {
					message.setFill(Color.FIREBRICK);
					message.setText(
							"Invalid Set up, please either choose a \n  " + "higher board size " + "or less boats");
				}
			}

		});
		s.show();
		s.setMaximized(false);

	}

	// Default settings
	public void initSettings() {
		settings.add(10);
		settings.add(1);
		settings.add(2);
		settings.add(2);
		settings.add(3);
	}

	// GAME STAGE
	public GridPane getGripPane(Stage s) {
		// for new game reset counter
		shipCount = 0;
		GridPane pane = new GridPane();
		// Add Enemy board to grid pane
		enemy = new board(false, e -> {
			if ((game) && (playerTurn)) {
				Button btn = (Button) e.getSource();
				if (hitAndMiss(enemy, btn)) {
					if (enemy.getShipSize() == 0) {
						appendChatBox("[GAME] Player Wins!!! \n" + "[GAME] Computer Loses.");
						game = false;
					} else {
						playerTurn = false;

						AIShoot();
					}
				}

			}

		});
		pane.add(enemy.getBoard(), 2, 1);

		Label enemyTitle = new Label("ENEMY");
		enemyTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		enemyTitle.setAlignment(Pos.CENTER);
		pane.add(enemyTitle, 2, 0);
		// Add Player board to grid pane
		player = new board(true, e -> {

			Button btn = (Button) e.getSource();
			if (playerTurn) {

				if (shipCount < player.getShipSize()) {
					player.getships().get(shipCount).setNorth(e.getButton() == MouseButton.PRIMARY);
					if (player.placeShip(player.getships().get(shipCount), GridPane.getColumnIndex(btn),
							GridPane.getRowIndex(btn), player.getships().get(shipCount).getNorth(), settings.get(0))) {
						shipCount++;

						if (shipCount >= player.getShipSize()) {
							showCurrentShip(0);
							chatBox.appendText("[PLAYER] I've placed all my ships \n");
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
		Label playerTitle = new Label("PLAYER");
		playerTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		playerTitle.setAlignment(Pos.CENTER);
		pane.add(playerTitle, 0, 0);
		// Add next ship label to gridPane
		Label next = new Label("Next: ");
		next.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
		pane.add(next, 0, 2);
		// Add ChatBox to gridPane
		chatBox = new TextArea();
		chatBox.setMaxWidth(250); // default width
		pane.add(chatBox, 3, 1);

		// Works out where to place next ship, when changing the board size and chatbox
		// width
		if (settings.get(0) >= 10) {
			nextShip = new Rectangle(60, 390 + ((settings.get(0) - 10) * 30),
					(player.getships().get(0).getLength() * 30), 30);
			chatBox.setMaxWidth(settings.get(0) * 30);
		} else if (settings.get(0) < 10) {
			nextShip = new Rectangle(60, 390 - ((10 - settings.get(0)) * 30),
					(player.getships().get(0).getLength() * 30), 30);
		}

		nextShip.setFill(Color.BLUE);
		nextShip.setStroke(Color.BLACK);
		nextShip.setStrokeWidth(4);
		nextShip.setStrokeType(StrokeType.INSIDE);
		pane.add(nextShip, 0, 0);

		VBox buttonGrid = new VBox();
		Button newGameBtn = new Button("New Game");
		newGameBtn.setMaxWidth(Double.MAX_VALUE);

		newGameBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				s.close();
				gameStage(s);

			}
		});
		Button settingsBtn = new Button("Settings");
		settingsBtn.setMaxWidth(Double.MAX_VALUE);

		settingsBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				s.close();
				settingStage(s);

			}
		});
		Button exitBtn = new Button("Exit");
		exitBtn.setMaxWidth(Double.MAX_VALUE);

		exitBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to exit?", ButtonType.YES,
						ButtonType.NO);
				alert.showAndWait();

				if (alert.getResult() == ButtonType.YES) {
					System.exit(0);
				}

			}
		});
		buttonGrid.getChildren().add(newGameBtn);
		buttonGrid.getChildren().add(settingsBtn);
		buttonGrid.getChildren().add(exitBtn);
		pane.add(buttonGrid, 1, 1);

		pane.setVgap(35);
		pane.setHgap(35);

		return pane;
	}

	public void gameStage(Stage s) {
		// Check to see if settings have been changed.
		Group root = new Group();
		root.getChildren().add(getGripPane(s));
		root.getChildren().add(nextShip);
		Scene scene = new Scene(root, 1000, 700);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		s.setScene(scene);
		s.show();
		s.setMaximized(true);
		s.setTitle("BattleShip Game");
		placeEnemyShips();
		playerTurn = true;

	}

	// Used in board class
	public static int getSetting(int index) {
		return settings.get(index);
	}

	// Used in board class
	public static void appendChatBox(String s) {
		chatBox.appendText(s);
	}

}
