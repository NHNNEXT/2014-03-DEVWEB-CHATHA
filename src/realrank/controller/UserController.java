package realrank.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import realrank.objects.User;
import realrank.support.Result;
import realrank.support.Utility;
import realrank.user.UserManager;

import com.google.gson.Gson;

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
		List searchResultList = UserManager.getUserByIdOrEmail(http.getParameter("champId"));
		result.put("userList", searchResultList);
		return new Json(result);
	}
	
	@Get("/users/userinfo.rk")
	public Response userinfo(Http http) {
		User user = http.getSessionAttribute(User.class, "user");
		Jsp jsp = new Jsp("userinfo.jsp");
		Gson gson = new Gson();
		jsp.put("user", gson.toJson(user));
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

	@Post("/users/login.rk")
	public Response login(Http http) {
		User user = http.getJsonObject(User.class, "user");
		if (user == null)
			return new Json(new Result(false, "유효하지 않은 접근입니다."));
		QueryExecuter qe = new QueryExecuter();
		User fromDB = qe.get(User.class, user.getId());
		qe.close();
		if (fromDB == null)
			return new Json(new Result(false, "없는 아이디입니다."));
		if (!fromDB.isPasswordCorrect(user))
			return new Json(new Result(false, "패스워드가 다릅니다."));
		http.setSessionAttribute("user", fromDB);
		return new Json(new Result(true, null));
	}

	@Post("/users/signup.rk")
	public Response signup(Http http) {
		User user = http.getJsonObject(User.class, "user");
		user.setBirthday(Utility.parseDate("MM/dd/yyyy", http.getParameter("birthday")));
		QueryExecuter qe = new QueryExecuter();
		int result = qe.insert(user);
		qe.close();
		if (result == 0) {
			http.setSessionAttribute("user", user);
			return new Json(new Result(false, "회원 가입 실패"));
		}
		return new Json(new Result(true, null));
	}
}