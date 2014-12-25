package realrank.objects;

import java.util.Date;

import easyjdbc.annotation.Key;
import easyjdbc.annotation.Table;

@Table("battle")
public class Battle {
	
	@Key
	private Integer id;
	private String challenger;
	private String champion;
	private Date req_time;
	private Date acc_time;
	private Integer state;
	private String winner;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getChallenger() {
		return challenger;
	}

	public void setChallenger(String challenger) {
		this.challenger = challenger;
	}

	public String getChampion() {
		return champion;
	}

	public void setChampion(String champion) {
		this.champion = champion;
	}

	public Date getReq_time() {
		return req_time;
	}

	public void setReq_time(Date req_time) {
		this.req_time = req_time;
	}

	public Date getAcc_time() {
		return acc_time;
	}

	public void setAcc_time(Date acc_time) {
		this.acc_time = acc_time;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

	@Override
	public String toString() {
		return "Battle [id=" + id + ", challenger=" + challenger + ", champion=" + champion + ", req_time=" + req_time + ", acc_time=" + acc_time
				+ ", state=" + state + ", winner=" + winner + "]";
	}

}
