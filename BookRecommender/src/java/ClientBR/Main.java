package ClientBR;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.event.EventHandler;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // 1) passa lo Stage al navigator (fondamentale per evitare "stage non inizializzato")
            SceneNavigator.setStage(primaryStage);
            primaryStage.setTitle("Book Recommender");
            primaryStage.setMinWidth(600);
            primaryStage.setMinHeight(400);

            //mostra la scena iniziale tramite il navigator
            SceneNavigator.switchToHome();

            //chiusura
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent e) {
                    Platform.exit();
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
            Platform.exit();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
