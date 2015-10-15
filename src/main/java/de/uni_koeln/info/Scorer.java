package de.uni_koeln.info;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import weka.classifiers.bayes.NaiveBayes;
import de.uni_koeln.info.classification.IClassifierImpl;
import de.uni_koeln.info.data.Score;
import de.uni_koeln.info.util.Delimiter;
import de.uni_koeln.info.util.Tokenizer;

@Component
public class Scorer {

	private Logger logger = LoggerFactory.getLogger(getClass());

	// @Autowired private Corpus corpus;

	// private final AtomicLong counter = new AtomicLong();

	private IClassifierImpl classifier;

	public Score getScore(int cardId, final String answer, Corpus corpus) {
		
		Tokenizer tokenizer = new Tokenizer(Delimiter.UNICODE_AWARE_DELIMITER, true, true);
		Document toBeScored = new Document(cardId, answer, 0, tokenizer);
		
		Index index = corpus.getIndex();
		
		List<Double> documentVector = index.computeVector(toBeScored);
		if (classifier == null)
			classifier = new IClassifierImpl(new NaiveBayes(), Arrays.asList(
					"1", "2", "3"), index.getTerms().size());
		
		index.getAllDocuments().forEach(d -> {
			List<Double> v = index.computeVector(d);
			classifier.train(v, String.valueOf(d.getScore()));
		});
		
		String clazz = classifier.classify(documentVector, null);
		logger.info("terms.count :: " + index.getTerms().size());
		
		return new Score(toBeScored.getCardId(), answer, Integer.parseInt(clazz));
	}

}
