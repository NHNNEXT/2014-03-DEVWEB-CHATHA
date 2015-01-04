package realrank.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import realrank.objects.Score;
import realrank.objects.User;
import realrank.support.Result;
import realrank.support.Utility;
import realrank.user.UserManager;

import com.google.gson.Gson;

import easyjdbc.query.ExecuteQuery;
import easyjdbc.query.GetRecordQuery;
import easyjdbc.query.QueryExecuter;
import easymapping.annotation.Controller;
import easymapping.annotation.Get;
import easymapping.annotation.Post;
import easymapping.mapping.Http;
import easymapping.response.Json;
import easymapping.response.Jsp;
import easymapping.response.Response;

@Controller
public class UserController {

	@Get("/users/user_search.json")
	public Response userSearch(Http http) {
		Map<String, Object> result = new HashMap<String, Object>();
		System.out.println(http.getParameter("champId"));
		List searchResultList = UserManager.getUserByIdOrEmail(http
				.getParameter("champId"));
		result.put("userList", searchResultList);
		return new Json(result);
	}

	@Get("/users/userinfo.rk")
	public Response userinfo(Http http) {
		User user = http.getSessionAttribute(User.class, "user");

		QueryExecuter qe = new QueryExecuter();
		Score score = qe.get(Score.class, user.getId());
		qe.close();

		Jsp jsp = new Jsp("userinfo.jsp");
		Gson gson = new Gson();
		jsp.put("user", gson.toJson(user));
		jsp.put("score", gson.toJson(score));
		return jsp;
	}

	@Get("/users/login.rk")
	public Response loginGet(Http http) {
		Jsp jsp = new Jsp("login.jsp");
		return jsp;
	}

	@Get("/users/logout.rk")
	public void logout(Http http) {
		http.removeSessionAttribute("user");
		http.sendRedirect("/");
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Post("/users/login.rk")
	public Response login(Http http) {
		User user = http.getJsonObject(User.class, "user");

		if (user == null)
			return new Json(new Result(false, "유효하지 않은 접근입니다."));
		QueryExecuter qe = new QueryExecuter();
		GetRecordQuery getR = new GetRecordQuery(
				7,
				"SELECT id, email, AES_DECRYPT(UNHEX(password), ?), nickname, gender, birthday, games FROM user WHERE id=?",
				user.getPassword(), user.getId());
		User fromDB = new User((ArrayList<Object>) qe.execute(getR));
		// User fromDB = qe.get(User.class, user.getId());

		if (fromDB == null)
			return new Json(new Result(false, "없는 아이디입니다."));
		if (!fromDB.isPasswordCorrect(user))
			return new Json(new Result(false, "패스워드가 다릅니다."));
		qe.close();
		http.setSessionAttribute("user", fromDB);
		return new Json(new Result(true, null));
	}

	@Post("/users/signup.rk")
	public Response signup(Http http) {
		User user = http.getJsonObject(User.class, "user");
		user.setBirthday(Utility.parseDate("MM/dd/yyyy",
				http.getParameter("birthday")));
		user.setGames(0);
		QueryExecuter qe = new QueryExecuter();
		ExecuteQuery eq = new ExecuteQuery(
				"INSERT INTO user VALUES (?, ?, HEX(AES_ENCRYPT(?, ?)), ?, ?, ?, ?)",
				user.getId(), user.getEmail(), user.getPassword(), user
						.getPassword(), user.getNickname(), user.getGender(),
				user.getBirthday(), user.getGames());
		boolean result = qe.execute(eq);
		qe.close();
		if (result) {
			http.setSessionAttribute("user", user);
			return new Json(new Result(false, "회원 가입 실패"));
		}
		return new Json(new Result(true, null));
	}
}
