package realrank.objects;

import easyjdbc.annotation.Exclude;
import easyjdbc.annotation.Key;
import easyjdbc.annotation.Table;
import easyjdbc.query.GetRecordQuery;
import easyjdbc.query.QueryExecuter;

@Table("score")
public class Rank {
	@Key
	String id;
	Integer score;
	
	@Exclude
	String rankname;
	@Exclude
	Integer userCount;
	@Exclude
	Integer rank;
	
	public void add(int i) {
		score +=i;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public void initRank(QueryExecuter qe) {
		GetRecordQuery query = new GetRecordQuery(1, "select count(id) from score");
		rank = (Integer) qe.execute(query);
		
	}
}
