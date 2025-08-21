package ClientBR.Controllers;

import ClientBR.SceneNavigator;
import javafx.fxml.FXML;
import javafx.scene.control.*;



public class LoginController {
    
    @FXML private TextField fUserID;
    @FXML private TextField pfPassword;
    @FXML private Button btnAccedi;
    @FXML private Button btnRegistrati;
    @FXML private Button btnHome;
    @FXML private Label lblError;
    
    @FXML private void initialize(){
        Helpers.clearError(lblError);
    }

    @FXML private void onRegistrati(){
        SceneNavigator.switchToRegister();
        Helpers.clearError(lblError);
    }  

    @FXML private void onHome() {
        SceneNavigator.switchToHome();
        Helpers.clearError(lblError);
    }
    
    @FXML private void onAccedi() {

        String userId = fUserID.getText().trim();
        String pswd = pfPassword.getText();

        if (!Helpers.validPswd(pswd)) {
            Helpers.showError("La password devere essere di almeno 8 caratteri", lblError);
            return;
        }

        //TODO: metodi per controllare password e userID

        //STUB
        boolean ok = true;
        if (ok) {
            SceneNavigator.switchToUtenteRegistrato();
        }




       
    }























}
