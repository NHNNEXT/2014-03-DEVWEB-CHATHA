package realrank.support.mail;

import easyjdbc.dao.DBMethods;
import realrank.objects.User;
import realrank.setting.Setting;

public class MailManager {
	public static void sendSimpleBattleMsg(String chalId, String recpId) {
		String subject = "Challenge hsa Arrived!!";
		String path = "winner/" + recpId;
		String msg = "링크";
		String contents = makeLink(path, msg) + "를 누르시면 패배를 인정하게 되며,<br>당신의 점수가 깎이게 됩니다.<br>";
		sendMail(chalId, recpId, subject, contents);
	}

	public static void sendChallegeAlert(String chalId, String recpId) {
		String subject = "Challenge hsa Arrived!!";
		String path = "battle/list/index.jsp";
		String msg = "링크";
		String contents = makeLink(path, msg) + "를 누르시면 도전 리스트 화면으로 이동합니다.";
		sendMail(chalId, recpId, subject, contents);
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
