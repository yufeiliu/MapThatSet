package mapthatset.aiplayer.util;

import java.util.List;

public interface Rule {
	public List<KnowledgePattern> getPatterns();
	//Specificity score of the rule being applied
	public int getSpecificty();
	
}
