package realrank.battle;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import easyjdbc.dao.DBMethods;
import realrank.objects.Battle;

public class BattleManagerTest {

	@Test
	public void testChallengeTo() {
		BattleManager.challengeTo("champ", "chal");
	}

	@Test
	public void testGetChallengeList() throws Exception {
		List<Battle> rc = BattleManager.getReceivedChallenges("champ");
		List<Battle> ac = BattleManager.getAcceptedChallenges("champ");
		List<Battle> sc = BattleManager.getSentChallenges("champ");
		System.out.println(rc);
		System.out.println(ac);
		System.out.println(sc);
		Date reqTime = DBMethods.get(Battle.class, 1, "state <> -1").getReq_time();
		System.out.println(reqTime);
	}
	
	@Test
	public void testAcceptChallenge() throws Exception {
		BattleManager.acceptChallenge(71);
		BattleManager.acceptChallenge(72);
		BattleManager.acceptChallenge(73);
		BattleManager.acceptChallenge(74);
		BattleManager.acceptChallenge(75);
		BattleManager.acceptChallenge(76);
		BattleManager.acceptChallenge(77);
		assertTrue(true);
	}
	
	@Test
	public void testDenyChallenge() throws Exception {
		BattleManager.denyChallenge(74);
	}
	
	@Test
	public void testShowAcceptedChallenge() throws Exception {
		System.out.println(BattleManager.showAcceptedChallenges("champ"));
	}
}
