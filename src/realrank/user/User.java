package realrank.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import realrank.annotation.DBExclude;
import realrank.annotation.PrimaryKey;
import realrank.annotation.TableName;

import com.google.gson.Gson;

@TableName("user")
public class User {
	@PrimaryKey
	private String userid;
	private String email;
	private String password;
	private String nickname;
	private String gender;
	private Date birthday;
	
	@DBExclude
	private int score;

	public User(String userId, String email, String password, String nickname,
			String gender, Date birthday) {
		this.userid = userId;
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.gender = gender;
		this.birthday = birthday;
	}

	public User() {
	}

	public boolean matchPassword(String password) {
		return this.password.equals(password);
	}

	@Override
	public String toString() {
		return "User [userId=" + userid + ", email=" + email + ", password="
				+ password + ", nickname=" + nickname + ", gender=" + gender
				+ ", birthday=" + birthday + "]";
	}

	public String getUserId() {
		return userid;
	}

	public void setUserId(String userId) {
		this.userid = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public boolean setBirthday(String birthdayString){
		SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
		Date birthday = null;
		try {
			birthday = date.parse(birthdayString);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		this.birthday = birthday;
		return true;
	}
	
	public String getJson(){
		Gson gson = new Gson();
		return gson.toJson(this);
	}

	public static User fromJson(String jsonString) {
		Gson gson = new Gson();
		return gson.fromJson(jsonString, User.class);
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public static String mailAddress(String cid) {
		String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
		if (cid.matches(emailRegex)) {
			return cid;
		}
		UserDAO champDAO = new UserDAO();
		User champ = champDAO.getUser(cid);
		if (champ == null) {
			
		}
		return champ.getEmail();
	}
}
