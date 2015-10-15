package de.uni_koeln.info.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import de.uni_koeln.info.Corpus;

public class ReaderUtil {
	
	private static String stopWords = "stopwords.txt";
	

	public static Reader getReader(File file) throws FileNotFoundException {
		return new BufferedReader(new InputStreamReader(new FileInputStream(file)));
	}
	
	public static Corpus getCorpus(int fileId) throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("data", fileId + ".cardle")));
		Corpus  corpus = (Corpus) ois.readObject();
		ois.close();
		return corpus;
	}
	
	public static Reader getReader(String fileName) throws FileNotFoundException {
		return new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName))));
	}
	
	public static List<String> getStopWords() {
		try {
			return Files.lines(Paths.get(stopWords)).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	public static List<String> getLines(final String fileName) {
		try {
			return Files.lines(Paths.get(fileName)).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
//	public static void main(String[] args) {
//		List<String> lines = ReaderUtil.getLines("output.txt");
//		System.out.println(lines.size());
//		Set<String> terms = new TreeSet<>(lines);
//		System.out.println(terms.size());
//	} 
	
	
	
}
