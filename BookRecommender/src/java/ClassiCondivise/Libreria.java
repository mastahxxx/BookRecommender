package ClassiCondivise;

import java.util.LinkedList;

public class Libreria {
    private String nome;
    private LinkedList<Libro> contenuto;
    private boolean controllo;

    public Libreria() {
        this.contenuto = new LinkedList<>();
    }

    public Libreria(String nome) {
        this.nome = nome;
        this.contenuto = new LinkedList<>();
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LinkedList<Libro> getContenuto() {
        return this.contenuto;
    }

    public boolean getControllo() {
        return this.controllo;
    }

    public void setControllo(boolean c) {
        this.controllo = c;
    }

}