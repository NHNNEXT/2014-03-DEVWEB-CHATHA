package realrank.support;

import realrank.user.User;

public class LoginResult {
	@SuppressWarnings("unused")
	private boolean success;
	@SuppressWarnings("unused")
	private String errmsg;
	@SuppressWarnings("unused")
	private User user;

	public LoginResult(User user) {
		if (user == null) {
			success = false;
			errmsg = "Login Failed";
			this.user = user;
		} else {
			success = true;
			errmsg = "Login Succeed";
			this.user = user;
		}
	}
}
