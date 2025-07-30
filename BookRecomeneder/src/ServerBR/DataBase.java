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
	
	
	public DataBase(){
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
}
