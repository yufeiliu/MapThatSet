package mapthatset.aiplayer.util;

import java.util.List;
import java.util.Set;

public interface AppliedRule extends Comparable<AppliedRule> {
	
	public void setKnowledgeUsed(List<Knowledge> ku);
	
	public List<Knowledge> getKnowledgeUsed();
	
	public Set<Knowledge> apply();
	
	public double getRecency();
	public double getSpecificity();
	
	public double getPriorityPenalty();
	
	public int compareTo(AppliedRule other);
}
