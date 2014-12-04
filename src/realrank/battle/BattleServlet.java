package realrank.battle;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import realrank.user.User;
import realrank.user.UserDAO;

@SuppressWarnings("serial")
@WebServlet("/winner/*")
public class BattleServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF8"); // this line solves the problem
		response.setContentType("application/json");

		if (request.getSession().getAttribute("user") == null) {
			response.sendRedirect("/support/signin");
			return;
		}
		
		String[] path = request.getPathInfo().split("/");
		UserDAO userdao = new UserDAO();
		User winner = userdao.getUser(path[1]);
		User loser = (User) request.getSession().getAttribute("user");
		BattleManager bm = new BattleManager();
		bm.addResult(winner, loser);
	}
}
