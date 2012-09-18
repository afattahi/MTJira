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

public class Release {
	private static final String BASE_DIR = "/Users/afattahi/Desktop/";

	private List<JiraIssue> baselineIssues;
	
	private List<JiraIssue> removedIssues;
	private List<JiraIssue> noEstimateIssues;
	private List<JiraIssue> addedIssues;
	private Map<JiraIssue, JiraIssue> modifiedIssues;
	private List<JiraIssue> snapshotIssues;
	private final String marker = "=============================================================\n";
	private StringBuilder report;
	
	public Release() {
		
		baselineIssues = new ArrayList<JiraIssue>();
		addedIssues = new ArrayList<JiraIssue>();
		removedIssues = new ArrayList<JiraIssue>();
		modifiedIssues = new Hashtable<JiraIssue, JiraIssue>();
		report = new StringBuilder();
		
	}
	
	public void createOutFile () {
		 try{
			  // Create file 
			  FileWriter fstream = new FileWriter(BASE_DIR+"ReleaseReport.txt");
			  BufferedWriter out = new BufferedWriter(fstream);
			  out.write(report.toString());
			  //Close the output stream
			  out.close();
			  }catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  }
	 }
	

	public void setBaselineIssues(List<JiraIssue> issues) {
		
		this.baselineIssues = issues;
	}
	
	public void setSnapshotIssues(List<JiraIssue> issues) {
		this.snapshotIssues = issues;
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

	private void calculateBaseLineNoEstimateIssues (List<JiraIssue> jiraIssues) {
		for ( JiraIssue issue: jiraIssues) {
			if (issue.getPoints().equals(0.0) || issue.getPoints().equals(1.399)) {
				noEstimateIssues.add(issue);
			}
		}
	}
	private void calculateModifiedIssues(List<JiraIssue> jiraIssues) {
		
		for (JiraIssue issue :this.baselineIssues) {
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
		
		addedIssues.removeAll(this.baselineIssues);
		
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
		Set<JiraIssue> removedIssues = new HashSet<JiraIssue>(this.baselineIssues);
		
		removedIssues.removeAll(jiraIssues);
		
		i = removedIssues.iterator();
		
		while (i.hasNext()) {
			JiraIssue issue = (JiraIssue) i.next();
			this.removedIssues.add(issue);
			//System.out.println(issue);
		}
	}
	
	public void comparison() {
		
		
		calculateAddedCards(snapshotIssues);
		
		calculateRemovedIssues(snapshotIssues);
		
		calculateModifiedIssues(snapshotIssues);
		
		generateReport();
		createOutFile ();
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
		
		Integer baselineTotalIssues = getBaseLineTotalIssues();
		Double baselineTotalPoints = getBaselineTotalPoints();
		Integer snapshotTotalIssues = getSnapshotTotalIssues();
		Double snapshotTotalPoints =  getSnapshotTotalPoints();
		Formatter f = new Formatter();
		f.format("%20s\t Baseline \t Snapshot \t Change\n","Line");
		f.format("%20s\t %d \t\t %d \t\t %+d\n","Total Issues:",baselineTotalIssues,snapshotTotalIssues,snapshotTotalIssues-baselineTotalIssues);
		f.format("%20s\t %5.2f \t\t %5.2f \t\t %+5.2f\n","Total Points:",baselineTotalPoints,snapshotTotalPoints,snapshotTotalPoints-baselineTotalPoints);
		report.append(f);
		
	}

	private Double getSnapshotTotalPoints() {
		
		Double totalPoints = 0.0;
		for (JiraIssue i:snapshotIssues) {
			totalPoints += i.getPoints();
		}
		return totalPoints ; 
		
	}

	private Integer getSnapshotTotalIssues() {
	
		return snapshotIssues.size();
	}

	private Double getBaselineTotalPoints() {
		
		Double totalPoints = 0.0;
		for (JiraIssue i:baselineIssues) {
			totalPoints += i.getPoints();
		}
		return totalPoints ; 
		
	}

	private Integer getBaseLineTotalIssues() {

		return baselineIssues.size();
	}

	private void generateLineSummary(String type, Integer issues, Double points) {
		//System.out.printf("** %s\t ISSUES ** COUNT: %d ** POINTS: %+5.2f **\n",type, issues,points);
	}


}
