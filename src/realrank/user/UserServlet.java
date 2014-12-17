package realrank.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import realrank.support.Result;

@SuppressWarnings("serial")
@WebServlet("/users/*")
public class UserServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF8"); // this line solves the problem
		response.setContentType("application/json");
		// users/로 들어오는 모든 요청을 받아 두번쨰 파라미터에 따라 다른 실행
		String[] path = request.getPathInfo().split("/");
		if (path.length == 2) {
			switch (path[1]) {
			case "login":
				login(request, response);
				break;
			case "signup":
				signup(request, response);
				break;
			case "logout":
				logout(request, response);
				break;
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF8"); // this line solves the problem
		response.setContentType("application/json");
		
		// users/로 들어오는 모든 요청을 받아 두번쨰 파라미터에 따라 다른 실행
		String[] path = request.getPathInfo().split("/");
		if (path.length == 2) {
			switch (path[1]) {
			case "login":
				login(request, response);
				break;
			case "signup":
				signup(request, response);
				break;
			case "logout":
				logout(request, response);
				break;
			}
		}
	}

	// 로그인
	private void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User user = User.fromJson(request.getParameter("user"));

		UserDAO userDAO = new UserDAO();
		User userFromDB = userDAO.getUser(user.getUserId());

		if (userFromDB == null) {
			response.getWriter().write(
					new Result(false, "존재하지 않는 아이디입니다.").toJson());
			return;
		}
		if (!userFromDB.matchPassword(user.getPassword())) {
			response.getWriter().write(
					new Result(false, "패스워드가 틀렸습니다.").toJson());
			return;
		}

		request.getSession().setAttribute("user", userFromDB);
		response.getWriter().write(new Result(true, "로그인 성공").toJson());
	}

	// 회원가입
	private void signup(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = User.fromJson(request.getParameter("user"));
		user.setBirthday(request.getParameter("birthday"));
		UserDAO userDAO = new UserDAO();
		if (userDAO.addUser(user)) {
			request.getSession().setAttribute("user", user);
			response.getWriter().write(new Result(true, null).toJson());
			return;
		}
		response.getWriter().write(new Result(false, "회원 가입 실패").toJson());
	}

	// 로그아웃
	private void logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().removeAttribute("user");
		System.out.println("logout");
		response.sendRedirect("/");
	}
}
