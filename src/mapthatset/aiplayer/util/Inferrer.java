package mapthatset.aiplayer.util;

import java.util.PriorityQueue;
import java.util.Set;

/**
 * This class infers new knowledge from knowledge base
 * @author yufeiliu
 *
 */
public class Inferrer {
	private static boolean converged = false;
	
	public static boolean hasConverged() { return converged; }
	
	public static void infer(Set<Knowledge> kb) {
		PriorityQueue<AppliedRule> applicables = new PriorityQueue<AppliedRule>();
		
		for (Rule rule : RuleUtil.getRules()) {
			applicables.addAll(rule.findApplications(kb));
		}
		
		if (applicables.size() == 0) {
			converged = true;
			System.out.println("Cannot select a rule, converged!");
			return;
		}
		
		AppliedRule best = applicables.remove();
		
		System.out.println("Applying rule - " + best);
		
		converged = false;
		
		kb.removeAll(best.getKnowledgeUsed());
		
		kb.addAll(best.apply());
	}
}
