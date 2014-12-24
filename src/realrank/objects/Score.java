package realrank.objects;

import easyjdbc.annotation.Key;
import easyjdbc.annotation.Table;
import easyjdbc.dao.Record;

@Table("score")
public class Score implements Record {
	@Key
	String id;
	Integer score;
	
	public void add(int i) {
		score +=i;
	}

	@Override
	public void set(Object... params) {
		id = params.length < 1 ? null : (String) params[0];
		score = params.length < 2 ? null : (Integer) params[1];
	}
}
