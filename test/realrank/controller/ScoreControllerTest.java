package realrank.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import realrank.objects.Score;
import realrank.objects.User;

public class ScoreControllerTest {

	private final static int GAMES = 10;
	private final static int LOW_SCORE = 800;
	private final static int MID_SCORE = 1000;
	private final static int HIGH_SCORE = 1200;

	private User challenger = new User();
	private User champion = new User();
	private Score challengerScore = new Score();
	private Score championScore = new Score();
	private ScoreController scoreController = new ScoreController();
	
	@Before
	public void setUp() throws Exception {
		challenger.setGames(GAMES);
		champion.setGames(GAMES);
	}

	@Test
	public void testCalculateDrawOnSameScore() {
		challengerScore.setScore(new Integer(MID_SCORE));
		championScore.setScore(new Integer(MID_SCORE));
		
		scoreController.calculateDraw(challenger, champion, challengerScore, championScore);
		
		assertEquals(MID_SCORE, (int)challengerScore.getScore());
		assertEquals(MID_SCORE, (int)championScore.getScore());
	}
	
	@Test
	public void testCalculateDrawOnLowerScoreChallenger() {
		challengerScore.setScore(new Integer(LOW_SCORE));
		championScore.setScore(new Integer(HIGH_SCORE));
		
		scoreController.calculateDraw(challenger, champion, challengerScore, championScore);
		
		assertTrue(LOW_SCORE < (int)challengerScore.getScore());
		assertTrue((int)challengerScore.getScore() < (int)championScore.getScore());
		assertTrue((int)championScore.getScore() < HIGH_SCORE);
	}
	
	@Test
	public void testCalculateDrawOnHigherScoreChallenger() {
		challengerScore.setScore(new Integer(HIGH_SCORE));
		championScore.setScore(new Integer(LOW_SCORE));
		
		scoreController.calculateDraw(challenger, champion, challengerScore, championScore);
		
		assertTrue(HIGH_SCORE > (int)challengerScore.getScore());
		assertTrue((int)challengerScore.getScore() > (int)championScore.getScore());
		assertTrue((int)championScore.getScore() > LOW_SCORE);
	}

	@Test
	public void testCalculateScoreOnSameScoreChallengerWin() {
		challengerScore.setScore(new Integer(MID_SCORE));
		championScore.setScore(new Integer(MID_SCORE));
		
		scoreController.calculateScore(challenger, champion, challengerScore, championScore);
		
		assertTrue(MID_SCORE < (int)challengerScore.getScore());
		assertTrue(MID_SCORE > (int)championScore.getScore());
	}
	
	@Test
	public void testCalculateScoreOnLowerScoreChallengerWin() {
		challengerScore.setScore(new Integer(LOW_SCORE));
		championScore.setScore(new Integer(HIGH_SCORE));
		
		scoreController.calculateScore(challenger, champion, challengerScore, championScore);
		
		assertTrue(LOW_SCORE < (int)challengerScore.getScore());
		assertTrue((int)challengerScore.getScore() < (int)championScore.getScore());
		assertTrue((int)championScore.getScore() < HIGH_SCORE);
	}
	
	@Test
	public void testCalculateScoreOnHigherScoreChallengerWin() {
		challengerScore.setScore(new Integer(HIGH_SCORE));
		championScore.setScore(new Integer(LOW_SCORE));
		
		scoreController.calculateScore(challenger, champion, challengerScore, championScore);
		
		assertTrue(HIGH_SCORE < (int)challengerScore.getScore());
		assertTrue((int)challengerScore.getScore() > (int)championScore.getScore());
		assertTrue((int)championScore.getScore() < LOW_SCORE);
	}
}
