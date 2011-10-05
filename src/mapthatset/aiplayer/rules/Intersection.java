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
	public Set<AppliedRule> findApplications(List<Knowledge> kb) {
		HashSet<String> noDuplicates = new HashSet<String>();
		
		Set<AppliedRule> rules = new HashSet<AppliedRule>();
		
		for (int i = 0; i < kb.size(); i++) {
			
			Knowledge k1 = kb.get(i);
			
			for (int j = 0; j < kb.size(); j++) {
				if (i==j) continue;
				
				Knowledge k2 = kb.get(j);
				
				if (k1.getD() != k2.getD()) continue;
				
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
