package at.ac.wu.is.psc.method;

import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import at.ac.wu.is.psc.model.Stargazer;

public class AdjustedStarBasedSimilarity extends SimilarityMethod
{
	private TreeMap<Integer, Stargazer> stargazersA, stargazersB;
	
	public AdjustedStarBasedSimilarity(TreeMap<Integer, Stargazer> stargazersA, TreeMap<Integer, Stargazer> stargazersB)
	{
		this.type = SimilarityMeasure.AdjustedStarBased;
		
		this.stargazersA = stargazersA;
		this.stargazersB = stargazersB;
		
		this.calculate();
	}
	
	@Override
	protected void calculate() 
	{
		if(stargazersA.size() < stargazersB.size())
		{
			this.similarity = calculate(stargazersA, stargazersB);
		}
		else
		{
			this.similarity = calculate(stargazersB, stargazersA);
		}
	}
	
	private double calculate(TreeMap<Integer, Stargazer> stargazersSmaller, TreeMap<Integer, Stargazer> stargazersBigger)
	{
		Set<Integer> sharedStargazers = new TreeSet<>();
		sharedStargazers.addAll(stargazersSmaller.keySet());
		sharedStargazers.retainAll(stargazersBigger.keySet());
		
		return stargazersSmaller.size() > 0 ? 1.0 * sharedStargazers.size() / stargazersSmaller.size() * 100.0 : 0;
	}
}