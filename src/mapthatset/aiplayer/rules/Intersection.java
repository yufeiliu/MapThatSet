package mapthatset.aiplayer.rules;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mapthatset.aiplayer.appliedRules.AppliedIntersection;
import mapthatset.aiplayer.util.AppliedRule;
import mapthatset.aiplayer.util.Knowledge;
import mapthatset.aiplayer.util.Rule;
import mapthatset.aiplayer.util.SetUtil;

public class Intersection implements Rule {

	private SetUtil<Integer> setUtil = new SetUtil<Integer>(); 
	
	@Override
	public Set<AppliedRule> findApplications(Set<Knowledge> kb, Knowledge current) {
		Set<AppliedRule> rules = new HashSet<AppliedRule>();
	
		Knowledge k1 = current;
			
			for (Knowledge k2 : kb) {
				
				if (k1.equals(k2)) return new HashSet<AppliedRule>();
				
				if (k1.getD() != k2.getD()) continue;
				
				if (k1.getPreimage().containsAll(k2.getPreimage()) && k1.getImage().containsAll(k2.getImage())) continue;
				if (k2.getPreimage().containsAll(k1.getPreimage()) && k2.getImage().containsAll(k1.getImage())) continue;
				
				if (k1.getPairings(AppliedIntersection.class.getName()).contains(k2.getRecency()))
					continue;
				
				Set<Integer> piIntersection = setUtil.intersect(k1.getPreimage(), k2.getPreimage());
				Set<Integer> iIntersection = setUtil.intersect(k1.getImage(), k2.getImage());
				
				if (piIntersection.size() < 1 || iIntersection.size() < 1) continue;
				
				if (iIntersection.size() > piIntersection.size()) continue;
				
				/*
				 * Deal with this case:
				 * 
				 * * mapthatset.aiplayer.appliedRules.AppliedIntersection: [2 3 4 ] -> [1 2 ], [1 3 4 ] -> [1 2 ], 
				 *   generated: [3 4 ] -> [1 2 ]
				 */
				if (iIntersection.size() == k1.getImage().size() && iIntersection.size() == k2.getImage().size()
						&& iIntersection.size() == piIntersection.size())
					continue;
				
				
				AppliedRule rule = new AppliedIntersection();
				List<Knowledge> ku = new ArrayList<Knowledge>();
				
				ku.add(k1); 
				ku.add(k2);
				
				rule.setKnowledgeUsed(ku);
				
				rules.add(rule);
				
			}
		
		return rules;
	}

}
