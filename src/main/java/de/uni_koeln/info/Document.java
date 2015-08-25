package de.uni_koeln.info;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.uni_koeln.info.util.Tokenizer;

public class Document {

	private List<String> tokens;
	private Map<String, Integer> termFreqMap;
	private int questionId;
	private int score;
	private String answer;
	private long docId;
	
	public Document(int questionId, final String answer, int score, Tokenizer tokenizer) {
		this.questionId = questionId;
		this.score = score;
		this.answer = answer;
		this.tokens = tokenizer.getTokens(answer);
		this.termFreqMap = getTermFreqMap();
	}

	public Document(long docId, int questionId, final String answer, int score, Tokenizer tokenizer) {
		this.docId = docId;
		this.questionId = questionId;
		this.score = score;
		this.answer = answer;
		this.tokens = tokenizer.getTokens(answer);
		this.termFreqMap = getTermFreqMap();
	}

	private Map<String, Integer> getTermFreqMap() {
		Map<String, Integer> termFreqMap = new HashMap<String, Integer>();
		for (String token : tokens) {
			Integer tf = termFreqMap.get(token);
			tf = tf == null ? 1 : tf + 1;
			termFreqMap.put(token, tf);
		}
		return termFreqMap;
	}

	public Set<String> getTerms() {
		return termFreqMap.keySet();
	}

	public double getTf(String term) {
		Integer integer = termFreqMap.get(term);
		return integer == null ? 0 : integer;
	}

	public List<String> getTokens() {
		return tokens;
	}

	public int getQuestionId() {
		return questionId;
	}

	public int getScore() {
		return score;
	}
	
	public String getAnswer() {
		return answer;
	}
	
	public long getDocId() {
		return docId;
	}

	@Override
	public String toString() {
		return "Document [docId=" + docId + ", tokens=" + tokens.size()
				+ ", terms=" + termFreqMap.size() + ", questionId="
				+ questionId + ", score=" + score + "]";
	}
	
	
}
