package ServerBR;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import BookRecommender.src.main.java.ClassiCondivise.String;

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
    	Libro libro;
    	String titolo = l.getTitolo();
    	Libro libro  = dbq.libriLibro(titolo);	
    	return libro;
    		
    }
    
    public synchronized LinkedList<Libro> cercaLibroPerAutore(l) {
    	LinkedList <Libro> result = new LinkedList<Libro>(); //utillizzo una LinkedList perchè potrebbero esserci più libri scritti dallo stesso autore
    	Libro libro;
    	String autore = l.getAutore();
    	result  = dbq.libriAutore(auore); //creare query per la ricerca dei libri in base al nome dell'autore e far ritornare una linkedList di libri
    	
    	return libro;		
    }
    
    public synchronized Libro cercaLibroPerAutoreAnno(l) {
    	String autore = l.getAutore();
    	String anno = l.getAnnoPublicazione();
    	Libro result  = dbq.libriAutoreAnno(auore, anno);	// creare query che restituiscse i libri ricercati per auore e anno
    	return libro;		
    }
    
    public synchronized boolean insertUtente(UtenteRegistrato u) {
    	String nomeCognome = u.getNomeCognome();
    	String codiceFiscale = u.getCodiceFiscale();
    	String mail = u.getmail();
    	String user = u.getUserId();
    	String password = u.getPassoword();
    	boolean esito = dbi.inserisciUtente(nomecognome, codiceFiscale, mail, user, password); //metodo ceh deve controllare se la mail e lo userId esistono già nel db; in caso di esito negativo l'utente viene inserito all'interno del db
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
