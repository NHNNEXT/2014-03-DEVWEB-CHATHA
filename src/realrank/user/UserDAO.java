package realrank.user;

import realrank.dao.Access;
import realrank.dao.DAO;

public class UserDAO {

	public User getUser(String userId) {
		return (User) Access.get(User.class, userId);
	}

	public boolean addUser(User user) {
		Access.insert(user);

		DAO addScoreTable = new DAO();
		addScoreTable.setSql("insert into score values(?,100)");
		addScoreTable.addParameter(user.getUserId());
		
		return Access.insert(user) && addScoreTable.doQuery();
	}
}
