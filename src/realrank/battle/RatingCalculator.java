package realrank.battle;

import realrank.objects.Score;
import realrank.objects.User;

public class RatingCalculator {
	final static int NEWBIE_K = 30;
	final static int MASTER_K = 10;
	final static int NORMAL_K = 15;
	final static float WIN = 1f;
	final static float DRAW = 0.5f;
	final static float LOSE = 0f;
	
	public static int getWinnerRating(User winner, Score win, Score lose) {
		return getRating(winner, win, lose, WIN);
	}
	
	public static int getLoserRating(User loser, Score lose, Score win) {
		return getRating(loser, lose, win, LOSE);
	}
	
	public static int getDrawRating(User user, Score own, Score counter) {
		return getRating(user, own, counter, DRAW);
	}
	
	private static int getRating(User user, Score own, Score counter, float score) {
		int k = getKFactor(user, own);
		double e = getExpectedWinRate(own, counter);
		int rating = (int)(own.getScore() + k*(score - e));
		return (rating < 0)?0:rating;
	}
	
	private static int getKFactor(User winner, Score win) {
		if (winner.getGames() <= 30)
			return NEWBIE_K;
		if (win.getScore() <= 2400)
			return NORMAL_K;
		return MASTER_K;
	}
	
	private static double getExpectedWinRate(Score win, Score lose) {
		return ((double)1)/(1 + Math.pow(10, ((lose.getScore() - win.getScore())/400)));
	}
}
