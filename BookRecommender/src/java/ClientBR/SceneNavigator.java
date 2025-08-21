package ClientBR;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.net.URL;


// classe adibita al cambio delle scene in un unico stage, gestisce le eccezioni così da non doverci pensare nei controller, mostra alert in caso di errore.

public class SceneNavigator { 

private static Stage stage;
private static final String BASE_PATH = "/views/";


    public static void setStage(Stage s) {
        stage = s;
    }

    // permette di rimpiazzare la scena corrente con una nuova stabilita dal parametro, eventuali errori vengono mostrati con un Alert
    public static void switchTo(String fxml) { 
        if (stage == null) {
            showError("Stage non iniziallizzato.");
            System.exit(1);
          }

          // risolviamo l' URL della risorsa e controlliao che esista
        try {
            URL url = SceneNavigator.class.getResource(BASE_PATH + fxml); 
            if (url == null) {
                showError("View non trovata: \n" + BASE_PATH + fxml  );
                System.exit(1);
                }

            // carica l' FXML della nuova scena
            Parent root = FXMLLoader.load(url);
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) { // Qualsiasi altro errore non gestito precedentemente (errori nel fxml, errori nel controller ecc..)
            showError("Impossibile aprire la schermata");
            System.exit(1);
        }
        
    }

    // scorciatoie per la navigazione dei controller
    public static void switchToHome() {switchTo("home.fxml");}
    public static void switchToLogin() {switchTo("login.fxml");}
    public static void switchToRegister() {switchTo("register.fxml");}
    public static void switchToCercaLibri() {switchTo("cercaLibri.fxml");}
    public static void switchToUtenteRegistrato() {switchTo("utenteRegistrato.fxml");}



    //scorciatoria per chiudere il programma
    public static void Esci(){
        System.exit(0);
    }




    //mostra alert (pupop) d' errore all'utente
    public static void showError(String messaggio){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERRORE");
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();

    }
    


}
