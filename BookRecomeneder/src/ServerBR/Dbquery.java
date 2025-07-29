package ServerBR;

import java.sql.ResultSet;
import java.sql.Statement;

public class Dbquery extends DataBase {

	
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
		
		
}
