package realrank.test;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import realrank.support.DAO;
import realrank.user.User;
import realrank.user.UserDAO;

import com.google.gson.Gson;

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
		assertTrue(true);
		
	}

	@Test
	public void addUserTest() {
		Gson gson = new Gson();
		System.out.println(gson.fromJson("{\"userId\":\"user\"}", User.class));
		
		
		User user = new User("mymy2", "1@1.1", "2", "asdf", "f",
				dao.parseDate("1986-11-24"));
		System.out.println(user);
		assertTrue(userdao.addUser(user));
	}

}
