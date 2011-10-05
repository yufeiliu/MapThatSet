package mapthatset.aiplayer.util;

import java.util.Set;

public class Knowledge implements Comparable<Knowledge> {
	private Set<Integer> preimage;
	private Set<Integer> image;
	private int D;
	private int recency;
	
	private static int recencyCounter = 0;
	
	public Knowledge(Set<Integer> pi, Set<Integer> i) {
		preimage = pi;
		image = i;
		D = image.size() - preimage.size();
		recency = recencyCounter++;
	}
	
	public Set<Integer> getPreimage() {
		return preimage;
	}
	
	public Set<Integer> getImage() {
		return image;
	}
	
	public int getD() {
		return D;
	}
	
	public int getRecency() {
		return recency;
	}
	
	public int compareTo(Knowledge other) {
		return (new Integer(recency)).compareTo(other.getRecency());
	}
	
	public String toString() {
		String s = "[";
		for (int p : preimage) {
			s += p + " ";
		}
		s+= "] -> [";
		for (int i : image) {
			s += i + " ";
		}
		s += "]";
		return s;
	}
}
