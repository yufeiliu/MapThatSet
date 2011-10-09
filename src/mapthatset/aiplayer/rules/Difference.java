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
	public Set<AppliedRule> findApplications(Set<Knowledge> kb, Knowledge current) {
		Set<AppliedRule> rules = new HashSet<AppliedRule>();
		
			for (Knowledge k2 : kb) {
				
				Knowledge k1 = current;
				
				//Don't compare if equal
				if (k1.equals(k2)) return new HashSet<AppliedRule>();
				
				if (k1.getD() != k2.getD()) continue;
				
				//Switch up k1 and k2 if in wrong order
				if (!(k1.getPreimage().size() < k2.getPreimage().size())) {
					Knowledge tmp = k1;
					k1 = k2;
					k2 = tmp;
				}
				
				//Restricted fact can't be used in difference
				if (k2.isRestricted()) continue;
				
				if (!(k2.getPreimage().containsAll(k1.getPreimage()) && k2.getImage().containsAll(k1.getImage()))) {
					continue;
				}
				
				// if (k1.getPairings(AppliedDifference.class.getName()).contains(k2.getRecency())) continue;
				
				AppliedRule rule = new AppliedDifference();
				List<Knowledge> ku = new ArrayList<Knowledge>();
				
				ku.add(k1); 
				ku.add(k2);
				
				rule.setKnowledgeUsed(ku);
				
				rules.add(rule);
			}
		
		return rules;
	}

}
