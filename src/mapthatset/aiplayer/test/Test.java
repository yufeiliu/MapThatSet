package mapthatset.aiplayer.test;

import java.util.HashSet;
import java.util.Set;

import mapthatset.aiplayer.util.Knowledge;

public class Test {
	public static void main(String[] args) {
		Set<Knowledge> kb = new HashSet<Knowledge>();
		
		Set<Integer> k1p = new HashSet<Integer>();
		Set<Integer> k2p = new HashSet<Integer>();
		
		k1p.add(2);
		k1p.add(3);
		k1p.add(4);
		
		k2p.add(2);
		k2p.add(3);
		k2p.add(4);
		
		Set<Integer> k1i = new HashSet<Integer>();
		Set<Integer> k2i = new HashSet<Integer>();
		
		k1i.add(7);
		k1i.add(8);
		k2i.add(7);
		k2i.add(8);
		
		Knowledge k1 = new Knowledge(k1p, k1i);
		Knowledge k2 = new Knowledge(k2p, k2i);
		
		kb.add(k1);
		kb.add(k2);
		
		for (Knowledge k : kb)
			System.out.println(k);
		
	}
}
