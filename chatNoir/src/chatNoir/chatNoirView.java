package chatNoir;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

// Red = #FF0000
// White = #FFFFFF
//Grey = #DCDCDC
public class chatNoirView extends Application implements PropertyChangeListener {
	private Model model;

	private GridPane gpGame;

	private Button[][] buttonArray = new Button[11][11];

	private Button Reset;

	private Label feedBack;

	public static void main(String[] args) {
		launch(args);
	}

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
			feedBack = new Label("Test");
			feedBack.setText(model.getFeedback());

			Reset.setOnAction(e -> model.startNewGame());
			
			//Don't want
			for (int i = 0; i < buttonArray.length; i++) {
				for (int j = 0; j < buttonArray[i].length; j++) {
					buttonArray[i][j] = new Button();
					buttonArray[i][j].setStyle("-fx-border-color: Red;" + model.getBoardColor(i, j));
					gpGame.add(buttonArray[i][j], 11 - i + j, j + i);
				}
			}
			
			//Don't want 
			for (int i = 0; i < buttonArray.length; i++) {
				for (int j = 0; j < buttonArray[i].length; j++) {
					int row = i;
					int col = j;
					buttonArray[row][col].setOnAction(e -> model.move(row,col));
				}
			}

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

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("New Game")) {
			for (int i = 0; i < buttonArray.length; i++) {
				for (int j = 0; j < buttonArray[i].length; j++) {
					buttonArray[i][j] = new Button();
					buttonArray[i][j].setStyle("-fx-border-color: Red;" + model.getBoardColor(i, j));
					gpGame.add(buttonArray[i][j], 11 - i + j, j + i);
					
					//Don't want
					int row = i;
					int col = j;
					buttonArray[row][col].setOnAction(e -> model.move(row,col));
				}
			}
		}
		if(evt.getPropertyName().equals("invalid")) {
			feedBack.setText(model.getFeedback());
		}
		if(evt.getPropertyName().equals("valid")) {
			for (int i = 0; i < buttonArray.length; i++) {
				for (int j = 0; j < buttonArray[i].length; j++) {
					buttonArray[i][j].setStyle("-fx-border-color: Red;" + model.getBoardColor(i, j));
				}
			}
			feedBack.setText(model.getFeedback());
		}
	}
}

//model.getBoardColor(model.getLastCatMove().getxValue()