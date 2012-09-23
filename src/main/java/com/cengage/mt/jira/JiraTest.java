package com.cengage.mt.jira;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.atlassian.jira.rest.client.JiraRestClient;
import com.atlassian.jira.rest.client.NullProgressMonitor;
import com.atlassian.jira.rest.client.domain.BasicIssue;
import com.atlassian.jira.rest.client.domain.Issue;
import com.atlassian.jira.rest.client.domain.SearchResult;
import com.atlassian.jira.rest.client.internal.jersey.JerseyJiraRestClientFactory;

public class JiraTest {

	/**
	 * @param args
	 */

	
	public static void main(String[] args)  {
	   
		try {
			
			
			List<JiraIssue> jiraIssues = new ArrayList<JiraIssue>();
		
				MTJiraClient client = new MTJiraClient();
			    
				String jql="project=NG and fixVersion=\"2.3\" and \"Scrum Team\" = Mobile order by issuekey";
			    		    
			 //   jiraIssues =  client.retrieveJiraIssues(jql);
			    
			    generateSnapshot("test",jql);
			    	
			    
			    for (JiraIssue i :jiraIssues) {
			    	System.out.println("KEY: "+ i.getKey()+" Points: "+ i.getPoints()+" Team: "+ i.getTeam());
			    }
		
			
		} catch (Exception e) {
			
			System.out.println(e.getCause());
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}
	

	
	public static void generateSnapshot(String label, String jql) {
		
		List<JiraIssue> jiraIssues = new ArrayList<JiraIssue>();		
		MTJiraClient client = new MTJiraClient();	
		jiraIssues =  client.retrieveJiraIssues(jql);
		
		JiraFileLoader loader = new JiraFileLoader();

		loader.generateExcelSheet(loader.getSnapshotFileName(label),jiraIssues,jql);
		
		
	}
	
	
	private static String generateHeader () {
		
		
		return new String();
	}

}