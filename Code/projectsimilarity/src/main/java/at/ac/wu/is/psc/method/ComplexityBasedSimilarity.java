package at.ac.wu.is.psc.method;

import at.ac.wu.is.psc.model.Language;
import at.ac.wu.is.psc.model.Project;
public class ComplexityBasedSimilarity extends SimilarityMethod
{
	private Project projectA, projectB;
	
	public ComplexityBasedSimilarity(Project projectA, Project projectB)
	{
		this.type = SimilarityMeasure.ComplexityBased;
		
		this.projectA = projectA;
		this.projectB = projectB;
		
		this.calculate();
	}
	
	@Override
	protected void calculate() 
	{
		int issueCountA = projectA.getIssueCount();
		int issueCountB = projectB.getIssueCount();
		
		long totalBytesA = 0, totalBytesB = 0;
		
		for(Language language : projectA.getLanguages().keySet())
		{
			totalBytesA += language.getBytes();
		}
		for(Language language : projectB.getLanguages().keySet())
		{
			totalBytesB += language.getBytes();
		}
		
		double issueSimilarity = -1, sizeSimilarity = -1;
		
		if(issueCountA < issueCountB)
		{
			issueSimilarity = calculateIssueSimilarity(issueCountA, issueCountB);
		}
		else
		{
			issueSimilarity = calculateIssueSimilarity(issueCountB, issueCountA);
		}
		
		if(totalBytesA < totalBytesB)
		{
			sizeSimilarity = calculateSizeSimilarity(totalBytesA, totalBytesB);
		}
		else
		{
			sizeSimilarity = calculateSizeSimilarity(totalBytesB, totalBytesA);
		}
		
		this.similarity = (100.0 * issueSimilarity / 2) + (100 * sizeSimilarity / 2);
	}
	
	private double calculateSizeSimilarity(long sizeSmaller, long sizeBigger)
	{
		return sizeBigger > 0 ? 1.0 * sizeSmaller / sizeBigger : 0;
	}
	
	private double calculateIssueSimilarity(int issueCountSmaller, int issueCountBigger)
	{
		return issueCountBigger > 0 ? 1.0 * issueCountSmaller / issueCountBigger : 0;
	}
}
