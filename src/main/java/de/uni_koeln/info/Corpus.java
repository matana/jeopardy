package de.uni_koeln.info;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.uni_koeln.info.data.CardleObject;
import de.uni_koeln.info.data.ScoredAnswers;
import de.uni_koeln.info.util.Delimiter;
import de.uni_koeln.info.util.Tokenizer;

public class Corpus implements Serializable, Comparable<Corpus> {
	
	private static final long serialVersionUID = 148052278269224124L;
	
	
	private transient Logger logger = LoggerFactory.getLogger(getClass());
	
	private Index index;
	
	private Set<Document> documents = new HashSet<>();
	private transient Set<Document> bad = new HashSet<>();
	private transient Set<Document> average = new HashSet<>();
	private transient Set<Document> good = new HashSet<>();
	
	private DateTime creationDate = new DateTime();
	
	private final AtomicLong docId = new AtomicLong();
	
	private transient Tokenizer tokenizer = new Tokenizer(Delimiter.UNICODE_AWARE_DELIMITER, true, true);
	
	private int cardId;
	

	public Corpus() throws JsonParseException, JsonMappingException, IOException {
	}
	
	public Corpus(File json) throws JsonParseException, JsonMappingException, IOException {
		if(json != null)
			addCardleObject(json);
		else 
			addCardleObject(new File("freetext.json"));
	}
	
	public DateTime getCreationDate() {
		return creationDate;
	}
	
	public boolean addCardleObject(CardleObject cardleObject) {
		boolean addDocuments = addDocuments(cardleObject);
		index = new IndexImpl(getDocuments());
		return addDocuments;
	}

	public Set<Document> getDocuments() {
		return documents;
	}

	public void addCardleObject(File json) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		CardleObject cardleObject = objectMapper.readValue(json, CardleObject.class);
		addDocuments(cardleObject);
	}

	private boolean addDocuments(CardleObject cardleObject) {
		
		this.documents = new HashSet<>();
		this.cardId = Integer.parseInt(cardleObject.getId());
		List<ScoredAnswers> scoredAnswers = cardleObject.getCardData().getScoredAnswers();
		List<Document> tmpDocuments = new ArrayList<>();
		scoredAnswers.forEach(sa -> {
			Document document = new Document(docId.getAndIncrement(), cardId, sa.getAnswer(), sa.getScore(), tokenizer);
			tmpDocuments.add(document);
			switch(document.getScore()) {
				case 1 : bad.add(document); break;
			 	case 2 : average.add(document); break;
			 	case 3 : good.add(document); break;
			 	default: break;
			}
		});
		
		String topAnswer = cardleObject.getCardData().getTopAnswer().getAnswer();
		Document topDoc = new Document(docId.getAndIncrement(), cardId, topAnswer, 3, tokenizer);
		tmpDocuments.add(topDoc);

		logger.info("documents.count=" + tmpDocuments.size());
//		logger.info("documents.with.score[1]=" + bad.size());
//		logger.info("documents.with.score[2]=" + average.size());
//		logger.info("documents.with.score[3]=" + good.size());
		
		return this.documents.addAll(tmpDocuments);
	}

	public Index getIndex() {
		return index;
	}
	
	public int getCardId() {
		return cardId;
	}

	@Override
	public int compareTo(Corpus o) {
		return this.getCreationDate().compareTo(o.getCreationDate());
	}
	
}
