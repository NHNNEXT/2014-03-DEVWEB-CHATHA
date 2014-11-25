package realrank.support;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DAO {
	public Connection getConnection() throws SQLException {
		Connection conn = null;

		String url = "jdbc:mysql://10.73.45.136:3306/realrank?useUnicode=true&characterEncoding=utf8";
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

	public boolean executeQuery(String sql, ArrayList<Object> parameters) {
		PreparedStatement pstmt = null;
		Connection conn = null;
		Date date;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);

			for (int i = 0; i < parameters.size(); i++) {
				if (parameters.get(i) instanceof String) {
					pstmt.setString(i + 1, (String) parameters.get(i));
				} else if (parameters.get(i) instanceof Integer) {
					pstmt.setInt(i + 1, (Integer) parameters.get(i));
				} else if (parameters.get(i) instanceof Date) {
					date = (Date) parameters.get(i);
					pstmt.setTimestamp(i + 1, new Timestamp(date.getTime()));
				}
			}
			pstmt.execute();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException sqle) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException sqle) {
				}
		}
		return false;
	}

	public ArrayList<Object> selectQuery(String sql,
			ArrayList<Object> parameters, int resultSetLength) {
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Date date;
		ArrayList<Object> result = new ArrayList<Object>();
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);

			for (int i = 0; i < parameters.size(); i++) {
				if (parameters.get(i) instanceof String) {
					pstmt.setString(i + 1, (String) parameters.get(i));
				} else if (parameters.get(i) instanceof Integer) {
					pstmt.setInt(i + 1, (Integer) parameters.get(i));
				} else if (parameters.get(i) instanceof Date) {
					date = (Date) parameters.get(i);
					pstmt.setTimestamp(i + 1, new Timestamp(date.getTime()));
				}
			}
			rs = pstmt.executeQuery();

			while (rs.next()) {
				for (int i = 0; i < resultSetLength; i++) {
					result.add(rs.getObject(i + 1));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException sqle) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException sqle) {
				}
			if (rs != null)
				try {
					conn.close();
				} catch (SQLException sqle) {
				}
		}
		return result;
	}

	public Date parseDate(Object object) {
		Date date = null;
		SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = datetime.parse(object.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
}
