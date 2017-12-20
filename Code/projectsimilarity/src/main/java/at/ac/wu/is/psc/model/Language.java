package at.ac.wu.is.psc.model;

public class Language implements Comparable<Language>
{
	private String name = "";
	private long bytes = 0;
	
	public Language(String name, long bytes)
	{
		this.name = name;
		this.bytes = bytes;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public long getBytes()
	{
		return this.bytes;
	}
	
	@Override
	public String toString()
	{
		return this.name;
	}

	@Override
	public int compareTo(Language otherLanguage) 
	{
		return this.getName().compareTo(otherLanguage.getName());
	}
}
