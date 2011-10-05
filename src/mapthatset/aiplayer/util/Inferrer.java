package mapthatset.aiplayer.util;

import java.util.List;
import java.util.PriorityQueue;

/**
 * This class infers new knowledge from knowledge base
 * @author yufeiliu
 *
 */
public class Inferrer {
	public static void infer(List<Knowledge> kb) {
		PriorityQueue<AppliedRule> applicables = new PriorityQueue<AppliedRule>();
		
		for (Rule rule : RuleUtil.getRules()) {
			applicables.addAll(rule.findApplications(kb));
		}
		
		AppliedRule best = applicables.remove();
		
		kb.removeAll(best.getKnowledgeUsed());
		
		kb.addAll(best.apply());
	}
}
