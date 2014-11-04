package org.nhnnext.chatha;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Group implements Owner{
	private int gId;
	private String name;
	private Calendar calendar;
	private Set<String> memberList = new HashSet<String>();	
}
