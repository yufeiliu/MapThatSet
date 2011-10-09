package mapthatset.aiplayer.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;

/**
 * This class infers new knowledge from knowledge base
 * @author yufeiliu
 *
 */
public class Inferrer {
	private static boolean converged = false;
	
	public static boolean hasConverged() { return converged; }
	
	public static void infer(Set<Knowledge> kb, Queue<Knowledge> newkb, ArrayList<Integer> answers, SortedSet<NumberCounter> freqs) {
		
		Map<Integer, NumberCounter> ncs = new HashMap<Integer, NumberCounter>();
		for (NumberCounter nc : freqs) {
			ncs.put(nc.number, nc);
		}
		
		
		while (newkb.size()>0) {
			PriorityQueue<AppliedRule> applicables = new PriorityQueue<AppliedRule>();
			
			Knowledge cur = newkb.remove();
			
			System.out.println("\nCurrent KB:");
			
			for (Knowledge tmpk : kb) {
				System.out.println("- " + tmpk);
			}
			
			System.out.println("Considering new knowledge: " + cur);
			
			for (Rule rule : RuleUtil.getRules()) {
				applicables.addAll(rule.findApplications(kb, cur));
			}
			
			if (applicables.size()>0) {
				System.out.println("Current applicable rules:");
			} else {
				System.out.println("No applicable rules!");
			}
			
			for (AppliedRule ar : applicables) {
				
				System.out.println("* " + ar);
				
				Set<Knowledge> resultant = ar.apply();
				
				System.out.print("* generated: ");
				for (Knowledge kk : resultant) {
					System.out.print(kk + ", ");
				}
				System.out.println();
				
				//This is weak, may not work on distribution
				if (kb.containsAll(resultant)) continue;
				
				newkb.addAll(resultant);
			}
			
			if (cur.isAtomic()) {
				int preimage = cur.getPreimage().iterator().next();
				int image = cur.getImage().iterator().next();
				
				System.out.println("** solved: " + preimage + " -> " + image);
				
				if (answers.get(preimage-1) == -1)
					answers.set(preimage - 1, image);
			}
			
			
			System.out.println("** Current ncs");
			for (NumberCounter nct : ncs.values()) {
				System.out.println(nct);
			}
			
			for (int pi : cur.getPreimage()) {
				
				NumberCounter toRemove = ncs.get(pi);
				NumberCounter toAdd = new NumberCounter();
				toAdd.number = toRemove.number;
				toAdd.freq = toRemove.freq + 1;
				
				freqs.remove(toRemove);
				freqs.add(toAdd);
			}
			
			kb.add(cur);
		}
		
		
		
		/*
		if (applicables.size() == 0) {
			converged = true;
			System.out.println("Cannot select a rule, converged!");
			return;
		}
		
		while (true) {
			
			if (applicables.size() == 0) {
				converged = true;
				System.out.println("Can't find a rule that produces no duplicates, converged!");
				return;
			}
			
			AppliedRule best = applicables.remove();
			
			System.out.println("Applying rule - " + best);
			
			converged = false;
			
			int beforeSize = kb.size();
			
			kb.addAll(best.apply());
			
			//Remove duplicate loop
			while (true){
				int i = 0;
				Set<Integer> js = new HashSet<Integer>();
				Knowledge toRemove = null;
				outer:
				for (Knowledge k1 : kb) {
					i++;
					
					if (js.contains(i)) continue;
					
					int j = 0;
					for (Knowledge k2 : kb) {
						j++;
						if (i==j) continue;
						if (k1.equals(k2)) {
							toRemove = k2;
							break outer;
						}
					}
				}
				
				if (toRemove == null) {
					break;
				} 
				
				kb.remove(toRemove);
			}
			
			if (kb.size() != beforeSize) break;
			
		}
		*/
	}
}
