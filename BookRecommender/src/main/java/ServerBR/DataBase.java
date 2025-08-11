package ServerBR;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class DataBase {

    protected String url = "jdbc:postgresql://localhost/BookReccomenderDB";
    protected String user = "entry";
    protected String password = "pass";
    protected Statement statement;
    private DbQuery dbq;
    private DbInsert dbi;
    private DbUpdate dbu;


    public DataBase(){
        dbq = new DbQuery();
        dbi = new DbInsert();
        dbu = new DbUpdate();
    	try {

            Connection connection = DriverManager.getConnection(url, user, password);
            if(connection != null)
            {
                System.out.println("connessione eseguita con successo");
                statement = connection.createStatement();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public synchronized Libro cercaLibroPerTitolo(l) {
    	int count  = 0;
    	String result;
    	Libro libro;
    	String titolo = l.getTitolo();
    	String result  = dbq.libriLibro(titolo);	
    	Libro libro = this.impostaParametriLibro(libro, result);
    	return libro;
    		
    }
    
    public synchronized LinkedList<Libro> cercaLibroPerAutore(l) {
    	int count  = 0;
    	LinkedList <Libro> result = new LinkedList<Libro>();
    	Libro libro;
    	String autore = l.getAutore();
    	result  = dbq.libriLibro(auore);	
    	Libro libro = this.impostaParametriLibro(libro, result);
    	
    	return libro;		
    }
    
    public synchronized Libro cercaLibroPerAutoreAnno(l) {
    	int count  = 0;
    	String result;
    	String autore = l.getAutore();
    	String anno = l.getAnnoPublicazione();
    	String result  = dbq.libriLibro(auore, anno);	
    	Libro libro = this.impostaParametriLibro(libro, result);
    	return libro;		
    }
    
    private synchronized Libro impostaParametriLibro(Libro l, String result) {
    	Libro libro;
    	String r = result.split(";");
    	if(!result.eqauls("")) {
    		libro.setTitolo(r[]);
    		libro.setAutore(r[]);
    		libro.setAnnoPublicazione(r[]);
    		libro.setStile(r[])
    		libro.setContenuto(r[]);
    		libro.setGradevolezza(r[]);
    		libro.setsetOriginalita(r[]);
    		libro.setEdizione(r[]);
    		libro.setNoteStile(r[]);
    		libro.setNoteContenuto(r[]);
    		libro.setNoteGradevolezza(r[]);
    		libro.setNoteOriginalita(r[]);
    		libro.setNoteEdizione(r[]);
    		libro.setControllo(true);
    		//bisogna gestire i libri consigliuati per il libro ricercato dal client
    	}
    	else {
    		libro.setControllo(false);
    	}
		
		return libro;
    }
}
