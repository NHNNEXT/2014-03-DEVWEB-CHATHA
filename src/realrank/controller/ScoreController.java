package realrank.controller;

import realrank.objects.Score;
import realrank.objects.User;
import easyjdbc.dao.DBMethods;
import easymapping.annotation.Controller;
import easymapping.annotation.Get;
import easymapping.mapping.Http;

@Controller
public class ScoreController {

	@Get("/winner/{}.rk")
	public void scoreRaise(Http http){
		User loser = http.getSessionAttribute(User.class, "user");
		String winnerId = http.getUriVariable(0);
		if ( loser == null) {
			http.sendRedirect("/support/signin");
			return;
		}
		if(loser.getId().equals(winnerId)){
			http.sendRedirect("/support/userinfo");
			return;
		}
		Score winnerscore = DBMethods.get(Score.class, winnerId);
		Score loserscore = DBMethods.get(Score.class, loser.getId());
		winnerscore.add(10);
		loserscore.add(-10);
		DBMethods.update(winnerscore);
		DBMethods.update(loserscore);
		http.sendRedirect("/support/userinfo");
	}
}
