package realrank.support;

import realrank.support.mail.MailManager;


public class Notification {
	public static void sendSimpleBattleMsg(String chalId, String recpId) {
		MailManager.sendSimpleBattleMsg(chalId, recpId);
	}

	public static void sendChallegeAlert(String chalId, String recpId) {
		MailManager.sendChallegeAlert(chalId, recpId);
	}
	
	public static void sendChallegeAcceptedAlert(String chalId, String recpId) {
		MailManager.sendChallegeAcceptedAlert(chalId, recpId);
	}
	
	public static void sendChallegeDeniedAlert(String chalId, String recpId) {
		MailManager.sendChallegeDeniedAlert(chalId, recpId);
	}
	
	public static void sendVictoryMsg(String chalId, String recpId) {
		MailManager.sendVictoryMsg(chalId, recpId);
	}
	
	public static void sendDefeatMsg(String chalId, String recpId) {
		MailManager.sendDefeatMsg(chalId, recpId);
	}
}
