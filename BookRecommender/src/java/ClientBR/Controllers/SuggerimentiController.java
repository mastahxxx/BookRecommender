//da visitare
package ClientBR.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;

import ClassiCondivise.Libro;
import ClassiCondivise.UtenteRegistrato;
import ClientBR.SceneNavigator;
import ClientBR.Controllers.Helpers;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

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
    private ObservableList<Libro> mieiLibri = FXCollections.observableArrayList(); //TODO popolare dal DB con tutti i libri disponibili in tutte le librerie del utente  
    private ObservableList<Libro> disponibili = FXCollections.observableArrayList();
    private ObservableList<Libro> selezionati = FXCollections.observableArrayList();

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
        cbLibro.valueProperty().addListener(this::onLibroChanged);

        lvDisponibili.setItems(disponibili);
        lvSelezionati.setItems(selezionati);
        ricalcolaDisponibli();
        refreshUI();
    }

    private void onLibroChanged(javafx.beans.value.ObservableValue<? extends Libro> obs,
        Libro oldV,
        Libro newV) {
        ricalcolaDisponibli();
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

        Libro lib = cbLibro.getValue();

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
        boolean ok = false;
        try {
        	InetAddress addr = InetAddress.getByName(null);
        	Socket socket=new Socket(addr, 8999);
        	ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        	ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        	out.writeObject("CONSIGLIA LIBRI");
        	out.writeObject(lib);
        	ok = (boolean) in.readObject(); 
            out.close();
    		in.close();
    		socket.close();
    	} catch (Exception e) {
             
        }
        if (ok) {Helpers.showInfo("suggerimenti salvati con successo", lblErr);}
        }
    
        private void caricaLibri(String userId){
            //TODO: unire al db e fornire tutti i libri presenti in tutte le librerie del utente
            try {
            	InetAddress addr = InetAddress.getByName(null);
            	Socket socket=new Socket(addr, 8999);
            	ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            	ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            	UtenteRegistrato ur = new UtenteRegistrato();
            	ur.setUserId(SceneNavigator.getUserID());
            	out.writeObject("CARICA LIBRI LIBRERIE CLIENT");
            	out.writeObject(ur);
            	
            	List<Libro> normalList = (List<Libro>) in.readObject();
            	mieiLibri = FXCollections.observableArrayList(normalList); 
                out.close();
        		in.close();
        		socket.close();
        	} catch (Exception e) {
                 
            }
            mieiLibri.clear();
            disponibili.clear();
            selezionati.clear();
            ultimoLibro = null;
        }

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




    
}
