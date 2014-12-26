package realrank.battle;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import realrank.user.User;

/**
 * Battle list for user (JSON format)
 * @author JinWoo Lee
 */
@SuppressWarnings("serial")
@WebServlet("/battle/list.json")
public class BattleListServlet extends HttpServlet {
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF8");
		response.setContentType("application/json");

		User user = (User)request.getSession().getAttribute("user");
		if (user == null) {
			// TODO: 400 Bad Request
		}
		
		ArrayList<BattleInfo> sentList = BattleManager.getSentChallenges(user.getUserId());
		ArrayList<BattleInfo> receivedList = BattleManager.getReceivedChallenges(user.getUserId());
		ArrayList<BattleInfo> acceptedList = BattleManager.getAcceptedChallenges(user.getUserId());
		
		Gson gson = new Gson();
		response.getWriter().write( "{" +
				"\"sent\" : " + gson.toJson(sentList) + "," +
				"\"received\" : " + gson.toJson(receivedList) + "," +
				"\"accepted\" : " + gson.toJson(acceptedList) + "}" );
	}

}
