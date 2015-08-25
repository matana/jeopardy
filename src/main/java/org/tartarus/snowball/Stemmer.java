package org.tartarus.snowball;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Stemmer {
	
	public static String DANISH = "danish";
	public static String DUTCH = "dutch";
	public static String ENGLISH = "english";
	public static String FINNISH = "finnish";
	public static String FRENCH = "french";
	public static String GERMAN = "german";
	public static String HUNGARIAN = "hungarian";
	public static String ITALIEN = "italien";
	public static String NORWEGIAN = "norwegian";
	public static String PORTER = "porter";
	public static String PORTUGUESE = "portuguese";
	public static String ROMANIAN = "romanian";
	public static String RUSSIAN = "russian";
	public static String SPANISH = "spanish";
	public static String SWEDISH = "swedish";
	public static String TURKISH = "turkish";
	
	
	public List<String> stem(Collection<String> tokens, final String language) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		SnowballStemmer stemmer = getStemmer(language);
		List<String> toReturn = new ArrayList<>();
		tokens.forEach(token -> {
			stemmer.setCurrent(token);
			stemmer.stem();
			toReturn.add(stemmer.getCurrent());
		});
		return toReturn;
	}

//	public static void main(String[] args) throws Throwable {
//		
//		SnowballStemmer stemmer = getStemmer(GERMAN);
//		File inputFile = new File("input.txt");
//		File outpuFile = null;
//		Writer writer = getWriter(outpuFile);
//		Reader reader = getReader(inputFile);
//		StringBuffer input = new StringBuffer();
//		int character;
//		while ((character = reader.read()) != -1) {
//			char ch = (char) character;
//			if (Character.isWhitespace((char) ch)) {
//				if (input.length() > 0) {
//					stemmer.setCurrent(input.toString());
//					stemmer.stem();
//					writer.write(stemmer.getCurrent());
//					writer.write('\n');
//					input.delete(0, input.length());
//				}
//			} else {
//				input.append(Character.toLowerCase(ch));
//			}
//		}
//		writer.flush();
//		writer.close();
//		reader.close();
//	}

	private static SnowballStemmer getStemmer(final String language) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class<?> stemClass = Class.forName("org.tartarus.snowball.ext." + language + "Stemmer");
		return (SnowballStemmer) stemClass.newInstance();
	}

	private static Reader getReader(File inputFile) throws FileNotFoundException {
		return new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));
	}

	private static Writer getWriter(File outpuFile) throws FileNotFoundException {
		OutputStream outputStream = outpuFile != null ? new FileOutputStream(outpuFile) : System.out;
		return new BufferedWriter(new OutputStreamWriter(outputStream));
	}
}
