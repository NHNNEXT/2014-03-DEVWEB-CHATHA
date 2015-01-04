package realrank.battle;

import org.junit.Test;
import org.mockito.Mockito;

import realrank.controller.BattleController;
import realrank.objects.Battle;
import realrank.objects.User;
import realrank.support.Result;
import easymapping.mapping.Http;
import easymapping.response.Response;

public class SendBattleTest {
	BattleController con = new BattleController();
	Http http = Mockito.mock(Http.class);

	
	@Test
	public void sendTest(){
		User user = new User();
		user.setId("champ");
		
		User user2 = new User();
		user2.setId("chan");
		
		Mockito.when(http.getJsonObject(User.class,"champId")).thenReturn(user2);
		Mockito.when(http.getSessionAttribute(User.class,  "user")).thenReturn(user);
		
		System.out.println(con.battleRequest(http));
	}
}
