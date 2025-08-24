package ClientBR.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ClassiCondivise.Libro;
import ClientBR.SceneNavigator;
import ClientBR.Controllers.Helpers;

public class SuggerimentiController {   

    @FXML private Button btnAdd;
    @FXML private Button btnRemove;
    @FXML private Button btnAnulla;
    @FXML private Button btnSalva;
    @FXML private Button btnLogout;
    @FXML private ComboBox<Libro> cbLibro;
    @FXML private ListView<Libro> lvSelezionati;
    @FXML private ListView<Libro> lvDisponibili;

    private static final int LIMITE = 3;
    private final ObservableList<Libro> mieiLibri = FXCollections.observableArrayList(); //TODO popolare dal DB con tutti i libri disponibili in tutte le librerie del utente  
    private final ObservableList<Libro> disponibili = FXCollections.observableArrayList();
    private final ObservableList<Libro> selezionati = FXCollections.observableArrayList();

    @FXML private void initialize(){
        if(SceneNavigator.getUserID() == null) {
            SceneNavigator.logout();
            return;
        }

        lvDisponibili.






    }

    






    
}
