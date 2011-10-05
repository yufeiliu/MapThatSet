package mapthatset.aiplayer.util;

import java.util.Set;

public interface AppliedRule extends Comparable<AppliedRule> {
	
	public void setKnowledgeUsed(Set<Knowledge> ku);
	
	public Set<Knowledge> getKnowledgeUsed();
	
	//Remove subset knowledgeUsed from knowledge base, apply this rule, then add resulting knowledge pieces
	//   If want to preserve previous knowledge pieces used in the rule, implementation
	//   should return a set that includes the original knowledge (i.e., in ku) as well.
	public Set<Knowledge> apply();
	
	public int getRecency();
	public int getSpecificity();
	
	public int compareTo(AppliedRule other);
}