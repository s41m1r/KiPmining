package at.ac.wu.is.psc.model;

import java.util.LinkedHashMap;

import at.ac.wu.is.psc.method.AdjustedStarBasedSimilarity;
import at.ac.wu.is.psc.method.BranchBasedSimilarity;
import at.ac.wu.is.psc.method.ComplexityBasedSimilarity;
import at.ac.wu.is.psc.method.ContributorBasedSimilarity;
import at.ac.wu.is.psc.method.LanguageBasedSimilarity;
import at.ac.wu.is.psc.method.SimilarityMethod;
import at.ac.wu.is.psc.method.StarBasedSimilarity;
import at.ac.wu.is.psc.method.StarTimeBasedSimilarity;
import at.ac.wu.is.psc.method.SimilarityMethod.SimilarityMeasure;

public class ProjectSimilarity
{
	private Project projectA, projectB;

	private LinkedHashMap<SimilarityMeasure, SimilarityMethod> similarityMeasures = new LinkedHashMap<>();
	
	public ProjectSimilarity(Project projectA, Project projectB)
	{
		this.projectA = projectA;
		this.projectB = projectB;
	}
	
	public void calculateSimilarities()
	{	
		System.out.print("Calculating similarities for " + this.projectA.getOwner() + "/" + this.projectA.getName() + " vs. " + this.projectB.getOwner() + "/" + this.projectB.getName() + "...");
		
		this.similarityMeasures.put(SimilarityMeasure.StarBased, new StarBasedSimilarity(projectA.getStargazers(), projectB.getStargazers()));
		this.similarityMeasures.put(SimilarityMeasure.AdjustedStarBased, new AdjustedStarBasedSimilarity(projectA.getStargazers(), projectB.getStargazers()));
		this.similarityMeasures.put(SimilarityMeasure.StarTimeBased, new StarTimeBasedSimilarity(projectA.getStargazers(), projectB.getStargazers()));
		this.similarityMeasures.put(SimilarityMeasure.ContributorBased, new ContributorBasedSimilarity(projectA.getContributors(), projectB.getContributors()));
		this.similarityMeasures.put(SimilarityMeasure.LanguageBased, new LanguageBasedSimilarity(projectA.getLanguages(), projectB.getLanguages()));
		this.similarityMeasures.put(SimilarityMeasure.BranchBased, new BranchBasedSimilarity(projectA.getBranches(), projectB.getBranches()));
		this.similarityMeasures.put(SimilarityMeasure.ComplexityBased, new ComplexityBasedSimilarity(projectA, projectB));
		
		System.out.println("DONE");
	}
	
	public SimilarityMethod getSimilarity(SimilarityMeasure similarityMeasure)
	{
		return this.similarityMeasures.get(similarityMeasure);
	}
	
	public double getSimilarityValue(SimilarityMeasure similarityMeasure)
	{
		return this.similarityMeasures.get(similarityMeasure).getSimilarity();
	}

	public Double[] getSimilarityValues()
	{
		return this.similarityMeasures.values().toArray(new Double[0]);
	}
	
	public Project getProjectA()
	{
		return this.projectA;
	}
	
	public Project getProjectB()
	{
		return this.projectB;
	}
	
	@Override
	public String toString()
	{
		return this.projectA.toString() +" vs. " + this.projectB.toString();
	}
}
