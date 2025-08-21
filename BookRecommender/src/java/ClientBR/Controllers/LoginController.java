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























}
