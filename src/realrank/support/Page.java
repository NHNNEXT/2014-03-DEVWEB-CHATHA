package realrank.support;

public class Page {
	private long index;
	private long size;
	
	public long getIndex() {
		return index;
	}

	public long getSize() {
		return size;
	}

	public Page(long index, long size) {
		this.index = index;
		this.size = size;
	}
}
