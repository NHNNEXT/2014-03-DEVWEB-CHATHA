package realrank.controller;

import realrank.battle.RatingCalculator;
import realrank.objects.Score;
import realrank.objects.User;
import easyjdbc.query.QueryExecuter;
import easymapping.annotation.Controller;
import easymapping.mapping.Http;

@Controller
public class ScoreController {


	
	public void setDraw(Http http, User challenger, User champion) {
		QueryExecuter qe = new QueryExecuter();
		
		Score challengerScore = qe.get(Score.class, challenger.getId());
		Score championScore = qe.get(Score.class, champion.getId());

		calculateDraw(challenger, champion, challengerScore, championScore);
		
		qe.update(challengerScore);
		qe.update(championScore);

		qe.close();
	}

	void calculateDraw(User challenger, User champion, Score challengerScore, Score championScore) {
		int challengerGain = RatingCalculator.getDrawRating(challenger, challengerScore, championScore);
		int championGain = RatingCalculator.getDrawRating(champion, championScore, challengerScore);
		
		challengerScore.setScore(challengerGain);
		championScore.setScore(championGain);
	}
	
	void setBattleResult(QueryExecuter qe, User loser, String winnerId){
		User winner = qe.get(User.class, winnerId);
		Score winnerScore = qe.get(Score.class, winnerId);
		Score loserScore = qe.get(Score.class, loser.getId());
		
		calculateScore(winner, loser, winnerScore, loserScore);

		qe.update(winnerScore);
		qe.update(loserScore);

	}

	
	void calculateScore(User winner, User loser, Score winnerScore, Score loserScore){
		
		int gain = RatingCalculator.getWinnerRating(winner, winnerScore, loserScore);
		int lose = RatingCalculator.getLoserRating(loser, loserScore, winnerScore);
		
		winnerScore.setScore(gain);
		loserScore.setScore(lose);
	}
	
	
	
	
	
	
}
