package de.uni_koeln.info.classification;

import java.util.ArrayList;
import java.util.List;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SparseInstance;

public class IClassifierImpl implements IClassifier {

	
	private Classifier classifier;
	private int generalVectorSize;
	private List<String> classes;
	private Instances trainingSet;
	private boolean classifierBuilt = false;
	

	public IClassifierImpl(Classifier classifier, List<String> classes, int generalVectorSize) {
		this.classifier = classifier;
		this.generalVectorSize = generalVectorSize;
		this.classes = classes;
		this.trainingSet = initTrainingSet();
	}

	@Override
	public void train(List<Double> vector, final String clazz) {
		trainingSet.add(instance(vector, clazz));
		classifierBuilt = false;
	}

	@Override
	public String classify(List<Double> vector, final String clazz) {
		try {
			if (!classifierBuilt) {
				classifier.buildClassifier(trainingSet);
				classifierBuilt = true;
			}
			int i = (int) classifier.classifyInstance(instance(vector, clazz));
			return classes.get(i);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Instances initTrainingSet() {
		ArrayList<Attribute> structureVector = new ArrayList<Attribute>();
		structureVector.add(new Attribute("classes", classes));
		
		for (int i = 0; i <= generalVectorSize; i++) {
			structureVector.add(new Attribute(i + ""));
		}
		
		Instances tmpTrainingsSet = new Instances("instanceStructure",
				structureVector, structureVector.size());
		tmpTrainingsSet.setClassIndex(0);
		return tmpTrainingsSet;
	}

	private Instance instance(List<Double> vector, final String clazz) {
		double[] vals = new double[vector.size() + 1];
		for (int i = 0; i < vector.size(); i++) {
			vals[i + 1] = vector.get(i);
		}
		
		Instance instance = new SparseInstance(1, vals);
		instance.setDataset(trainingSet);

		if (clazz == null) {
			instance.setClassMissing();
		} else {
			instance.setClassValue(clazz);
		}
		
		return instance;
	}

}
