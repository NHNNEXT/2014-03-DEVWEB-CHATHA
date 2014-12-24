package realrank.battle;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import realrank.objects.Battle;

public class BattleManagerTest {

	@Test
	public void testChallengeTo() {
		BattleManager.challengeTo("champ", "chal");
	}

	@Test
	public void testGetAcceptibleChallenges() throws Exception {
		List<Battle> ret = BattleManager.getReceivedChallenges("champ");
		ret.forEach(battle -> {
			System.out.println(battle.toString());
		});
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
