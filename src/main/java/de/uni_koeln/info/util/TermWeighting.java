package de.uni_koeln.info.util;

import de.uni_koeln.info.Document;
import de.uni_koeln.info.Index;

public class TermWeighting {

	public static Double tfIdf(String term, Document document, Index index) {
		double tf = document.getTf(term);
		double n = index.getAllDocuments().size();
		double df = index.getDocFreq(term);
		double idf = Math.log(n/df);
		double tfIdf = tf * idf;
		return tfIdf;
	}
	
}
