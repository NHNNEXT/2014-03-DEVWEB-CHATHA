package realrank.controller;

import realrank.battle.RatingCalculator;
import realrank.objects.Score;
import realrank.objects.User;
import realrank.support.Notification;
import easyjdbc.dao.DBMethods;
import easymapping.annotation.Controller;
import easymapping.annotation.Get;
import easymapping.mapping.Http;

@Controller
public class ScoreController {

	@Get("/winner/{}.rk")
	public void scoreRaise(Http http){
		User loser = http.getSessionAttribute(User.class, "user");
		String winnerId = http.getUriVariable(0);
		if ( loser == null) {
			http.sendRedirect("/support/signin");
			return;
		}
		if(loser.getId().equals(winnerId)){
			http.sendRedirect("/support/userinfo");
			return;
		}
		User winner = DBMethods.get(User.class, winnerId);
		Score winnerScore = DBMethods.get(Score.class, winnerId);
		Score loserScore = DBMethods.get(Score.class, loser.getId());
		
		int gain = RatingCalculator.getWinnerRating(winner, winnerScore, loserScore);
		int lose =RatingCalculator.getLoserRating(winner, winnerScore, loserScore);
		
		winnerScore.add(gain);
		loserScore.add(-lose);
		
		DBMethods.update(winnerScore);
		DBMethods.update(loserScore);
		System.out.println("loser: "+ loser.getId());
		System.out.println("winner: "+ winnerId);
		Notification.sendVictoryMsg(loser.getId(), winnerId);
		Notification.sendDefeatMsg(winnerId, loser.getId());
		
		http.sendRedirect("/support/userinfo");
	}
}
