package mapthatset.aiplayer.util;

import java.util.HashSet;
import java.util.Set;

public class SetUtil<T> {
	public Set<T> intersect(Set<T> s1, Set<T> s2) {
		Set<T> copy = new HashSet<T>();
		copy.addAll(s1);
		
		copy.retainAll(s2);
		
		return copy;
	}
	
	public Set<T> difference(Set<T> s1, Set<T> s2) {
		Set<T> copy = new HashSet<T>();
		copy.addAll(s1);
		
		copy.removeAll(s2);
		
		return copy;
	}
	
	public static Set<Integer> parseIntSet(String s) {
		Set<Integer> ints = new HashSet<Integer>();
		for (String c : s.split(",")) {
			ints.add(Integer.parseInt(c));
		}
		
		return ints;
	}
}
