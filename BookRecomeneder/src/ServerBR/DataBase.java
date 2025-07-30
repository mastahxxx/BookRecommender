package ServerBR;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class DataBase {
	
	private String url = "jdbc:postgresql://localhost/BookReccomenderDB";
	private String user = "entry";
	private String password = "pass";
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
		//prova
	}
}
