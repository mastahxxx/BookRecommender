package ServerBR;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbQuery extends DataBase {

    private Connection connection = null;

    public DbQuery(){

        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void queryProva()
    {
        try {
            ResultSet result = statement.executeQuery("SELECT nome FROM public.\"UtentiRegistrati\"");
            result.next();
            System.out.println(result.getString(1));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    public ResultSet libriLibro(String param)
    {
        ResultSet result;
        result = null;
        String query;
        query = "select * from public.\"Libri\" where titolo = ? or autore = ?;"; // manca la ricerca per autore e anno insieme

        try {
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setString(1, param);
            pstm.setString(2, param);
            result = statement.executeQuery(query);
            result.next();
            System.out.println(result.getString(1));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        //fammi ritornare una stringa al posto di un oggetto di tipo ResultSet se possibile inoltre ogni attributo è separato da punto e virgola
        //Se il libro eventualmente non essitesse fammi tornare una stringa vuota
        return result;  
    }
    
    //Andre utilizza questo metodo per impostare i libri 
    private Libro impostaParametriLibro(Libro l, String result) {
    	Libro libro;
    	String r = result.split(";");
    	if(!result.eqauls("")) {
    		//mancano i numeri della posizione del vettore perchè non so in che posizioone finiscono i vari valori
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
