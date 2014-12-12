package realrank.support.mail;

import static org.junit.Assert.*;

import org.junit.Test;

public class MailManagerTest {

	@Test
	public void testSendSimpleBattleMsg() {
		MailManager.sendSimpleBattleMsg("chul", "chul");
	}

}
