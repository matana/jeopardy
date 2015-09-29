package de.uni_koeln.info.controller;

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

import de.uni_koeln.info.Corpus;
import de.uni_koeln.info.Scorer;
import de.uni_koeln.info.data.CardleObject;
import de.uni_koeln.info.data.Score;

@RestController
public class RESTfulController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private Scorer scorer;
	
	@Autowired
	private Corpus corpus;
	

	@RequestMapping(value="/validate", method=RequestMethod.GET)
	public @ResponseBody Score validate(@RequestParam(value = "questionId") int questionId, @RequestParam(value = "text") String answer) {
		return scorer.getScore(questionId, answer);
	}
	
	@RequestMapping(value="/train", method=RequestMethod.POST)
	public HttpStatus train(@RequestBody CardleObject cardleObject) {
		boolean added = corpus.addCardleObject(cardleObject);
		if(added)
			return HttpStatus.OK;
		else
			return HttpStatus.BAD_REQUEST;
	}

}
