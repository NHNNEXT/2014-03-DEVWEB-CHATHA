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

		BattleManager.createBattle(uid, sendTo, BattleManager.STATE_NEW);
		Notification.sendChallegeAlert(uid, sendTo);
		
		return new Json(new Result(true, null));
	}
	
	@Get("/battle/battle_send.rk")
	public Response getBattleRequestForm(Http http){
		User user = http.getSessionAttribute(User.class, "user");
		if (user == null) {
			http.sendRedirect("/users/login.rk?redirect=/battle/battle_send.rk");
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
			http.sendRedirect("/users/login.rk?redirect=/battle/battle_list.rk");
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
		
		BattleManager.createBattle(uid, cid, BattleManager.STATE_NEW);
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
	
	@Get("/battle/battle_start.rk")
	public Response getBattle(Http http){
		User user = http.getSessionAttribute(User.class, "user");
		if (user == null) {
			http.sendRedirect("/users/login.rk?redirect=/battle/battle_list.rk");
			return null;
		}
		QueryExecuter qe = new QueryExecuter();
		Battle battle = qe.get(Battle.class, http.getParameter("bid"));
		qe.close();
		
		Jsp jsp = new Jsp("battle.jsp");
		Gson gson = new Gson();
		jsp.put("user", gson.toJson(user));
		jsp.put("battle", gson.toJson(battle));
		return jsp;
	}
	
	@Post("/battle/battle_start.rk")
	public Response startChallenge(Http http){
		User user = http.getSessionAttribute(User.class, "user");
		
		QueryExecuter qe = new QueryExecuter();
		Battle battle = qe.get(Battle.class, http.getParameter("battleId"));
		qe.close();
		
		Jsp jsp = new Jsp("battle.jsp");
		Gson gson = new Gson();
		jsp.put("user", gson.toJson(user));
		jsp.put("battle", gson.toJson(battle));
		return jsp;
	}
	
	@Post("/battle_end.rk")
	public Response endChallenge(Http http){
		User loser = http.getSessionAttribute(User.class, "user");
		long battleId = Long.valueOf(http.getParameter("battle_id"));
		String winnerId = http.getParameter("winner_id");
		
		if ( loser == null) {
			http.sendRedirect("/users/login.rk");
			return null;
		}
		if(loser.getId().equals(winnerId)){
			http.sendRedirect("/users/userinfo.rk");
			return null;
		}
		
		QueryExecuter qe = new QueryExecuter();
	
		Battle battle = qe.get(Battle.class, battleId);
		User winner = qe.get(User.class, winnerId);
		finishBattle(qe, battle, loser, winner);

		qe.close();

		return new Json(new Result(true, "패배하셨습니다. 클릭하시면 마이페이지로 이동합니다."));
		
	}

	@Get("/winner/{}.rk")
	public void setSimpleBattleResult(Http http){
		User loser = http.getSessionAttribute(User.class, "user");
		String winnerId = http.getUriVariable(0);
		if ( loser == null) {
			http.sendRedirect("/users/login.rk");
			return;
		}
		if(loser.getId().equals(winnerId)){
			http.sendRedirect("/users/userinfo.rk");
			return;
		}
		
		QueryExecuter qe = new QueryExecuter();
		
		Battle battle = BattleManager.createBattle(loser.getId(), winnerId, BattleManager.STATE_NEW);
		User winner = qe.get(User.class, winnerId);
		finishBattle(qe, battle, loser, winner);
		
		qe.close();
		
		Notification.sendBattleResult(winnerId, loser.getId());
		
		http.sendRedirect("/users/userinfo.rk");
	}

	private void finishBattle(QueryExecuter qe, Battle battle, User loser, User winner) {
		new ScoreController().setBattleResult(qe, loser, winner);
		
		UserController userControllser = new UserController();
		userControllser.increaseGameCount(qe, loser);
		userControllser.increaseGameCount(qe, winner);
	}
}