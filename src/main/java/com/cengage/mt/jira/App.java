package com.cengage.mt.jira;

import java.io.IOException;

/**
 * Hello world!
 *
 */


public class App 
{
    public static void main( String[] args ) throws Exception
    {
        

    	String baselineFile = args[0];
        String snapshotFile = args[1];
        
        JiraFileLoader loader = new JiraFileLoader();
        Release release = new Release();

			release.setBaselineIssues(loader.load(baselineFile));
			release.setSnapshotIssues(loader.load(snapshotFile));
			
			release.comparison();
    }
    

}
