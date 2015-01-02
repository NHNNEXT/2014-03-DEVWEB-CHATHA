package realrank.controller;

import realrank.objects.User;
import realrank.support.Result;
import realrank.support.Utility;

import com.google.gson.Gson;

import easyjdbc.dao.DBMethods;
import easymapping.annotation.Controller;
import easymapping.annotation.Get;
import easymapping.annotation.Post;
import easymapping.mapping.Http;
import easymapping.response.Json;
import easymapping.response.Jsp;
import easymapping.response.Response;

@Controller
public class UserController {
	
	@Get("/users/logout.rk")
	public void logout(Http http){
		http.removeSessionAttribute("user");
		http.sendRedirect("/");
	}

	@Post("/users/login.rk")
	public Response login(Http http) {
		User user = http.getJsonObject(User.class, "user");
		if (user == null)
			return new Json(new Result(false, "유효하지 않은 접근입니다."));

		User fromDB = DBMethods.get(User.class, user.getId());
		System.out.println(user);
		System.out.println(fromDB);
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
		if (!DBMethods.insert(user)) {
			http.setSessionAttribute("user", user);
			return new Json(new Result(false, "회원 가입 실패"));
		}
		return new Json(new Result(true, null));
	}
	
	@Get("/users/userinfo.rk")
	public Response userinfo(Http http) {
		User user = http.getSessionAttribute(User.class, "user");
		Jsp jsp = new Jsp("userinfo.jsp");
		Gson gson = new Gson();
		jsp.put("user", gson.toJson(user));
		return jsp;
	}


}
