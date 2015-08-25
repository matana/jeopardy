package de.uni_koeln.info.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CardleObject {
	
private String id;
	
	private String author;
	
	@JsonProperty("author_name")
	private String authorName;
	
	@JsonProperty("author_name_for_admin")
	private String authorNameForAdmin;
	
	private String status;
	
	@JsonProperty("max_points")
	private String maxPoints;
	
	@JsonProperty("exam_time")
	private String examTime;
	
	@JsonProperty("version_nr")
	private String versionNumber;
	
	@JsonProperty("basic_card_type")
	private String basicCardType;
	
	@JsonProperty("card_data_type")
	private String cardDataType;
	
	private Question question;
	
	@JsonProperty("card_data")
	private CardData cardData;
	
	private List<String> tags;
	
	@JsonProperty("tag_ids")
	private List<String> tagIds;
	
	private List<String> flags;
	
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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getAuthorNameForAdmin() {
		return authorNameForAdmin;
	}

	public void setAuthorNameForAdmin(String authorNameForAdmin) {
		this.authorNameForAdmin = authorNameForAdmin;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMaxPoints() {
		return maxPoints;
	}

	public void setMaxPoints(String maxPoints) {
		this.maxPoints = maxPoints;
	}

	public String getExamTime() {
		return examTime;
	}

	public void setExamTime(String examTime) {
		this.examTime = examTime;
	}

	public String getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	public String getCardDataType() {
		return cardDataType;
	}

	public void setCardDataType(String cardDataType) {
		this.cardDataType = cardDataType;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public CardData getCardData() {
		return cardData;
	}

	public void setCardData(CardData cardData) {
		this.cardData = cardData;
	}
	
	public String getBasicCardType() {
		return basicCardType;
	}

	public void setBasicCardType(String basicCardType) {
		this.basicCardType = basicCardType;
	}
	
	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public List<String> getTagIds() {
		return tagIds;
	}

	public void setTagIds(List<String> tagIds) {
		this.tagIds = tagIds;
	}

	public List<String> getFlags() {
		return flags;
	}

	public void setFlags(List<String> flags) {
		this.flags = flags;
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
		return "CardleObject [id=" + id + ", author=" + author + ", authorName="
				+ authorName + ", authorNameForAdmin=" + authorNameForAdmin
				+ ", status=" + status + ", maxPoints=" + maxPoints
				+ ", examTime=" + examTime + ", versionNumber=" + versionNumber
				+ ", basicCardType=" + basicCardType + ", cardDataType="
				+ cardDataType + ", question=" + question + ", cardData="
				+ cardData + ", tags=" + tags + ", tagIds=" + tagIds
				+ ", flags=" + flags + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + "]";
	}

}
