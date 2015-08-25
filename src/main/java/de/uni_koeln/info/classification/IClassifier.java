package de.uni_koeln.info.classification;

import java.util.List;


public interface IClassifier {
	

	void train(List<Double> vector, String clazz);
	
	String classify(List<Double> vector, String clazz);
	
}
