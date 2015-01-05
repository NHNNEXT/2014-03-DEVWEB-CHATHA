package realrank.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import realrank.objects.Score;
import realrank.objects.User;
import realrank.support.Result;
import realrank.user.UserManager;
import easyjdbc.query.ExecuteQuery;
import easyjdbc.query.GetRecordQuery;
import easyjdbc.query.GetRecordsQuery;
import easyjdbc.query.QueryExecuter;
import easymapping.annotation.Controller;
import easymapping.annotation.Get;
import easymapping.annotation.Post;
import easymapping.mapping.Http;
import easymapping.response.Json;
import easymapping.response.Jsp;
import easymapping.response.Response;

@Controller
public class UserController {

	@Get("/users/user_search.json")
	public Response userSearch(Http http) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<User> searchResultList = UserManager.getUsersByKeyword(http.getParameter("keyword"));
		searchResultList.forEach(user -> {
			user.setPassword("");
		});
		result.put("userList", searchResultList);
		return new Json(result);
	}

	@Get("/users/login.rk")
	public Response loginGet(Http http) {
		Jsp jsp = new Jsp("login.jsp");
		return jsp;
	}

	@Get("/users/logout.rk")
	public void logout(Http http) {
		http.removeSessionAttribute("user");
		http.sendRedirect("/");
	}

	@Get("/users/register.rk")
	public Response register(Http http) {
		return new Jsp("register.jsp");
	}

	@Post("/users/login.rk")
	public Response login(Http http) {
		User user = http.getJsonObject(User.class, "user");
		List<Object> result;

		if (user == null)
			return new Json(new Result(false, "유효하지 않은 접근입니다."));
		QueryExecuter qe = new QueryExecuter();
		GetRecordQuery getR = new GetRecordQuery(7,
				"SELECT id, email, AES_DECRYPT(UNHEX(password), ?), nickname, gender, birthday, games FROM user WHERE id=?", user.getPassword(),
				user.getId());
		result = qe.execute(getR);
		if (result.size() <= 0)
			return new Json(new Result(false, "없는 아이디입니다."));
		if (result.get(2) == null)
			return new Json(new Result(false, "패스워드가 다릅니다."));
		User fromDB = new User((ArrayList<Object>) result);
		qe.close();
		
		fromDB.setPassword("");
		http.setSessionAttribute("user", fromDB);

		new BattleController().drawBattleTimeout(fromDB.getId());

		return new Json(new Result(true, null));
	}

	@Post("/users/register.rk")
	public Response signup(Http http) {
		User user = http.getJsonObject(User.class, "user");
		user.setGames(0);
		QueryExecuter qe = new QueryExecuter();
		ExecuteQuery eq = new ExecuteQuery("INSERT INTO user VALUES (?, ?, HEX(AES_ENCRYPT(?, ?)), ?, ?, ?, ?)", user.getId(), user.getEmail(),
				user.getPassword(), user.getPassword(), user.getNickname(), user.getGender(), user.getBirthday(), user.getGames());
		boolean result = qe.execute(eq);
		qe.close();
		if (result) {
			Score score = new Score();
			score.setId(user.getId());
			score.setScore(1500);
			score.setReputation(100);
			qe = new QueryExecuter();
			qe.insert(score);
			qe.close();
			http.setSessionAttribute("user", user);
			return new Json(new Result(true, null));
		}
		return new Json(new Result(false, "회원 가입 실패"));
	}

	@Post("/users/checkid.rk")
	public Response checkId(Http http) {
		QueryExecuter qe = new QueryExecuter();
		User user = qe.get(User.class, http.getParameter("id"));
		qe.close();
		if (user != null) {
			return new Json(new Result(false, null));
		}
		return new Json(new Result(true, null));
	}

	@Get("/users/modify.rk")
	public Response modify(Http http) {
		User user = http.getSessionAttribute(User.class, "user");
		if (user == null) {
			http.sendRedirect("/users/login.rk?redirect=/users/modify.rk");
			return null;
		}
		Jsp jsp = new Jsp("modify.jsp");
		jsp.put("user", user.toJson());
		QueryExecuter qe = new QueryExecuter();
		jsp.put("score", user.getScoreJson(qe));
		qe.close();
		return jsp;
	}

	@Post("/users/modify.rk")
	public Response modifyId(Http http) {
		User user = http.getSessionAttribute(User.class, "user");

		User usermod = http.getJsonObject(User.class, "user");
		//String oldPassword = http.getParameter("oldPassword");
		//usermod.setPassword(oldPassword);
		//if (!user.isPasswordCorrect(usermod))
		//	return new Json(new Result(false, "기존 패스워드가 일치하지 않습니다."));
		user.update(usermod);
		QueryExecuter qe = new QueryExecuter();

		ExecuteQuery eq = new ExecuteQuery("update user set email=?, password=HEX(AES_ENCRYPT(?, ?)), nickname=?, gender=?, birthday=? where id=?",
				user.getEmail(), user.getPassword(), user.getPassword(), user.getNickname(), user.getGender(),
				user.getBirthday(), user.getId());
		boolean result = qe.execute(eq);

		qe.close();
		if (!result) {
			return new Json(new Result(false, null));
		}
		return new Json(new Result(true, null));
	}
	
	@Post("/users/rank.rk")
	public Response getRanks(Http http) {
		System.out.println(http.getParameter("rankType"));
		int rankType = Integer.parseInt(http.getParameter("rankType"));
		String rankTable;
		Gson gson = new Gson();
		List<Score> ranks = new ArrayList<Score>();
		List<List<Object>> rankList;
		switch (rankType) {
		case 2:
			rankTable = "weekly_score";
			break;
		case 3:
			rankTable = "monthly_score";
			break;
		default:
			rankTable = "daily_score";
			break;
		}
		
		String sql = "SELECT * FROM " + rankTable;
		
		QueryExecuter qe = new QueryExecuter();
		GetRecordsQuery qrq = new GetRecordsQuery(4, sql);
		rankList = qe.execute(qrq);
		rankList.forEach((list) -> {
//			System.out.println(list);
			Score score = new Score();
			ArrayList<Object> retScore = (ArrayList<Object>) list;
			score.setRank((int) retScore.get(0));
			score.setId((String) retScore.get(1));
			score.setScore((int) retScore.get(2));
			score.setReputation((int) retScore.get(3));
			ranks.add(score);
		});
		System.out.println(gson.toJson(ranks));
//		System.out.println(gson.toJson(qe.execute(qrq)));
		return new Json(ranks);
	}

	void increaseGameCount(QueryExecuter qe, User user) {
		String sql = "UPDATE user set games=games+1" + " WHERE id='" + user.getId() + "'";
		System.out.println("[DEBUG] " + sql);
		qe.execute(new ExecuteQuery(sql));
	}

}
