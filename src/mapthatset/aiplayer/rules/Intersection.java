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
	public Set<AppliedRule> findApplications(Set<Knowledge> kb) {
		HashSet<String> noDuplicates = new HashSet<String>();
		
		Set<AppliedRule> rules = new HashSet<AppliedRule>();
		
		int i = 0;
		
		for (Knowledge k1 : kb) {
			
			i++;
			
			int j = 0;
			
			for (Knowledge k2 : kb) {
				
				j++;
				
				if (i==j) continue;
				
				if (k1.getD() != k2.getD()) continue;
				
				if (k1.getPreimage().containsAll(k2.getPreimage()) && k1.getImage().containsAll(k2.getImage())) continue;
				if (k2.getPreimage().containsAll(k1.getPreimage()) && k2.getImage().containsAll(k1.getImage())) continue;
				
				if (k1.getPairings(AppliedIntersection.class.getName()).contains(k2.getRecency())) {
					continue;
				}
				
				Set<Integer> piIntersection = setUtil.intersect(k1.getPreimage(), k2.getPreimage());
				Set<Integer> iIntersection = setUtil.intersect(k1.getImage(), k2.getImage());
				
				if (piIntersection.size() < 1 || iIntersection.size() < 1) continue;
				
				//We already considered this pair
				if (noDuplicates.contains(Intersection.getStringFromTwoInts(i, j))) continue;
				
				AppliedRule rule = new AppliedIntersection();
				List<Knowledge> ku = new ArrayList<Knowledge>();
				
				ku.add(k1); 
				ku.add(k2);
				
				rule.setKnowledgeUsed(ku);
				
				rules.add(rule);
				
				noDuplicates.add(Intersection.getStringFromTwoInts(i, j));
			}
		}
		
		return rules;
	}
	
	private static String getStringFromTwoInts(int a, int b) {
		int tmp;
		//make sure a,b are in ascending order
		if (b < a) {
			tmp = b;
			b = a;
			a = tmp;
		}
		
		return a + "," + b;
	}

}
