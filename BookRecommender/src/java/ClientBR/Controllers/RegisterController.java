package ClientBR.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.function.ToDoubleBiFunction;

import ClientBR.SceneNavigator;



public class RegisterController {

    @FXML private TextField fNome;
    @FXML private TextField fCognome;
    @FXML private TextField fCodiceFiscale;
    @FXML private TextField fEmail;
    @FXML private TextField fUserId;
    @FXML private PasswordField pfPassword;
    @FXML private PasswordField pfConfermaPassword;
    @FXML private Label lblError;


    @FXML private void initialize() {
        clearError();
    }



    //Bottoni torna, accedi e conferma
    @FXML private void onTorna() {
        SceneNavigator.switchToHome();
        clearError();
    }

    @FXML private void onAccedi(){
        SceneNavigator.switchToLogin();
        clearError();
    }

    @FXML private void onConferma(){ //qui tutti i dati vengono salvati nelle proprie stringhe e fatto qualche controllo, manca usare i dati ricavati
        
        clearError();

        String nome = fNome.getText().trim();
        String cognome = fCognome.getText().trim();
        String cf = fCodiceFiscale.getText().trim().toUpperCase();
        String email = fEmail.getText().trim();
        String userId = fUserId.getText().trim();
        String pswd = pfPassword.getText();
        String pswd2 = pfConfermaPassword.getText();

        //validazioni base, lato client prima di consultare il db
        if (nome.isEmpty() || cognome.isEmpty() || cf.isEmpty() || email.isEmpty() || userId.isEmpty() || pswd.isEmpty() || pswd2.isEmpty() ) {
            showError("completa tutti i campi");
            return;
        }

        if(!validCF(cf)) {
            showError("cofice fiscale errato, inserisci 16 caratteri alfanumerici");
            return;
        }

        if (!pswd.equals(pswd2)) {
            showError("le password non coincidono");
            return;
        }


        //TODO invio parametri al DB
        boolean ok = true; //STUB per test
        if (ok) {
            clearFields();
            showInfo("Registrazione effettuata, ora puoi accedere");
        } else {
            showError("Registrazione non riuscita, UserID o email giaà in uso"); //dipende dalla logica del DB
        }

    }



                                                                 // Helpers, metodi di supporto

private void clearError() { //serve per resettare in automatico il lable di errore
        lblError.setText("");          
}

private void showError(String err) {
    lblError.setText(err);
    lblError.setStyle("-fx-text-fill: red;"); //gli errori verranno mostrati in rosso
}

private void showInfo(String info){
    lblError.setText(info);
    lblError.setStyle("-fx-text-fill: green;"); //le info verranno mostrati in verde
}

private void clearFields(){ //resettiamo tutti i fields
    fNome.clear();
    fCognome.clear();
    fCodiceFiscale.clear();
    fEmail.clear();
    fUserId.clear();
    pfPassword.clear();
    pfConfermaPassword.clear();
}


//controllo codice fiscale secondo le seguenti regole: caratteri validi da A a Z e cifre da 0 a 9, esattamente 16 caratteri.
private boolean validCF(String CF) { 
    return CF.matches("[A-Z0-9]{16}$"); 

}













    
}
