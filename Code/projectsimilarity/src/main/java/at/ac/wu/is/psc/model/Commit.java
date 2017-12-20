package at.ac.wu.is.psc.model;

import java.util.ArrayList;

public class Commit
{
	private int id;
	private String sha;
	private ArrayList<String> parents = new ArrayList<>();
	
	public Commit(int id, String sha)
	{
		this.id = id;
		this.sha = sha;
	}
	
	public int getID()
	{
		return this.id;
	}
	
	public String getSHA()
	{
		return this.sha;
	}
	
	public void addParent(String parent)
	{
		this.parents.add(parent);
	}
	
	public  ArrayList<String> getParents()
	{
		return this.parents;
	}
}
