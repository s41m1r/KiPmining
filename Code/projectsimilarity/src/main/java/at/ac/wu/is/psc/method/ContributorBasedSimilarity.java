package at.ac.wu.is.psc.method;

import java.util.Set;
import java.util.TreeSet;

public class ContributorBasedSimilarity extends SimilarityMethod
{
	private TreeSet<Integer> contributorsA, contributorsB;
	
	public ContributorBasedSimilarity(TreeSet<Integer> contributorsA, TreeSet<Integer> contributorsB)
	{
		this.type = SimilarityMeasure.ContributorBased;
		
		this.contributorsA = contributorsA;
		this.contributorsB = contributorsB;
		
		this.calculate();
	}
	
	@Override
	protected void calculate() 
	{
		Set<Integer> sharedContributors = new TreeSet<>();
		sharedContributors.addAll(contributorsA);
		sharedContributors.retainAll(contributorsB);	
		
		Set<Integer> unionContributors = new TreeSet<>();
		unionContributors.addAll(contributorsA);
		unionContributors.addAll(contributorsB);
		
		this.similarity = unionContributors.size() > 0 ? 1.0 * sharedContributors.size() / unionContributors.size() * 100.0 : 0;
	}
}