import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
 
/**
 *
 * @author selvyn
 */
public class DbConnection
{
    private Connection itsConnection;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        DbConnection _main = new DbConnection();
         
        _main.execute();
    }
 
 
    public  void    execute()
    {
      try
      {
        Class.forName( "com.mysql.jdbc.Driver" );
 
        itsConnection = DriverManager.getConnection( "jdbc:mysql://192.168.99.100:3306/db_grad_cs_1917", "root", "ppp" );
          
        System.out.println( itsConnection.getCatalog());
      }
      catch( ClassNotFoundException e )
      {
         e.printStackTrace();
      }
      catch( SQLException e )
      {
         e.printStackTrace();
      }
    }
}