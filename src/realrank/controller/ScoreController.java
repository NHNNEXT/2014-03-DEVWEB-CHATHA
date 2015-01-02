package realrank.controller;

import realrank.battle.RatingCalculator;
import realrank.objects.Score;
import realrank.objects.User;
import realrank.support.Notification;
import easyjdbc.query.QueryExecuter;
import easymapping.annotation.Controller;
import easymapping.annotation.Get;
import easymapping.mapping.Http;

@Controller
public class ScoreController {

	@Get("/winner/{}.rk")
	public void setSimpleBattleResult(Http http){
		User loser = http.getSessionAttribute(User.class, "user");
		String winnerId = http.getUriVariable(0);
		setBattleResult(http, loser, winnerId);
	}
	
	//TODO 방식과 URL을 편한대로 지정해주세요! 
	public void setNormalBattleResult(Http http){
		User loser = http.getSessionAttribute(User.class, "user");
		String winnerId = http.getParameter("cid");
		setBattleResult(http, loser, winnerId);

	}
	
	
	private void setBattleResult(Http http, User loser, String winnerId){
		if ( loser == null) {
			http.sendRedirect("/users/login.rk");
			return;
		}
		if(loser.getId().equals(winnerId)){
			http.sendRedirect("/users/userinfo.rk");
			return;
		}
		QueryExecuter qe = new QueryExecuter();
		User winner = qe.get(User.class, winnerId);
		Score winnerScore = qe.get(Score.class, winnerId);
		Score loserScore = qe.get(Score.class, loser.getId());
		
		setCalculatedScore(winner, winnerScore, loserScore);
		
		qe.update(winnerScore);
		qe.update(loserScore);
		
		qe.close();
		
		Notification.sendBattleResult(winnerId, loser.getId());
		
		http.sendRedirect("/users/userinfo.rk");
	}

	
	private void setCalculatedScore(User winner, Score winnerScore, Score loserScore){
		
		int gain = RatingCalculator.getWinnerRating(winner, winnerScore, loserScore);
		int lose = RatingCalculator.getLoserRating(winner, winnerScore, loserScore);
		
		winnerScore.add(gain);
		loserScore.add(-lose);
	}
	
	
	
	
	
	
}
