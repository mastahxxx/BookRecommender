package ClientBR.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import ClassiCondivise.Libro;
import ClientBR.SceneNavigator;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class VisualizzaLibroController {

    @FXML private Label libroVisionato;
    @FXML private Label stile;
    @FXML private Label contenuto;
    @FXML private Label gradevolezza;
    @FXML private Label originalita;
    @FXML private Label edizione;
    @FXML private Label votoFinale;
    @FXML private TextArea note;
    @FXML private Button home;
    @FXML private Button cercaLibri;

    @FXML private TableView<Libro> tblViewConsigli;
    @FXML private TableColumn<Libro, String> tTitolo;
    @FXML private TableColumn<Libro, String> tAutore;
    @FXML private TableColumn<Libro, String> tAnno;

    private Libro libro;
    private final ObservableList<Libro> consigliatiData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {


        this.libro = SceneNavigator.getLibro();
        refreshUI();

        note.setEditable(false); //l' utente non può scrivere nella textarea
        note.setWrapText(true); //per andare a capo automaticamente

        //le colonne si aggiornano in automatico attraverso i getter corrispondenti
        tTitolo.setCellValueFactory(new PropertyValueFactory<>("titolo"));
        tAutore.setCellValueFactory(new PropertyValueFactory<>("autore"));
        tAnno.setCellValueFactory(new PropertyValueFactory<>("annoPubblicazione")); // 2 b
        tblViewConsigli.setItems(consigliatiData);

        //doppio click per aprire il libro selezionato
        tblViewConsigli.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                apriLibro();
            }
        });
    }

    //bottoni
    @FXML private void onCercaLibri(){
        SceneNavigator.switchToCercaLibri();
    }
    @FXML private void onHome(){
        SceneNavigator.switchToHome();
    }

    public void apriLibro() { 
        Libro sel = tblViewConsigli.getSelectionModel().getSelectedItem(); //prendiamo il libro selezionato dal utente
        if (sel == null) return;
        this.libro = sel;
        refreshUI();
    }

    private void refreshUI() {
        if (libro == null) {
            clearUI();
            return;
        }

        libroVisionato.setText(
            Objects.toString(libro.getTitolo(), "") + " — " +
            Objects.toString(libro.getAutore(), "") + " (" +
            Objects.toString(libro.getAnnoPubblicazione(), "") + ")"
        );

        stile.setText("voto stile: " + libro.getStile());
        contenuto.setText("voto contenuto: " + libro.getContenuto());
        gradevolezza.setText("voto gradevolezza: " + libro.getGradevolezza());
        edizione.setText("voto edizione: " + libro.getEdizione());
        originalita.setText("voto originalità: " + libro.getOriginalita());

        int media = (libro.getStile() + libro.getContenuto() + libro.getGradevolezza() + libro.getEdizione() + libro.getOriginalita()) / 5;
        votoFinale.setText("voto finale: " + media);

        note.setText(concatenaNote(
            libro.getNoteStile(),
            libro.getNoteContenuto(),
            libro.getNoteGradevolezza(),
            libro.getNoteOriginalità(), 
            libro.getNoteEdizione()
        ));

        //mostriamo nella tableview i libri consigliati
        LinkedList<Libro> consigliati = libro.getLibriConsigliati();
        consigliatiData.setAll(consigliati == null ? List.of() : consigliati);
    }

    private void clearUI() {
        libroVisionato.setText("");
        stile.setText("");
        contenuto.setText("");
        gradevolezza.setText("");
        edizione.setText("");
        originalita.setText("");
        votoFinale.setText("");
        note.setText("");
        consigliatiData.clear();
    }

    
    private final String concatenaNote(List<String>... liste) {
        StringBuilder sb = new StringBuilder();//buffer per costruire la stringa finale
        for (List<String> l : liste) {
            if (l == null) continue;
            for (String n : l) {
                if (n == null || n.isBlank()) continue;
                if (sb.length() > 0) sb.append("\n---\n");
                sb.append(n.trim());
            }
        }
        return sb.toString();
    }
}
