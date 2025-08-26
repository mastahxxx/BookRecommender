package ClientBR.Controllers;
import ClientBR.SceneNavigator;              
import ClassiCondivise.Libro;                
import javafx.collections.FXCollections;      
import javafx.collections.ObservableList;     
import javafx.fxml.FXML;                      
import javafx.scene.control.*;    



public class ValutaLibroController {
    
    @FXML private ComboBox<Libro> cbLibro;          
    @FXML private ComboBox<Integer> cbContenuto;      
    @FXML private ComboBox<Integer> cbStile;          
    @FXML private ComboBox<Integer> cbGradevolezza;   
    @FXML private ComboBox<Integer> cbOriginalita;    
    @FXML private ComboBox<Integer> cbEdizione;       
    @FXML private TextArea taContenuto;   
    @FXML private TextArea taStile;       
    @FXML private TextArea taGradevolezza;
    @FXML private TextArea taOriginalita; 
    @FXML private TextArea taEdizione;         
    @FXML private Button btnSalva;         
    @FXML private Label lblErr;
    @FXML private TextField tfVotoFinale;
               
    private final ObservableList<Libro> mieiLibri = FXCollections.observableArrayList();

    @FXML private void initialize() {
        Helpers.clearError(lblErr);

        caricaMieiLibri(SceneNavigator.getUserID()); // TODO: riempi mieiLibri dal DB

        cbLibro.setItems(mieiLibri); // collega i libri 
        cbLibro.valueProperty().addListener(this::onLibroChanged); // quando cambia libro, reset campi

        var voti = FXCollections.observableArrayList(1,2,3,4,5); // lista 1..5 per tutte le combobox voto
        cbContenuto.setItems(voti); cbContenuto.getSelectionModel().clearSelection(); 
        cbStile.setItems(voti); cbStile.getSelectionModel().clearSelection();     
        cbGradevolezza.setItems(voti); cbGradevolezza.getSelectionModel().clearSelection(); 
        cbOriginalita.setItems(voti); cbOriginalita.getSelectionModel().clearSelection();  
        cbEdizione.setItems(voti); cbEdizione.getSelectionModel().clearSelection();     

        // ogni volta che cambia un voto, aggiorniamo la media e il plusante salva
        cbContenuto.valueProperty().addListener(this::onVotoChanged);
        cbStile.valueProperty().addListener(this::onVotoChanged);
        cbGradevolezza.valueProperty().addListener(this::onVotoChanged);
        cbOriginalita.valueProperty().addListener(this::onVotoChanged);
        cbEdizione.valueProperty().addListener(this::onVotoChanged);

        // ogni nota ha un limite di 256 caratteri
        maxLen(taContenuto);
        maxLen(taStile);
        maxLen(taGradevolezza);
        maxLen(taOriginalita);
        maxLen(taEdizione);
        tfVotoFinale.setText("");                     
        btnSalva.setDisable(true);                    // disabilitato finche non si votano tutti e 5 i criteri
    }

    //bottoni
    @FXML private void onLogout() {
        SceneNavigator.logout();
    }
    @FXML private void onAnnulla()  {
         SceneNavigator.switchToUtenteRegistrato();
    }
    @FXML private void onAggiungiSuggerimenti(){
        SceneNavigator.switchToSuggerimenti();
    }

    private void onLibroChanged(javafx.beans.value.ObservableValue<? extends Libro> obs,
                                Libro oldV,
                                Libro newV) {
        onLibroChanged();
    }

    private void onLibroChanged() {
        Helpers.clearError(lblErr);                                                  
        refreshUI();                                
        aggiornaVotoFinale();                        // calcolo media (non uscirà nulla finche mancano voti)
        aggiornaBtnSalva();                          // aggiorna stato del bottone Salva
    }

