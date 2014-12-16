package realrank.user;

import java.util.ArrayList;

import realrank.dao.DAO;
import realrank.score.ScoreDAO;

public class UserDAO {

	public User getUser(String userId) {
		DAO dao = new DAO();
		dao.setSql("select * from user where id=?");
		dao.addParameter(userId);
		dao.setResultSize(6);
		ArrayList<Object> result = dao.getRecord();
		if (result.size() == 0)
			return null;
		
		User user = new User((String) result.get(0), (String) result.get(1),
			(String) result.get(2), (String) result.get(3), result.get(4)
			.toString(), dao.parseDate(result.get(5)));
		
		user.setScore(new ScoreDAO().getScore(userId));
		return user;
	}

	public boolean addUser(User user) {
		DAO addUserTable = new DAO();
		addUserTable.setSql("insert into user values(?,?,?,?,?,?)");
		addUserTable.addParameter(user.getUserId());
		addUserTable.addParameter(user.getEmail());
		addUserTable.addParameter(user.getPassword());
		addUserTable.addParameter(user.getNickname());
		addUserTable.addParameter(user.getGender());
		addUserTable.addParameter(user.getBirthday());

		DAO addScoreTable = new DAO();
		addScoreTable.setSql("insert into score values(?,100)");
		addScoreTable.addParameter(user.getUserId());
		
		return addUserTable.doQuery() && addScoreTable.doQuery();
	}
}
