package de.uni_koeln.info;

import java.io.Serializable;
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

import de.uni_koeln.info.util.Delimiter;
import de.uni_koeln.info.util.Printer;
import de.uni_koeln.info.util.SetOperations;
import de.uni_koeln.info.util.TermWeighting;
import de.uni_koeln.info.util.Tokenizer;
import de.uni_koeln.info.util.VectorComparison;

//@Service("index")
public class IndexImpl implements Index, Serializable {
	
	private static final long serialVersionUID = -8327672309397298022L;

	private Logger logger = LoggerFactory.getLogger(getClass());

	private Map<String, SortedSet<Integer>> index;

	private Set<Document> documents;
	
	private transient static final Tokenizer PREPROCESSOR = new Tokenizer(Delimiter.UNICODE_AWARE_DELIMITER, true, true);

	//@Autowired
	public IndexImpl(Set<Document> documents) {
		logger.info("documents.size() :: " + documents.size());
		this.documents = documents;
		long start = System.currentTimeMillis();
		index = createIndex(documents);
		logger.info("index built, time: " + (System.currentTimeMillis() - start) + " ms.");
	}

	private Map<String, SortedSet<Integer>> createIndex(Set<Document> documents) {
		HashMap<String, SortedSet<Integer>> index = new HashMap<String, SortedSet<Integer>>();
		List<Document> tmp = new ArrayList<>(documents);
		for (int i = 0; i < tmp.size(); i++) {
			Set<String> terms = tmp.get(i).getTerms();
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
		return new ArrayList<>(documents);
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
