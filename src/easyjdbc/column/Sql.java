package easyjdbc.column;

import java.util.ArrayList;
import java.util.List;

public class Sql {
	private String tableName;
	private List<Column> columns = new ArrayList<Column>();
	private List<Column> keys = new ArrayList<Column>();

	public Sql(String tableName) {
		this.tableName = tableName;
	}

	public void addColumn(Column... column) {
		for (int i = 0; i < column.length; i++)
			columns.add(column[i]);
	}

	public void addKey(Column... column) {
		for (int i = 0; i < column.length; i++)
			keys.add(column[i]);
	}

	public String getSelectSql() {
		String select = getCommaDividedString(columns) + getCommaDividedString(keys);
		select = select.substring(0, select.length() - 1);
		return "select "+ select + " from " + tableName;
	}

	public String getUpdateSql() {
		String fieldsString = getStringByColumns(columns);
		String condition = getStringByColumns(keys);
		return "update " + tableName + " set " + fieldsString + " where " + condition;
	}
	
	public String getInsertSql() {
		String condition = getQuestionString(columns) + getQuestionString(keys);
		String insert = getCommaDividedString(columns) + getCommaDividedString(keys);
		insert = insert.substring(0, insert.length() - 1);
		condition = condition.substring(0, condition.length() - 1);
		return "insert into " + tableName + " (" + insert + ") values (" + condition + ")";
	}
	
	private String getQuestionString(List<Column> columns) {
		String result = "";
		for (int i = 0; i < columns.size(); i++) {
			result += "?,";
		}
		return result;
	}

	private String getCommaDividedString(List<Column> columns) {
		String result = "";
		for (int i = 0; i < columns.size(); i++) {
			result += columns.get(i).getSqlName() + ",";
		}
		return result;
	}
	
	private String getStringByColumns(List<Column> columns) {
		String resultString = "";
		for (int i = 0; i < columns.size(); i++) {
			resultString += columns.get(i).getSqlName() + "=?, ";
		}
		return resultString;
	}

}
