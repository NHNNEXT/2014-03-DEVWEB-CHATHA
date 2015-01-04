package realrank.battle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import easyjdbc.query.ExecuteQuery;
import easyjdbc.query.GetRecordQuery;
import easyjdbc.query.GetRecordsQuery;
import easyjdbc.query.QueryExecuter;
import realrank.objects.Battle;
import realrank.objects.BattleInfo;

public class BattleManager {
	public final static int STATE_NEW = 0;
	public final static int STATE_ACCEPTED = 1;
	public final static int STATE_FINISHED = 2;
	public final static int STATE_DRAWED = 3;
	public final static int STATE_CANCELED = 4;
	public final static int STATE_DENIED = 5;
	public final static int STATE_OUTDATED = 6;

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
		String sql = "SELECT b.*, s.score, s.reputation FROM battle b LEFT OUTER JOIN score s ON b.champion = s.id WHERE challenger = "
				+ forCondition(userId) + " and state = " + state;
		GetRecordsQuery query = new GetRecordsQuery(9, sql);
		@SuppressWarnings("unchecked")
		List<List<Object>> records = (List<List<Object>>) qe.execute(query);
		qe.close();

		List<BattleInfo> list = new ArrayList<BattleInfo>();
		records.forEach(record -> {
			list.add(new BattleInfo((Integer) record.get(0), (String) record.get(1), (String) record.get(2), (Date) record.get(3), (Date) record
					.get(4), (Integer) record.get(5), (String) record.get(6), (record.get(7) == null)?0:((Integer) record.get(7)), (record.get(8) == null)?0:((Integer) record.get(8))));
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
	
	public static void updateBattleTimeout(String championId) {
		String sql = "UPDATE battle SET state=" + STATE_OUTDATED + 
				" WHERE champion=" + "'" + championId + "'" +
				" AND state=" + STATE_NEW +
				" AND TIMEDIFF(NOW(), req_time) > " + "'24:00:00'";
		System.out.println("[DEBUG] " + sql);

		QueryExecuter qe = new QueryExecuter();
		ExecuteQuery query = new ExecuteQuery(sql);
		qe.execute(query);
		qe.close();
	}

	public static boolean acceptChallenge(long battleId) {
		QueryExecuter qe = new QueryExecuter();
		Battle battle = qe.getWhere(Battle.class, "id=? and state=?", battleId, STATE_NEW);
		qe.close();
		if (determineTimeValidity(battle.getReq_time())) {
			return setState(battleId, STATE_ACCEPTED);
		}
		updateBattleTimeout(battle.getChampion());
		return false;
	}

	public static boolean denyChallenge(long battleId) {
		return setState(battleId, STATE_DENIED);
	}
	
	public static boolean cancelChallenge(long battleId) {
		return setState(battleId, STATE_CANCELED);
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
		@SuppressWarnings("unchecked")
		List<Object> l = (List<Object>) qe.execute(query);
		qe.close();
		return (Date) l.get(0);
	}

	static boolean determineTimeValidity(Date reqTime) {
		Date currentTime = getServerTime();
		return (currentTime.getTime() - reqTime.getTime()) < (1000L * 60 * 60 * 24);
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
