package de.uni_koeln.info;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.uni_koeln.info.util.Delimiter;
import de.uni_koeln.info.util.SetOperations;
import de.uni_koeln.info.util.Printer;
import de.uni_koeln.info.util.TermWeighting;
import de.uni_koeln.info.util.Tokenizer;
import de.uni_koeln.info.util.VectorComparison;

@Service("index")
public class IndexImpl implements Index {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	private Map<String, SortedSet<Integer>> index;
	private static final Tokenizer PREPROCESSOR = new Tokenizer(Delimiter.UNICODE_AWARE_DELIMITER, true, true);
	private Corpus corpus;

	@Autowired
	public IndexImpl(Corpus corpus) {
		long start = System.currentTimeMillis();
		this.corpus = corpus;
		index = createIndex(corpus);
		logger.info("index built, time: " + (System.currentTimeMillis() - start) + " ms.");
	}

	private Map<String, SortedSet<Integer>> createIndex(Corpus corpus) {
		HashMap<String, SortedSet<Integer>> index = new HashMap<String, SortedSet<Integer>>();
		List<Document> documents = new ArrayList<>(corpus.getDocuments());
		for (int i = 0; i < documents.size(); i++) {
			Set<String> terms = documents.get(i).getTerms();
			for (String t : terms) {
				SortedSet<Integer> postings = index.get(t);
				if (postings == null) {
					postings = new TreeSet<Integer>();
					index.put(t, postings);
				}
				postings.add(i);
			}
		}
		return index;
	}

	@Override
	public Set<Document> search(String query) {
		long start = System.currentTimeMillis();
		
		List<SortedSet<Integer>> allPostings = new ArrayList<SortedSet<Integer>>();
		List<String> queries = PREPROCESSOR.getTerms(query);
		
		queries.forEach(q -> allPostings.add(index.get(q)));

		Collections.sort(allPostings,
				(SortedSet<Integer> o1, SortedSet<Integer> o2) 
					-> Integer.valueOf(o1.size()).compareTo(o2.size()));
		
		SortedSet<Integer> result = new TreeSet<>(allPostings.get(0));
		for (SortedSet<Integer> set : allPostings) {
			result = SetOperations.intersect(result, set);
		}

		logger.info("search time: " + (System.currentTimeMillis() - start) + " ms.");

		Set<Document> resultAsDocSet = new HashSet<Document>();
		result.forEach(docId -> resultAsDocSet.add(getAllDocuments().get(docId)));

		return resultAsDocSet;
	}

	public List<Document> getAllDocuments() {
		return new ArrayList<>(corpus.getDocuments());
	}

	public Set<String> getTerms() {
		return index.keySet();
	}

	public Integer getDocFreq(String term) {
		return index.get(term).size();
	}

	public Double similarity(Document doc, Document query) {
		List<Double> queryVec = computeVector(doc);
		List<Double> docVector = computeVector(query);
		return VectorComparison.compare(queryVec, docVector);
	}

	public List<Double> computeVector(Document doc) {
		Set<String> terms = getTerms();
		List<Double> docVector = new ArrayList<Double>(terms.size());
		terms.forEach(term -> docVector.add(TermWeighting.tfIdf(term, doc, this)));
		
		Map<String, Double> termWeightMap = new HashMap<>();
		List<String> list = new ArrayList<>(terms);
		for (int i = 0; i < list.size(); i++) {
			String t = list.get(i);
			Double val = docVector.get(i);
			termWeightMap.put(t, val);
		}
//		logger.info("Text=" + doc.getAnswer());
//		logger.info(termWeightMap.entrySet());
		return docVector;
	}
	
	public void printIndex() {
		Printer.printSortedIndexTerms(index, logger);
	}

}
