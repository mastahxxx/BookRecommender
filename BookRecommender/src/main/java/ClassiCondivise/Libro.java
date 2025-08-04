package ClassiCondivise;

import java.util.LinkedList;

public class Libro {
    private String titolo;
    private String  autore;
    private String annoPubblicazione;
    private int contenuto;
    private  int stile;
    private int gradevolezza;
    private int originalita;
    private int edizione;
    private String noteContenuto;
    private String noteStile;
    private String noteGradevolezza;
    private String noteOriginalita;
    private String noteEdizione;
    private LinkedList<Libro> libriConsigliati;
    private boolean controllo;


    public Libro () {
        this.libriConsigliati = new LinkedList<Libro>();
    }
    public String getTitolo() {
        return this.titolo;
    }

    public String getAutore() {
        return this.autore;
    }

    public String getAnnoPublicazione() {
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

    public String getNoteStile() {
        return this.noteStile;
    }

    public String getNoteContenuto() {
        return this.noteContenuto;
    }

    public String getNoteGradevolezza() {
        return this.noteGradevolezza;
    }

    public String getNoteOriginalità() {
        return this.noteOriginalita;
    }

    public String getNoteEdizione() {
        return this.noteEdizione;
    }



    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public void setAnnoPublicazione(String annoPublicazione) {
        this.annoPubblicazione = annoPublicazione;
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

    public void setNoteStile(String noteStile) {
        this.noteStile = noteStile;
    }

    public void setNoteContenuto(String noteContenuto) {
        this.noteContenuto = noteContenuto;
    }

    public void setNoteGradevolezza(String noteGradevolezza) {
        this.noteGradevolezza = noteGradevolezza;
    }

    public void setNoteOriginalita(String noteOriginalita) {
        this.noteOriginalita = noteOriginalita;
    }

    public void setNoteEdizione(String noteEdizione) {
        this.noteEdizione = noteEdizione;
    }

    public boolean getControllo() {
        return this.controllo;
    }

    public void setControllo(boolean c) {
        this.controllo = c;
    }

    public LinkedList<Libro> getLibriCosnigliati(){
        return this.libriConsigliati;
    }

    public void setLibriConsigliati(Libro libro) {
        this.libriConsigliati.add(libro);
    }



}
