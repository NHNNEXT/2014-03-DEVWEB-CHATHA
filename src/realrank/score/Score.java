package realrank.score;

public class Score {
	private int score;
	private String id;

	public Score(String id, int score) {
		this.id = id;
		this.score = score;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
