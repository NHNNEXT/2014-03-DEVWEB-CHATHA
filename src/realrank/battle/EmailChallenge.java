package realrank.battle;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import realrank.support.mail.MessageMod;
import realrank.support.mail.SendMailSSL;
import realrank.user.User;

/**
 * Servlet implementation class EmailChallenge
 */
@WebServlet("/email_challenge")
public class EmailChallenge extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String uid = (String) session.getAttribute("user");
		String cid = request.getParameter("cid");
		BattleManager.challengeTo(uid, cid);
		SendMailSSL.sendTo(new MessageMod(BattleManager.makeLink(uid), User.mailAddress(cid), "Battle Requested", "링크를 누르시면 패배를 인정하게 되며,<br>당신의 점수가 깎이게 됩니다.<br>"));
		response.sendRedirect("");
	}

}
