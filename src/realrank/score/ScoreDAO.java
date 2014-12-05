package realrank.score;

import java.util.ArrayList;

import realrank.support.DAO;

public class ScoreDAO {
	public Score getScore(String userId) {
		DAO dao = new DAO();
		dao.setSql("select * from score where id=?");
		dao.addParameters(userId);
		dao.setResultSetLength(2);
		ArrayList<Object> result = dao.getRecord();
		if (result.size() == 0)
			return null;
		return new Score((String) result.get(0), (int) result.get(1));
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
