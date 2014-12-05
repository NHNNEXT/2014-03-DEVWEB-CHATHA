package realrank.score;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import realrank.user.User;

@SuppressWarnings("serial")
@WebServlet("/winner/*")
public class ScoreServlet extends HttpServlet {

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
		User loser = (User) request.getSession().getAttribute("user");
		
		if(loser.getUserId().equals(path[1])){
			response.sendRedirect("/support/userinfo");
			return;
		}
		
		ScoreDAO scoredao = new ScoreDAO();
		scoredao.urlWinner(path[1], loser.getUserId());
		loser.setScore(loser.getScore()-10);
		response.sendRedirect("/support/userinfo");
	}
}
