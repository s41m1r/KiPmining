package at.ac.wu.is.psc.method;

import java.util.ArrayList;

import at.ac.wu.is.psc.model.Branch;
public class BranchBasedSimilarity extends SimilarityMethod
{
	private ArrayList<Branch> branchesA, branchesB;
	
	public BranchBasedSimilarity(ArrayList<Branch> branchesA, ArrayList<Branch> branchesB)
	{
		this.type = SimilarityMeasure.AdjustedStarBased;
		
		this.branchesA = branchesA;
		this.branchesB = branchesB;
		
		this.calculate();
	}
	
	@Override
	protected void calculate() 
	{
		if(branchesA.size() < branchesB.size())
		{
			this.similarity = calculate(branchesA, branchesB);
		}
		else
		{
			this.similarity = calculate(branchesB, branchesA);
		}
	}
	
	private double calculate(ArrayList<Branch> branchesSmaller, ArrayList<Branch> branchesBigger)
	{	
		return branchesBigger.size() > 0 ? 1.0 * branchesSmaller.size() / branchesBigger.size() * 100.0 : 0;
	}
}
