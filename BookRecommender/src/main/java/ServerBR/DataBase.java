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
    
    
    
    
}
