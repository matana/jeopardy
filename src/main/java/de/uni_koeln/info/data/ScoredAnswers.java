package de.uni_koeln.info.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ScoredAnswers {
	
	private String id;
	
	@JsonProperty("text")
	private String answer;
	
	private int score;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "ScoredAnswers [id=" + id + ", answer=" + answer + ", score="
				+ score + "]";
	}
	
}
