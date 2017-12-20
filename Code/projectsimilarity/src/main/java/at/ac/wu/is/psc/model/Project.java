package at.ac.wu.is.psc.model;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

import at.ac.wu.is.psc.api.GithubAPI;
import at.ac.wu.is.psc.database.DataAccess;

public class Project 
{
	private int id = -1;
	private String owner = "", name = "";
		
	private TreeMap<Integer, Stargazer> stargazers;
	private TreeSet<Integer> contributors;
	private TreeMap<Language, Double> languages;
	private ArrayList<Branch> branches;
	private int issueCount = 0;
	
	public Project(String owner, String name)
	{
		this.owner = owner;
		this.name = name;
	}
	
	public void load()
	{
		System.out.print("Loading project " + this + " from Database... ");
		
		this.id = DataAccess.getProjectID(this.owner, this.name);
		this.stargazers = DataAccess.getStargazers(this.id);
		this.contributors = DataAccess.getContributors(this.id);
		this.languages = DataAccess.getLanguages(this.id);
		this.issueCount = DataAccess.getIssueCount(this.id);
		this.branches = GithubAPI.getAllBranches(this.toString());
		
		System.out.println("DONE");
	}
	
	@Override
	public String toString()
	{
		return this.owner + "/" + this.name;
	}
	
	public int getID()
	{
		return this.id;
	}
	
	public String getOwner()
	{
		return this.owner;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public TreeMap<Integer, Stargazer> getStargazers()
	{
		return this.stargazers;
	}
	
	public TreeSet<Integer> getContributors()
	{
		return this.contributors;
	}
	
	public TreeMap<Language, Double> getLanguages()
	{
		return this.languages;
	}
	
	public ArrayList<Branch> getBranches()
	{
		return this.branches;
	}
	
	public int getIssueCount()
	{
		return this.issueCount;
	}
}
