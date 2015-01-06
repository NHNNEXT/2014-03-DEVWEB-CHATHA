package realrank.battle;

import static org.junit.Assert.*;

import org.junit.Test;

import easyjdbc.query.QueryExecuter;
import realrank.objects.Battle;

public class BattleManagerTest {

	@Test
	public void test() {
		QueryExecuter qe = new QueryExecuter();
		Battle battle = qe.get(Battle.class, "2");
		System.out.println(battle);
	}

}
