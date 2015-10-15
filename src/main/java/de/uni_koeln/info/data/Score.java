package de.uni_koeln.info.data;

public class Score {
	
	final private long cardId;
	final private String answer;
	final private int score;

	public Score(long cardId, final String answer, int score) {
		this.cardId = cardId;
		this.answer = answer;
		this.score = score;
	}

	public int getScore() {
		return score;
	}

	public String getRequestCount() {
		return answer;
	}

	public long getCardId() {
		return cardId;
	}
	
	

}
