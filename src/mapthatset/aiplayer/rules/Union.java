package mapthatset.aiplayer.rules;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mapthatset.aiplayer.appliedRules.AppliedIntersection;
import mapthatset.aiplayer.util.AppliedRule;
import mapthatset.aiplayer.util.Knowledge;
import mapthatset.aiplayer.util.Rule;

public class Union implements Rule {

	@Override
	public Set<AppliedRule> findApplications(List<Knowledge> kb) {
		HashSet<String> noDuplicates = new HashSet<String>();
		
		Set<AppliedRule> rules = new HashSet<AppliedRule>();
		
		for (int i = 0; i < kb.size(); i++) {
			
			Knowledge k1 = kb.get(i);
			
			for (int j = 0; j < kb.size(); j++) {
				if (i==j) continue;
				
				Knowledge k2 = kb.get(j);
				
				//We already considered this pair
				if (noDuplicates.contains(Union.getStringFromTwoInts(i, j))) continue;
				
				AppliedRule rule = new AppliedIntersection();
				List<Knowledge> ku = new ArrayList<Knowledge>();
				
				ku.add(k1); 
				ku.add(k2);
				
				rule.setKnowledgeUsed(ku);
				rules.add(rule);
				
				noDuplicates.add(Union.getStringFromTwoInts(i, j));
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
