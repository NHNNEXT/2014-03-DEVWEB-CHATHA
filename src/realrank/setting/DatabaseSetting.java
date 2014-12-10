package realrank.setting;

public class DatabaseSetting {
	
	private String url = "jdbc:mysql://10.73.45.136:3306/realrank?useUnicode=true&characterEncoding=utf8";
	private String id = "realrank_test";
	private String password = "1234";

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
