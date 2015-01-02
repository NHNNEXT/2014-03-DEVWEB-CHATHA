package realrank.battle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import easyjdbc.query.GetRecordQuery;
import easyjdbc.query.GetRecordsQuery;
import easyjdbc.query.QueryExecuter;
import realrank.objects.Battle;
import realrank.objects.BattleInfo;

public class BattleManager {
	public final static int STATE_NEW = 0;
	public final static int STATE_ACCEPTED = 1;
	public final static int STATE_OUTDATED = 2;
	public final static int STATE_CANCELED = 3;
	public final static int STATE_DENIED = 4;

	private static String forCondition(String String) {
		return "'" + String + "'";
	}

	public static boolean challengeTo(String userId, String champId) {
		Battle battle = new Battle();
		battle.setChallenger(userId);
		battle.setChampion(champId);
		battle.setReq_time(new Date());
		battle.setState(BattleManager.STATE_NEW);
		QueryExecuter qe = new QueryExecuter();
		int result = qe.insert(battle);
		qe.close();
		return result != 0;
	}

	public static List<BattleInfo> getSentChallenges(String userId, int state) {
		QueryExecuter qe = new QueryExecuter();
		String sql = "SELECT b.*, s.score, s.reputation FROM battle b INNER JOIN score s WHERE b.champion = s.id and " + "challenger = "
				+ forCondition(userId) + " and state = " + state;
		GetRecordsQuery query = new GetRecordsQuery(9, sql);
		@SuppressWarnings("unchecked")
		List<List<Object>> records = (List<List<Object>>) qe.execute(query);
		qe.close();

		List<BattleInfo> list = new ArrayList<BattleInfo>();
		records.forEach(record -> {
			list.add(new BattleInfo((Integer) record.get(0), (String) record.get(1), (String) record.get(2), (Date) record.get(3), (Date) record
					.get(4), (Integer) record.get(5), (String) record.get(6), (Integer) record.get(7), 0));
		});

		return list;
	}

	public static List<BattleInfo> getReceivedChallenges(String userId, int state) {
		String sql = "SELECT b.*, s.score, s.reputation FROM battle b INNER JOIN score s WHERE b.challenger = s.id and " + "champion = "
				+ forCondition(userId) + " and state = " + state;

		QueryExecuter qe = new QueryExecuter();
		GetRecordsQuery query = new GetRecordsQuery(9, sql);
		@SuppressWarnings("unchecked")
		List<List<Object>> records = (List<List<Object>>) qe.execute(query);
		qe.close();
		List<BattleInfo> list = new ArrayList<BattleInfo>();
		records.forEach(record -> {
			list.add(new BattleInfo((Integer) record.get(0), (String) record.get(1), (String) record.get(2), (Date) record.get(3), (Date) record
					.get(4), (Integer) record.get(5), (String) record.get(6), (Integer) record.get(7), 0));
		});

		return list;
	}

	public static boolean acceptChallenge(long battleId) {
		QueryExecuter qe = new QueryExecuter();
		Date reqTime = qe.get(Battle.class, "id=? and state <> -1", battleId).getReq_time();
		qe.close();
		if (determineTimeValidity(reqTime)) {
			return setState(battleId, STATE_ACCEPTED);
		}
		setState(battleId, STATE_OUTDATED);
		return false;
	}

	public static boolean denyChallenge(long battleId) {
		return setState(battleId, STATE_DENIED);
	}

	static boolean setState(long battleId, int state) {
		Battle battle = new Battle();
		battle.setId((int) battleId);
		battle.setState(state);
		QueryExecuter qe = new QueryExecuter();
		int result = qe.update(battle);
		return result != 0;
	}

	static Date getServerTime() {
		QueryExecuter qe = new QueryExecuter();
		GetRecordQuery query = new GetRecordQuery(1, "select now()");
		Date date = (Date) qe.execute(query);
		qe.close();
		return date;
	}

	static boolean determineTimeValidity(Date reqTime) {
		Date currentTime = getServerTime();
		if (currentTime.getTime() - reqTime.getTime() < 180000) {
			return true;
		}
		return false;
	}

	static List<List<Object>> showAcceptedChallenges(String userId) {
		// 챔피언이랑 챌린저랑 같은이유 ..?
		QueryExecuter qe = new QueryExecuter();
		GetRecordsQuery query = new GetRecordsQuery(7, "select * from battle where (challenger = ? or champion = ?) and state = 1");
		query.addParameters(userId);
		query.addParameters(userId);
		List<List<Object>> result = new ArrayList<List<Object>>();
		qe.close();
		return result;
	}

	public static String makeLink(String uid) {
		return "<a href=#> " + uid + " </a>";
	}

	public static Map<String, Long> getReputation(Set<String> userList) {
		// TODO Auto-generated method stub
		return null;
	}
}