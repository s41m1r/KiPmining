package at.ac.wu.is.psc.database;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Map.Entry;

import at.ac.wu.is.psc.model.Commit;
import at.ac.wu.is.psc.model.Language;
import at.ac.wu.is.psc.model.Stargazer;

import java.util.TreeMap;
import java.util.TreeSet;

public class DataAccess 
{	
	public static int getProjectID(String owner, String name)
	{
		int pID = -1;
		
		try
		{
			CloseableResultSet rs = MySQLDatabase.sql("SELECT p.id FROM projects p, users u WHERE p.name = '" + name + "' AND p.owner_id = u.id AND u.login='" + owner + "'");
			rs.getResultSet().next();
			pID = rs.getResultSet().getInt(1);
			rs.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return pID;
	}
	
	public static TreeMap<Integer, Stargazer> getStargazers(int pID)
	{
		TreeMap<Integer, Stargazer> stargazers = null;
		
		try
		{
			CloseableResultSet rs = MySQLDatabase.sql("SELECT u.id, u.login, w.created_at FROM watchers w, users u WHERE w.repo_id = " + pID + " AND w.user_id = u.id");
			stargazers = new TreeMap<>();
			
			while(rs.getResultSet().next())
			{
				int userID = rs.getResultSet().getInt(1);
				String userLogin = rs.getResultSet().getString(2);
				LocalDateTime watchedAt = rs.getResultSet().getTimestamp(3).toLocalDateTime();
				stargazers.put(userID, new Stargazer(userID, userLogin, watchedAt));
			}
			rs.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return stargazers;
	}
	
	public static TreeSet<Integer> getContributors(int pID)
	{
		TreeSet<Integer> contributors = null;
		
		try
		{
			CloseableResultSet rs = MySQLDatabase.sql("SELECT c.author_id AS user_id FROM commits c WHERE c.project_id = " + pID + " UNION SELECT i.reporter_id AS user_id FROM issues i WHERE i.repo_id = " + pID);
			contributors = new TreeSet<>();
			
			while(rs.getResultSet().next())
			{
				int userID = rs.getResultSet().getInt(1);
				contributors.add(userID);
			}
			rs.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return contributors;
	}

	public static TreeMap<Language, Double> getLanguages(int pID) 
	{
		TreeMap<Language, Double> languages = null;
		
		try
		{
			CloseableResultSet rs = MySQLDatabase.sql("SELECT language, bytes FROM project_languages WHERE project_id = " + pID + " ORDER BY created_at ASC;");
			languages = new TreeMap<>();
			
			while(rs.getResultSet().next())
			{
				String language = rs.getResultSet().getString(1);
				long bytes = rs.getResultSet().getLong(2);
				
				languages.put(new Language(language, bytes), (double)bytes);
			}
			
			double totalBytes = languages.values().stream().mapToDouble(d -> d).sum();
			
			for(Entry<Language, Double> language : languages.entrySet())
			{
				language.setValue(language.getValue() / totalBytes);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return languages;
	}
	
	public static TreeMap<Integer, Commit> getCommits(int pID)
	{
		TreeMap<Integer, Commit> commits = null;
		
		try
		{
			CloseableResultSet rs = MySQLDatabase.sql("SELECT c.id, c.sha, p.parent_id FROM commits c, commit_parents p WHERE c.project_id = " + pID + " AND c.id = p.commit_id");
			commits = new TreeMap<>();
			
			while(rs.getResultSet().next())
			{
				int id = rs.getResultSet().getInt(1);
				String sha = rs.getResultSet().getString(2);
				String parent = rs.getResultSet().getString(3);
				
				Commit c = commits.getOrDefault(id, new Commit(id, sha));
				c.addParent(parent);
				commits.putIfAbsent(id, c);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return commits;
	}
	
	public static int getIssueCount(int pID)
	{
		int issueCount = 0;
		
		try
		{
			CloseableResultSet rs = MySQLDatabase.sql("SELECT COUNT(*) FROM issues WHERE repo_id = " + pID);
			
			while(rs.getResultSet().next())
			{
				issueCount = rs.getResultSet().getInt(1);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return issueCount;
	}
}