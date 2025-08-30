package ClientBR.Controllers;
import ClientBR.SceneNavigator;              
import ClassiCondivise.Libro;
import ClassiCondivise.UtenteRegistrato;
import javafx.collections.FXCollections;      
import javafx.collections.ObservableList;     
import javafx.fxml.FXML;                      
import javafx.scene.control.*;  
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;



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
               
    private ObservableList<Libro> mieiLibri = FXCollections.observableArrayList();

    @FXML private void initialize() {
        Helpers.clearError(lblErr);

        caricaMieiLibri(SceneNavigator.getUserID()); 

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
        boolean ok = false;
        try {
        	InetAddress addr = InetAddress.getByName(null);
        	Socket socket=new Socket(addr, 8999);
        	ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        	ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        	out.writeObject("INSETISCI VALUTAZIONE");
        	out.writeObject(l);
        	ok = (boolean) in.readObject(); 
            out.close();
    		in.close();
    		socket.close();
    	} catch (Exception e) {
             
        }
        if(ok) {
        	Helpers.showInfo("Valutazione salvata!", lblErr);
        }
                      
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

    private void caricaMieiLibri(String userId) { //come in suggerimenticontroller
        // TODO
    	boolean ok = false;
        try {
        	InetAddress addr = InetAddress.getByName(null);
        	Socket socket=new Socket(addr, 8999);
        	ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        	ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        	UtenteRegistrato ur = new UtenteRegistrato();
        	ur.setUserId(SceneNavigator.getUserID());
        	out.writeObject("CARICA LIBRI LIBRERIE CLIENT");
        	out.writeObject(ur);
        	mieiLibri = (ObservableList<Libro>) in.readObject();
        	ok = (boolean) in.readObject(); 
            out.close();
    		in.close();
    		socket.close();
    	} catch (Exception e) {
             
        }
    	mieiLibri.clear(); 
    }

}
