package de.uni_koeln.info.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import de.uni_koeln.info.Corpus;
import de.uni_koeln.info.Scorer;
import de.uni_koeln.info.data.CardleObject;
import de.uni_koeln.info.data.Score;

@Service
public class Cache {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private Scorer scorer;
	
	Map<Integer, Corpus> cache = new HashMap<>();

	public Score validate(int cardId, String answer) throws NoCorpusException {
		Corpus corpus = cache.get(cardId);
		if(corpus == null) {
			corpus = readCorpus(cardId);
			if(corpus == null) {
				throw new NoCorpusException("No corpus found for id=" + cardId);
			}
			handleCache(cardId, corpus);
		}
		return scorer.getScore(cardId, answer, corpus);
	}

	public HttpStatus train(CardleObject cardleObject) throws JsonParseException, JsonMappingException, IOException {
		
		Corpus corpus = new Corpus();
		int cardId = Integer.parseInt(cardleObject.getId());
		
		handleCache(cardId, corpus);
		
		boolean added = corpus.addCardleObject(cardleObject);
		
		if(added) {
			Writer.writeCorpus(corpus, cardId);
			logger.info("Corpus wrote to file with id= " + cardId);
			return HttpStatus.OK;
		} else
			return HttpStatus.BAD_REQUEST;
	}
	
	private Corpus readCorpus(int cardId) {
		try {
			return ReaderUtil.getCorpus(cardId);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void handleCache(int cardId, Corpus corpus) {
		if(cache.keySet().size() > 25)
			optimize();
		cache.put(cardId, corpus);
	}

	private void optimize() {
		
		List<Corpus> values = new ArrayList<Corpus>(cache.values());
		Collections.sort(values);
		Map<Integer, Corpus> tmpCache = new HashMap<>();
		
		for (Integer key  : cache.keySet()) {
			Corpus corpus = cache.get(key);
			Hours hoursBetween = Hours.hoursBetween(corpus.getCreationDate(), new DateTime());
			if(!hoursBetween.isGreaterThan(Hours.EIGHT))  {
				tmpCache.put(key, corpus);
			}
		}
		cache = tmpCache;
	}
	

}
