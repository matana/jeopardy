package de.uni_koeln.info.data;

public class Score {
	
	final private long qustionId;
	final private String answer;
	final private int score;

	public Score(long qustionId, final String answer, int score) {
		this.qustionId = qustionId;
		this.answer = answer;
		this.score = score;
	}

	public int getScore() {
		return score;
	}

	public String getRequestCount() {
		return answer;
	}

	public long getQustionId() {
		return qustionId;
	}
	
	

}
