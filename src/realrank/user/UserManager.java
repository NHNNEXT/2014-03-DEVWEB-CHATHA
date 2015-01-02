package realrank.user;

import java.util.List;

import realrank.objects.User;
import easyjdbc.dao.DBMethods;

public class UserManager {
	public static List<User> getUserByIdOrEmail(String query) {
		return DBMethods.getList(User.class, "id like ? or email like ?", query+"%", query+"%");
	}
}
