package at.ac.wu.is.psc.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLDatabase
{
	private static MySQLDatabase _instance = null;
	
//	private static final String URL = "jdbc:mysql://raffel.digital:3306/projectsimilarity?useSSL=false";
//	private static final String USERNAME = "projectsimilarity";
//	private static final String PASSWORD = "project1315similarity";
	
	private static final String URL = "jdbc:mysql://localhost:3306/ghtorrent?useSSL=false";
	private static final String USERNAME = "ght";
	private static final String PASSWORD = "";
	
	private Connection dbConnection = null;
	
	private MySQLDatabase(){}
	
	public static boolean establishConnection()
	{	
	    try 
	    {
	        getInstance().dbConnection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
	    }
	    catch (SQLException e) 
	    {
	    	e.printStackTrace();
	    	return false;
	    } 
	    
	    return true;
	}
	
	public static boolean closeConnection()
	{
		try 
		{
			getInstance().dbConnection.close();
		}
		catch (Exception e)
		{
			return false;
		}
		
		return true;
	}
	
	public static CloseableResultSet sql(String sql)
	{
		try 
		{
			Statement stmt = getInstance().dbConnection.createStatement();
			
			if(sql.startsWith("SELECT"))
			{
				return new CloseableResultSet(stmt.executeQuery(sql), stmt);
			}
			else
			{
				stmt.execute(sql, Statement.RETURN_GENERATED_KEYS);
				return new CloseableResultSet(stmt.getGeneratedKeys(), stmt);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return null;
		}
	}
	
	private static MySQLDatabase getInstance()
	{
		if(_instance == null)
		{
			synchronized(MySQLDatabase.class)
			{
				if(_instance == null)
				{
					_instance = new MySQLDatabase();
				}
			}
		}
		
		return _instance;
	}
}
