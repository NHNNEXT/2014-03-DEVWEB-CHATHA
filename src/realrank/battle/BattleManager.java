package realrank.battle;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import realrank.objects.Battle;
import realrank.objects.BattleInfo;
import easyjdbc.query.QueryExecuter;
import easyjdbc.query.raw.ExecuteQuery;
import easyjdbc.query.raw.GetRecordQuery;
import easyjdbc.query.raw.GetRecordsQuery;

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
	
	public static Battle createBattle(String chalId, String champId, int state) {
		Battle battle = new Battle();
		battle.setChallenger(chalId);
		battle.setChampion(champId);
		battle.setReq_time(new Date());
		battle.setState(state);
		QueryExecuter qe = new QueryExecuter();
		
		
		BigInteger key = (BigInteger) qe.insertAndGetPrimaryKey(battle);
		long battleId = key.longValue(); 


		battle=qe.get(Battle.class, battleId);
		qe.close();

		return battle;
	}

	public static List<BattleInfo> getSentChallenges(QueryExecuter qe, String userId, int state) {
		String sql = "SELECT b.*, s.score, s.reputation FROM battle b LEFT OUTER JOIN score s ON b.champion = s.id WHERE challenger = "
				+ forCondition(userId) + " and state = " + state;
		GetRecordsQuery query = new GetRecordsQuery(9, sql);
		List<List<Object>> records =  qe.execute(query);

		List<BattleInfo> list = new ArrayList<BattleInfo>();
		records.forEach(record -> {
			list.add(new BattleInfo((Integer) record.get(0), (String) record.get(1), (String) record.get(2), (Date) record.get(3), (Date) record
					.get(4), (Integer) record.get(5), (String) record.get(6), (record.get(7) == null)?0:((Integer) record.get(7)), (record.get(8) == null)?0:((Integer) record.get(8))));
		});

		return list;
	}

	public static List<BattleInfo> getReceivedChallenges(QueryExecuter qe, String userId, int state) {
		String sql = "SELECT b.*, s.score, s.reputation FROM battle b INNER JOIN score s WHERE b.challenger = s.id and " + "champion = "
				+ forCondition(userId) + " and state = " + state;

		GetRecordsQuery query = new GetRecordsQuery(9, sql);
		List<List<Object>> records = qe.execute(query);
		List<BattleInfo> list = new ArrayList<BattleInfo>();
		records.forEach(record -> {
			list.add(new BattleInfo((Integer) record.get(0), (String) record.get(1), (String) record.get(2), (Date) record.get(3), (Date) record
					.get(4), (Integer) record.get(5), (String) record.get(6), (Integer) record.get(7), 0));
		});

		return list;
	}
	
	public static void updateAcceptTimeout(QueryExecuter qe, String userId) {
		String sql = "UPDATE battle SET state=" + STATE_OUTDATED + 
				" WHERE (challenger=" + "'" + userId + "' OR champion=" + "'" + userId + "')" +
				" AND state=" + STATE_NEW +
				" AND ADDDATE(req_time, 1) < NOW()";

		qe.execute(new ExecuteQuery(sql));
	}
	
	public static boolean acceptChallenge(QueryExecuter qe, long battleId) {
		Battle battle = qe.getWhere(Battle.class, "id=? and state=?", battleId, STATE_NEW);
		if (determineTimeValidity(getServerTime(qe), battle.getReq_time())) {
			battle.setState(STATE_ACCEPTED);
			battle.setAcc_time(getServerTime(qe));
			qe.update(battle);
			return true;
		}
		updateAcceptTimeout(qe, battle.getChampion());
		return false;
	}

	public static boolean finishChallenge(String winnerId, long battleId) {
		if(!setWinner(battleId, winnerId))
			return false;
		return setState(battleId, STATE_FINISHED);
	}
	
	public static boolean drawChallenge(long battleId) {
		return setState(battleId, STATE_DRAWED);
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
		qe.close();
		return result != 0;
	}
	
	static boolean setWinner(long battleId, String winnerId) {
		Battle battle = new Battle();
		battle.setId((int) battleId);
		battle.setWinner(winnerId);
		QueryExecuter qe = new QueryExecuter();
		int result = qe.update(battle);
		qe.close();
		return result != 0;
	}

	static Date getServerTime(QueryExecuter qe) {
		GetRecordQuery query = new GetRecordQuery(1, "select now()");
		List<Object> l =  qe.execute(query);
		return (Date) l.get(0);
	}

	static boolean determineTimeValidity(Date currentTime, Date reqTime) {
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
}
