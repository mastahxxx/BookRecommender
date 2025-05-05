package ServerBR;

import java.sql.ResultSet;
import java.sql.Statement;

public class Dbquery extends DbConnect {

	
	public void queryProva()
	{
		DbConnect dbconnect = new DbConnect();
		Statement statement = dbconnect.connect();
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
		
		
}
