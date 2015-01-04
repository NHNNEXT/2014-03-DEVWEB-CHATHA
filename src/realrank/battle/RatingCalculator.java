package realrank.battle;

import realrank.objects.Rank;
import realrank.objects.User;

public class RatingCalculator {
	final static int NEWBIE_K = 30;
	final static int MASTER_K = 10;
	final static int NORMAL_K = 15;
	final static float WIN = 1f;
	final static float DRAW = 0.5f;
	final static float LOSE = 0f;
	
	public static int getWinnerRating(User winner, Rank win, Rank lose) {
		return getRating(winner, win, lose, WIN);
	}
	
	public static int getLoserRating(User loser, Rank lose, Rank win) {
		return getRating(loser, lose, win, LOSE);
	}
	
	public static int getDrawRating(User user, Rank own, Rank counter) {
		return getRating(user, own, counter, DRAW);
	}
	
	private static int getRating(User user, Rank own, Rank counter, float score) {
		int k = getKFactor(user, own);
		double e = getExpectedWinRate(own, counter);
		return (int)(own.getScore() + k*(score - e));
	}
	
	private static int getKFactor(User winner, Rank win) {
		if (winner.getGames() <= 30)
			return NEWBIE_K;
		if (win.getScore() <= 2400)
			return NORMAL_K;
		return MASTER_K;
	}
	
	private static double getExpectedWinRate(Rank win, Rank lose) {
		return ((double)1)/(1 + Math.pow(10, ((lose.getScore() - win.getScore())/400)));
	}
}
