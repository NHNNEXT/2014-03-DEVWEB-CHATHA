package realrank;

import static org.junit.Assert.*;

import org.junit.Test;

import realrank.setting.Setting;

public class SettingTest {

	@Test
	public void test() {
		Setting setting = Setting.getInstance();
		String id = setting.getDb().getId();
		String password = setting.getDb().getPassword();
		String url = setting.getDb().getUrl();
		System.out.println(id);
		System.out.println(password);
		System.out.println(url);
		assertNotNull(setting.getDb());
	}

}
