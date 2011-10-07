package mapthatset.aiplayer.util;

public class NumberCounter implements Comparable<NumberCounter> {
	public int number;
	public int freq;
	
	public String toString() {
		return "[" + number + ": " + freq + "]";
	}
	
	@Override
	public int compareTo(NumberCounter other) {
		int order = (new Integer(freq)).compareTo(other.freq);
		if (order!=0) {
			return order;
		}
		
		return (new Integer(number)).compareTo(other.number);
	}
}
