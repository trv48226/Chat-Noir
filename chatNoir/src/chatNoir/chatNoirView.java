package chatNoir;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * View: A GUI the displays the images for a game
 * 
 * @author Trevor and Lukas
 * @Version Spring2020
 */
public class chatNoirView extends Application implements PropertyChangeListener {
	/** Creates an instance of model to use in this class */
	private Model model;

	/** Gridpane to hold the game diamond */
	private GridPane gpGame;

	/** Double array to hold the buttons on the board */
	private Button[][] buttonArray = new Button[11][11];

	/** Button to reset the game */
	private Button Reset;

	/** Label to display the feedback on the screen (whose turn/who won) */
	private Label feedBack;

	/**
	 * Main method to launch the visual GUI
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Start method to display all the information of the game on the screen with a
	 * reset button, feedback, and a diamond playable board
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			model = new Model();
			model.addPropertyChangeListener(this);
			gpGame = new GridPane();
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, 475, 750);
			primaryStage.setTitle(model.getTitle());
			Reset = new Button("Reset");
			Reset.setFont(new Font("Times New Roman", 18));
			feedBack = new Label("");

			Reset.setOnAction(e -> model.startNewGame());

			model.startNewGame();
			root.setBottom(feedBack);
			root.setTop(Reset);
			root.setCenter(gpGame);
			root.setStyle("-fx-background-color: #F5F5DC");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * PropertyChange method that takes in propertyChanges and updates the visual
	 * board based on information the model sends in
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("New Game")) {
			Label blankLabel = new Label("");
			blankLabel.setPrefSize(20, 20);
			gpGame.add(blankLabel, 1, 9);
			for (int i = 0; i < buttonArray.length; i++) {
				for (int j = 0; j < buttonArray[i].length; j++) {
					buttonArray[i][j] = new Button();
					buttonArray[i][j].setPrefSize(40, 20);
					buttonArray[i][j].setStyle("-fx-border-color: Red;" + model.getBoardColor(i, j));
					gpGame.add(buttonArray[i][j], 11 - i + j, j + i, 2, 1);
					int row = i;
					int col = j;
					buttonArray[row][col].setOnAction(e -> model.move(row, col));
				}
			}
			feedBack.setText(model.getFeedback());
			feedBack.setFont(new Font("Times New Roman", 20));
		}
		if (evt.getPropertyName().equals("invalid cat move")) {
			Alert info = new Alert(Alert.AlertType.ERROR);
			info.setTitle("Invalid Cat Move");
			info.setContentText("The cat can only move to the surrounding unblocked squares");
			info.showAndWait();
			return;
		}
		if (evt.getPropertyName().equals("invalid owner move")) {
			Alert info = new Alert(Alert.AlertType.ERROR);
			info.setTitle("Invalid Owner Move");
			info.setContentText("The owner cannot block an already blocked square or the square containing the cat");
			info.showAndWait();
			return;
		}
		if (evt.getPropertyName().equals("valid")) {
			for (int i = 0; i < buttonArray.length; i++) {
				for (int j = 0; j < buttonArray[i].length; j++) {
					buttonArray[i][j].setStyle("-fx-border-color: Red;" + model.getBoardColor(i, j));
				}
			}
			feedBack.setText(model.getFeedback());
		}
	}
}
