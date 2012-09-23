package com.cengage.mt.jira;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */


public class App 
{
    public static void main( String[] args ) 
    {
    	try {
        
    	String action = args[0];
   
        
        App app = new App();
        
        if (action.equalsIgnoreCase("compare")) {
        	
         	String baselineFile = args[1];
            String snapshotFile = args[2];
            String reportFile = args[3];
        	app.generateComparison(baselineFile, snapshotFile, reportFile);
        	
        } else if (action.equalsIgnoreCase("generate-snapshot")) {
        	
        	 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        	 System.out.println("Enter label for the snapshot: ");
        	String label = br.readLine();
        	System.out.println("nEnter you jql on one line:");
        	String jql = br.readLine();
        	
        	app.generateSnapshot(label, jql);
        	
        } else 
        	System.out.println("Unknown action... "+ action);
        	System.exit(-1);
        
    	} catch (Exception e) {
    		e.printStackTrace();
    	}

    }
    
    public void generateComparison(String baselineFile, String snapshotFile, String reportFile) {
    	
        
        JiraFileLoader loader = new JiraFileLoader();
        ComparisonEngine release = new ComparisonEngine(loader.load(baselineFile),loader.load(snapshotFile));

			release.comparison();
			release.createOutFile(reportFile);
    	
    }
    
    
    public void generateSnapshot(String label, String jql) {
    	
		List<JiraIssue> jiraIssues = new ArrayList<JiraIssue>();		
		MTJiraClient client = new MTJiraClient();	
		jiraIssues =  client.retrieveJiraIssues(jql);
		
		JiraFileLoader loader = new JiraFileLoader();
		
		loader.generateExcelSheet(loader.getSnapshotFileName(label),jiraIssues,jql);
    	
    }

}
