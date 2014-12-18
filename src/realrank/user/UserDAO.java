package realrank.user;

import realrank.dao.DBMethods;
import realrank.dao.DAO;

public class UserDAO {

	public User getUser(String userId) {
		return (User) DBMethods.get(User.class, userId);
	}

	public boolean addUser(User user) {
		DBMethods.insert(user);

		DAO addScoreTable = new DAO();
		addScoreTable.setSql("insert into score values(?,100)");
		addScoreTable.addParameter(user.getUserId());
		
		return DBMethods.insert(user) && addScoreTable.doQuery();
	}
}
