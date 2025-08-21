package ClientBR.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import ClientBR.SceneNavigator;
import ClientBR.Controllers.Helpers;

public class RegisterController {

    @FXML private TextField fNome;
    @FXML private TextField fCognome;
    @FXML private TextField fCodiceFiscale;
    @FXML private TextField fEmail;
    @FXML private TextField fUserID;
    @FXML private PasswordField pfPassword;
    @FXML private PasswordField pfConfermaPassword;
    @FXML private Label lblError;

    @FXML private void initialize() {
        Helpers.clearError(lblError);
    }

    //Bottoni torna, accedi e conferma
    @FXML private void onTorna() {
        SceneNavigator.switchToHome();
        Helpers.clearError(lblError);
    }

    @FXML private void onAccedi(){
        SceneNavigator.switchToLogin();
        Helpers.clearError(lblError);
    }

    @FXML private void onConferma(){ //qui tutti i dati vengono salvati nelle proprie stringhe e fatto qualche controllo, manca usare i dati ricavati
        
        Helpers.clearError(lblError);

        String nome = fNome.getText().trim();
        String cognome = fCognome.getText().trim();
        String cf = fCodiceFiscale.getText().trim().toUpperCase();
        String email = fEmail.getText().trim();
        String userId = fUserID.getText().trim();
        String pswd = pfPassword.getText();
        String pswd2 = pfConfermaPassword.getText();

        //validazioni base, lato client prima di consultare il db
        if (nome.isEmpty() || cognome.isEmpty() || cf.isEmpty() || email.isEmpty() || userId.isEmpty() || pswd.isEmpty() || pswd2.isEmpty() ) {
            Helpers.showError("completa tutti i campi", lblError);
            return;
        }

        if(!Helpers.validCF(cf)) {
            Helpers.showError("cofice fiscale errato, inserisci 16 caratteri alfanumerici",lblError);
            return;
        }

        if(!Helpers.validEmail(email)) {
            Helpers.showError("email non valida",lblError);
            return;
        }

        if (!pswd.equals(pswd2)) {
            Helpers.showError("le password non coincidono",lblError);
            return;
        }

        if(!Helpers.validPswd(pswd)) {
            Helpers.showError("password non valida: la password deve avere almeno 8 caratteri",lblError);
            return;
        }

        //inserire metodi controllo mail e userid già registrati
        //TODO invio parametri al DB

        boolean ok = true; //STUB per test
        if (ok) {
            Helpers.clearFields(fNome,fCognome,fEmail,fCodiceFiscale,fUserID,pfPassword,pfConfermaPassword);
            Helpers.showInfo("Registrazione effettuata, ora puoi accedere",lblError);
        } else {
            Helpers.showError("Registrazione non riuscita, UserID o email giaà in uso",lblError); //dipende dalla logica del DB
        }
    }
  
}
