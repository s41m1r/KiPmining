package at.ac.wu.is.psc.model;

public class User 
{
	protected int id;
	protected String login;

	public User(int id, String login)
	{
		this.id = id;
		this.login = login;
	}
	
	public int getID()
	{
		return this.id;
	}
	
	public String getLogin()
	{
		return this.login;
	}
}
