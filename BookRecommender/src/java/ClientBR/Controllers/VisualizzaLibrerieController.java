package ClientBR.Controllers;

import ClientBR.SceneNavigator;
import ClassiCondivise.Libreria; 
import ClassiCondivise.Libro;   
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections; 
import javafx.collections.ObservableList; 
import javafx.fxml.FXML; 
import javafx.scene.control.*; 
import javafx.scene.control.cell.PropertyValueFactory; 

import java.util.Optional;

public class VisualizzaLibrerieController {

    @FXML private Label lblNomeLibreria;
    @FXML private Label lblTotale;
    @FXML private Label lblErr;
    @FXML private Button btnAggiungi;
    @FXML private Button btnRimuovi;              
    @FXML private Button btnIndietro;             
    @FXML private Button btnLogout;     

    @FXML private TableView<Libro> tblLibri;           
    @FXML private TableColumn<Libro, String> tTitolo;
    @FXML private TableColumn<Libro, String> tAutore;
    @FXML private TableColumn<Libro, String> tAnno;

    private final ObservableList<Libro> libri = FXCollections.observableArrayList();

    private Libreria libreriaCorrente;

    @FXML private void initialize() {

        libreriaCorrente = SceneNavigator.getLibreria();
        tTitolo.setCellValueFactory(new PropertyValueFactory<>("titolo"));
        tAutore.setCellValueFactory(new PropertyValueFactory<>("autore"));
        tAnno.setCellValueFactory(new PropertyValueFactory<>("annoPubblicazione"));
        tblLibri.setItems(libri);
        tblLibri.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblLibri.setPlaceholder(new Label("Nessun libro in questa libreria"));

        // listener sulla selezione per abilitare/disabilitare il bottone Rimuovi
        tblLibri.getSelectionModel().selectedItemProperty().addListener(this::onSelezioneLibroCambiata);

        // carica i dati dalla libreria corrente (stub: copia la lista esistente)
        caricaLibriDaLibreria();

        lblNomeLibreria.setText(libreriaCorrente.getNome() != null ? libreriaCorrente.getNome() : "(senza nome)");
        refreshTotale();
        refreshUI();
    }

    // listener della selezione per aggiornare UI abilita rimuovi
    private void onSelezioneLibroCambiata(ObservableValue<? extends Libro> obs, Libro oldV, Libro newV) {
        refreshUI();
    }

    private void caricaLibriDaLibreria() {
        libri.clear();
        if (libreriaCorrente.getContenuto() != null) { 
            libri.addAll(libreriaCorrente.getContenuto());
        }
    }

    // aggiorna il conteggio totale
    private void refreshTotale() {
        int tot = libri.size();
        lblTotale.setText(String.valueOf(tot)); 
    }

    // abilita/disabilita pulsanti in base alla selezione
    private void refreshUI() {
        boolean hasSel = tblLibri.getSelectionModel().getSelectedItem() != null;
        
        btnRimuovi.setDisable(!hasSel);
    }

    @FXML private void onAggiungi() { //si mette a mano titolo, autore e anno, controllare nel db e esiste
        Helpers.clearError(lblErr);
        //titolo
        TextInputDialog dTitolo = new TextInputDialog();
        dTitolo.setTitle("Aggiungi libro");
        dTitolo.setHeaderText(null);
        dTitolo.setContentText("Titolo:");

        Optional<String> r1 = dTitolo.showAndWait();
        if (!r1.isPresent()) return; 
        String titolo = r1.get().trim();
        if (titolo.isEmpty()) {
            Helpers.showError("Inserisci un titolo valido.", lblErr);
            return;
        }

        //autore
        TextInputDialog dAutore = new TextInputDialog();
        dAutore.setTitle("Aggiungi libro");
        dAutore.setHeaderText(null);
        dAutore.setContentText("Autore:");
        Optional<String> r2 = dAutore.showAndWait();
        if (!r2.isPresent()) return;
        String autore = r2.get().trim();
        if (autore.isEmpty()) {
            Helpers.showError("Inserisci un autore valido.", lblErr);
            return;
        }

        //anno (opzionale)
        TextInputDialog dAnno = new TextInputDialog();
        dAnno.setTitle("Aggiungi libro");
        dAnno.setHeaderText(null);
        dAnno.setContentText("Anno di pubblicazione:");
        Optional<String> r3 = dAnno.showAndWait();
        if (!r3.isPresent()) return;
        String anno = r3.get().trim();

        // crea l’oggetto Libro e valorizza i campi principali
        Libro nuovo = new Libro();
        nuovo.setTitolo(titolo);
        nuovo.setAutore(autore);
        nuovo.setAnnoPubblicazione(anno);
        //if(non esiste nel db) { //TODO
        //Helpers.showError("libro non eisstente")
        //return;}

        // aggiungi al modello e alla lista visibile
        libreriaCorrente.getContenuto().add(nuovo);
        libri.add(nuovo);
        Helpers.showInfo("Libro aggiunto.", lblErr);
        refreshTotale();
        refreshUI();                                            
        
    }

    // rimuove il libro selezionato
    @FXML private void onRimuovi() {
        Helpers.clearError(lblErr);

        Libro sel = tblLibri.getSelectionModel().getSelectedItem();
        if (sel == null) { 
            Helpers.showError("Seleziona un libro da rimuovere.", lblErr); 
            return;
        }

        // conferma rimozione
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); 
        alert.setTitle("Rimuovi libro");
        alert.setHeaderText(null);
        alert.setContentText("Vuoi rimuovere \"" + sel.getTitolo() + "\" dalla libreria?");
        Optional<ButtonType> conferma = alert.showAndWait(); 
        if (!conferma.isPresent() || conferma.get() != ButtonType.OK) { 
            return; 
        }

        libreriaCorrente.getContenuto().remove(sel);  
        libri.remove(sel); 

        Helpers.showInfo("Libro rimosso.", lblErr); 
        refreshTotale();
        refreshUI();
    }
    @FXML private void onIndietro() {
        SceneNavigator.switchToLibrerie(); 
    }
    @FXML private void onLogout() {
        SceneNavigator.logout();
    }
}