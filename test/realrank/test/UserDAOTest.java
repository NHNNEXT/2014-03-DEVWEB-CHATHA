package realrank.test;

import org.junit.Before;
import org.junit.Test;

import realrank.support.DAO;
import realrank.user.User;
import realrank.user.UserDAO;

public class UserDAOTest {
	UserDAO userdao;
	DAO dao;

	@Before
	public void setup() {
		userdao = new UserDAO();
		dao = new DAO();
	}

	@Test
	public void getUserTest() {
		User user = userdao.getUser("mymy");
		System.out.println(user);
	}

	@Test
	public void addUserTest() {

	/*	User user = new User("mymy", "1@1.1", "2", "asdf", "f",
				dao.parseDate("1986-11-24"));
		System.out.println(user);
		assertTrue(userdao.addUser(user));*/
	}

}
