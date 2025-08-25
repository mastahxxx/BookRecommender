package ClientBR.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;

import ClassiCondivise.Libro;
import ClientBR.SceneNavigator;
import ClientBR.Controllers.Helpers;

public class SuggerimentiController {   

    @FXML private Button btnAdd;
    @FXML private Button btnRemove;
    @FXML private Button btnAnnulla;
    @FXML private Button btnSalva;
    @FXML private Button btnLogout;
    @FXML private ComboBox<Libro> cbLibro;
    @FXML private ListView<Libro> lvSelezionati;
    @FXML private ListView<Libro> lvDisponibili;
    @FXML private Label lblErr;

    private static final int LIMITE = 3;
    private final ObservableList<Libro> mieiLibri = FXCollections.observableArrayList(); //TODO popolare dal DB con tutti i libri disponibili in tutte le librerie del utente  
    private final ObservableList<Libro> disponibili = FXCollections.observableArrayList();
    private final ObservableList<Libro> selezionati = FXCollections.observableArrayList();

    private Libro ultimoLibro;

    @FXML private void initialize(){
        if(SceneNavigator.getUserID() == null) {
            SceneNavigator.logout();
            return;
        }
        //abilito selezione multipla
        lvDisponibili.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lvSelezionati.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //TODO: metodo che carica tutti i libri disponibli da tutte le librerie del utente in
        caricaLibri(SceneNavigator.getUserID());

        //mettiamo i libri nella combobox
        cbLibro.setItems(mieiLibri);
        //cambia in automatico la lista dei libri nella combo
        cbLibro.valueProperty().addListener((obs, oldV, newV) -> ricalcolaDisponibli()); 

        lvDisponibili.setItems(disponibili);
        lvSelezionati.setItems(selezionati);
        ricalcolaDisponibli();
        refreshUI();
    }

    @FXML private void onLogout(){
        SceneNavigator.logout();
    }
    @FXML private void onAnnulla() {
        SceneNavigator.switchToUtenteRegistrato();
    }

    @FXML private void onAdd() {
        if (cbLibro.getValue() == null) {   
            Helpers.showError("Scegli prima il libro a cui vuoi aggiungere suggerimenti", lblErr);
            return;
        }
        //selezione corrente dei libri dalla lista disponibili
        List<Libro> scelti = List.copyOf(lvDisponibili.getSelectionModel().getSelectedItems());
        if (scelti.isEmpty()) { return; }

        for(Libro l : scelti) {
            if(selezionati.size() >= LIMITE) break; //non più di 3 suggerimenti
            if(!selezionati.contains(l)) selezionati.add(l); //no si possono aggiungere duplicati
        }

        ricalcolaDisponibli(); //aggiorniamo la lista disponibili per rimuovere i libri gia selezionati.
        refreshUI();
        lvDisponibili.getSelectionModel().clearSelection(); //deseleziono tutto
    }

    @FXML private void onRemove(){

        //seleziono libri da rimuovere dai selezionati e li rimuovo
        List<Libro> scelti = List.copyOf(lvSelezionati.getSelectionModel().getSelectedItems());
        selezionati.removeAll(scelti);
        ricalcolaDisponibli();
        refreshUI();
        lvSelezionati.getSelectionModel().clearSelection();
    }

    @FXML private void onSalva(){

        var lib = cbLibro.getValue();

        if(lib == null) {
            Helpers.showError("seleziona un libro dalla libreria", lblErr);
            return;
        } 
        if (selezionati.isEmpty()) {
            Helpers.showError("seleziona almeno un suggerimento",lblErr);
            return;
        }

        lib.getLibriConsigliati().clear();
        lib.getLibriConsigliati().addAll(selezionati);
        //TODO: salvare sul DB il libro lib, che ora contiene i suggerimenti.
        Boolean ok = true; //STUB da rimuovere con un boolean di conferma da parte del DB

        if (ok) {Helpers.showInfo("suggerimenti salvati con successo", lblErr);}
        }
    
    //    private void caricaMieiLibri(String userId){
    //        //TODO: unire al db e fornire tutti i libri presenti in tutte le librerie del utente
    //        mieiLibri.clear();
    //        disponibili.clear();
    //        selezionati.clear();
    //        ultimoLibro = null;
     //   }

     private void ricalcolaDisponibli() {
    Libro lib = cbLibro.getValue();
    boolean cambiato = (lib != null && lib != ultimoLibro) || (lib == null && ultimoLibro != null);

    if (cambiato) {
        // resetta messaggio quando cambio libro
        lblErr.setText("");
        lblErr.setStyle(""); // opzionale, così togli anche il colore
        
        
        
        // Ricarica i selezionati dal libro scelto
        selezionati.clear();
        if (lib != null && lib.getLibriConsigliati() != null) {
            for (Libro l : lib.getLibriConsigliati()) {
                if (selezionati.size() >= LIMITE) break;
                if (!selezionati.contains(l)) selezionati.add(l);
            }
        }
        ultimoLibro = lib;
    }

    // Ricostruisci i disponibili
    disponibili.clear();
    for (Libro l : mieiLibri) {
        if (lib != null && l == lib) continue;   // escludi il libro base
        if (selezionati.contains(l)) continue;   // escludi già selezionati
        disponibili.add(l);
    }

    refreshUI();
}


    private void refreshUI(){
    btnSalva.setText("Salva (" + selezionati.size() + "/" + LIMITE + ")");
    btnSalva.setDisable(cbLibro.getValue() == null || selezionati.isEmpty());
    }


             private void caricaLibri(String userId) { //METODO PER TEST; CANCELLARE E IMPLEMENTARE IL METODO A RIGA 114
    // Reset liste
    mieiLibri.clear();
    disponibili.clear();
    selezionati.clear();
    ultimoLibro = null;

    // --- DATI FINTI PER TEST ---
    
    Libro l1 = new Libro();
    l1.setTitolo("Il nome della rosa");
    l1.setAutore("Umberto Eco");
    l1.setAnnoPubblicazione("1980");
    Libro l2 = new Libro();
    l2.setTitolo("1984");
    l2.setAutore("George Orwell");
    l2.setAnnoPubblicazione("1949");
    Libro l3 = new Libro();
    l3.setTitolo("Il Signore degli Anelli");
    l3.setAutore("J.R.R. Tolkien");
    l3.setAnnoPubblicazione("1954");
    Libro l4 = new Libro();
    l4.setTitolo("Cecità");
    l4.setAutore("José Saramago");
    l4.setAnnoPubblicazione("1995");
    Libro l5 = new Libro();
    l5.setTitolo("La coscienza di Zeno");
    l5.setAutore("Italo Svevo");
    l5.setAnnoPubblicazione("1923");
    mieiLibri.addAll(l1, l2, l3, l4, l5);
    l1.getLibriConsigliati().add(l2);
    l1.getLibriConsigliati().add(l4);
    cbLibro.getSelectionModel().select(l1);
}


    
}
