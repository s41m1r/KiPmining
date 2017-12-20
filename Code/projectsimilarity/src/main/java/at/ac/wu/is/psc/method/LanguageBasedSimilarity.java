package at.ac.wu.is.psc.method;

import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import at.ac.wu.is.psc.model.Language;

public class LanguageBasedSimilarity extends SimilarityMethod
{
	private TreeMap<Language, Double> languagesA, languagesB;
	
	public LanguageBasedSimilarity(TreeMap<Language, Double> languagesA, TreeMap<Language, Double> languagesB)
	{
		this.type = SimilarityMeasure.LanguageBased;
		
		this.languagesA = languagesA;
		this.languagesB = languagesB;
		
		this.calculate();
	}
	
	@Override
	protected void calculate() 
	{
		Set<Language> sharedLanguages = new TreeSet<>();
		sharedLanguages.addAll(languagesA.keySet());
		sharedLanguages.retainAll(languagesB.keySet());
		
		Set<Language> unionLanguages = new TreeSet<>();
		unionLanguages.addAll(languagesA.keySet());
		unionLanguages.addAll(languagesB.keySet());
		
		this.similarity = unionLanguages.size() > 0 ? 1.0 * sharedLanguages.size() / unionLanguages.size() * 100.0 : 0;
	}
}