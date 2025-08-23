package ClientBR.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ClassiCondivise.Libro;

public class VisualizzaLibroController {

    @FXML private Label lblTitolo;
    @FXML private Label lblAutore;
    @FXML private Label lblAnno;

    private Libro libro;

    // Questo metodo viene chiamato da CercaLibroController per passare il libro selezionato
    public void setLibro(Libro libro) {
        this.libro = libro;

        // Popolo le Label con i dati del libro
        lblTitolo.setText(libro.getTitolo());
        lblAutore.setText(libro.getAutore());
        lblAnno.setText(String.valueOf(libro.getAnnoPublicazione()));
    }
}
