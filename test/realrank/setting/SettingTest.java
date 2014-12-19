package realrank.setting;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

public class SettingTest {

	@Test
	public void testGetDb() {
		String db = Setting.get("db", "url");
		System.out.println(db);
	}

}
