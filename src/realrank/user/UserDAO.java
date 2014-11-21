package realrank.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO extends DAO {
	private static final DAO dao = new DAO();

	public static User getUser(String userId) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;

		try {
			connection = dao.getConnection();
			String sql = "select * from user where id=?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, userId);
			resultSet = pstmt.executeQuery();
			
			if (resultSet.next()) {
				return new User(resultSet.getString("id"),
						resultSet.getString("email"),
						resultSet.getString("password"),
						resultSet.getString("nickname"),
						resultSet.getString("gender").charAt(0),
						resultSet.getDate("birthDay"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (resultSet != null)
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		return null;
	}
}
