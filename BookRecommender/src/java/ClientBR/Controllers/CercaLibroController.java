package ClientBR.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import ClientBR.SceneNavigator;
import ClientBR.Controllers.Helpers;

import java.util.List;

import ClassiCondivise.Libro;

public class CercaLibroController {

@FXML private TextField fTitolo; 
@FXML private TextField fAutore;
@FXML private TextField fAnno;
@FXML private Button btnCerca;
@FXML private Button btnPulisci;
@FXML private Button btnHome;
@FXML private TableView tblView;
@FXML private TableColumn tTitolo;
@FXML private TableColumn tAutore;
@FXML private TableColumn tAnno;


//lista che contiene i risultati della ricerca.
private final ObservableList<Libro> risultati = FXCollections.observableArrayList(); 
@FXML private void initialize(){

    tblView.setOnMouseClicked(e -> {
        if (e.getClickCount()==2) {
            apriInfoLibro();
         }
    });

    //collegamento delle colonne ai getter della classe Libro, ogni volta che si aggiunge un oggetto di tipo Libro alla lista le colonne prenderanno in automatio il getter del libro
    tTitolo.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("titolo"));
    tAutore.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("autore"));
    tAnno.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("annoPubblicazione"));

    //collegamento lista alla tabella
    tblView.setItems(risultati);

    //faccio in modo che si possa selezionare un solo libro per volta
    tblView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    //placeholder per non lasciare la tabella vuota quando non ci sono dati
    tblView.setPlaceholder(new Label("Nessun risultato"));

    //apriamo informazioni Libro con doppio click del mouse
    tblView.addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> {
        if (evt.getButton() == MouseButton.PRIMARY && evt.getClickCount() == 2) {
            //TODO apriInfoLibro();
          }
    }  );
}


@FXML private void onCerca(){

    String titolo = fTitolo.getText().trim();
    String autore =fAutore.getText().trim();
    String anno = fAnno.getText().trim();

    //INSERIRE RICERCA SUL DB DEI LIBRI E INSERIMENTO LIBRI,  PER ORA C è UN PLACEHOLDER

    btnCerca.setDisable(true);
    tblView.setPlaceholder(new Label("Ricerca in corso..."));

    try {
        List<Libro> res;
        //List<Libro> res = socketClient.searchLibri(titolo,autore,anno); QUI METTERE IL COLLEGAMENTO AL SOCKET

        //risultati.setAll(res);  aggiungere i libri che arrivano tramite socket in res che poi verranno aggiunti alla lista risultati
        
        //if(res.isEmpty()) {
        //    tblView.setPlaceholder(new Label("Nessun risultato..."));
        //}

    } catch (Exception e) {
        System.exit(1);
    }

    finally {
        btnCerca.setDisable(false); //riabilito il pulsante cerca
    }
}


@FXML private void onPulisci(){
    Helpers.clearFields(fTitolo,fAutore,fAnno);
}

@FXML private void onHome(){
    SceneNavigator.switchToHome();
}

private void apriInfoLibro(){
    Libro sel = (Libro) tblView.getSelectionModel().getSelectedItem();//verificare che non ci siano eventuali errori per colpa del cast
    if (sel==null) { return;}
    try { //non usiamo scenenavigator per questa volta
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/visualizzaLibro.fxml"));
        Parent root = loader.load();

        //passo il libro selezionato 
        VisualizzaLibroController controller = loader.getController();
        controller.setLibro(sel);

        Stage stage = (Stage) tblView.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();



    } catch (Exception e) {
        System.exit(1);
        SceneNavigator.showError("imp aprire libro");
    }    

}









    
}       
