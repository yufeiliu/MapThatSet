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
}
