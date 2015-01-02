package realrank.objects;

import static org.junit.Assert.*;

import org.junit.Test;

import easyjdbc.dao.DBMethods;

public class ScoreTest {

	@Test
	public void test() {
		assertEquals(DBMethods.get(Score.class, "chal").getScore(), (Integer) 0);
	}

}
