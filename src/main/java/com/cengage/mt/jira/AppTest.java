package com.cengage.mt.jira;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Hello world!
 *
 */


public class AppTest 
{
    public static void main( String[] args ) 
    {
    	try {
        //    System.out.println(App.class.getProtectionDomain().getCodeSource().getLocation());
    	//	ResourceBundle.getBundle("env");
    		
    	String action = args[0];
   
        
        AppTest app = new AppTest();
        app.loadProperties();
        
        if (action.equalsIgnoreCase("compare")) {
        	
         	String baselineFile = args[1];
            String snapshotFile = args[2];
            String label = args[3];
        	app.generateComparison(baselineFile, snapshotFile, label);
        	
        } else if (action.equalsIgnoreCase("generate-snapshot")) {
        	
        	 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        	 System.out.println("Enter label for the snapshot: ");
        	String label = br.readLine();
        	System.out.println("nEnter you jql on one line:");
        	String jql = br.readLine();	
        	
        	app.generateSnapshot(label, jql);
        	
        } else if (action.equalsIgnoreCase("fy13")) {
        	/* BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        	 System.out.println("Enter label for the snapshot: ");
        	String label = br.readLine();
        	System.out.println("nEnter you jql on one line:");
        	String jql = br.readLine();	
        	*/
        	app.fy13Report("fy13", "filter=25483");
        	
        } else {
        	System.out.println("Unknown action... "+ action);
        	System.exit(-1);
        }	
        
    	} catch (Exception e) {
    		e.printStackTrace();
    	}

    }
    
    public void loadProperties() throws Exception {
     	
    	String externalFileName = System.getProperty("app.properties");
    	System.out.println(externalFileName);
    	InputStream in = new FileInputStream(new File(externalFileName));
    	
    	Properties result = null;
    	
        result = new Properties ();
        result.load (in); // Can throw IOException
        System.out.println(result.toString());
        AppProperties.setProperties(result);
   
    }
    
    public void generateComparison(String baselineFile, String snapshotFile, String label) {
    	
        
        JiraFileLoader loader = new JiraFileLoader();
        ComparisonEngine engine = new ComparisonEngine(loader.load(baselineFile),loader.load(snapshotFile));

			engine.comparison();
			engine.createOutFile(loader.getReport1FileName(label));
    	
    }
    
    
    public void generateSnapshot(String label, String jql) {
    	
		List<JiraIssue> jiraIssues = new ArrayList<JiraIssue>();		
		MTJiraClient client = new MTJiraClient();	
		jiraIssues =  client.retrieveJiraIssues(jql);
		
		JiraFileLoader loader = new JiraFileLoader();
		
		loader.generateExcelSheet(loader.getSnapshotFileName(label),jiraIssues,jql);
    	
    }
    
    public void fy13Report (String label, String jql) {
    	
		List<JiraIssue> jiraIssues = new ArrayList<JiraIssue>();		
		MTJiraClient client = new MTJiraClient();	
		jiraIssues =  client.retrieveJiraIssues(jql);
		
		FY13EpicsManager manager = new FY13EpicsManager();
		System.out.println("Aggragating data");
		manager.aggregateData(jiraIssues);
		System.out.println("Printing...");
		manager.printEpics();
    
    }

}
