package realrank.user;

import java.util.List;

import easyjdbc.query.QueryExecuter;
import realrank.objects.User;


public class UserManager {
	public static List<User> getUserByKeyword(String keyword) {
		QueryExecuter qe = new QueryExecuter();
		List<User> userList = qe.getList(User.class, "id like ? OR email like ? OR nickname like ?", "%"+keyword+"%", "%"+keyword+"%", "%"+keyword+"%");
		qe.close();
		return userList;
	}
}
