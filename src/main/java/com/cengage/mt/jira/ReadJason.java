package com.cengage.mt.jira;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.cengage.mt.jira.json.JiraIssue;

public class ReadJason {
	
	
	 public static void main( String[] args ) throws Exception
	    {
		 
	      URL url = new URL("http://jira.cengage.com/rest/api/2/issue/NG-1000");
	     //   String query = INSERT_HERE_YOUR_URL_PARAMETERS;

	        //make connection
	        URLConnection urlc = url.openConnection();
	     

	        urlc.setDoOutput(true);

	        urlc.setRequestProperty("Authorization", "Basic YW5hcy5mYXR0YWhpOkFmYXR0YWhp");
	   
	        BufferedReader br = new BufferedReader(new InputStreamReader(urlc
	            .getInputStream()));
	        String l = null;
	        StringBuilder sb = new StringBuilder();
	        while ((l=br.readLine())!=null) {
	            sb.append(l);
	        	System.out.println(l);
	        }
	        br.close();
	        
	        Gson gson = new GsonBuilder().setPrettyPrinting().create();
	        JiraIssue j =  gson.fromJson(sb.toString(), com.cengage.mt.jira.json.JiraIssue.class);
	        
	        System.out.println("JIRA ID:"+j.getFields().getAssignee().getName());
	    }

}
