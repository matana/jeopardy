package de.uni_koeln.info.data;

public class Score {
	
	final private long qustionId;
	final private long requestCount;
	final private int score;

	public Score(long requestCount, long qustionId, int score) {
		this.qustionId = qustionId;
		this.requestCount = requestCount;
		this.score = score;
	}

	public int getScore() {
		return score;
	}

	public long getRequestCount() {
		return requestCount;
	}

	public long getQustionId() {
		return qustionId;
	}
	
	

}
