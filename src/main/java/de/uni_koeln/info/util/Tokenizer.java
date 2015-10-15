package de.uni_koeln.info.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tartarus.snowball.Stemmer;

public final class Tokenizer {
	
	//Logger logger = LoggerFactory.getLogger(getClass());

	private String delimiter;
	private boolean normalize;
	private List<String> stopWords;
	private boolean removeStopWords;

	private Stemmer stemmer;

	public Tokenizer(Delimiter delimiter, boolean normalize, boolean removeStopWords) {
		this.delimiter = delimiter.getValue();
		this.normalize = normalize;
		this.stopWords = ReaderUtil.getStopWords();
		this.removeStopWords = removeStopWords;
		this.stemmer = new Stemmer();
	}

	// TERMS
	public List<String> getTerms(final String input) {
		SortedSet<String> result = new TreeSet<String>();
		add(input, result);
		if(normalize) {
			result = (SortedSet<String>) normalize(result);
		}
		return new ArrayList<String>(result);
	}

	// TOKENS
	public List<String> getTokens(final String input) {
		List<String> result = new ArrayList<String>();
		add(input, result);
		// logger.info("original :: " + result.size());
		StringBuilder sbOut = new StringBuilder();
		result.forEach(s -> sbOut.append(s).append(", "));
		// logger.info(sbOut.toString());
		if(normalize) {
			result = (List<String>) normalize(result);
			// logger.info("normalized :: " + result.size());
			StringBuilder sb = new StringBuilder();
			result.forEach(s -> sb.append(s).append(", "));
			// logger.info(sb.toString());
		}
		if(removeStopWords) {
			result = SetOperations.complement(result, stopWords);
			// logger.info("stopwords removed :: " + result.size());
			StringBuilder sb = new StringBuilder();
			result.forEach(s -> sb.append(s).append(", "));
			// logger.info(sb.toString());
		}
		// logger.info("---------");
		return result;
	}

	private Collection<String> normalize(Collection<String> result) {
		try {
			result = stemmer.stem(result, Stemmer.GERMAN);
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			e.printStackTrace();
		}
		return result;
	}

	private void add(final String input, Collection<String> result) {
		List<String> list = Arrays.asList(input.toLowerCase().split(delimiter));
		for (String s : list) {
			if (s.trim().length() > 0) {
				result.add(s.trim());
			}
		}
	}

}
