package mapthatset.aiplayer.util;

import java.util.List;
import java.util.Set;

public interface Rule {
	public Set<AppliedRule> findApplications(List<Knowledge> kb);
}
