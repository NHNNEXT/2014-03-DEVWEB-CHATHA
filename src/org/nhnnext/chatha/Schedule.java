package org.nhnnext.chatha;

import java.util.Date;

public class Schedule {
	//primary key는 PID+스케쥴입력시간 
	private Date startTime;
	private Date endTime;
	private String name;
	private Owner owner;
	//TODO: 반복 필드.
	private Repeat repeatInfo;
	
	
}
