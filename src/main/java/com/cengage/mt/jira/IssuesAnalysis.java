package com.cengage.mt.jira;

import java.util.ArrayList;
import java.util.List;

public class IssuesAnalysis {
	
	private static final String STATUS_VERIFIED = "VERIFIED";
	private static final String STATUS_CLOSED = "Closed";
	private static final String STATUS_IN_PROGRESS = "In Progress";
	private static final String STATUS_ACTIVE = "Active";
	private static final String STATUS_REVEIWED = "Reviewed";
	private static final String STATUS_READY_FOR_QA = "Ready for QA";
	private static final String STATUS_COMPLETE = "Complete";
	private static final String STATUS_QA_ACTIVE = "QA Active";
	private static final String STATUS_REOPENED = "Reopened";
	private static final String STATUS_READY = "Ready";
	private static final String STATUS_OPEN = "Open";
	
	private List<JiraIssue> issues;
	private List<JiraIssue> doneIssues;
	private List<JiraIssue> inProgressIssues;
	private List<JiraIssue> backlogIssues;
	private List<JiraIssue> noEstimateIssues;
	private List<JiraIssue> inDevIssues;
	private List<JiraIssue> inQAIssues;
	
	
	public IssuesAnalysis(List<JiraIssue> issues) {
		
		this.issues = new ArrayList<JiraIssue>(issues);
		init();
		analyize();
			
	}
	
	public Double getDonePoints () {
		return getPoints(doneIssues);
	}
	
	public int getDoneIssueCount () {
		return doneIssues.size();
	}
	
	public Double getBacklogPoints () {
		return getPoints(doneIssues);
	}
	
	public int getBacklogIssueCount () {
		return doneIssues.size();
	}
	
	
	public Double getTotalPoints() {
		
		return getPoints(this.issues); 
	}

	public Double getPoints(List<JiraIssue> issues) {
		
		Double totalPoints = 0.0;
		
		for (JiraIssue i:issues) {
			totalPoints += i.getPoints();
		}
		return totalPoints ;
	}
	
	public int getTotalIssues() {
		
		return issues.size();
	}

	private void init() {
		doneIssues = new ArrayList<JiraIssue>();
		inProgressIssues = new ArrayList<JiraIssue>();
		backlogIssues = new ArrayList<JiraIssue>();
		noEstimateIssues = new ArrayList<JiraIssue>();
		inDevIssues = new ArrayList<JiraIssue>();
		inQAIssues = new ArrayList<JiraIssue>();
	}

	private void analyize () {
		breakCardsByStatus();
		calculateNoEstimateIssues();
		
	}
	
	
	private void calculateNoEstimateIssues () {
		
		for ( JiraIssue issue: issues) {
			if (issue.getPoints().equals(0.00) || 
				issue.getPoints().equals(1.399) || 
				issue.getPoints().equals(1.599) ||
				issue.getPoints().equals(0.49) ) {
				
				noEstimateIssues.add(issue);
			}
		}
	}
	
	private boolean isDone(String status) {
		return (status.equalsIgnoreCase(STATUS_CLOSED) || status.equalsIgnoreCase(STATUS_VERIFIED));
	}
	
	
	private void breakCardsByStatus () {
		
		for (JiraIssue i: issues) {
			if (isDone(i.getStatus())) {
				doneIssues.add(i);
			} else if (isInProgress(i.getStatus())) {
				inProgressIssues.add(i);
				if (isInDev(i.getStatus())) {
					inDevIssues.add(i);
				} else if (isInQA ( i.getStatus()) ) 
				{
					inQAIssues.add(i);
				}
			} else if (isInBacklog(i.getStatus())) {
				backlogIssues.add(i);
			} else 
				throw new RuntimeException("Uknown Issue status: "+i.getStatus());
		}
	}
	
	private boolean isInQA(String status) {
		return (status.equalsIgnoreCase(STATUS_READY_FOR_QA)||status.equalsIgnoreCase(STATUS_QA_ACTIVE));
	}
	
	private boolean isInDev(String status) {
		
		return (status.equalsIgnoreCase(STATUS_IN_PROGRESS)||status.equalsIgnoreCase(STATUS_ACTIVE)
				|| status.equalsIgnoreCase(STATUS_REVEIWED));
	}


	private boolean isInBacklog(String status) {
		
		return (status.equalsIgnoreCase(STATUS_OPEN)||
				status.equalsIgnoreCase(STATUS_READY)||
				status.equalsIgnoreCase(STATUS_REOPENED));
	}

	


	private boolean isInProgress(String status) {
		return (status.equalsIgnoreCase(STATUS_QA_ACTIVE) ||
				status.equalsIgnoreCase(STATUS_COMPLETE) ||
				status.equalsIgnoreCase(STATUS_READY_FOR_QA) ||
				status.equalsIgnoreCase(STATUS_REVEIWED) ||
				status.equalsIgnoreCase(STATUS_ACTIVE) ||
				status.equalsIgnoreCase(STATUS_IN_PROGRESS) );
	
	}

	public List<JiraIssue> getIssues() {
		return issues;
	}

	public List<JiraIssue> getDoneIssues() {
		return doneIssues;
	}

	public List<JiraIssue> getInProgressIssues() {
		return inProgressIssues;
	}

	public List<JiraIssue> getBacklogIssues() {
		return backlogIssues;
	}

	public List<JiraIssue> getNoEstimateIssues() {
		return noEstimateIssues;
	}

	public List<JiraIssue> getInDevIssues() {
		return inDevIssues;
	}

	public List<JiraIssue> getInQAIssues() {
		return inQAIssues;
	}

}
