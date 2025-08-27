
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

    public String libriLibro(String param)
    {
        ResultSet result;
        result = null;
        String query;
        String metreturn = null;
        query = "select * from public.\"Libri\" where titolo = ? or autore = ?;"; 

        try {
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setString(1, param);
            pstm.setString(2, param);
            result = statement.executeQuery(query);
            result.next();
            System.out.println(result.getString(1));
            DbQuery classe = new DbQuery();
            metreturn = classe.resultSetToString(result);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        //fammi ritornare una stringa al posto di un oggetto di tipo ResultSet se possibile inoltre ogni attributo è separato da punto e virgola
        //Se il libro eventualmente non essitesse fammi tornare una stringa vuota..
        // matte questa è praticamente una lista, preferisci una stringa con il ; ????
        //lista di tipo List così evitiamo di toccare il codice della gui
        return metreturn;
    }
    
    public String libriLibroAA(String autore, int anno)
    {
        ResultSet result;
        result = null;
        String query;
        String metreturn = null;
        query = "select titolo from public.\"Libri\" where autore = ? and anno_pubblicazione = ?;"; 

        try {
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setString(1, autore);
            pstm.setInt(2, anno);
            result = statement.executeQuery(query);
            result.next();
            System.out.println(result.getString(1));
            DbQuery classe = new DbQuery();
            metreturn = classe.resultSetToString(result);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return metreturn;  
    }
    
    public String UtentiRegistratiEP(String email, String pass)
    {
        ResultSet result;
        result = null;
        String query;
        String metreturn = null;
        query = "select codice_fiscale from public.\"UtentiRegistrati\" where email = ? and password = ?";

        try {
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setString(1, email);
            pstm.setString(2, pass);
            result = statement.executeQuery(query);
            result.next();
            System.out.println(result.getString(1));
            DbQuery classe = new DbQuery();
            metreturn = classe.resultSetToString(result);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return metreturn;  
    }
    
    public String MediaValutazioni(String idLibro)
    {
        ResultSet result;
        result = null;
        String query;
        String metreturn = null;
        query = "select avg(stile), avg(contenuto), avg(gradevolezza), avg(originalità), avg(edizione), avg(voto_finale) from public.\"Valutazioni\" where id_libro = ?";

        try {
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setString(1, idLibro);
            result = statement.executeQuery(query);
            result.next();
            System.out.println(result.getString(1));
            DbQuery classe = new DbQuery();
            metreturn = classe.resultSetToString(result);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return metreturn;  
    }
    
    
    public static String resultSetToString(ResultSet result) throws SQLException {
        if (result == null) {
        	String res ="";
            return res;
        }

        StringBuilder sb = new StringBuilder();
        boolean hasRows = false;

        ResultSetMetaData metaData = result.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (result.next()) {
            hasRows = true;
            for (int i = 1; i <= columnCount; i++) {
                Object value = result.getObject(i);
                if (value != null) {
                    sb.append(value.toString());
                }
                if (i < columnCount) {
                    sb.append(";");
                }
            }
            sb.append(";"); // separatore tra righe
        }

        if (!hasRows) {
            return null;
        }

        // Rimuove eventuale ultimo ";" in eccesso
        if (sb.length() > 0 && sb.charAt(sb.length() - 1) == ';') {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    
    //Andre utilizza questo metodo per impostare i libri
    //matte se io ti passo una stringa poi lo richiami te nel server è inutile richiamarlo per ogni query che viene fatta incodizionalmente dal risultato
    /* 
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

*/
}
