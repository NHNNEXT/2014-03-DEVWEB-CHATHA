package realrank.user;

import java.util.Date;

public class User {
	private String userId;
	private String email;
	private String password;
	private String nickname;
	private char gender;
	private Date birthday;

	public User(String userId, String email, String password, String nickname, char gender, Date birthday) {
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
