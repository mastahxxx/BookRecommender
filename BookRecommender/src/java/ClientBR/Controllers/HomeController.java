package ClientBR.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeController {
    @FXML private Label title;

    @FXML
    private void onClick(ActionEvent e) {
        title.setText("Ciao!");
    }
}
