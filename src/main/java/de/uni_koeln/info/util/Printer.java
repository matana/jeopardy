package de.uni_koeln.info.util;

import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.slf4j.Logger;

public class Printer {

	
	public static void printSortedIndexTerms(Map<String, SortedSet<Integer>> index, Logger logger) {
		TreeSet<String> terms = new TreeSet<String>(index.keySet());
		for (String string : terms) {
			logger.info(string);
		}
		logger.info("terms.count :: " + terms.size());
	}
}
