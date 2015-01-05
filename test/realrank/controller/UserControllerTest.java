package realrank.controller;

import static org.junit.Assert.*;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.gson.Gson;

import realrank.objects.Score;
import realrank.objects.User;
import realrank.support.Utility;
import easyjdbc.query.ExecuteQuery;
import easyjdbc.query.QueryExecuter;
import easymapping.mapping.Http;

public class UserControllerTest extends Mockito{
	public HttpServletRequest req;
	public HttpServletResponse resp;
	public Http http;
	public UserController userController;
	
	@Before
	public void setup() {
		req = mock(HttpServletRequest.class);
		resp = mock(HttpServletResponse.class);
		http = new Http(req, resp);
		userController = new UserController();
	}

	
	@Test
	public void testPostLogin() {
		User user = new User();
		user.setId("test2");
		user.setEmail("test2@test.com");
		user.setPassword("asdf");
		user.setNickname("testarian");
		user.setGender("F");
		user.setGames(0);
		
		QueryExecuter qe = new QueryExecuter();
		ExecuteQuery eq = new ExecuteQuery(
				"INSERT INTO user VALUES (?, ?, HEX(AES_ENCRYPT(?, ?)), ?, ?, NOW(), ?)",
				user.getId(), user.getEmail(), user.getPassword(), user
						.getPassword(), user.getNickname(), user.getGender(),
				 user.getGames());
		boolean result = qe.execute(eq);
	}
	
	@Test
	public void testGetRanks() {
		http = mock(Http.class);
		Mockito.when(http.getParameter("rankType")).thenReturn("1");
		userController.getRanks(http);
	}
}
