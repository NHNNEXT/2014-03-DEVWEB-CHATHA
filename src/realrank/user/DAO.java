package realrank.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO {
	public Connection getConnection() throws SQLException {
		Connection conn = null;

		String url = "jdbc:mysql://localhost:3306/realrank?useUnicode=true&characterEncoding=utf8";
		String id = "realrank_test";
		String pw = "1234";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		conn = DriverManager.getConnection(url, id, pw);

		return conn;
	}
}
