package realrank.battle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import realrank.objects.Battle;
import easyjdbc.dao.DAO;
import easyjdbc.dao.DBMethods;

public class BattleManager {
	public final static int STATE_NEW = 0;
	public final static int STATE_ACCEPTED = 1;
	public final static int STATE_OUTDATED = 2;
	public final static int STATE_CANCELED = 3;
	public final static int STATE_DENIED = 4;

	public static boolean challengeTo(String userId, String champId) {
		Battle battle = new Battle();
		battle.setChallenger(userId);
		battle.setChampion(champId);
		battle.setReq_time(new Date());
		battle.setState(BattleManager.STATE_NEW);
		return DBMethods.insert(battle);
	}

	public static List<Battle> getSentChallenges(String userId) {
		return DBMethods.getList(Battle.class, "challenger = '" + userId + "' and state = " + STATE_NEW);
	}

	public static List<Battle> getReceivedChallenges(String userId) {
		return maskUnacceptibleChallenges(DBMethods.getList(Battle.class, "champion = '" + userId + "' and state = " + STATE_NEW));
	}

	public static List<Battle> getAcceptedChallenges(String userId) {
		return DBMethods.getList(Battle.class, "(challenger = '" + userId + "' or champion = '" + userId + "') and state = " + STATE_ACCEPTED);
	}

	private static List<Battle> maskUnacceptibleChallenges(List<Battle> challengeList) {
		ArrayList<Battle> removeObject = new ArrayList<Battle>();
		ArrayList<Battle> errorChallenges = new ArrayList<Battle>();

		challengeList.forEach(curChallenge -> {
			Date reqTime = curChallenge.getReq_time();
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

	private static void handleErrorChallenges(List<Battle> errorChallenges) {

	}

	static boolean acceptChallenge(long battleId) {
		Date reqTime = DBMethods.get(Battle.class, battleId, "state <> -1").getReq_time();
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
		Battle battle = new Battle();
		battle.setId((int) battleId);
		battle.setState(state);
		return DBMethods.update(battle);
	}

	static Date getServerTime() {
		DAO dao = new DAO();
		dao.setSql("select now()");
		dao.setResultSize(1);
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
		dao.setResultSize(7);
		dao.addParameter(userId);
		dao.addParameter(userId);
		return dao.getRecords();
	}

	public static String makeLink(String uid) {
		return "<a href=#> " + uid + " </a>";
	}
}
