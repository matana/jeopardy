package de.uni_koeln.info;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.uni_koeln.info.data.CardData;
import de.uni_koeln.info.data.CardleObject;
import de.uni_koeln.info.data.ScoredAnswers;
import de.uni_koeln.info.util.Delimiter;
import de.uni_koeln.info.util.Tokenizer;

@Repository
public class Corpus implements Serializable {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * UID
	 */
	private static final long serialVersionUID = 148052278269224124L;
	
	private Set<Document> documents = new HashSet<>();
	
	private Set<Document> bad = new HashSet<>();
	private Set<Document> average = new HashSet<>();
	private Set<Document> good = new HashSet<>();
	
	private final AtomicLong docId = new AtomicLong();
	
	private Tokenizer tokenizer = new Tokenizer(Delimiter.UNICODE_AWARE_DELIMITER, true, true);
	

	public Corpus() throws JsonParseException, JsonMappingException, IOException {
		this(null);
	}
	
	public Corpus(File json) throws JsonParseException, JsonMappingException, IOException {
		if(json != null)
			addCardleObject(json);
		else 
			addCardleObject(new File("freetext.json"));
	}

	public Set<Document> getDocuments() {
		return documents;
	}

	public void addCardleObject(File json) throws JsonParseException, JsonMappingException, IOException {
		
		ObjectMapper objectMapper = new ObjectMapper();
		CardleObject cardleObject = objectMapper.readValue(json, CardleObject.class);
		CardData cardData = cardleObject.getCardData();
		int questionId = Integer.parseInt(cardleObject.getQuestion().getId());
		List<ScoredAnswers> scoredAnswers = cardData.getScoredAnswers();
		
		List<Document> tmpDocuments = new ArrayList<>();
		scoredAnswers.forEach(sa -> {
			Document document = new Document(docId.getAndIncrement(), questionId, sa.getAnswer(), sa.getScore(), tokenizer);
			tmpDocuments.add(document);
			switch(document.getScore()) {
				case 1 : bad.add(document); break;
			 	case 2 : average.add(document); break;
			 	case 3 : good.add(document); break;
			 	default: break;
			}
		});
		
		String topAnswer = cardleObject.getCardData().getTopAnswer().getAnswer();
		Document topDoc = new Document(docId.getAndIncrement(), questionId, topAnswer, 3, tokenizer);
		tmpDocuments.add(topDoc);

		logger.info("documents.count=" + tmpDocuments.size());
		logger.info("documents.with.score[1]=" + bad.size());
		logger.info("documents.with.score[2]=" + average.size());
		logger.info("documents.with.score[3]=" + good.size());
		
		this.documents.addAll(tmpDocuments);
	}

}
