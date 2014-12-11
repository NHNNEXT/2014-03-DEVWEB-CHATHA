package realrank.battle;

import java.util.ArrayList;
import java.util.Date;

import realrank.support.DAO;

class BattleManager {
	private final static int STATE_ACCEPTED	= 1;
	private final static int STATE_OUTDATED	= 2;
	private final static int STATE_CANCELED	= 3;
	private final static int STATE_DENIED	= 4;

	static boolean challengeTo(String userId, String champId) {
		DAO dao = new DAO();
		dao.setSql("insert into battle (challenger, champion, req_time, state) values(?,?, now(),?)");
		dao.addParameters(userId);
		dao.addParameters(champId);
		dao.addParameters(0);
		return dao.executeQuery();
	}

	static ArrayList<ArrayList<Object>> getAcceptibleChallenges(String userId) {
		DAO dao = new DAO();
		// 0 means acceptable and validated
		dao.setSql("select * from battle where champion = ? and state = 0");
		dao.addParameters(userId);
		dao.setResultSetLength(7);
		return maskUnacceptibleChallenges(dao.getRecords());
	}

	private static ArrayList<ArrayList<Object>> maskUnacceptibleChallenges(
			ArrayList<ArrayList<Object>> challengeList) {
		ArrayList<Object> removeObject = new ArrayList<Object>();
		ArrayList<Object> errorChallenges = new ArrayList<Object>();
		challengeList.forEach(curChallenge -> {
			Date reqTime = (Date) (curChallenge.get(3));
			if (!determineTimeValidity(reqTime)) {
				if (!setState((long) (curChallenge.get(0)), STATE_OUTDATED)) {
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
		dao.setSql("select req_time from battle where id = ? and state <> -1");
		dao.addParameters(battleId);
		dao.setResultSetLength(1);
		reqTime = (Date) dao.getRecord().get(0);
		if (determineTimeValidity(reqTime)) {
			return setState(battleId, STATE_ACCEPTED);
		}
		setState(battleId, STATE_OUTDATED);
		return false;
	}

	static boolean denyChallenge(long battleId) {
		return setState(battleId, STATE_DENIED);
	}

	static boolean setState(long battleId, int state) {
		DAO dao = new DAO();
		dao.setSql("update battle set state = ? where id = ?");
		dao.addParameters(state);
		dao.addParameters(battleId);
		return dao.executeQuery();
	}

	static Date getServerTime() {
		DAO dao = new DAO();
		dao.setSql("select now()");
		dao.setResultSetLength(1);
		return (Date) (dao.getRecord().get(0));
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
		// 챔피언이랑 챌린저랑 같은이유 ..?
		dao.setSql("select * from battle where (challenger = ? or champion = ?) and state = 1");
		dao.setResultSetLength(7);
		dao.addParameters(userId);
		dao.addParameters(userId);
		return dao.getRecords();
	}

	public static String makeLink(String uid) {
		return "<a href=#> "+ uid + " </a>";
	}
}
