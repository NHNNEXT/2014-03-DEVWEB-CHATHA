package realrank.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import realrank.battle.BattleManager;
import realrank.objects.Battle;
import realrank.objects.User;
import realrank.support.Notification;
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
			http.sendError(400, "Bad Request : Not logged on");
			return null;
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
	public void emailChallenge(Http http) {
		String uid = http.getSessionAttribute(User.class, "user").getId();
		String cid = http.getParameter("cid");
		if(BattleManager.challengeTo(uid, cid)){
			Notification.sendSimpleBattleMsg(uid, cid);
		}
		//SendMailSSL.sendTo(new MessageMod(BattleManager.makeLink(uid), User.mailAddress(cid), "Battle Requested", "링크를 누르시면 패배를 인정하게 되며,<br>당신의 점수가 깎이게 됩니다.<br>"));
		http.sendRedirect("/");
	}
	
	//TODO 어떤 방식으로 접근할지는... 잘 모르겠.... 
	public void acceptChallenge(Http http){
		String bId = http.getParameter("battleId");
		long battleId = Long.valueOf(bId);
		String recpId = http.getSessionAttribute(User.class,  "user").getId();
		String chalId = http.getParameter("cid");
		
		if(BattleManager.acceptChallenge(battleId)){
			Notification.sendChallegeAcceptedAlert(chalId, recpId);
		};
		
		//TODO sendRedirect를 '도전 수락이 완료되었습니다!' 뭐 이런 페이지를 따로 만들어서 할지 아니면 alert를 뜨게 할지....
		http.sendRedirect("/");
	}
	
	public void denyChallenge(Http http){
		String bId = http.getParameter("battleId");
		long battleId = Long.valueOf(bId);
		String recpId = http.getSessionAttribute(User.class,  "user").getId();
		String chalId = http.getParameter("cid");
		
		if(BattleManager.denyChallenge(battleId)){
			Notification.sendChallegeDeniedAlert(chalId, recpId);
		};
		http.sendRedirect("/");
	}
	

	
	
}
