package realrank;

import static org.junit.Assert.*;

import org.junit.Test;

import realrank.setting.Setting;

public class SettingTest {

	@Test
	public void test() {
		System.out.println(Setting.get("db"));
		assertEquals("realrank_test", Setting.get("db").get("id"));

	}

}
