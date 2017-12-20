package at.ac.wu.is.psc.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CloseableResultSet 
{
	private ResultSet resultSet;
	private Statement statement;
	
	public CloseableResultSet(ResultSet resultSet, Statement statement)
	{
		this.resultSet = resultSet;
		this.statement = statement;
	}
	
	public ResultSet getResultSet()
	{
		return this.resultSet;
	}
	
	public void close()
	{
		try
		{
			this.statement.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.statement = null;
			this.resultSet = null;
		}
	}
}
