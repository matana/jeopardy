package de.uni_koeln.info.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.uni_koeln.info.data.CardleObject;
import de.uni_koeln.info.data.Score;
import de.uni_koeln.info.util.Cache;
import de.uni_koeln.info.util.NoCorpusException;

@RestController
public class RESTfulController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	Cache cache;
	

	@RequestMapping(value="/validate", method=RequestMethod.GET)
	public @ResponseBody Score validate(@RequestParam(value = "questionId") int questionId, @RequestParam(value = "text") String answer) throws NoCorpusException {
		logger.info("validate :: " +  questionId);
		return cache.validate(questionId, answer);
	}
	
	@RequestMapping(value="/train", method=RequestMethod.POST)
	public HttpStatus train(@RequestBody CardleObject cardleObject) throws FileNotFoundException, IOException {
		logger.info("train :: " + cardleObject.getId());
		return cache.train(cardleObject);
	}

}
