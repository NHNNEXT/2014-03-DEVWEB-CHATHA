package realrank.battle;

import java.util.Date;

class BattleInfo {
	private int id;
	private String challengerId;
	private String champId;
	private Date reqTime;
	private Date accTime;
	private int state;
	private String winnerId;
	
	protected BattleInfo(int id, String challengerId, String champId,
			Date reqTime, Date accTime, int state, String winnerId) {
		super();
		this.id = id;
		this.challengerId = challengerId;
		this.champId = champId;
		this.reqTime = reqTime;
		this.accTime = accTime;
		this.state = state;
		this.winnerId = winnerId;
	}

	int getId() {
		return id;
	}

	void setId(int id) {
		this.id = id;
	}

	String getChallengerId() {
		return challengerId;
	}

	void setChallengerId(String challengerId) {
		this.challengerId = challengerId;
	}

	String getChampId() {
		return champId;
	}

	void setChampId(String champId) {
		this.champId = champId;
	}

	Date getReqTime() {
		return reqTime;
	}

	void setReqTime(Date reqTime) {
		this.reqTime = reqTime;
	}

	Date getAccTime() {
		return accTime;
	}

	void setAccTime(Date accTime) {
		this.accTime = accTime;
	}

	int getState() {
		return state;
	}

	void setState(int state) {
		this.state = state;
	}

	String getWinnerId() {
		return winnerId;
	}

	void setWinnerId(String winnerId) {
		this.winnerId = winnerId;
	}

	@Override
	public String toString() {
		return "BattleInfo [id=" + id + ", challengerId=" + challengerId
				+ ", champId=" + champId + ", reqTime=" + reqTime
				+ ", accTime=" + accTime + ", state=" + state + ", winnerId="
				+ winnerId + "]";
	}
}
