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
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/home.fxml")); // FXML nella cartella resources
        Scene scene = new Scene(root);
        primaryStage.setTitle("Book Recommender");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
