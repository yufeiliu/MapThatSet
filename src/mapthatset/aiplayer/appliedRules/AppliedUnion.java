package mapthatset.aiplayer.appliedRules;

import java.util.HashSet;
import java.util.Set;

import mapthatset.aiplayer.util.Knowledge;

public class AppliedUnion extends AbstractAppliedRule {

	@Override
	public Set<Knowledge> apply() {
		Set<Knowledge> result = new HashSet<Knowledge>();
		result.addAll(ku);
		
		Knowledge union = new Knowledge(ku.get(0).getPreimage(), ku.get(0).getImage());
		union.getPreimage().addAll(ku.get(1).getPreimage());
		union.getImage().addAll(ku.get(1).getImage());
		
		result.add(union);
		return result;
	}
	
	@Override
	public double getPriorityPenalty() {
		// TODO Auto-generated method stub
		return -100;
	}

}
