package realrank.objects;

import static org.junit.Assert.*;

import org.junit.Test;

import easyjdbc.dao.DBMethods;

public class UserTest {

	@Test
	public void test() {
		assertEquals(DBMethods.get(User.class, "champ").getGender(), "M");
	}

}
