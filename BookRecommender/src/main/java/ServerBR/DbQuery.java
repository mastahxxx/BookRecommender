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
        query = "select * from public.\"Libri\" where titolo = ? or autore = ?;";

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

        return result;
    }


}
