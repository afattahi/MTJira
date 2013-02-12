package com.cengage.mt.jira;

import java.util.Hashtable;
import java.util.Map;

public class Epic {


	Map<String,JiraIssue> issues;
	JiraIssue epic;
	
 
 	public Epic() {
 		init();
 	}
 	public Epic(JiraIssue i) {
 		
 		epic = i;
 		init();
 	
 	}
 	
 	public String getEpicLabel() {
 		
 		
 		return (epic == null||epic.getEpic()==null?"UNKNOWN":epic.getEpic());
 	}
 	
 	public String getSummary() {
 		return (epic == null||epic.getSummary()==null?"UNKNOWN":epic.getSummary());
 	}
 	
 	private void init() {
 		issues = new Hashtable<String,JiraIssue>();
 	}
 	
 	public void addIssue(JiraIssue i) {
 		issues.put(i.getKey(), i);
 	}
 	
 	public String getFeature() {
 		return (epic == null||epic.getFeature()==null?"UNKNOWN":epic.getFeature());
 	}
 	public Double getEpicPoints() {
 		return (epic==null||epic.getPoints()==null?0.0:epic.getPoints());
 	}
 	
 	
 	public Double getEpicAdjustedPoints() {
 		
 		if (epic==null||epic.getPoints()==null) {
 			return 0.0;
 		} else if  (epic.getConfidenceFactor() == 0.0) {
 			return (epic.getPoints()*2.0 + epic.getPoints())/2;
 		} else if (epic.getConfidenceFactor() == 10.0 ||epic.getConfidenceFactor() == 9.0 ) {
 			return epic.getPoints();
 		} else if (epic.getConfidenceFactor() == 8.0 ||epic.getConfidenceFactor() == 7.0 
 					||epic.getConfidenceFactor() == 6.0 ||epic.getConfidenceFactor() == 5.0 ) {
 			return (epic.getPoints()*2.0 + epic.getPoints())/2;
 		} else if (epic.getConfidenceFactor() == 1.0 ||epic.getConfidenceFactor() == 2.0 
					||epic.getConfidenceFactor() == 3.0 ||epic.getConfidenceFactor() == 4.0 ) {
			return (epic.getPoints()*3.0 + epic.getPoints())/2;
 		} else {
 			return -999999999.99;
 		}

 	}
 	
 	
 	public Double getStoryPoints() {
 		Double points = 0.0;
 		for (JiraIssue i:issues.values()) {
 			points+= i.getPoints();
 			
 		}
 		
 		return points;
 	}
 	
 
 	

 	public int getIssueCount() {
 		return issues.size();
 	}
 	
 	public void setEpic(JiraIssue i) {
 		epic = i;
 	}
	public String getKey() {
		
		return (epic==null?"UNKNOWN":epic.getKey());
	}
 	
}