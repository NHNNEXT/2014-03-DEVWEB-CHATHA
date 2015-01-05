package realrank.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import realrank.battle.BattleManager;
import realrank.objects.Battle;
import realrank.objects.BattleInfo;
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
		System.out.println(sendTo);
		if (sendTo == null)
			return new Json(new Result(false, "유효하지 않은 접근입니다."));
		
		QueryExecuter qe = new QueryExecuter();
		User fromDB = qe.get(User.class, sendTo);

		if (fromDB == null){
			qe.close();
			return new Json(new Result(false, "없는 아이디입니다."));
		}
		
		
		System.out.println(fromDB.getId());
		System.out.println(uid);
		if (fromDB.getId().equals(uid)){
			qe.close();
			return new Json(new Result(false, "자신에게 대결을 신청할 수는 없습니다."));
		}
		

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
		jsp.put("user", user.toJson());
		QueryExecuter qe = new QueryExecuter();
		jsp.put("score", user.getScoreJson(qe));
		qe.close();
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
		jsp.put("user", user.toJson());
		QueryExecuter qe = new QueryExecuter();
		jsp.put("score", user.getScoreJson(qe));
		qe.close();
		return jsp;
	}
	


	
	
	
	
	
	@Get("/battle/battle_list.json")
	public Response getBattleListData(Http http){
		User user = http.getSessionAttribute(User.class, "user");
		if (user == null) {
			http.sendError(400, "Bad Request : Not logged on");
			return null;
		}
		
		QueryExecuter qe = new QueryExecuter();
		BattleManager.updateAcceptTimeout(qe, user.getId());
		List<BattleInfo> sentList = BattleManager.getSentChallenges(qe, user.getId(), BattleManager.STATE_NEW);
		List<BattleInfo> receivedList = BattleManager.getReceivedChallenges(qe, user.getId(), BattleManager.STATE_NEW);
		List<BattleInfo> acceptedList = BattleManager.getSentChallenges(qe, user.getId(), BattleManager.STATE_ACCEPTED);
		acceptedList.addAll(BattleManager.getReceivedChallenges(qe, user.getId(), BattleManager.STATE_ACCEPTED));
		qe.close();
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
	public Response acceptChallenge(Http http){
		String recpId = http.getSessionAttribute(User.class,  "user").getId();
		String bid = http.getParameter("battleId");
		long battleId = Long.valueOf(bid);
		String chalId = http.getParameter("challengerId");
			QueryExecuter qe = new QueryExecuter();
		if(!BattleManager.acceptChallenge(qe, battleId)){
			return new Json(new Result(false, "오류가 발생했습니다. 다시 시도해주세요. "));
		}
		qe.close();
		Notification.sendChallegeAcceptedAlert(recpId, chalId);	
		
		return new Json(new Result(true, null));
	}
	
	@Post("/battle/battle_deny.rk")
	public Response denyChallenge(Http http){
		String recpId = http.getSessionAttribute(User.class,  "user").getId();
		String bid = http.getParameter("battleId");
		long battleId = Long.valueOf(bid);
		String chalId = http.getParameter("challengerId");

		if(!BattleManager.denyChallenge(battleId)){
			return new Json(new Result(false, "오류가 발생했습니다. 다시 시도해주세요. "));
		}
		Notification.sendChallegeDeniedAlert(recpId, chalId);
		return new Json(new Result(true, null));
	}
	
	@Post("/battle/battle_cancel.rk")
	public Response cancelChallenge(Http http){
		String bid = http.getParameter("battleId");
		long battleId = Long.valueOf(bid);

		if(!BattleManager.cancelChallenge(battleId)){
			return new Json(new Result(false, "오류가 발생했습니다. 다시 시도해주세요. "));
		}
		return new Json(new Result(true,null));
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
		Jsp jsp = new Jsp("battle.jsp");
		Gson gson = new Gson();
		jsp.put("user", user.toJson());
		jsp.put("score", user.getScoreJson(qe));
		qe.close();
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
		jsp.put("user", user.toJson());
		jsp.put("score", user.getScoreJson(qe));
		qe.close();
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
			http.sendRedirect("/battle/battle_list.rk");
			return null;
		}
		
		QueryExecuter qe = new QueryExecuter();
	
		Battle battle = qe.get(Battle.class, battleId);
		User winner = qe.get(User.class, winnerId);
		finishBattle(qe, battle, loser, winner);

		qe.close();
		
		Notification.sendBattleResult(winnerId, loser.getId());

		return new Json(new Result(true, "패배하셨습니다. 클릭하시면 마이페이지로 이동합니다."));
		
	}

	@Get("/{}.winner")
	public Response setSimpleBattleResult(Http http){
		User loser = http.getSessionAttribute(User.class, "user");
		String winnerId = http.getUriVariable(0);
		if ( loser == null) {
			http.sendRedirect("/users/login.rk?redirect=/winner/"+winnerId+".rk");
			return null;
		}
		Jsp jsp = new Jsp("battle_result_alert.jsp");
		if(loser.getId().equals(winnerId)){
			jsp.put("alert", "자기 자신과는 대결할 수 없습니다. Challenge List로 이동합니다.");
			jsp.put("redirect", "/battle/battle_list.rk");
			return jsp;
		}
		
		QueryExecuter qe = new QueryExecuter();
		
		Battle battle = BattleManager.createBattle(loser.getId(), winnerId, BattleManager.STATE_NEW);
		User winner = qe.get(User.class, winnerId);
		finishBattle(qe, battle, loser, winner);
		
		qe.close();
		
		Notification.sendBattleResult(winnerId, loser.getId());

		jsp.put("alert", "패배하셨습니다. 클릭하시면 Challenge List로 이동합니다.");
		jsp.put("redirect", "/battle/battle_list.rk");
		return jsp;	
	}

	public void drawBattleTimeout(String userId) {
		QueryExecuter qe = new QueryExecuter();
		List<Battle> battles = qe.getList(Battle.class, "(challenger=? OR champion=?) AND state=? AND TIMEDIFF(NOW(), acc_time) > '24:00:00'", userId, userId, BattleManager.STATE_ACCEPTED);

		battles.forEach(battle -> {
			drawBattle(qe, battle);
		});

		qe.close();
	}
	
	private void drawBattle(QueryExecuter qe, Battle battle) {
		User chal = qe.get(User.class, battle.getChallenger());
		User champ = qe.get(User.class, battle.getChampion());

		new ScoreController().setDraw(qe, chal, champ);
		
		UserController userControllser = new UserController();
		userControllser.increaseGameCount(qe, chal);
		userControllser.increaseGameCount(qe, champ);
		
		BattleManager.drawChallenge(battle.getId());
	}

	private void finishBattle(QueryExecuter qe, Battle battle, User loser, User winner) {
		new ScoreController().setBattleResult(qe, loser, winner);
		
		UserController userControllser = new UserController();
		userControllser.increaseGameCount(qe, loser);
		userControllser.increaseGameCount(qe, winner);
		qe.close();
		BattleManager.finishChallenge(winner.getId(), battle.getId());
	}
}