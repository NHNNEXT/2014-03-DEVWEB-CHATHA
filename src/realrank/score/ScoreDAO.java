package realrank.score;

import java.util.ArrayList;

import realrank.support.DAO;

public class ScoreDAO {
	public int getScore(String userId) {
		DAO dao = new DAO();
		dao.setSql("select * from score where id=?");
		dao.addParameters(userId);
		dao.setResultSetLength(2);
		ArrayList<Object> result = dao.getRecord();
		if (result.size() == 0)
			return 0;
		return (int) result.get(1);
	}

	public boolean urlWinner(String winner, String loser) {
		return raiseScore(winner, 10) && reduceScore(loser, 10);
	}
	
	public boolean raiseScore(String userId, int amount){
		DAO dao = new DAO();
		dao.setSql("update score set score= score + ? where id=?");
		dao.addParameters(amount);
		dao.addParameters(userId);
		return dao.executeQuery();
	}
	
	public boolean reduceScore(String userId, int amount){
		DAO dao = new DAO();
		dao.setSql("update score set score= score - ? where id=?");
		dao.addParameters(amount);
		dao.addParameters(userId);
		return dao.executeQuery();
	}
	
	
}
