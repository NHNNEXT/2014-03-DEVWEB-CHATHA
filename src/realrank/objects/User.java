package realrank.objects;

import java.util.Date;

import easyjdbc.annotation.Exclude;
import easyjdbc.annotation.Key;
import easyjdbc.annotation.Table;
import easyjdbc.dao.DBMethods;

@Table("user")
public class User {

	@Key
	private String id;
	private String email;
	private String password;
	private String nickname;
	private String gender;
	private Date birthday;
	@Exclude
	private int games;

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
		User champ = DBMethods.get(User.class, cid);
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


}
