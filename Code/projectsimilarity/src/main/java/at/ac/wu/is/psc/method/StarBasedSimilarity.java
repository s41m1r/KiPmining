package at.ac.wu.is.psc.method;

import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import at.ac.wu.is.psc.model.Stargazer;

public class StarBasedSimilarity extends SimilarityMethod
{
	private TreeMap<Integer, Stargazer> stargazersA, stargazersB;
	
	public StarBasedSimilarity(TreeMap<Integer, Stargazer> stargazersA, TreeMap<Integer, Stargazer> stargazersB)
	{
		this.type = SimilarityMeasure.StarBased;
		
		this.stargazersA = stargazersA;
		this.stargazersB = stargazersB;
		
		this.calculate();
	}
	
	@Override
	protected void calculate() 
	{
		Set<Integer> sharedStargazers = new TreeSet<>();
		sharedStargazers.addAll(stargazersA.keySet());
		sharedStargazers.retainAll(stargazersB.keySet());
		
		Set<Integer> unionStargazers = new TreeSet<>();
		unionStargazers.addAll(stargazersA.keySet());
		unionStargazers.addAll(stargazersB.keySet());
		
		this.similarity = unionStargazers.size() > 0 ? 1.0 * sharedStargazers.size() / unionStargazers.size() * 100.0 : 0;
	}
}