package de.uni_koeln.info.util;

import java.util.List;
import java.util.stream.IntStream;

public class VectorComparison {

	public static double compare(List<Double> v1, List<Double> v2) {
		return dotProduct(v1, v2) / (euclidicLength(v1) * euclidicLength(v2));
	}

	private static double dotProduct(List<Double> v1, List<Double> v2) {
		return IntStream.range(0, v1.size()).mapToDouble(i -> v1.get(i) * v2.get(i)).sum();
	}

	private static double euclidicLength(List<Double> v) {
		return Math.sqrt(v.stream().mapToDouble(val -> Math.pow(val, 2)).sum());
	}

}
