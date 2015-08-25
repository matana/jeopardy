package de.uni_koeln.info.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TopAnswer {
	
	private String id;
	
	@JsonProperty("text")
	private String answer;
	
	@JsonProperty("upload_id")
	private String uploadId;
	
	private String randomstring;
	
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
		return "TopAnswer [id=" + id + ", answer=" + answer + ", uploadId="
				+ uploadId + ", randomstring=" + randomstring + "]";
	}
	

}
