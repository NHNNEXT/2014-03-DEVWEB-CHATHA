package realrank.user;

public class LoginResult {
	private boolean success;
	private String errmsg;
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
