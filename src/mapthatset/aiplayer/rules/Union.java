package mapthatset.aiplayer.rules;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mapthatset.aiplayer.appliedRules.AppliedUnion;
import mapthatset.aiplayer.util.AppliedRule;
import mapthatset.aiplayer.util.Knowledge;
import mapthatset.aiplayer.util.Rule;
import mapthatset.aiplayer.util.SetUtil;

public class Union implements Rule {

	@Override
	public Set<AppliedRule> findApplications(Set<Knowledge> kb, Knowledge current) {
		Set<AppliedRule> rules = new HashSet<AppliedRule>();
		
		Knowledge k1 = current;
		
			for (Knowledge k2 : kb) {
				
				if (k1.equals(k2)) return new HashSet<AppliedRule>();
				
				if (k1.getPairings(AppliedUnion.class.getName()).contains(k2.getRecency())) {
					continue;
				}
				
				if (k1.getPreimage().containsAll(k2.getPreimage()) && k1.getImage().containsAll(k2.getImage())) continue;
				if (k2.getPreimage().containsAll(k1.getPreimage()) && k2.getImage().containsAll(k1.getImage())) continue;
				
				if (kb.contains(SetUtil.unionKnowledge(k1,k2))) continue;
				
				AppliedRule rule = new AppliedUnion();
				List<Knowledge> ku = new ArrayList<Knowledge>();
				
				ku.add(k1); 
				ku.add(k2);
				
				rule.setKnowledgeUsed(ku);
				rules.add(rule);
			}
		
		return rules;
	}
	
}
