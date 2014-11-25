package realrank.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import realrank.support.LoginResult;

import com.google.gson.Gson;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();

		String userId	= request.getParameter("id");
		String password	= request.getParameter("password");

		// DAO : get user from DB
		UserDAO userDAO= new UserDAO();
		User user = userDAO.getUser(userId);
		
		if (user != null && user.matchPassword(password) == false)
			user = null;

		// Set session
		if (user != null) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
		}
		
		LoginResult result = new LoginResult(user);
		response.getWriter().write(gson.toJson(result));
	}
}
