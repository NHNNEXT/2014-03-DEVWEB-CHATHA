package realrank.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import realrank.battle.BattleManager;
import realrank.objects.Battle;
import realrank.objects.User;
import realrank.support.mail.MessageMod;
import realrank.support.mail.SendMailSSL;
import easymapping.annotation.Controller;
import easymapping.annotation.Get;
import easymapping.annotation.Post;
import easymapping.mapping.Http;
import easymapping.response.Json;
import easymapping.response.Response;

@Controller
public class BattleController {

	@Post("/battle/requestbattle.rk")
	public void requestBattle(Http http){
		String uid = http.getSessionAttribute(User.class, "user").getId();
		String cid = http.getParameter("pid");
		BattleManager.challengeTo(uid, cid);
	}
	
	@Get("/battle/battle_list.rk")
	public Response getBattleList(Http http){
		User user = http.getSessionAttribute(User.class, "user");
		if (user == null) {
			http.sendError(404, "Not Logged");
		}

		List<Battle> sentList = BattleManager.getSentChallenges(user.getId());
		List<Battle> receivedList = BattleManager.getReceivedChallenges(user.getId());
		List<Battle> acceptedList = BattleManager.getAcceptedChallenges(user.getId());
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("sent", sentList);
		result.put("received", receivedList);
		result.put("accepted", acceptedList);
		
		return new Json(result);
	}
	
	@Post("/battle/email_challenge.rk")
	protected void emailChallenge(Http http) {
		String uid = http.getSessionAttribute(User.class, "user").getId();
		String cid = http.getParameter("cid");
		BattleManager.challengeTo(uid, cid);
		SendMailSSL.sendTo(new MessageMod(BattleManager.makeLink(uid), User.mailAddress(cid), "Battle Requested", "링크를 누르시면 패배를 인정하게 되며,<br>당신의 점수가 깎이게 됩니다.<br>"));
		http.sendRedirect("/");
	}
	
}
