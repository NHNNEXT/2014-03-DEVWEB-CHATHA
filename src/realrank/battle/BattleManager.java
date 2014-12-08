package realrank.battle;

import java.util.ArrayList;
import java.util.Date;

import realrank.support.DAO;

class BattleManager {
	static boolean challengeTo(String userId, String champId) {
		DAO dao = new DAO();
		ArrayList<Object> parameters = new ArrayList<Object>();
		String sql = "insert into battle (challenger, champion, req_time, state) values(?,?, now(),?)";
		parameters.add(userId);
		parameters.add(champId);
		parameters.add(0);
		return dao.executeQuery(sql, parameters);
	}

	static ArrayList<ArrayList<Object>> getAcceptibleChallenges(String userId) {
		DAO dao = new DAO();
		ArrayList<Object> parameters = new ArrayList<Object>();
		// 0 means acceptable and validated
		String sql = "select * from battle where champion = ? and state = 0";
		parameters.add(userId);
		return maskUnacceptibleChallenges(dao.selectQuery(sql, parameters, 7));
	}

	private static ArrayList<ArrayList<Object>> maskUnacceptibleChallenges(
			ArrayList<ArrayList<Object>> challengeList) {
		ArrayList<Object> removeObject = new ArrayList<Object>();
		ArrayList<Object> errorChallenges = new ArrayList<Object>();
		challengeList.forEach(curChallenge -> {
			Date reqTime = (Date) (curChallenge.get(3));
			if (!determineTimeValidity(reqTime)) {
				if (!setState((long) (curChallenge.get(0)), 2)) {
					errorChallenges.add(curChallenge);
				}
				removeObject.add(curChallenge);
			}
		});
		removeObject.forEach(obj -> {
			challengeList.remove(obj);
		});
		handleErrorChallenges(errorChallenges);
		return challengeList;
	}

	private static void handleErrorChallenges(ArrayList<Object> errorChallenges) {
		// TODO Auto-generated method stub
		
	}

	static boolean acceptChallenge(long battleId) {
		Date reqTime;
		DAO dao = new DAO();
		ArrayList<Object> parameters = new ArrayList<Object>();
		// -1 means canceled.
		String sql = "select req_time from battle where id = ? and state <> -1";
		parameters.add(battleId);
		reqTime = (Date) ((dao.selectQuery(sql, parameters, 1).get(0)).get(0));
		if (determineTimeValidity(reqTime)) {
			// 1 means accepted.
			return setState(battleId, 1);
		}
		setState(battleId, 2);
		return false;
	}

	static boolean denyChallenge(long battleId) {
//		-2 means denied.
		return setState(battleId, -2);
	}
	
	static boolean setState(long battleId, int state) {
		DAO dao = new DAO();
		ArrayList<Object> parameters = new ArrayList<Object>();
		String sql = "update battle set state = ? where id = ?";
		parameters.add(state);
		parameters.add(battleId);
		return dao.executeQuery(sql, parameters);
	}
	
	static Date getServerTime() {
		DAO dao = new DAO();
		String sql = "select now()";
		return (Date) (dao.selectQuery(sql, 1).get(0));
	}

	static boolean determineTimeValidity(Date reqTime) {
		Date currentTime = getServerTime();
		if (currentTime.getTime() - reqTime.getTime() < 180000) {
			return true;
		}
		return false;
	}

	static ArrayList<ArrayList<Object>> showAcceptedChallenges(String userId) {
		DAO dao = new DAO();
		ArrayList<Object> parameters = new ArrayList<Object>();
		String sql = "select * from battle where (challenger = ? or champion = ?) and state = 1";
		parameters.add(userId);
		parameters.add(userId);
		return dao.selectQuery(sql, parameters, 7);
	}
}
