package realrank.user;

import java.util.ArrayList;
import java.util.List;

import realrank.objects.User;
import realrank.support.Page;

public class UserList {
	private List<User> list;
	Page page;

	public List<User> getList() { return list; }
	public Page getPage() { return page; }

	/**
	 * Create BattleList with given list and page
	 * @param list Given list
	 * @param page Given page. page size should be same as list.size()
	 */
	public UserList(List<User> list, Page page) {
		this.list = list;
		this.page = page;
	}

	/**
	 * Create BattleList with given list (single page)
	 * @param list
	 */
	public UserList(List<User> list) {
		this(list, new Page(0, list.size()));
	}

	/**
	 * Create Empty BattleList
	 * @param page
	 */
	public UserList(Page page) {
		this(new ArrayList<User>(), page);
	}
	
	public boolean add(User battle) {
		return list.add(battle);
	}
}
