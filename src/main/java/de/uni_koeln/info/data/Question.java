package de.uni_koeln.info.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Question {
	
	private String id;
	
	@JsonProperty("text")
	private String question;
	
	@JsonProperty("upload_id")
	private String uploadId;
	
	private String randomstring;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getUploadId() {
		return uploadId;
	}

	public void setUploadId(String uploadId) {
		this.uploadId = uploadId;
	}

	public String getRandomstring() {
		return randomstring;
	}

	public void setRandomstring(String randomstring) {
		this.randomstring = randomstring;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", question=" + question + ", uploadId="
				+ uploadId + ", randomstring=" + randomstring + "]";
	}
	
}
