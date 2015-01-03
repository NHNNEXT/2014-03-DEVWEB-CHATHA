package realrank.user;

import java.util.List;

import easyjdbc.query.QueryExecuter;
import realrank.objects.User;


public class UserManager {
	public static List<User> getUserByIdOrEmail(String query) {
		QueryExecuter qe = new QueryExecuter();
		List<User> userList = qe.getList(User.class, "id like ? or email like ?", "%"+query+"%", "%"+query+"%");
		qe.close();
		return userList;
	}
}
