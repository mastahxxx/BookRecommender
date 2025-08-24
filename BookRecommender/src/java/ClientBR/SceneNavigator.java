package ClientBR;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import ClassiCondivise.Libro;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import ClientBR.Controllers.Helpers;


// classe adibita al cambio delle scene in un unico stage, gestisce le eccezioni così da non doverci pensare nei controller, mostra alert in caso di errore.
// si occupa anche di passare un oggetto di tibo libro fra le classi e di memorizzare l' userID del utente corrente
public class SceneNavigator { 

public static Libro libro; //da usare per passare un libro da un controller al altro
public static String userID; //da usare per impostare lo userID della sessione nel caso un utente dovesse accedere, null se l' utente non è loggato
private static Stage stage;
private static final String BASE_PATH = "/views/";


    public static void setStage(Stage s) {
        stage = s;
    }

    // permette di rimpiazzare la scena corrente con una nuova stabilita dal parametro, eventuali errori vengono mostrati con un Alert
    public static void switchTo(String fxml) { 
        if (stage == null) {
            Helpers.showError("Stage non iniziallizzato.");
            System.exit(1);
          }

          // risolviamo l' URL della risorsa e controlliao che esista
        try {
            URL url = SceneNavigator.class.getResource(BASE_PATH + fxml); 
            if (url == null) {
                Helpers.showError("View non trovata: \n" + BASE_PATH + fxml  );
                System.exit(1);
                }

            // carica l' FXML della nuova scena
            Parent root = FXMLLoader.load(url);
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) { // Qualsiasi altro errore non gestito precedentemente (errori nel fxml, errori nel controller ecc..)
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw) );
            Helpers.showError("Impossibile aprire la schermata");

            //System.exit(1);
        }
        
    }

    // scorciatoie per la navigazione dei controller
    public static void switchToHome() {switchTo("home.fxml");}
    public static void switchToLogin() {switchTo("login.fxml");}
    public static void switchToRegister() {switchTo("register.fxml");}
    public static void switchToCercaLibri() {switchTo("cercaLibro.fxml");}
    public static void switchToUtenteRegistrato() {switchTo("utenteRegistrato.fxml");}
    public static void switchToVisualizzaLibro() {switchTo("visualizzaLibro.fxml");}
    public static void switchToLibrerie() {switchTo("librerie.fxml");}
    public static void switchToValutaLibro() {switchTo("valutaLibro.fxml");}
    public static void switchToSuggerimenti() {switchTo("suggerimenti.fxml");}

    public static void logout(){ //setta userID null e ritorna alla home
        setUserIDNull();
        switchToHome();
    }

    //scorciatoria per chiudere il programma
    public static void Esci(){
        System.exit(0);
    }

    //getter e setter di un oggetto libro, cosi da venir passato fra i vari controller in caso di necessità (ad esmepio fra cercalibro e visualizzalibro)
    public static void setLibro(Libro lib){
        libro = lib;
    }

    public static Libro getLibro(){
        return libro;
    }

    //getter e setter del userID
    public static void setUserID(String uID){ //questo avviene solo dopo che il DB ha controllato la validita del login
        userID = uID;
    }  
    public static String getUserID(){
        return userID;
    }

    //setta userID come null nella classse SceneNavigator
    public static void setUserIDNull(){
        userID = null;
    }


    
    


}
