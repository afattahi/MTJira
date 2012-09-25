package com.cengage.mt.jira;

import java.util.Formatter;

import org.codehaus.jettison.json.JSONObject;

import com.atlassian.jira.rest.client.domain.Issue;

public class JiraIssue {
	
	private Issue i;
	
	private String key;
	private String summary;
	private Double points;
	private String issueType;
	private String status;
	private String team;

	public static final String STORY_POINTS = "customfield_10792";
	public static final String SCRUM_TEAM = "customfield_11261"; 
	
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
			//System.out.println("TEAM " + i.getField(SCRUM_TEAM).getType()+i.getField(SCRUM_TEAM).getValue());
			JSONObject o = (JSONObject) i.getField(SCRUM_TEAM).getValue();
			if (o!=null) {
			this.team = o.getString("value");
			}
			
		} catch ( Exception e) {
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
		return points;
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
	
	

}
