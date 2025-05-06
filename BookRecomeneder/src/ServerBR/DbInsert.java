package ServerBR;

import java.sql.ResultSet;
import java.sql.Statement;

public class DbInsert extends DbConnect {
	
	private DbConnect dbconnect = new DbConnect();
	private Statement statement = dbconnect.connect();
	private String query;

	
	public void loadUtentiRegistrati(String nome, String cognome, String cf, String email, String uid, String password)
	{
		
		try {
			query = "insert into public.\"UtentiRegistrati\" values (";
			query = query + "'" + nome + "',";
			query = query + "'" + cognome + "',";
			query = query + "'" + cf + "',";
			query = query + "'" + email + "',";
			query = query + "'" + uid + "',";
			query = query + "'" + password + "')";
			statement.executeQuery(query);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void loadLibri(String titolo, String autore, int annoP, String editore, String categoria, int codLibro) {
		
		try {
			query = "insert into public.\"Libri\" values (";
			query = query + "'" + titolo + "',";
			query = query + "'" + autore + "',";
			query = query + "'" + annoP + "',";
			query = query + "'" + editore + "',";
			query = query + "'" + categoria + "',";
			query = query + "'" + codLibro + "')";
			statement.executeQuery(query);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
