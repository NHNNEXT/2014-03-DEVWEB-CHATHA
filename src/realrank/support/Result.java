package realrank.support;

import com.google.gson.Gson;

public class Result {
	boolean success;
	String errorMessage;

	public Result(boolean success, String errorMessage) {
		this.success = success;
		this.errorMessage = errorMessage;
	}

	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

	@Override
	public String toString() {
		return "Result [success=" + success + ", errorMessage=" + errorMessage
				+ "]";
	}

}
