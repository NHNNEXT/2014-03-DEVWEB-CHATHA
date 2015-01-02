package realrank.battle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import realrank.objects.Battle;
import realrank.objects.BattleInfo;
import easyjdbc.dao.DAO;
import easyjdbc.dao.DBMethods;

public class BattleManager {
	public final static int STATE_NEW = 0;
	public final static int STATE_ACCEPTED = 1;
	public final static int STATE_OUTDATED = 2;
	public final static int STATE_CANCELED = 3;
	public final static int STATE_DENIED = 4;

	private static String forCondition(String String){
		return "'" + String + "'";
	}

	public static boolean challengeTo(String userId, String champId) {
		Battle battle = new Battle();
		battle.setChallenger(userId);
		battle.setChampion(champId);
		battle.setReq_time(new Date());
		battle.setState(BattleManager.STATE_NEW);
		
		return DBMethods.insert(battle);
	}

	public static List<BattleInfo> getSentChallenges(String userId, int state) {
		DAO dao = new DAO();
		String sql = "SELECT b.*, s.score, s.reputation FROM battle b INNER JOIN score s WHERE b.champion = s.id and " + "challenger = " + forCondition(userId) + " and state = " + state;

		dao.setSql(sql);
		dao.setResultSize(9);
		List<ArrayList<Object>> records = dao.getRecords();

		List<BattleInfo> list = new ArrayList<BattleInfo>();
		records.forEach(record -> {
			list.add(new BattleInfo((Integer)record.get(0),
					(String)record.get(1), (String)record.get(2),
					(Date)record.get(3), (Date)record.get(4),
					(Integer)record.get(5), (String)record.get(6),
					(Integer)record.get(7), 0));
		});
		
		return list;
	}

	public static List<BattleInfo> getReceivedChallenges(String userId, int state) {
		DAO dao = new DAO();
		String sql = "SELECT b.*, s.score, s.reputation FROM battle b INNER JOIN score s WHERE b.challenger = s.id and " + "champion = " + forCondition(userId) + " and state = " + state;

		dao.setSql(sql);
		dao.setResultSize(9);
		List<ArrayList<Object>> records = dao.getRecords();

		List<BattleInfo> list = new ArrayList<BattleInfo>();
		records.forEach(record -> {
			list.add(new BattleInfo((Integer)record.get(0),
					(String)record.get(1), (String)record.get(2),
					(Date)record.get(3), (Date)record.get(4),
					(Integer)record.get(5), (String)record.get(6),
					(Integer)record.get(7), 0));
		});
		
		return list;
	}

	public static boolean acceptChallenge(long battleId) {
		Date reqTime = DBMethods.get(Battle.class, "id=? and state <> -1", battleId).getReq_time();
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

	public static Map<String, Long> getReputation(Set<String> userList) {
		// TODO Auto-generated method stub
		return null;
	}
}
