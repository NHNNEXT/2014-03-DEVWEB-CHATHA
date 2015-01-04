package realrank.objects;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;

import easyjdbc.annotation.Exclude;
import easyjdbc.annotation.Key;
import easyjdbc.annotation.Table;
import easyjdbc.query.QueryExecuter;
import easymapping.annotation.DateFormat;

@Table("user")
public class User {

	@Key
	private String id;
	private String email;
	private String password;
	private String nickname;
	private String gender;
	@DateFormat("yyyy-MM-dd")
	private Date birthday;
	private int games;

	public User() {};
	
	public User(ArrayList<Object> data) {
		this.id = (String) data.get(0);
		this.email = (String) data.get(1);
		System.out.println(data.get(2));
		this.password = new String((byte[]) data.get(2));
		this.nickname = (String) data.get(3);
		this.gender = (String) data.get(4);
		this.birthday = (Date) data.get(5);
		this.games = (int) data.get(6);
	}
	
	public int getGames() {
		return games;
	}

	public void setGames(int games) {
		this.games = games;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Exclude
	private int score;


	public String getId() {
		return id;
	}
	
	public static String mailAddress(String cid) {
		String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
		if (cid.matches(emailRegex)) {
			return cid;
		}
		QueryExecuter qe = new QueryExecuter();
		User champ = qe.get(User.class, cid);
		qe.close();
		if (champ == null) {
			return null;
		}
		return champ.getEmail();
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + ", nickname=" + nickname + ", gender=" + gender + ", birthday="
				+ birthday + ", score=" + score + "]";
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getNickname() {
		return nickname;
	}

	public String getGender() {
		return gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public int getScore() {
		return score;
	}

	public boolean isPasswordCorrect(User user) {
		return password.equals(user.getPassword());
	}

	public List<Object> toList() {
		ArrayList<Object> ret = new ArrayList<Object>();
		ret.add(id);
		ret.add(email);
		ret.add(password);
		ret.add(nickname);
		ret.add(gender);
		ret.add(birthday);
		ret.add(games);
		return ret; 
	}

	public void update(User usermod) {
		
	}

	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	public String getScoreJson(QueryExecuter qe) {
		Gson gson = new Gson();
		return gson.toJson(qe.get(Score.class, id));
	}
}
