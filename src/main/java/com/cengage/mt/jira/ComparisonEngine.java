package com.cengage.mt.jira;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ComparisonEngine {

//	private List<JiraIssue> snapshotIssues;
	private IssuesAnalysis baselineIssuesAnalysis;
	private IssuesAnalysis snapshotIssuesAnalysis;
	
	private List<JiraIssue> removedIssues;
	private List<JiraIssue> addedIssues;
	private Map<JiraIssue, JiraIssue> modifiedIssues;
	

	private final String marker = "=============================================================\n";
	private StringBuilder report;

	
	public ComparisonEngine(List<JiraIssue> baseline, List<JiraIssue> snapshot) {
		
	//	baselineIssues = new ArrayList<JiraIssue>();
		
		baselineIssuesAnalysis = new IssuesAnalysis(baseline);
		
		snapshotIssuesAnalysis = new IssuesAnalysis(snapshot);
		
		init();
		
	}
	
	public void init () {
		
		addedIssues = new ArrayList<JiraIssue>();
		removedIssues = new ArrayList<JiraIssue>();
		modifiedIssues = new Hashtable<JiraIssue, JiraIssue>();
		report = new StringBuilder();
		
	}
	
	public void createOutFile (String reportFile) {
		 try{
			  // Create file 
			  FileWriter fstream = new FileWriter(reportFile);
			  BufferedWriter out = new BufferedWriter(fstream);
			  out.write(report.toString());
			  //Close the output stream
			  out.close();
			  }catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  }
	 }
	

	private Double totalModifiedPoints() {
		
		Double totalBaseline = 0.0;
		Double totalOthers = 0.0;
		for (JiraIssue issue : modifiedIssues.keySet()) {
			JiraIssue otherIssue = modifiedIssues.get(issue);
	
			totalBaseline += issue.getPoints();
			totalOthers += otherIssue.getPoints();
		}
		return totalBaseline - totalOthers;
	}

	private Integer totalModifiedIssues() {
	
		return modifiedIssues.size();
	}

	private Double totalRemovedPoints() {
		Double total = 0.0;
		for (JiraIssue i:removedIssues) {
			total += i.getPoints();
		}
		return total;
	}

	private Integer totalRemovedIssues() {
		return removedIssues.size();
	}

	private Double totalAddedPoints() {
		
		Double total = 0.0;
		for (JiraIssue i:addedIssues) {
			total += i.getPoints();
		}
		return total;
	}

	private Integer totalAddedIssues() {
		
		return this.addedIssues.size();
	}


	private void calculateModifiedIssues(List<JiraIssue> jiraIssues) {
		
		for (JiraIssue issue :this.baselineIssuesAnalysis.getIssues()) {
			for (JiraIssue otherIssue : jiraIssues) {
				if (issue.equals(otherIssue)) {
					if (! issue.getPoints().equals( otherIssue.getPoints())) {
						modifiedIssues.put(issue, otherIssue);
					}
				}
			}
		}
		
		for (JiraIssue issue :modifiedIssues.keySet()) {
			JiraIssue otherIssue = modifiedIssues.get(issue);
		}
		
	}
	

	private void calculateAddedCards(List<JiraIssue> jiraIssues) {
		Set<JiraIssue> addedIssues = new HashSet<JiraIssue>(jiraIssues);
		
		addedIssues.removeAll(this.baselineIssuesAnalysis.getIssues());
		
		removedIssues.removeAll(jiraIssues);
		
		Iterator i = addedIssues.iterator();
		
		while (i.hasNext()) {
			JiraIssue issue = (JiraIssue) i.next();
			this.addedIssues.add(issue);
			//System.out.println(issue);
		}
	}

	private void calculateRemovedIssues(List<JiraIssue> jiraIssues) {
		Iterator i;
		Set<JiraIssue> removedIssues = new HashSet<JiraIssue>(this.baselineIssuesAnalysis.getIssues());
		
		removedIssues.removeAll(jiraIssues);
		
		i = removedIssues.iterator();
		
		while (i.hasNext()) {
			JiraIssue issue = (JiraIssue) i.next();
			this.removedIssues.add(issue);
			//System.out.println(issue);
		}
	}
	
	public void comparison() {
		
		
		calculateAddedCards(snapshotIssuesAnalysis.getIssues());
		
		calculateRemovedIssues(snapshotIssuesAnalysis.getIssues());
		
		calculateModifiedIssues(snapshotIssuesAnalysis.getIssues());
		
		generateReport();

	}
	
	
	
	public void generateReport () {
		
	System.out.println("Running the report....");
		
		Integer totalAddedIssues = totalAddedIssues();
		Double  totalAddedPoints = totalAddedPoints();
		
		
		generateLineSummary("ADDED", totalAddedIssues, totalAddedPoints);
		Integer totalRemovedIssues = totalRemovedIssues();
		Double  totalRemovedPoints = totalRemovedPoints();
		generateLineSummary ("REMOVED", totalRemovedIssues,totalRemovedPoints);
		Integer totalModifiedIssues = totalModifiedIssues();
		Double  totalModifiedPoints = totalModifiedPoints();
		generateLineSummary ("MODIFIED", totalModifiedIssues,totalModifiedPoints);
		
		gernerateNetChange();
		
		generateDetails();
		System.out.println(report);
	}

	private void generateDetails() {
		
		report.append(marker);
		report.append("\tDETAILS\n");
		report.append(marker);
		report.append("\n");
		report.append("++++ NOT ESTIMATED CARDS ++++\n");
		printIssues(snapshotIssuesAnalysis.getNoEstimateIssues());
		report.append("++++ ADDED CARDS ++++\n");
		printIssues (addedIssues);
		report.append("\n");
		report.append("++++ REMOVED CARDS ++++\n");
		printIssues (removedIssues);
		report.append("\n");
		report.append("++++ MODIFIED CARDS ++++\n");
		printModifiedIssues (modifiedIssues);
		report.append("\n");
		
	}

	private void printModifiedIssues(Map<JiraIssue, JiraIssue> modifiedIssues) {
		
		Formatter f = new Formatter();
		for (JiraIssue baselineIssue:modifiedIssues.keySet()) {
			JiraIssue snapshotIssue = modifiedIssues.get(baselineIssue);
			f.format("%-10s: %-18s %5.2f %5.2f %+5.2f \t %s\n",baselineIssue.getKey(),baselineIssue.getIssueType(),baselineIssue.getPoints(),
						snapshotIssue.getPoints(),  snapshotIssue.getPoints() -baselineIssue.getPoints(), baselineIssue.getSummary());
			
		}
		report.append(f);
		
	}

	private void printIssues(List<JiraIssue> addedIssues) {
		
		for (JiraIssue i: addedIssues) {
			report.append(i.toString()+"\n");
		}
	}

	private void gernerateNetChange() {
		
		Integer baselineTotalIssues = baselineIssuesAnalysis.getTotalIssues();
		Double baselineTotalPoints = baselineIssuesAnalysis.getTotalPoints();
		Integer snapshotTotalIssues = snapshotIssuesAnalysis.getTotalIssues();
		Double snapshotTotalPoints =  snapshotIssuesAnalysis.getTotalPoints();
		Integer baselineDoneTotalIssues = baselineIssuesAnalysis.getDoneIssueCount();
		Double baselineDoneTotalPoints = baselineIssuesAnalysis.getDonePoints();
		Integer snapshotDoneTotalIssues = snapshotIssuesAnalysis.getDoneIssueCount();
		Double snapshotDoneTotalPoints = snapshotIssuesAnalysis.getDonePoints();
	
		Formatter f = new Formatter();
		f.format("%20s\t Baseline \t Snapshot \t Change\n","Line");
		f.format("%20s\t %d \t\t %d \t\t %+d\n","Total Issues:",baselineTotalIssues,snapshotTotalIssues,snapshotTotalIssues-baselineTotalIssues);
		f.format("%20s\t %5.2f \t\t %5.2f \t\t %+5.2f\n","Total Points:",baselineTotalPoints,snapshotTotalPoints,snapshotTotalPoints-baselineTotalPoints);
		f.format("%20s\t %d \t\t %d \t\t %+d\n","Done Issues:",baselineDoneTotalIssues,snapshotDoneTotalIssues,snapshotDoneTotalIssues-baselineDoneTotalIssues);
		f.format("%20s\t %5.2f \t\t %5.2f \t\t %+5.2f\n","Done Points:", baselineDoneTotalPoints,snapshotDoneTotalPoints,snapshotDoneTotalPoints- baselineDoneTotalPoints);
		report.append(f);
		
	}


	private void generateLineSummary(String type, Integer issues, Double points) {
	
		//System.out.printf("** %s\t ISSUES ** COUNT: %d ** POINTS: %+5.2f **\n",type, issues,points);
	}


}
