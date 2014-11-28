package realrank.user;

import java.util.ArrayList;

import realrank.support.DAO;

public class UserDAO {
	DAO dao = new DAO();
	ArrayList<Object> parameters = new ArrayList<Object>();
	ArrayList<Object> result;
	public User getUser(String userId) {

		String sql = "select * from user where id=?";
		
		parameters.add(userId);
//		Changed because the return type's change of selectQuery
		result = (dao.selectQuery(sql, parameters, 6)).get(0);
		return new User((String) result.get(0), (String) result.get(1),
				(String) result.get(2), (String) result.get(3), result.get(4)
						.toString(), dao.parseDate(result.get(5)));

	}

	public boolean addUser(User user) {
		String sql = "insert into user values(?,?,?,?,?,?)";
		parameters.add(user.getUserId());
		parameters.add(user.getEmail());
		parameters.add(user.getPassword());
		parameters.add(user.getNickname());
		parameters.add(user.getGender());
		parameters.add(user.getBirthday());
		return dao.executeQuery(sql, parameters);

	}
}
