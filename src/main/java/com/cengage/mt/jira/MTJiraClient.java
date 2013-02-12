package com.cengage.mt.jira;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.atlassian.jira.rest.client.JiraRestClient;
import com.atlassian.jira.rest.client.NullProgressMonitor;
import com.atlassian.jira.rest.client.domain.BasicIssue;
import com.atlassian.jira.rest.client.domain.Issue;
import com.atlassian.jira.rest.client.domain.SearchResult;
import com.atlassian.jira.rest.client.internal.jersey.JerseyJiraRestClientFactory;

public class MTJiraClient {
	
	private final String USER_NAME = "********";
	private final String PASSWORD = "******";
	private final String JIRA_URL = "********";
	private static final  int  MAX_ISSUES= 50;
	
	
	private final JiraRestClient restClient;
	final NullProgressMonitor pm;
	
	public MTJiraClient () {
		
		try {
		final JerseyJiraRestClientFactory factory = new JerseyJiraRestClientFactory();
	    
		final URI jiraServerUri = new URI(JIRA_URL);
	    
	    restClient = factory.createWithBasicHttpAuthentication(jiraServerUri, USER_NAME, PASSWORD);
	    pm = new NullProgressMonitor();
	    
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public final JiraRestClient getClient() {
		return restClient;
	}
	
	public  List<Issue> retrieveIssues( String jql) {
		
		List<Issue> issues= new ArrayList<Issue>();
		SearchResult searchResult = restClient.getSearchClient().searchJql(jql,MAX_ISSUES,0, pm);
		int totalIssues= searchResult.getTotal();
		int requiredFetches = totalIssues /MAX_ISSUES;
				
		   retrieveIssueRange( issues, searchResult);
		
		for( int i= 1; i<=requiredFetches;i++) {
			
			searchResult = restClient.getSearchClient().searchJql(jql,MAX_ISSUES,i*MAX_ISSUES, pm);
			
			 retrieveIssueRange( issues, searchResult);
			
		}
		
		return issues;
	}
	
	public List<JiraIssue> retrieveJiraIssues(String jql) {
		
		List<Issue> issues= new ArrayList<Issue>();
		List<JiraIssue> jiraIssues = new ArrayList<JiraIssue>();
		issues = retrieveIssues(jql);
		
		 for (Issue i : issues) {
		   
		    	jiraIssues.add(new JiraIssue(i));  
		 }
		 
		 return jiraIssues;
	}


	public void retrieveIssueRange( List<Issue> issues,
			SearchResult searchResult) {
		for (BasicIssue issue : searchResult.getIssues()) {
			
			//System.out.println(issue.getKey());
		   Issue issueDetails =  restClient.getIssueClient().getIssue(issue.getKey(), pm);
		   issues.add(issueDetails);
		  // System.out.println(issueDetails.getSummary());
		  // System.out.println(issueDetails.getFieldByName("Story Points"));
		}

	}
	

}
