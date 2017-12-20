package at.ac.wu.is.psc.method;

import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import at.ac.wu.is.psc.model.Stargazer;

public class StarTimeBasedSimilarity extends SimilarityMethod
{	
	private TreeMap<Integer, Stargazer> stargazersA, stargazersB;
	
	public StarTimeBasedSimilarity(TreeMap<Integer, Stargazer> stargazersA, TreeMap<Integer, Stargazer> stargazersB)
	{
		this.type = SimilarityMeasure.StarTimeBased;
		
		this.stargazersA = stargazersA;
		this.stargazersB = stargazersB;
		
		this.calculate();
	}
	
	@Override
	protected void calculate() 
	{
		int timeSimilarity = 0;
		
		Set<Integer> sharedStargazers = new TreeSet<>();
		sharedStargazers.addAll(stargazersA.keySet());
		sharedStargazers.retainAll(stargazersB.keySet());
		
		for(int stargazerID : sharedStargazers)
		{
			Stargazer stargazerA = stargazersA.get(stargazerID);
			Stargazer stargazerB = stargazersB.get(stargazerID);
			
			long difference = ChronoUnit.MINUTES.between(stargazerA.getWatchedAt(), stargazerB.getWatchedAt());
			
			if(Math.abs(difference) <= 30)
			{
				timeSimilarity++;
			}
		}
		
		this.similarity = sharedStargazers.size() > 0 ? 1.0 * timeSimilarity / sharedStargazers.size() * 100.0 : 0;
	}
}
