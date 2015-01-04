package easyjdbc.column;

import static org.junit.Assert.*;

import org.junit.Test;

public class SqlTest {

	@Test
	public void test() {
		Sql sql = new Sql("sql");
		sql.addColumn(new Column("abc"), new Column("abc"), new Column("abc"));
		sql.addKey(new Column("abc"), new Column("abc"), new Column("abc"));
		System.out.println(sql.getInsertSql());
		System.out.println(sql.getUpdateSql());
		System.out.println(sql.getSelectSql());
		
	}

}
