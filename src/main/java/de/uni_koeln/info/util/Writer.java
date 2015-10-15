package de.uni_koeln.info.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import de.uni_koeln.info.Corpus;

public class Writer {
	
	
	public static void writeCorpus(Corpus corpus, int cardId) throws FileNotFoundException, IOException {
		ObjectOutputStream ous = new ObjectOutputStream(new FileOutputStream(new File("data", cardId + ".cardle")));
		ous.writeObject(corpus);
		ous.close();
	}

}
