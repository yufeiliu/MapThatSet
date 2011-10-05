package mapthatset.aiplayer.rules;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mapthatset.aiplayer.appliedRules.AppliedDifference;
import mapthatset.aiplayer.util.AppliedRule;
import mapthatset.aiplayer.util.Knowledge;
import mapthatset.aiplayer.util.Rule;

public class Difference implements Rule {

	@Override
	public Set<AppliedRule> findApplications(List<Knowledge> kb) {
		Set<AppliedRule> rules = new HashSet<AppliedRule>();
		
		for (int i = 0; i < kb.size(); i++) {
			
			Knowledge k1 = kb.get(i);
			
			for (int j = 0; j < kb.size(); j++) {
				if (i==j) continue;
				
				Knowledge k2 = kb.get(j);
				
				if (k1.getD() != k2.getD()) continue;
				if (!(k1.getD() < k2.getD())) continue;
				if (!(k2.getPreimage().containsAll(k1.getPreimage()) && k2.getImage().containsAll(k1.getImage()))) continue;
				
				AppliedRule rule = new AppliedDifference();
				List<Knowledge> ku = new ArrayList<Knowledge>();
				
				ku.add(k1); 
				ku.add(k2);
				
				rule.setKnowledgeUsed(ku);
				
				rules.add(rule);
			}
		}
		
		return rules;
	}

}
