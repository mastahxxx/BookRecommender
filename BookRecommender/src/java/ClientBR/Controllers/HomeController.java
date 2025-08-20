package ClientBR.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import ClientBR.SceneNavigator;

public class HomeController {

    // fxid dei bottoni di home, in questo caso non ci servono ma è utile averli per modifiche future
    @FXML private Button btnCercaLibri;
    @FXML private Button btnAccedi;
    @FXML private Button btnRegistrati;
    @FXML private Button btnEsci;


    //handler dei bottoni
    @FXML
    private void onCercaLibri(){
        SceneNavigator.switchToCercaLibri();
    }

    @FXML
    private void onAccedi(){
        SceneNavigator.switchToLogin();
    }

    @FXML
    private void onRegistrati() {
        SceneNavigator.switchToRegister();
    }

    @FXML
    private void onEsci(){
        SceneNavigator.Esci();
    }






















}
