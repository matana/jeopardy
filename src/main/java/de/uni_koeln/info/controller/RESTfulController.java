package de.uni_koeln.info.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.uni_koeln.info.Scorer;
import de.uni_koeln.info.data.Score;

@RestController
public class RESTfulController {
	
	@Autowired
	private Scorer scorer;

	@RequestMapping("/validate")
	public Score validate(@RequestParam(value = "questionId") int questionId, @RequestParam(value = "text") String answer) {
		return scorer.getScore(questionId, answer, 0);
	}

}
