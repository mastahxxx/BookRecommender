package ClassiCondivise;

import java.util.LinkedList;

public class Libro {
    private String titolo;
    private String autore;
    private String annoPubblicazione; 

    private int contenuto;
    private int stile;
    private int gradevolezza;
    private int originalita;
    private int edizione;

    private LinkedList<String> noteContenuto;
    private LinkedList<String> noteStile;
    private LinkedList<String> noteGradevolezza;
    private LinkedList<String> noteOriginalita;
    private LinkedList<String> noteEdizione;

    private LinkedList<Libro> libriConsigliati;

    private boolean controllo;

    public Libro () {
        this.libriConsigliati = new LinkedList<>();
        this.noteContenuto = new LinkedList<>();
        this.noteStile = new LinkedList<>();
        this.noteGradevolezza = new LinkedList<>();
        this.noteOriginalita = new LinkedList<>();
        this.noteEdizione = new LinkedList<>();
    }

    //getter
    public String getTitolo() {
        return this.titolo; 
    }
    public String getAutore() {
        return this.autore; 
    }
    public String getAnnoPublicazione() {
        return this.annoPubblicazione; 
    }
    public String getAnnoPubblicazione() {
        return this.annoPubblicazione; 
    }
    public int getStile() {
        return this.stile;
    }
    public int getContenuto() {
        return this.contenuto;
    }
    public int getGradevolezza() {
        return this.gradevolezza;
    }
    public int getOriginalita() {
        return this.originalita; 
    }
    public int getEdizione() {
        return this.edizione; 
    }
    public LinkedList<String> getNoteStile() {
        return this.noteStile;
     }
    public LinkedList<String> getNoteContenuto() {
        return this.noteContenuto; 
    }
    public LinkedList<String> getNoteGradevolezza() {
        return this.noteGradevolezza; 
    }
    public LinkedList<String> getNoteOriginalità() {
        return this.noteOriginalita; 
    } 
    public LinkedList<String> getNoteEdizione() {
        return this.noteEdizione; 
    }
    public boolean getControllo() {
        return this.controllo; 
    }

    public LinkedList<Libro> getLibriConsigliati() { 
        return this.libriConsigliati; 
    }


    //setter
    public void setTitolo(String titolo) {
        this.titolo = titolo; 
    }
    public void setAutore(String autore) {
        this.autore = autore; 
    }
    public void setAnnoPubblicazione(String annoPubblicazione) {
        this.annoPubblicazione = annoPubblicazione; 
    }
    public void setStile(int valutazione) {
        this.stile = valutazione; 
    }
    public void setContenuto(int valutazione) {
        this.contenuto = valutazione; 
    }
    public void setGradevolezza(int valutazione) {
        this.gradevolezza = valutazione;
    }
    public void setOriginalita(int valutazione) {
        this.originalita = valutazione; 
    }
    public void setEdizione(int valutazione) {
        this.edizione = valutazione; 
    }
    public void setControllo(boolean c) {
        this.controllo = c; 
    }

    //aggiungo note alla lista
    public void setNoteStile(String noteSt, String autore) {
        String str = "Note stile : " + autore + ": " + noteSt + ". ";
        noteStile.add(str);
    }
    public void setNoteContenuto(String noteCo, String autore) {
        String str = "Note contenuto : " + autore + ": " + noteCo + ". ";
        noteContenuto.add(str);
    }
    public void setNoteGradevolezza(String noteGr, String autore) {
        String str = "Note gradevolezza : " + autore + ": " + noteGr + ". ";
        noteGradevolezza.add(str);
    }
    public void setNoteOriginalita(String noteOr, String autore) {
        String str = "Note originalità : " + autore + ": " + noteOr + ". ";
        noteOriginalita.add(str);
    }
    public void setNoteEdizione(String noteEd, String autore) {
        String str = "Note edizione : " + autore + ": " + noteEd + ". ";
        noteEdizione.add(str);
    }
    
    //serve per cabmiare Libro, es: classe VisualizzaLibroController
    public void setLibriConsigliati(Libro libro) {
        if (libro != null) {
            this.libriConsigliati.add(libro);
        }
    }
}
