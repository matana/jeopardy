package de.uni_koeln.info.data;

public class UnscoredAnswer {
	
	private final long id;
    private final String answer;
    private final long questionId;

    public UnscoredAnswer(long id, long questionId, final String answer) {
        this.id = id;
        this.questionId = questionId;
        this.answer = answer;
    }

    public long getId() {
        return id;
    }

    public String getAnswer() {
        return answer;
    }

	public long getQuestionId() {
		return questionId;
	}

}
