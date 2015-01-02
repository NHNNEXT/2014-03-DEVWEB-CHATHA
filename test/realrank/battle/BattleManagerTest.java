package realrank.battle;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import realrank.objects.Battle;

public class BattleManagerTest {

	@Test
	public void test() {
		List<Battle> list = BattleManager.getAcceptedChallenges("chal").getList();
		Iterator ir = list.iterator();
		while(ir.hasNext()){
			System.out.println(ir.next());
		}
	}

}
