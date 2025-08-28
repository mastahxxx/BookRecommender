package ClientBR.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import ClientBR.SceneNavigator;
import ClassiCondivise.Libro;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class CercaLibroRegistratoController { //classe quasi indentica della cercaLibro, ma con bottoni di logOut e indietro 

    @FXML private TextField fTitolo;
    @FXML private TextField fAutore;
    @FXML private TextField fAnno;
    @FXML private Button btnCerca;
    @FXML private Button btnPulisci;
    @FXML private TableView<Libro> tblView;
    @FXML private TableColumn<Libro, String> tTitolo;
    @FXML private TableColumn<Libro, String> tAutore;
    @FXML private TableColumn<Libro, String> tAnno;
    @FXML private Label lblErr;

    private final ObservableList<Libro> risultati = FXCollections.observableArrayList();

    @FXML
    private void initialize() {

        //le colonne si aggiornano in automatico attraverso i getter corrispondenti
        tTitolo.setCellValueFactory(new PropertyValueFactory<>("titolo"));
        tAutore.setCellValueFactory(new PropertyValueFactory<>("autore"));
        tAnno.setCellValueFactory(new PropertyValueFactory<>("annoPubblicazione")); 
        tblView.setItems(risultati);
        tblView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE); //fa in modo che si possa apire un solo libro alla volta
        tblView.setPlaceholder(new Label("Nessun risultato"));

        // doppio click per aprire il libro
        tblView.setOnMouseClicked(this::clickTabella);
        

        //TODO: LIBRI PER TEST, SCAMBIARE CON LIBRI PRESENTI NEL DB, UTILIZZARE LE STRINGHE CHE VENGONO DATE NEL METODO onCerca(); (riga100-102). assicurarsi di cancellare tutto il resto di questo metodo.
        Libro demo = new Libro();
        demo.setTitolo("Il nome della rosa");
        demo.setAutore("Umberto Eco");
        demo.setAnnoPubblicazione("1980");
        demo.setStile(8);
        demo.setContenuto(9);
        demo.setGradevolezza(8);
        demo.setOriginalita(9);
        demo.setEdizione(7);

        

        //SECONDO LIBRO DI TEST
        Libro consigliato = new Libro();
        consigliato.setTitolo("Il pendolo di Foucault");
        consigliato.setAutore("Umberto Eco");
        consigliato.setAnnoPubblicazione("1988");
        consigliato.setStile(8);
        consigliato.setContenuto(8);
        consigliato.setGradevolezza(7);
        consigliato.setOriginalita(9);
        consigliato.setEdizione(7);
//note per demo
demo.setNoteContenuto("Trama avvincente e ben strutturata", "Admin");
demo.setNoteStile("Prosa ricca ma scorrevole", "Admin");
demo.setNoteOriginalita("Intreccio investigativo originale", "Admin");
demo.setNoteGradevolezza("Ritmo costante, mai noioso", "Admin");
demo.setNoteEdizione("Buona qualità di stampa", "Admin");

// note per il consigliato
consigliato.setNoteContenuto("Più criptico ma affascinante", "Admin");
consigliato.setNoteStile("Linguaggio denso, tante citazioni", "Admin");


        demo.setLibriConsigliati(consigliato);

        risultati.add(demo);
    }
        private void clickTabella(MouseEvent e) {
    if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
        apriInfoLibro();
    }
}
    // cerca i libri utilizzando titolo, autore, anno e mostra nella tabella i libri trovati, quali libri vengono trovati con un determinato inpu va stabilito separatamente
    @FXML private void onCerca() {
        String titolo = fTitolo.getText().trim(); //da usare per cercare libri nel DB
        String autore = fAutore.getText().trim();
        String anno   = fAnno.getText().trim();

                //si può cercare per autore, titolo oppure autore ed anno
        Helpers.showInfo("inserisci titolo, autore oppure autore e anno", lblErr);
        if(titolo.equals("") || autore.equals("")) {
            Helpers.showError("Inserisci titolo o autore", lblErr);
        }

        btnCerca.setDisable(true); //blocchiamo il pulsante finchè non finisce la ricerca
        tblView.setPlaceholder(new Label("Ricerca in corso..."));

        try {
            // TODO: integra la ricerca reale DB
        	InetAddress addr = InetAddress.getByName(null);
    		Socket socket=new Socket(addr, 8999);
    		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
    		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        	Libro l = new Libro();
            l.setTitolo(titolo);
            l.setAutore(autore);
            l.setAnnoPubblicazione(anno);
            out.writeObject("CERCA LIBRO"); //invio libro da cercare al db
            out.writeObject(l);
            List<Libro> res = (List <Libro>) in.readObject(); //ricevo un libro
            //List<Libro> res = List.of(); 
            risultati.setAll(res); //mettere al posto del res i libri trovati dal DB
            if (res.isEmpty()) { //res può essere vuoto senza causare errori
                tblView.setPlaceholder(new Label("Nessun risultato..."));
            }
            out.close();
			in.close();
			socket.close();
        } catch (Exception e) {
            tblView.setPlaceholder(new Label("Errore durante la ricerca"));
            Helpers.showError("Errore durante la ricerca");
            System.exit(1);
        } finally {
        	
            btnCerca.setDisable(false); //riattiviamo pulsante
            
        }
    }

    @FXML private void onPulisci() {
        Helpers.clearFields(fTitolo,fAutore,fAnno);     
        risultati.clear();
        tblView.setPlaceholder(new Label(""));
    }

    @FXML private void onLogout() {
        SceneNavigator.logout();
    }
    @FXML private void onIndietro(){
        SceneNavigator.switchToUtenteRegistrato();
    }
    //quando l' utente fa doppio click sul libro dobbiamo passare a una nuova scena ma ricordandoci che libro aveva scelto l' utente
    private void apriInfoLibro() {
        Libro sel = tblView.getSelectionModel().getSelectedItem(); //salviamo il libro selezionato
        SceneNavigator.setLibro(sel);
        if (sel == null) { return; }

        SceneNavigator.setLibro(sel); //salviamo il libro selezionato in VisualizzaLibroController
        SceneNavigator.switchToVisualizzaLibroRegistrato();      
    }


}
