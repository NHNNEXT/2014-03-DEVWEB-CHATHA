package realrank.support;

import org.junit.Test;

public class SendMailSSLTest {
	MessageMod testMsg = new MessageMod("test@test.com", "unitimes@naver.com", "test", "test contests  <a href='http://www.naver.com'>naver</a>");
	
	@Test
	public void testSendMail() {
		SendMailSSL.sendTo(testMsg);
	}

}
