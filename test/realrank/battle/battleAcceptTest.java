package realrank.battle;


import org.junit.Test;
import org.mockito.Mockito;

import realrank.controller.BattleController;
import realrank.objects.Battle;
import realrank.objects.User;
import easyjdbc.query.QueryExecuter;
import easyjdbc.query.raw.GetRecordQuery;
import easymapping.mapping.Http;

public class battleAcceptTest {

	BattleController con = new BattleController();
	Http http = Mockito.mock(Http.class);

	
	@Test
	public void acceptTest(){
		Battle battle = new Battle();
		battle.setId(10);
		battle.setChallenger("chal");
		User user = new User();
		user.setId("champ");
		Mockito.when(http.getJsonObject(Battle.class, "battle")).thenReturn(battle);
		Mockito.when(http.getSessionAttribute(User.class,  "user")).thenReturn(user);
		
		con.acceptChallenge(http);
	}
	
	@Test
	public void getTime(){
		GetRecordQuery que = new GetRecordQuery(1, "select now()");
		QueryExecuter qe = new QueryExecuter();
		System.out.println(qe.execute(que));

	}
	@Test
	public void denyTest(){
		Battle battle = new Battle();
		battle.setId(10);
		battle.setChallenger("chal");
		User user = new User();
		user.setId("champ");
		Mockito.when(http.getJsonObject(Battle.class, "battle")).thenReturn(battle);
		Mockito.when(http.getSessionAttribute(User.class,  "user")).thenReturn(user);
		
		con.denyChallenge(http);
	}
	
}
