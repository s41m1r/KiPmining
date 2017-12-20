package at.ac.wu.is.psc.api;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import at.ac.wu.is.psc.model.Branch;
import at.ac.wu.is.psc.model.Stargazer;

public class GithubAPI 
{			
	public static ArrayList<Branch> getAllBranches(String project)
	{
		LinkedList<JSONArray> jsonResponses = GithubAPI.getAllPages("/repos/" + project + "/branches");
		
		ArrayList<Branch> branches = new ArrayList<>();
		
		for(JSONArray jsonResponse : jsonResponses)
		{
			for(int i = 0; i < jsonResponse.length(); i++)
			{
				JSONObject jsonObject = jsonResponse.getJSONObject(i);
				Branch branch= new Branch(jsonObject.getString("name"));
				
				branches.add(branch);
			}
		}
		
		return branches;		
	}
	
	private static LinkedList<JSONArray> getAllPages(String apiPath)
	{
		Link nextPage = Link.fromUri("https://api.github.com" + apiPath + "?per_page=100&page=1").build();
		Response queryResponse;
		
		LinkedList<JSONArray> jsonResponses = new LinkedList<>();
		
		int page = 0;
		do
		{
			page++;
			
			queryResponse = GithubAPI.query(nextPage.getUri().toString());
			
			System.out.println(queryResponse);
			
			
			JSONArray jsonResponse = new JSONArray(queryResponse.readEntity(String.class));
			
			jsonResponses.add(jsonResponse);
			
		}
		while((nextPage = queryResponse.getLink("next")) != null);
		
		return jsonResponses;		
	}
	
	private static Response query(String uri)
	{
		Client client = ClientBuilder.newClient();
		//Load GitHub API access token from file .token 
		String TOKEN = loadToken(".token");
		return client.target(uri + "&access_token=" + TOKEN).request().get();
	}

	private static String loadToken(String string) {
		try {
			FileReader fr = new FileReader(string);
			BufferedReader br = new BufferedReader(fr);
			String token = br.readLine();
			br.close();
			return token;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
