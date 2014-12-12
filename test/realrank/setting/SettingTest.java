package realrank.setting;

import static org.junit.Assert.*;

import org.junit.Test;

public class SettingTest {

	@Test
	public void test() {
		assertEquals("realrank_test", Setting.getInstance().getDb().getId());
	}

}
