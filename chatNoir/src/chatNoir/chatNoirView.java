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
// Red = #FF0000
// White = #FFFFFF
//Grey = #DCDCDC
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
			Scene scene = new Scene(root, 450, 700);
			primaryStage.setTitle(model.getTitle());
			for(int i = 0; i < buttonArray.length; i++) {
				for(int j = 0; j < buttonArray[i].length; j++) {
					buttonArray[i][j] = new Button();
					buttonArray[i][j].setStyle(model.getBoardColor(i,j));
					//buttonArray[i][j].setStyle("-fx-border-color: Red");
					gp.add(buttonArray[i][j], 11 - i + j, j + i);
				}
			}
			
			root.setCenter(gp);
			root.setStyle("-fx-background-color: #F5F5DC");
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
