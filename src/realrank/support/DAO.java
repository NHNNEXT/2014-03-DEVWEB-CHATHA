package realrank.support;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
				} else if (parameters.get(i) instanceof Long) {
					pstmt.setLong(i + 1, (long) parameters.get(i));
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

//	레코드 많은 경우 다루기 어려워 2중 ArrayList로 바꿈
	public ArrayList<ArrayList<Object>> selectQuery(String sql,
			ArrayList<Object> parameters, int resultSetLength) {
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Date date;
		ArrayList<ArrayList<Object>> result = new ArrayList<ArrayList<Object>>();
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);

			for (int i = 0; i < parameters.size(); i++) {
				if (parameters.get(i) instanceof String) {
					pstmt.setString(i + 1, (String) parameters.get(i));
				} else if (parameters.get(i) instanceof Integer) {
					pstmt.setInt(i + 1, (Integer) parameters.get(i));
				} else if (parameters.get(i) instanceof Long) {
					pstmt.setLong(i + 1, (long) parameters.get(i));
				} else if (parameters.get(i) instanceof Date) {
					date = (Date) parameters.get(i);
					pstmt.setTimestamp(i + 1, new Timestamp(date.getTime()));
				}
			}
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ArrayList<Object> record = new ArrayList<Object>();
				for (int i = 0; i < resultSetLength; i++) {
					record.add(rs.getObject(i + 1));
				}
				result.add(record);
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			closeConnection(pstmt, conn, rs);
		}
		return result;
	}
	
	public ArrayList<Object> selectQuery(String sql, int resultSetLength) {
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		ArrayList<Object> result = new ArrayList<Object>();
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				for (int i = 0; i < resultSetLength; i++) {
					result.add(rs.getObject(i + 1));
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			closeConnection(stmt, conn, rs);
		}
		return result;
	}

	private void closeConnection(Statement stmt, Connection conn,
			ResultSet rs) {
		closeConnection(stmt, conn);
		if (rs != null)
			try {
				conn.close();
			} catch (SQLException sqle) {
			}
	}
	
	private void closeConnection(Statement stmt, Connection conn) {
		if (stmt != null)
			try {
				stmt.close();
			} catch (SQLException sqle) {
			}
		if (conn != null)
			try {
				conn.close();
			} catch (SQLException sqle) {
			}
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
