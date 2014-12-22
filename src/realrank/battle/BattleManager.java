package realrank.battle;

import java.util.ArrayList;
import java.util.Date;

import realrank.support.DAO;

class BattleManager {
	private final static int STATE_NEW		= 0;
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

	static ArrayList<BattleInfo> getSentChallenges(String userId) {
		DAO dao = new DAO();
		dao.setSql("select * from battle where challenger = ? and state = " + STATE_NEW);
		dao.addParameters(userId);
		dao.setResultSetLength(7);

		ArrayList<ArrayList<Object>> queryResults = dao.getRecords();

		ArrayList<BattleInfo> battleList = new ArrayList<BattleInfo>();
		queryResults.forEach(result -> {
			battleList.add(new BattleInfo((long)result.get(0), (String)result.get(1),
					(String)result.get(2), (Date)result.get(3), (Date)result.get(4),
					(Integer)result.get(5), (String)result.get(6)));
		});

		return battleList;
	}

	static ArrayList<BattleInfo> getReceivedChallenges(String userId) {
		DAO dao = new DAO();

		dao.setSql("select * from battle where champion = ? and state = " + STATE_NEW);
		dao.addParameters(userId);
		dao.setResultSetLength(7);

		ArrayList<ArrayList<Object>> queryResults = dao.getRecords();

		ArrayList<BattleInfo> battleList = new ArrayList<BattleInfo>();
		queryResults.forEach(result -> {
			battleList.add(new BattleInfo((long)result.get(0), (String)result.get(1),
					(String)result.get(2), (Date)result.get(3), (Date)result.get(4),
					(Integer)result.get(5), (String)result.get(6)));
		});

		return maskUnacceptibleChallenges(battleList);
	}
	
	static ArrayList<BattleInfo> getAcceptedChallenges(String userId) {
		DAO dao = new DAO();
		dao.setSql("select * from battle where (challenger = ? or champion = ?) and state = " + STATE_ACCEPTED);
		dao.addParameters(userId);
		dao.addParameters(userId);
		dao.setResultSetLength(7);

		ArrayList<ArrayList<Object>> queryResults = dao.getRecords();

		ArrayList<BattleInfo> battleList = new ArrayList<BattleInfo>();
		queryResults.forEach(result -> {
			battleList.add(new BattleInfo((long)result.get(0), (String)result.get(1),
					(String)result.get(2), (Date)result.get(3), (Date)result.get(4),
					(Integer)result.get(5), (String)result.get(6)));
		});

		return battleList;
	}

	private static ArrayList<BattleInfo> maskUnacceptibleChallenges(ArrayList<BattleInfo> challengeList) {
		ArrayList<BattleInfo> removeObject = new ArrayList<BattleInfo>();
		ArrayList<BattleInfo> errorChallenges = new ArrayList<BattleInfo>();
		
		challengeList.forEach(curChallenge -> {
			Date reqTime = curChallenge.getReqTime();
			if (!determineTimeValidity(reqTime)) {
				if (!setState(curChallenge.getId(), STATE_OUTDATED)) {
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

	private static void handleErrorChallenges(ArrayList<BattleInfo> errorChallenges) {
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
