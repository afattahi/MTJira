package com.cengage.mt.jira;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.cengage.mt.jira.json.JiraIssue;

public class ReadJason {
	
	
	 public static void main( String[] args ) throws Exception
	    {
		 
	    //  getIssue();
	      searchIssues();  
	        

	        
	    }

	private static void getIssue() throws MalformedURLException, IOException {
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
	 
	  static void searchIssues() throws Exception { 
		   URL  url = new URL("http://jira.cengage.com/rest/api/2/search?jql=fixversion=%222.2%22");
		     //   String query = INSERT_HERE_YOUR_URL_PARAMETERS;

		        //make connection
		        URLConnection urlc = url.openConnection();
		     

		        urlc.setDoOutput(true);

		        urlc.setRequestProperty("Authorization", "Basic YW5hcy5mYXR0YWhpOkFmYXR0YWhp");
		   
		      /*  BufferedReader br = new BufferedReader(new InputStreamReader(urlc
		            .getInputStream()));
		        String l = null;
		        StringBuilder sb = new StringBuilder();
		        while ((l=br.readLine())!=null) {
		            sb.append(l);
		        	System.out.println(l);
		        }
		        br.close();
		        
		        System.out.println(sb.toString());
		    */
		        JsonReader jsonReader = new JsonReader(new InputStreamReader(urlc
			            .getInputStream()));
		        //System.out.println(jsonReader.);
		     //   jsonReader.beginObject()
		        int count = 0;
		        while (jsonReader.hasNext()) {
		        	count++;
		        	jsonReader.skipValue();
		        }
		        System.out.println(count);
		        jsonReader.endObject();
		        
		        jsonReader.beginObject();
		       System.out.println(jsonReader.nextName() );
		     
		       
		       /*Gson gson = new GsonBuilder().setPrettyPrinting().create();
		      
		        Type collectionType = new TypeToken<Collection<JiraIssue>>(){}.getType();
		        Collection<JiraIssue> j =  gson.fromJson(sb.toString(), collectionType);
		        
		*/
		        
		       // System.out.println("JIRA ID:"+j.getFields().getAssignee().getName());
		        		        
		        
		       
		 
	 }
	 

}
