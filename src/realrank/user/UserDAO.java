package realrank.user;

import java.util.ArrayList;

import realrank.support.DAO;

public class UserDAO {

	public User getUser(String userId) {
		DAO dao = new DAO();
		dao.setSql("select * from user where id=?");
		dao.addParameters(userId);
		dao.setResultSetLength(6);
		ArrayList<Object> result = dao.getRecord();
		if (result.size() == 0)
			return null;
		return new User((String) result.get(0), (String) result.get(1),
				(String) result.get(2), (String) result.get(3), result.get(4)
						.toString(), dao.parseDate(result.get(5)));
	}

	public boolean addUser(User user) {
		DAO dao = new DAO();
		dao.setSql("insert into user values(?,?,?,?,?,?)");
		dao.addParameters(user.getUserId());
		dao.addParameters(user.getEmail());
		dao.addParameters(user.getPassword());
		dao.addParameters(user.getNickname());
		dao.addParameters(user.getGender());
		dao.addParameters(user.getBirthday());
		return dao.executeQuery();
	}
}
