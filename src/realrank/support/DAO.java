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

import realrank.setting.Setting;

public class DAO {

	private ArrayList<Object> parameters = new ArrayList<Object>();
	private String sql;
	private int resultSetLength;

	public void setResultSetLength(int resultSetLength) {
		this.resultSetLength = resultSetLength;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public void addParameters(Object obj) {
		parameters.add(obj);
	}

	public Connection getConnection() throws SQLException {
		Setting setting = Setting.getInstance();
		Connection conn = null;
		String url = setting.db().getUrl();
		String id = setting.db().getId();
		String password = setting.db().getPassword();

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		conn = DriverManager.getConnection(url, id, password);

		return conn;
	}

	public boolean executeQuery() {
		PreparedStatement pstmt = null;
		Connection conn = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);

			setParameters(pstmt);
			pstmt.execute();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;

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
	}

	// 프라이머리 키로 검색하는 경우
	public ArrayList<Object> getRecord() {
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		ArrayList<Object> result = new ArrayList<Object>();
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			setParameters(pstmt);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				for (int i = 0; i < resultSetLength; i++) {
					result.add(rs.getObject(i + 1));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			closeConnection(pstmt, conn, rs);
		}
		return result;
	}

	// 프라이머리 키 외의 값으로 셀렉트 하는 경우
	public ArrayList<ArrayList<Object>> getRecords() {
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		ArrayList<ArrayList<Object>> result = new ArrayList<ArrayList<Object>>();
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);

			setParameters(pstmt);
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

	private void setParameters(PreparedStatement pstmt) throws SQLException {
		Date date;
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
	}

	private void closeConnection(Statement stmt, Connection conn, ResultSet rs) {
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