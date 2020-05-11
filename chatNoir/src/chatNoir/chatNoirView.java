package chatNoir;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class chatNoirView extends Application implements PropertyChangeListener {
	private Model model;
	
	private GridPane gp;
	
	private Button[][] buttonArray = new Button[11][11];
	
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			model = new Model();
			model.addPropertyChangeListener(this);
			gp = new GridPane();
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, 500, 750, Color.BEIGE);
			primaryStage.setTitle(model.getTitle());
			for(int i = 0; i < buttonArray.length; i++) {
				for(int j = 0; j < buttonArray[i].length; j++) {
					buttonArray[i][j] = new Button();
					
					gp.add(buttonArray[i][j], 11 - i + j, j + i);
					buttonArray[i][j].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
				}
			}
			root.setCenter(gp);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		// TODO Auto-generated method stub

	}
}
