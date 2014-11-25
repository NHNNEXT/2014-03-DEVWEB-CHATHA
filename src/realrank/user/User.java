package realrank.user;

import java.util.Date;

public class User {
	@Override
	public String toString() {
		return "User [userId=" + userId + ", email=" + email + ", password="
				+ password + ", nickname=" + nickname + ", gender=" + gender
				+ ", birthday=" + birthday + "]";
	}

	private String userId;
	private String email;

	public String getUserId() {
		return userId;
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

	private String password;
	private String nickname;
	private String gender;
	private Date birthday;

	public User(String userId, String email, String password, String nickname,
			String gender, Date birthday) {
		this.userId = userId;
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.gender = gender;
		this.birthday = birthday;
	}

	public boolean matchPassword(String password) {
		return this.password.equals(password);
	}
}
