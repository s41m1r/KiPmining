package at.ac.wu.is.psc;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import at.ac.wu.is.psc.database.MySQLDatabase;
import at.ac.wu.is.psc.method.SimilarityMethod.SimilarityMeasure;
import at.ac.wu.is.psc.model.Project;
import at.ac.wu.is.psc.model.ProjectSimilarity;

public class ProjectSimilarityCalculator 
{

	public static void main(String[] args) 
	{
		if(args.length <= 0)
		{
			LinkedHashMap<String, Project> projects = new LinkedHashMap<>();
			LinkedList<Project> projectsA = new LinkedList<>();
			LinkedList<Project> projectsB = new LinkedList<>();
			LinkedList<String> projectSimilarityScores = new LinkedList<>();
			
			try
			{
				CSVParser csvSource = new CSVParser(new FileReader("./similarity_source.csv"), CSVFormat.newFormat(';'));
				csvSource.forEach((r) ->
				{
					String pOwnerA = r.get(0).split("/")[0];
					String pNameA = r.get(0).split("/")[1];
					String pOwnerB = r.get(1).split("/")[0];
					String pNameB = r.get(1).split("/")[1];
					
					Project pA = projects.getOrDefault(pOwnerA + "/" + pNameA, new Project(pOwnerA, pNameA));
					Project pB = projects.getOrDefault(pOwnerB + "/" + pNameB, new Project(pOwnerB, pNameB));
					
					projects.putIfAbsent(pOwnerA + "/" + pNameA, pA);
					projects.putIfAbsent(pOwnerB + "/" + pNameB, pB);
					
					projectsA.add(pA);
					projectsB.add(pB);
					projectSimilarityScores.add(r.get(2));
					
				});
				
				csvSource.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			
			MySQLDatabase.establishConnection();
			
			for(Project p : projects.values())
			{
				p.load();
			}
					
			LinkedList<ProjectSimilarity> projectSimilarities = new LinkedList<>();
			
			for(int i = 0; i < projectsA.size(); i++)
			{
				Project pA = projectsA.get(i);
				Project pB = projectsB.get(i);
				
				ProjectSimilarity projectSimilarity = new ProjectSimilarity(pA, pB);
			
				projectSimilarity.calculateSimilarities();
				projectSimilarities.add(projectSimilarity);
			}
			
			try
			{
				CSVPrinter csv = new CSVPrinter(new FileWriter("similarities.csv"), CSVFormat.DEFAULT);
				csv.printRecord("Project A", "Project B", "StarBased", "AdjustedStarBased", "StarTimeBased", "ContributorBased", "LanguageBased", "BranchBased", "ComplexityBased", "SimilarityRating");
				
				for(int i = 0; i < projectSimilarities.size(); i++)
				{
					ProjectSimilarity projectSimilarity = projectSimilarities.get(i);
					String projectSimilarityScore = projectSimilarityScores.get(i);
					Project projectA = projectsA.get(i);
					Project projectB = projectsB.get(i);
					
					// make sure linked lists behave as intended
					if(projectA == projectSimilarity.getProjectA() && projectB == projectSimilarity.getProjectB())
					{				
						System.out.println(projectSimilarity.getProjectA() + " | " + projectSimilarity.getProjectB() + " | " 
								+ projectSimilarity.getSimilarityValue(SimilarityMeasure.StarBased) + " | " 
								+ projectSimilarity.getSimilarityValue(SimilarityMeasure.AdjustedStarBased) + " | "
								+ projectSimilarity.getSimilarityValue(SimilarityMeasure.StarTimeBased) + " | "
								+ projectSimilarity.getSimilarityValue(SimilarityMeasure.ContributorBased) + " | "
								+ projectSimilarity.getSimilarityValue(SimilarityMeasure.LanguageBased) + " | "
								+ projectSimilarity.getSimilarityValue(SimilarityMeasure.BranchBased) + " | "
								+ projectSimilarity.getSimilarityValue(SimilarityMeasure.ComplexityBased) + " | "
								+ projectSimilarityScore);
						
						csv.printRecord(projectSimilarity.getProjectA().toString(), projectSimilarity.getProjectB().toString(),
								projectSimilarity.getSimilarityValue(SimilarityMeasure.StarBased),
								projectSimilarity.getSimilarityValue(SimilarityMeasure.AdjustedStarBased),
								projectSimilarity.getSimilarityValue(SimilarityMeasure.StarTimeBased),
								projectSimilarity.getSimilarityValue(SimilarityMeasure.ContributorBased),
								projectSimilarity.getSimilarityValue(SimilarityMeasure.LanguageBased),
								projectSimilarity.getSimilarityValue(SimilarityMeasure.BranchBased),
								projectSimilarity.getSimilarityValue(SimilarityMeasure.ComplexityBased),
								projectSimilarityScore);
					}
					else
					{
						System.out.println("Error in list order.");
					}
				}
				
				csv.flush();
				csv.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			String projectA = args[0];
			String projectB = args[1];
			
			Project pA = new Project(projectA.split("/")[0], projectA.split("/")[1]);
			Project pB = new Project(projectB.split("/")[0], projectB.split("/")[1]);
			
			MySQLDatabase.establishConnection();
			pA.load();
			pB.load();
			
			ProjectSimilarity projectSimilarity = new ProjectSimilarity(pA, pB);
			projectSimilarity.calculateSimilarities();
			
			System.out.println(projectSimilarity.getProjectA() + " | " + projectSimilarity.getProjectB() + " | " 
					+ projectSimilarity.getSimilarityValue(SimilarityMeasure.StarBased) + " | " 
					+ projectSimilarity.getSimilarityValue(SimilarityMeasure.AdjustedStarBased) + " | "
					+ projectSimilarity.getSimilarityValue(SimilarityMeasure.StarTimeBased) + " | "
					+ projectSimilarity.getSimilarityValue(SimilarityMeasure.ContributorBased) + " | "
					+ projectSimilarity.getSimilarityValue(SimilarityMeasure.LanguageBased) + " | "
					+ projectSimilarity.getSimilarityValue(SimilarityMeasure.BranchBased) + " | "
					+ projectSimilarity.getSimilarityValue(SimilarityMeasure.ComplexityBased));
		}
	}

}
