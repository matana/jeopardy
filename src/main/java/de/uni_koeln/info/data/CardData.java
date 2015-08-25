package de.uni_koeln.info.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CardData {

	private String id;

	@JsonProperty("answer")
	private TopAnswer topAnswer;

	@JsonProperty("scored_answers")
	private List<ScoredAnswers> scoredAnswers;
	
	@JsonProperty("created_at")
	private String createdAt;
	
	@JsonProperty("updated_at")
	private String updatedAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TopAnswer getTopAnswer() {
		return topAnswer;
	}

	public void setTopAnswer(TopAnswer topAnswer) {
		this.topAnswer = topAnswer;
	}

	public List<ScoredAnswers> getScoredAnswers() {
		return scoredAnswers;
	}

	public void setScoredAnswers(List<ScoredAnswers> scoredAnswers) {
		this.scoredAnswers = scoredAnswers;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "CardData [id=" + id + ", topAnswer=" + topAnswer
				+ ", scoredAnswers=" + scoredAnswers + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
}
