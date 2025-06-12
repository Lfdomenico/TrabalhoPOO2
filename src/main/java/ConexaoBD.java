import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {
	
	public static Connection getConexao() throws SQLException {
		
		String url = "jdbc:mysql://localhost:3306/projetopoo2";
		String usuario = "root";
		String password = "utfpr";
		
		return DriverManager.getConnection(url, usuario, password);
			
	}
}
