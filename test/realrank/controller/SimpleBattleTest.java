package realrank.controller;

import org.junit.Test;
import org.mockito.Mockito;

import realrank.objects.User;
import easymapping.mapping.Http;

public class SimpleBattleTest {
	BattleController con = new BattleController();
	Http http = Mockito.mock(Http.class);

	
	@Test
	public void simpleTest(){
		User user = new User();
		user.setId("champ");
		Mockito.when(http.getSessionAttribute(User.class, "user")).thenReturn(user);
		Mockito.when(http.getUriVariable(0)).thenReturn("chal");
		
		con.setSimpleBattleResult(http);
	}
}
