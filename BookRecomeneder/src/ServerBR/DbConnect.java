package ServerBR;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbConnect {
	
	private String url = "jdbc:postgresql://localhost/BookReccomenderDB";
	private String user = "postgres";
	private String password = "Ceriano71";
	
	
	public Statement connect()
	{
		try {
			
			Connection connection = DriverManager.getConnection(url, user, password);
			if(connection != null)
			{
				System.out.println("connessione eseguita con successo");
				Statement statement = connection.createStatement();
				return statement;
		}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	return null;
	}
			
}
	
	
