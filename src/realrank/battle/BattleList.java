package realrank.battle;

import java.util.ArrayList;
import java.util.List;

import realrank.objects.Battle;
import realrank.support.Page;

public class BattleList {
	private List<Battle> list;
	Page page;

	public List<Battle> getList() { return list; }
	public Page getPage() { return page; }

	/**
	 * Create BattleList with given list and page
	 * @param list Given list
	 * @param page Given page. page size should be same as list.size()
	 */
	public BattleList(List<Battle> list, Page page) {
		this.list = list;
		this.page = page;
	}

	/**
	 * Create BattleList with given list (single page)
	 * @param list
	 */
	public BattleList(List<Battle> list) {
		this(list, new Page(0, list.size()));
	}

	/**
	 * Create Empty BattleList
	 * @param page
	 */
	public BattleList(Page page) {
		this(new ArrayList<Battle>(), page);
	}
	
	public boolean add(Battle battle) {
		return list.add(battle);
	}
}
