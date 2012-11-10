package com.cengage.mt.jira;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class FY13EpicsManager {
	
	Map<String,Epic> epics;
	
	public FY13EpicsManager () {
		epics = new Hashtable<String,Epic>();
	}
	
	public void aggregateData(List<JiraIssue> issues) {
		
		for (JiraIssue i : issues) {
			
			String epicName = i.getEpic();
			String issueType = i.getIssueType();
			
			if ( epics.containsKey(epicName)) {
				Epic epic = epics.get(epicName);
				if(issueType.equals("Epic")) {
					epic.setEpic(i);
				} else {
					epic.addIssue(i);
				}
			} else {
				Epic epic = new Epic();
				
				if(issueType.equals("Epic")) {
					epic.setEpic(i);
				} else {
					epic.addIssue(i);
				}
				//epic.addIssue(i);
				epics.put(i.getEpic(), epic);
			}
		}
	}
	
	public void printEpics() {
		System.out.println("Printing");
		System.out.println("Key \t Epic Point \t Story Points \t Story Count \t Epic Label \t Epic Summary");
		for (Epic epic: epics.values()) {
			System.out.printf("%10s, %5.2f, %5.2f, %5.2f, %5d, %50s, %s\n",epic.getKey(),epic.getEpicPoints(),epic.getStoryPoints(),epic.getStoryPoints()-epic.getEpicPoints(),epic.getIssueCount(),epic.getEpicLabel(),epic.getSummary());
		}
	}
	
	public void printFeature() {
		System.out.println("Printing");
		
		for (Epic epic: epics.values()) {
			//System.out.printf("%10s\t%5.2f\t%5.2f\t%5.2f\t%5d\t%50s\t%s\n",epic.getKey(),epic.getEpicPoints(),epic.getStoryPoints(),epic.getStoryPoints()-epic.getEpicPoints(),epic.getIssueCount(),epic.getEpicLabel(),epic.getSummary());
			System.out.printf("%10s,%5.2f,%5.2f,%5.2f,%5d,%50s,%s\n",epic.getKey(),epic.getEpicPoints(),epic.getStoryPoints(),epic.getStoryPoints()-epic.getEpicPoints(),epic.getIssueCount(),epic.getEpicLabel(),epic.getSummary());
		}
	}

}
