package de.uni_koeln.info.tests;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import weka.classifiers.bayes.NaiveBayes;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import de.uni_koeln.info.Corpus;
import de.uni_koeln.info.Document;
import de.uni_koeln.info.IndexImpl;
import de.uni_koeln.info.classification.IClassifierImpl;

public class JeopardyTests {

	Logger logger = LoggerFactory.getLogger("JeopardyTests");
	private IndexImpl index;
	private IClassifierImpl classifier;
	Map<Document, List<Double>> randomDocumentVectorMap;

	@Before
	public void setup() throws JsonParseException, JsonMappingException, IOException {
		Corpus corpus = new Corpus(new File("freetext.json"));
		index = new IndexImpl(corpus.getDocuments());
		classifier = new IClassifierImpl(new NaiveBayes(), Arrays.asList("1", "2", "3"), index.getTerms().size());

		// RANDOM DOCUMENTS
		List<Document> allDocuments = index.getAllDocuments();
		Random r = new Random(allDocuments.size());
		IntStream ints = r.ints(allDocuments.size()/2, 0, allDocuments.size() + 1);
		randomDocumentVectorMap = new HashMap<>();
		Set<Document> docs = new HashSet<>();
		ints.forEach(i -> {
			Document document = index.getAllDocuments().get(i);
			if (docs.add(document)) {
				randomDocumentVectorMap.put(document, index.computeVector(document));
				logger.info("index[" + i + "] " + document);
			}
		});
	}
	
	@Test
	public void validate() {
		
		// TRAIN CLASSIFIER
		randomDocumentVectorMap.keySet().forEach(d -> {
			List<Double> vector = randomDocumentVectorMap.get(d);
			classifier.train(vector, String.valueOf(d.getScore()));
		});
		
		System.out.println();
		
		// CLASSIFY
		randomDocumentVectorMap.keySet().forEach(d -> {
			List<Double> vector = randomDocumentVectorMap.get(d);
			String clazz = classifier.classify(vector, String.valueOf(d.getScore()));
			logger.info(d + " clazz=" + clazz);
		});
	}

}