    @FXML
    private void onSalva() {
        Helpers.clearError(lblErr);                
        Libro l = cbLibro.getValue();                

        if (l == null) {                             
            Helpers.showError("Seleziona un libro da valutare.", lblErr);
            return;
        }

        if (!tuttiVotiPresenti()) {                  // controlla che tutti i criteri abbiano i voti
            Helpers.showError("Completa tutti i voti (1–5) prima di salvare.", lblErr);
            return;
        }

        // salviamo i voti nel oggetto libro
        l.setContenuto(cbContenuto.getValue());
        l.setStile(cbStile.getValue());
        l.setGradevolezza(cbGradevolezza.getValue());
        l.setOriginalita(cbOriginalita.getValue());
        l.setEdizione(cbEdizione.getValue());

        // Aggiungi note solo se non vuote 
        String autore = SceneNavigator.getUserID();
        if (!isBlank(taContenuto.getText())) l.setNoteContenuto(taContenuto.getText().trim(), autore);
        if (!isBlank(taStile.getText())) l.setNoteStile(taStile.getText().trim(), autore);
        if (!isBlank(taGradevolezza.getText())) l.setNoteGradevolezza(taGradevolezza.getText().trim(), autore);
        if (!isBlank(taOriginalita.getText())) l.setNoteOriginalita(taOriginalita.getText().trim(), autore);
        if (!isBlank(taEdizione.getText())) l.setNoteEdizione(taEdizione.getText().trim(), autore);

        // TODO: mandare al  DB il libro l con voti e note salvate

        Helpers.showInfo("Valutazione salvata!", lblErr);              
    }

    private void onVotoChanged(javafx.beans.value.ObservableValue<? extends Integer> obs,
                               Integer oldV,
                               Integer newV) {
        aggiornaVotoFinale();
        aggiornaBtnSalva();
    }

    private void aggiornaVotoFinale() {
        if (!tuttiVotiPresenti()) {               
            tfVotoFinale.clear();                
            return;
        }
        Integer media = (cbContenuto.getValue() + cbStile.getValue()
                + cbGradevolezza.getValue() + cbOriginalita.getValue() + cbEdizione.getValue()) / 5;
        tfVotoFinale.setText(media.toString()); 
    }

    private void aggiornaBtnSalva() {
        boolean ok = cbLibro.getValue() != null && tuttiVotiPresenti(); // serve libro + 5 voti
        btnSalva.setDisable(!ok);                                       // abilita/disabilita
    }

    private boolean tuttiVotiPresenti() {
        return cbContenuto.getValue() != null &&
               cbStile.getValue() != null &&
               cbGradevolezza.getValue() != null &&
               cbOriginalita.getValue() != null &&
               cbEdizione.getValue() != null;
    }

    private void refreshUI() {
        taContenuto.clear();
        taStile.clear();
        taGradevolezza.clear();
        taOriginalita.clear();
        taEdizione.clear();
        cbContenuto.getSelectionModel().clearSelection();
        cbStile.getSelectionModel().clearSelection();
        cbGradevolezza.getSelectionModel().clearSelection();
        cbOriginalita.getSelectionModel().clearSelection();
        cbEdizione.getSelectionModel().clearSelection();
    }

    private void onTextChanged(javafx.beans.value.ObservableValue<? extends String> obs,
                               String oldV,
                               String newV) {
        if (newV != null && newV.length() > 256) {
            ((javafx.scene.control.TextArea)((javafx.beans.property.StringProperty)obs).getBean())
                .setText(newV.substring(0, 256));
        }
    }

    private void maxLen(TextArea ta) {
        ta.textProperty().addListener(this::onTextChanged);
}

    private boolean isBlank(String s) { return s == null || s.trim().isEmpty(); } // util

   // private void caricaMieiLibri(String userId) { //come in suggerimenticontroller
        // TODO
    //    mieiLibri.clear(); 
    //}


    //STUB TEST
    private void caricaMieiLibri(String userID) { // da eliminare quando il vero metodo verrà creato
    mieiLibri.clear();

    String suffisso = (userID == null || userID.isBlank()) ? "" : " — utente " + userID;

    Libro l1 = new Libro();
    l1.setTitolo("Il Gattopardo" + suffisso);
    l1.setAutore("Giuseppe Tomasi di Lampedusa");
    l1.setAnnoPubblicazione("1958");

    Libro l2 = new Libro();
    l2.setTitolo("Se questo è un uomo" + suffisso);
    l2.setAutore("Primo Levi");
    l2.setAnnoPubblicazione("1947");

    Libro l3 = new Libro();
    l3.setTitolo("Le città invisibili" + suffisso);
    l3.setAutore("Italo Calvino");
    l3.setAnnoPubblicazione("1972");

    Libro l4 = new Libro();
    l4.setTitolo("La coscienza di Zeno" + suffisso);
    l4.setAutore("Italo Svevo");
    l4.setAnnoPubblicazione("1923");

    Libro l5 = new Libro();
    l5.setTitolo("Il nome della rosa" + suffisso);
    l5.setAutore("Umberto Eco");
    l5.setAnnoPubblicazione("1980");

    mieiLibri.addAll(l1, l2, l3, l4, l5);
}

}
