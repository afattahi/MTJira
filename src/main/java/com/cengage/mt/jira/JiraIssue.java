package com.cengage.mt.jira;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.atlassian.jira.rest.client.domain.Issue;

public class JiraIssue {
	
	private Issue i;
	
	private String key;
	private String summary;
	private Double points = 0.0;
	private String issueType;
	private String status;
	private String team;
	private List<String> features;
	private List<String> epics;
	private Double confidenceFactor;
	private int rank;

	public static final String STORY_POINTS = "customfield_10792";
	public static final String SCRUM_TEAM = "customfield_11261"; 
	public static final String EPIC = "customfield_10850"; 
	public static final String FEATURE = "customfield_12545";
	public static final String CONFIDENCE_FACTOR = "customfield_13532";
	public static final String GLOBAL_RANK = "customfield_11630";

	private static final String MULTIPLE_LABELS = "Multiple Labels in the field: "; 	
	
	public JiraIssue (String key,String summary, Double points) {
		this.key = key;
		this.summary = summary;
		this.points = points;
		
	}
	public JiraIssue (Issue i)  {
		
		try {
			
			this.i = i;
			this.key = i.getKey();
			this.summary = i.getSummary();
			this.points = (Double) i.getField(STORY_POINTS).getValue();
			this.issueType = i.getIssueType().getName();
			this.status = i.getStatus().getName();
			this.epics = new ArrayList<String>();
			this.features = new ArrayList<String>();
			//System.out.println("CONFIDENCE_FACTOR: "+ i.getField(CONFIDENCE_FACTOR).getValue());
		//	System.out.println("Rank: "+ i.getField(GLOBAL_RANK).toString());
			this.confidenceFactor = (Double) i.getField(CONFIDENCE_FACTOR).getValue();
			//System.out.println("TEAM " + i.getField(SCRUM_TEAM).getType()+i.getField(SCRUM_TEAM).getValue());
		//	this.rank = (Integer) i.getField(GLOBAL_RANK).getValue();
			JSONObject o = (JSONObject) i.getField(SCRUM_TEAM).getValue();
			
			JSONArray jsonArray = (JSONArray) i.getField(EPIC).getValue();
				if (jsonArray!=null) {
				for (int n=0; n< jsonArray.length(); n++) {
					epics.add(jsonArray.getString(n));
				}
			}
			
			 jsonArray = (JSONArray) i.getField(FEATURE).getValue();
				if (jsonArray!=null) {
				for (int n=0; n< jsonArray.length(); n++) {
					features.add(jsonArray.getString(n));
				}
			}
			
			
			
			if (o!=null) {
			this.team = o.getString("value");
			}
			
			if (epics.size()>1) {
				System.out.println(this.i.getKey()+" "+MULTIPLE_LABELS+"Epic/Theme");
			}
			if (features.size()>1) {
				System.out.println(this.i.getKey()+" "+MULTIPLE_LABELS+"Feature");
			}
			
		} catch ( Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	public String getIssueType() {
		return issueType;
	}

	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	final private String BASE_URL = "Http://http://jira.cengage.com/browse/";
	
	public JiraIssue () {
		
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof JiraIssue))
			return false;
		JiraIssue other = (JiraIssue) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equalsIgnoreCase(other.key))
			return false;
		return true;
	}

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Double getPoints() {
		return (points==null?0.0:points);
	}
	public void setPoints(Double points) {
		this.points = points;
	}
	
	public String getUrl () {
		return BASE_URL+getKey();
	}

	@Override
	public String toString() {
		Formatter f = new Formatter();
		return f.format("%-10s %-20s %-18s %-12s %10.2f %s",key ,team, issueType, status, points, summary).toString() ;
	}
	public String getFeature() {
		return (features.size()==0?" ":features.get(0));
	}

	public String getEpic() {
		return (epics.size()==0?" ":epics.get(0));
	}
	public Double getConfidenceFactor() {
		return  (confidenceFactor==null?0.0:confidenceFactor);
	}
	public void setConfidenceFactor(Double confidenceFactor) {
		this.confidenceFactor = confidenceFactor;
	}
		

}
