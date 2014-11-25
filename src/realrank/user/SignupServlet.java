package realrank.user;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import realrank.support.Result;

@SuppressWarnings("serial")
@WebServlet("/user/signup")
public class SignupServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String userId = req.getParameter("id");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String nickname = req.getParameter("nickname");
		String gender = req.getParameter("gender");
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		Date birthday = null;
		try {
			birthday = date.parse(req.getParameter("date"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		User user = new User(userId, email, password, nickname, gender,
				birthday);
		
		UserDAO userDAO = new UserDAO();
		if(userDAO.addUser(user)){
			Result result = new Result(true, null);
			resp.getWriter().write(result.toJson());
		
		}
		

	}
}
