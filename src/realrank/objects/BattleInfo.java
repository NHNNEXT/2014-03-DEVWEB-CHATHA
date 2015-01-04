package realrank.objects;

import java.util.Date;

public class BattleInfo {
	private int id;
	private String challenger;
	private String champion;
	private Date req_time;
	private Date acc_time;
	private Integer state;
	private String winner;
	private int opponentScore;
	private int opponentReputation;

	public BattleInfo(int id, String challenger, String champion,
			Date req_time, Date acc_time, Integer state, String winner,
			int opponentScore, int opponentReputation) {
		this.id = id;
		this.challenger = challenger;
		this.champion = champion;
		this.req_time = req_time;
		this.acc_time = acc_time;
		this.state = state;
		this.winner = winner;
		this.opponentScore = opponentScore;
		this.opponentReputation = opponentReputation;
	}

	public int getId() {
		return id;
	}
	public String getChallenger() {
		return challenger;
	}
	public String getChampion() {
		return champion;
	}
	public Date getReq_time() {
		return req_time;
	}
	public Date getAcc_time() {
		return acc_time;
	}
	public Integer getState() {
		return state;
	}
	public String getWinner() {
		return winner;
	}
	public int getOpponentScore() {
		return opponentScore;
	}
	public int getOpponentReputation() {
		return opponentReputation;
	}

}
