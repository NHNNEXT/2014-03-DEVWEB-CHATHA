package realrank.support.mail;

import easyjdbc.dao.DBMethods;
import realrank.objects.User;
import realrank.setting.Setting;

public class MailManager {
	public static void sendSimpleBattleMsg(String chalId, String recpId) {
		String subject = "Challenge has Arrived!!";
		String path = "winner/" + recpId;
		String msg = "링크";
		String contents = makeLink(path, msg) + "를 누르시면 패배를 인정하게 되며,<br>당신의 점수가 깎이게 됩니다.<br>";
		sendMail(chalId, recpId, subject, contents);
	}

	public static void sendChallegeAlert(String chalId, String recpId) {
		String subject = "Challenge has Arrived!!";
		String path = "battle/list/index.jsp";
		String msg = "링크";
		String contents = makeLink(path, msg) + "를 누르시면 도전 리스트 화면으로 이동합니다.";
		sendMail(chalId, recpId, subject, contents);
	}
	
	public static void sendChallegeAcceptedAlert(String chalId, String recpId) {
		String subject = "Challenge Accepted!!";
		
		// TODO 배틀 화면 링크를 새로 따야 함. 
		String path = "battle/";
		String msg = "링크";
		String contents = recpId + "님께서 도전을 수락하셨습니다! " + makeLink(path, msg) + "를 누르시면 배틀 화면으로 이동합니다.";
		sendMail(recpId, chalId, subject, contents);
	}
	
	public static void sendChallegeDeniedAlert(String chalId, String recpId) {
		String subject = "Challenge Denied";
		String path = "battle/list/index.jsp";
		String msg = "링크";
		String contents = recpId + "님께서 도전을 거절하셨습니다. " + makeLink(path, msg) + "를 누르시면 도전 리스트 화면으로 이동합니다.";
		sendMail(recpId, chalId, subject, contents);
	}
	
	public static void sendVictoryMsg(String chalId, String recpId) {
		String subject = "You Win!!";
		String path = "/";
		String msg = "링크";
		String contents = chalId + "님과의 대결에서 승리하였습니다! " + makeLink(path, msg) + "를 누르시면 내 정보 화면으로 이동합니다.";
		sendMail(recpId, chalId, subject, contents);
	}
	
	public static void sendDefeatMsg(String chalId, String recpId) {
		String subject = "You Lose";
		String path = "/";
		String msg = "링크";
		String contents = chalId + "님과의 대결에서 패배하였습니다. " + makeLink(path, msg) + "를 누르시면 내 정보 화면으로 이동합니다.";
		sendMail(recpId, chalId, subject, contents);
	}

	private static String makeLink(String path, String msg) {
		return "<a href='" + Setting.get("domain", "url") + path + "'>" + msg + "</a>";
	}

	private static void sendMail(String from, String to, String subject, String contents) {
		User recp = DBMethods.get(User.class, to);
		MessageMod msg = new MessageMod(from, recp.getEmail(), subject, contents);
		SendMailSSL.sendTo(msg);
	}
}
