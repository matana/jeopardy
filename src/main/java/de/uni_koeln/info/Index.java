package de.uni_koeln.info;

import java.util.List;
import java.util.Set;

public interface Index {

	public Set<Document> search(String query);

	public Set<String> getTerms();

	public List<Document> getAllDocuments();

	public Integer getDocFreq(String t);
	
	public List<Double> computeVector(Document document);

}
