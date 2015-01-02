package realrank.controller;

import realrank.objects.User;
import easymapping.annotation.Controller;
import easymapping.annotation.Get;
import easymapping.mapping.Http;


@Controller
public class HomeController {

	@Get("/index.rk")
	public void home(Http http) {
		User user = http.getSessionAttribute(User.class, "user");
		if (user == null) {
			http.sendRedirect("/users/login.rk");
			return;
		}
		http.sendRedirect("/users/userinfo.rk");
	}

}
