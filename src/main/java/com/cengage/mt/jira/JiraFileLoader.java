package com.cengage.mt.jira;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class JiraFileLoader {
	 
    private final int HEADER_OFFSET = 4;
    private final int FOOTER_OFFSET = 1;


	  public List<JiraIssue> load(String inputFile) throws Exception  {
	    
		File inputWorkbook = new File(inputFile);
	    Workbook w;
	  
	      w = Workbook.getWorkbook(inputWorkbook);
	      // Get the first sheet
	      Sheet sheet = w.getSheet(0);
	      // Loop over first 10 column and lines
	      Cell keyCell = sheet.findCell("Key");
	      Cell summaryCell = sheet.findCell("Summary");
	      Cell pointsCell = sheet.findCell("Story Points");
	      Cell statusCell = sheet.findCell("Status");
	      Cell issueTypeCell = sheet.findCell("Issue Type");
	      Cell teamCell = sheet.findCell("Scrum Team");
	      
	      List<JiraIssue> jiraIssues = new ArrayList<JiraIssue>();
	     
	        for (int i = HEADER_OFFSET; i < sheet.getRows()-FOOTER_OFFSET; i++) {
	        	
	          JiraIssue issue = new JiraIssue();
	          
	          Cell cell = sheet.getCell(keyCell.getColumn(), i);
	          
	          issue.setKey(cell.getContents());

	          cell=null;
	          
	          cell = sheet.getCell(summaryCell.getColumn(), i);
	          
	          issue.setSummary(cell.getContents());
	          
	          cell=null;
	          
	          cell = sheet.getCell(issueTypeCell.getColumn(), i);
	          
	          issue.setIssueType(cell.getContents());
	          
	          cell=null;
	          
	          cell = sheet.getCell(statusCell.getColumn(), i);
	          
	          issue.setStatus(cell.getContents());
	          
	          cell=null;
	          cell = sheet.getCell(teamCell.getColumn(), i);  
	          issue.setTeam(cell.getContents());
	         
	          cell=null;
	          
	          cell = sheet.getCell(pointsCell.getColumn(), i);
	          
	  
	          CellType type = cell.getType();
	          
	          if (cell.getType() == CellType.EMPTY) {
	          
	        	  issue.setPoints(0.0);
	        	  
	          } else if (cell.getType() == CellType.NUMBER) {
	        	  issue.setPoints(Double.parseDouble(cell.getContents()));
	          } else {
	        	  throw new RuntimeException("Invalid value");
	          }

	          jiraIssues.add(issue);
	      }
	        
	   /*     for (JiraIssue issue:jiraIssues) {
	        	System.out.println(issue.getKey()+"\t"+issue.getPoints()+"\t"+issue.getSummary());
	        }
	     */   
	        return jiraIssues;
	    
 }
}

