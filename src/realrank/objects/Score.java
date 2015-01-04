package realrank.objects;

import easyjdbc.annotation.Key;
import easyjdbc.annotation.Table;

@Table("score")
public class Score {
	@Key
	String id;
	Integer score;
	Integer reputation;
	
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

	public Integer getReputation() {
		return reputation;
	}

	public void setReputation(Integer reputation) {
		this.reputation = reputation;
	}
}
