package realrank.objects;

import java.util.Date;

import easyjdbc.annotation.Exclude;
import easyjdbc.annotation.Key;
import easyjdbc.annotation.Table;
import easyjdbc.dao.DBMethods;
import easyjdbc.dao.Record;

@Table("user")
public class User implements Record {

	@Key
	private String id;
	private String email;
	private String password;
	private String nickname;
	private String gender;
	private Date birthday;

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Exclude
	private int score;

	@Override
	public void set(Object... params) {
		id = params.length < 1 ? null : (String) params[0];
		email = params.length < 3 ? null : (String) params[1];
		password = params.length < 2 ? null : (String) params[2];
		nickname = params.length < 4 ? null : (String) params[3];
		gender = params.length < 5 ? null : (String) params[4];
		birthday = params.length < 6 ? null : (Date) params[5];
	}

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
