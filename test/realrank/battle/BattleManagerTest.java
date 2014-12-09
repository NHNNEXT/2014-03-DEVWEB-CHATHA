package realrank.battle;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class BattleManagerTest {

	@Test
	public void testChallengeTo() {
		BattleManager.challengeTo("chal", "champ");
	}

	@Test
	public void testGetAcceptibleChallenges() throws Exception {
		ArrayList<ArrayList<Object>> ret = new ArrayList<ArrayList<Object>>();
		ret = BattleManager.getAcceptibleChallenges("champ");
		System.out.println(ret.toString());
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
