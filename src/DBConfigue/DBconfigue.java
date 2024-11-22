package DBConfigue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconfigue {

	public static Connection getConnection() throws ClassNotFoundException, SQLException {

		Class.forName("com.mysql.cj.jdbc.Driver");

		String url = "jdbc:mysql://localhost:3306/Bank2";
		String username = "root";
		String password = "Amanpande@123";
				
		Connection con = DriverManager.getConnection(url, username, password);

		return con;

	}
	
}
