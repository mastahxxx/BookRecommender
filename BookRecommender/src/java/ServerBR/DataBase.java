

package ServerBR;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import ClassiCondivise.Libreria;
import ClassiCondivise.Libro;
import ClassiCondivise.UtenteRegistrato;

//import BookRecommender.src.main.java.ClassiCondivise.String;

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
    
    public synchronized List<Libro> cercaLibroPerAutore(Libro l){
    	String titolo = l.getTitolo();
    	String autore = l.getAutore();
    	String anno = l.getAnnoPublicazione();
    	List <libro> ris = new List();
    	if(titolo != "" && autore != "" && anno != "") {
    		ris = dbq.ceracaTitoloAutoreAnno(titolo, autore, anno);
    	}
    	else {
    		if(titolo != "" && anno != "") {
        		ris = dbq.cercaTitoloAnno(titolo, anno);	
        	}
    		else {
    			if(autore != "" && anno != "") {
    				ris = dbq.cercaAuotreAnno(autore, anno);
    			}
    			else {
    				if(titolo != "") {
    					ris = dbq.cercaTitolo(titolo);
    				}
    				else {
    					if(titolo != "") {
        					ris = dbq.cercaAuotre(autore);
        				}
    				}
    			}
    		} 		
    	}
    	return ris;	
    }
    
    public synchronized List<Libro> caricaLibrerie(UtenteRegistrato u){
    	List <libro> ris = new List();
    	String userId = u.getUserId();
    	ris = dbq.CaricaLibriDalleLibrerie(userId); //metodo che restituisce una lista contenente tutti i libri presenti in tutte le librerie del utente
    	return ris;
    }
    
    public synchronized boolean controllaEmail(UtenteRegistrato u){
    	String mail = u.getMail();
    	boolean esito = dbq.controllaEmailInDB(mail); //metodo che restituisce true se la mail non è presente nel db
    	return esito;	
    }
    
    public synchronized boolean controllaEmail(UtenteRegistrato u){
    	String userId = u.getUserId();
    	boolean esito = dbq.controllaUserIDInDB(userId); //metodo che restituisce true se lo userId non è presente nel db
    	return esito;	
    }
        
    public synchronized boolean insertUtente(UtenteRegistrato u) {
    	String nomeCognome = u.getNomeCognome();
    	String codiceFiscale = u.getCodiceFiscale();
    	String mail = u.getmail();
    	String user = u.getUserId();
    	String password = u.getPassoword();
    	boolean esito = dbi.inserisciUtente(nomeCognome, codiceFiscale, mail, user, password); l'utente viene inserito all'interno del db
    	return esito;
    }
    
    public synchronized boolean login(UtenteRegistrato u) {
    	String mail = u.getmail();
    	String password = u.getPassoword();
    	boolean esito = dbq.loginMail(mail, password);;//metodo che controlla il login tramite mail; Se il login va a buon fine restituisce true
    	if(!esito) {
    		String userId = u.getUserId();
    		esito = dbq.loginUserId(userId, password);///metodo che controlla il login tramite mail; Se il login va a buon fine restituisce true
    	}
    	return esito;
    }
    
    public synchronized boolean iserisciValutazioni(Libro l) {
    	String titolo = l.getTitolo();
    	int contenuto = l.getContenuto();
    	int stile = l.getTitolo();
    	int gadevolezza = l.getgradevolezza();
    	int originalita = l.getOriginalita();
    	int edizione = l.getEdizione();
    	String noteContenuto = l.getNoteStile();
    	String noteStile = l.getNoteContenuto();
    	String noteGradevolezza = l.getNoteGradevolezza();
    	String noteOriginalita = l.getNoteOriginalità();
    	String noteEdizione = l.getNoteEdizione();
    	boolean controllo = dbi.inserisciValutazioneDb(titolo, contenuto, stile, gadevolezza, originalita, edizione, noteContenuto, noteStile, noteGradevolezza, noteOriginalita, noteEdizione);
    	//metodo che inserisci le valutazione di un utente nel db e restituisce true in caso di esito posito altrimenti false
    	return controllo;
    }
    
    public synchronized boolean InserisciLibreria(Libreria libreria) {
    	String nome = libreria.getNome();
    	LinkedList contenuto = libreria.getContenuto();
    	boolean controllo = dbi.InserisciLibreriaDb(nome, contenuto); //metrodo che inserisci la libreira creata dall'utente nel db; in caso di esito positivo restituisce true altrimenti flase
    	return controllo;
    }
    
    public synchronized boolean InserisciConsigli(UtenteRegistrato u, String libriConsigliati) {
    	String[] vettConsigliati = libriConsigliati.split(";"); //vettore che avrà max dim 4; in pos 0 contiene il tittolo del libro su cui l'utente starà effettuando i consigli mentre nelle rimanente ci saranno i libri consigliati
    	boolean controllo = dbq.controllaLibroInLibreria(u, vettConsigliati[0]); //metodo che controlla se il libro è presente nelle librerie dell'utente; se è presente restituisce true altrimenti flase
    	boolean esito = false;
    	if(controllo) {
    		esito = dbi.inserisciLibriConsigliatiInDb(u, vettConsigliati); //metodo che inserisce i libri consigliati nel db e restitusice true in caso di esito positivo altrimenti false 
    	}
    	return esito;
    } 
    
}
