package mapthatset.aiplayer.util;

import java.util.Set;

public class Knowledge {
	private Set<Integer> preimage;
	private Set<Integer> image;
	private int D;
	
	public Knowledge(Set<Integer> pi, Set<Integer> i) {
		preimage = pi;
		image = i;
		D = image.size() - preimage.size();
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
}
