package ClientBR.Controllers;
import ClientBR.SceneNavigator;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class UtenteRegistratoController {

@FXML private Button btnLibrerie;
@FXML private Button btnValutaLibro;
@FXML private Button btnSuggerimenti;
@FXML private Button btnLogout;
@FXML private Label lblBenvenuto;
@FXML private Button btnCercaLibriUtenteRegistrato;




@FXML private void initialize(){
    String benvenuto = "Benvenuto   " + SceneNavigator.getUserID();
    lblBenvenuto.setText(benvenuto);
}

@FXML private void onLibrerie(){
    SceneNavigator.switchToLibrerie();
}
@FXML private void onValutaLibro(){
    SceneNavigator.switchToValutaLibro();
}
@FXML private void onSuggerimenti(){
    SceneNavigator.switchToSuggerimenti();
}
@FXML private void onLogout(){
    SceneNavigator.logout();
}
@FXML private void onCercaLibriRegistrato(){
    SceneNavigator.switchToCercaLibroRegistrato();
}


}
