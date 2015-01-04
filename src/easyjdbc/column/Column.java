package easyjdbc.column;

public class Column {
	
	private String name;
	private String sqlName;

	public Column(String name) {
		this.name = name;
		this.sqlName = name;
	}

	public String getSqlName() {
		return sqlName;
	}


}
