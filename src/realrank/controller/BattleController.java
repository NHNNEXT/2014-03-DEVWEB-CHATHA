package realrank.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import realrank.battle.BattleManager;
import realrank.objects.Battle;
import realrank.objects.BattleInfo;
import realrank.objects.Score;
import realrank.objects.User;
import realrank.support.Notification;
import realrank.support.Result;
import easyjdbc.query.QueryExecuter;
import easymapping.annotation.Controller;
import easymapping.annotation.Get;
import easymapping.annotation.Post;
import easymapping.mapping.Http;
import easymapping.response.Json;
import easymapping.response.Jsp;
import easymapping.response.Response;

@Controller
public class BattleController {
	

	@Post("/battle/battle_send.rk")
	public Response battleRequest(Http http){
		String uid = http.getSessionAttribute(User.class, "user").getId();
		String sendTo = http.getParameter("champId");
		if (sendTo == null)
			return new Json(new Result(false, "유효하지 않은 접근입니다."));
		QueryExecuter qe = new QueryExecuter();
		User fromDB = qe.get(User.class, sendTo);

		if (fromDB == null)
			return new Json(new Result(false, "없는 아이디입니다."));
		qe.close();
		http.setSessionAttribute("user", fromDB);

		BattleManager.challengeTo(uid, sendTo);
		Notification.sendChallegeAlert(uid, sendTo);
		
		return new Json(new Result(true, null));
	}
	
	@Get("/battle/battle_send.rk")
	public Response getBattleRequestForm(Http http){
		User user = http.getSessionAttribute(User.class, "user");
		if (user == null) {
			http.sendError(401, "Bad Request : Not logged on");
			return null;
		}
		Jsp jsp = new Jsp("battle_send.jsp");
		Gson gson = new Gson();
		jsp.put("user", gson.toJson(user));
		return jsp;
	}
	
	@Get("/battle/battle_list.rk")
	public Response getBattleListView(Http http){
		User user = http.getSessionAttribute(User.class, "user");
		if (user == null) {
			http.sendError(401);
			return null;
		}
		Jsp jsp = new Jsp("battle_list.jsp");
		Gson gson = new Gson();
		jsp.put("user", gson.toJson(user));
		return jsp;
	}
	
	@Get("/battle/battle_list.json")
	public Response getBattleListData(Http http){
		User user = http.getSessionAttribute(User.class, "user");
		if (user == null) {
			http.sendError(400, "Bad Request : Not logged on");
			return null;
		}

		List<BattleInfo> sentList = BattleManager.getSentChallenges(user.getId(), BattleManager.STATE_NEW);
		List<BattleInfo> receivedList = BattleManager.getReceivedChallenges(user.getId(), BattleManager.STATE_NEW);
		List<BattleInfo> acceptedList = BattleManager.getSentChallenges(user.getId(), BattleManager.STATE_ACCEPTED);
		acceptedList.addAll(BattleManager.getReceivedChallenges(user.getId(), BattleManager.STATE_ACCEPTED));
		
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
		
		BattleManager.challengeTo(uid, cid);
		Notification.sendSimpleBattleMsg(uid, cid);
		
		//SendMailSSL.sendTo(new MessageMod(BattleManager.makeLink(uid), User.mailAddress(cid), "Battle Requested", "링크를 누르시면 패배를 인정하게 되며,<br>당신의 점수가 깎이게 됩니다.<br>"));
		http.sendRedirect("/");
	}
	
	@Post("/battle/battle_accept.rk")
	public void acceptChallenge(Http http){
		String recpId = http.getSessionAttribute(User.class,  "user").getId();
		String bid = http.getParameter("battleId");
		long battleId = Long.valueOf(bid);
		String chalId = http.getParameter("challengerId");
			
		BattleManager.acceptChallenge(battleId);
		Notification.sendChallegeAcceptedAlert(recpId, chalId);
		
		http.sendRedirect("/");
	}
	
	@Post("/battle/battle_deny.rk")
	public void denyChallenge(Http http){
		String recpId = http.getSessionAttribute(User.class,  "user").getId();
		String bid = http.getParameter("battleId");
		long battleId = Long.valueOf(bid);
		String chalId = http.getParameter("challengerId");

		BattleManager.denyChallenge(battleId);
		Notification.sendChallegeDeniedAlert(recpId, chalId);
		
		http.sendRedirect("/");
	}
	
	@Post("/battle/battle_cancel.rk")
	public void cancelChallenge(Http http){
		String bid = http.getParameter("battleId");
		long battleId = Long.valueOf(bid);

		BattleManager.cancelChallenge(battleId);
		
		http.sendRedirect("/");
	}

}