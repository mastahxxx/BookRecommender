package ClientBR;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class HomeController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private Button clickButton;

    @FXML
    private void handleButtonClick() {
        welcomeLabel.setText("Hai cliccato il bottone!");
    }
}
