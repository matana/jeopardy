package de.uni_koeln.info.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;


public class SetOperations {


	public static SortedSet<Integer> intersect(Set<Integer> pl1, Set<Integer> pl2) {
		SortedSet<Integer> intersection = new TreeSet<Integer>(pl1);
		intersection.retainAll(pl2);
		return intersection;
	}
	
	public static List<String> complement(List<String> original, List<String> toBeRemoved) {
		List<String> toReturn = new ArrayList<>(original);
		boolean removeAll = toReturn.removeAll(toBeRemoved);
		return removeAll ? toReturn : original;
	}

}
