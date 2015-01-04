package realrank.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import realrank.objects.Score;
import realrank.objects.User;

public class ScoreControllerTest {

	@Test
	public void testCalculateDrawOnSameScore() {
		final int GAMES = 10;
		final int SCORE = 1000;

		User challenger = new User();
		User champion = new User();
		Score challengerScore = new Score();
		Score championScore = new Score();
		
		challenger.setGames(GAMES);
		champion.setGames(GAMES);
		challengerScore.setScore(new Integer(SCORE));
		championScore.setScore(new Integer(SCORE));
		
		new ScoreController().calculateDraw(challenger, champion, challengerScore, championScore);
		
		assertEquals(SCORE, (int)challengerScore.getScore());
		assertEquals(SCORE, (int)championScore.getScore());
	}
	
	@Test
	public void testCalculateDrawOnLowerScoreChallenger() {
		final int GAMES = 10;
		final int LOW_SCORE = 800;
		final int HIGH_SCORE = 1200;

		User challenger = new User();
		User champion = new User();
		Score challengerScore = new Score();
		Score championScore = new Score();
		
		challenger.setGames(GAMES);
		champion.setGames(GAMES);
		challengerScore.setScore(new Integer(LOW_SCORE));
		championScore.setScore(new Integer(HIGH_SCORE));
		
		new ScoreController().calculateDraw(challenger, champion, challengerScore, championScore);
		
		assertTrue(LOW_SCORE < (int)challengerScore.getScore());
		assertTrue((int)challengerScore.getScore() < (int)championScore.getScore());
		assertTrue((int)championScore.getScore() < HIGH_SCORE);
	}
	
	@Test
	public void testCalculateDrawOnHigherScoreChallenger() {
		final int GAMES = 10;
		final int LOW_SCORE = 800;
		final int HIGH_SCORE = 1200;

		User challenger = new User();
		User champion = new User();
		Score challengerScore = new Score();
		Score championScore = new Score();
		
		challenger.setGames(GAMES);
		champion.setGames(GAMES);
		challengerScore.setScore(new Integer(HIGH_SCORE));
		championScore.setScore(new Integer(LOW_SCORE));
		
		new ScoreController().calculateDraw(challenger, champion, challengerScore, championScore);
		
		assertTrue(HIGH_SCORE > (int)challengerScore.getScore());
		assertTrue((int)challengerScore.getScore() > (int)championScore.getScore());
		assertTrue((int)championScore.getScore() > LOW_SCORE);
	}
}
