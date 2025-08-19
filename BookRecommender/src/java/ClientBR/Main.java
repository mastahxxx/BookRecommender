package ClientBR;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);  // Avvia JavaFX
    }

    @Override
    public void start(Stage primaryStage) {
        SceneNavigator.setStage(primaryStage);
        boolean ok = SceneNavigator.switchToHome();

    if (!ok) {
         System.exit(1);
     }

    primaryStage.setTitle("BookRecommender");
    primaryStage.show();
       
    }
}
