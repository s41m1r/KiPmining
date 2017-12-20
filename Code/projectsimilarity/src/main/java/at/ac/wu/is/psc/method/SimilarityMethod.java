package at.ac.wu.is.psc.method;

public abstract class SimilarityMethod 
{
	protected double similarity = -1;
	protected SimilarityMeasure type;
	
	protected abstract void calculate();
	
	public double getSimilarity()
	{
		return this.similarity;
	}
	
	public SimilarityMeasure getType()
	{
		return this.type;
	}
	
	public enum SimilarityMeasure
	{
		StarBased, AdjustedStarBased, StarTimeBased, ContributorBased, LanguageBased, BranchBased, ComplexityBased
	}
}
