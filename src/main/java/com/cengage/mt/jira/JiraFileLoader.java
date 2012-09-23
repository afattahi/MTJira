package com.cengage.mt.jira;
import java.io.File;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.management.RuntimeErrorException;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class JiraFileLoader {
	 
    private final int HEADER_OFFSET = 4;
    private final int FOOTER_OFFSET = 1;
    
    private final String HEADER_KEY = "Key";
    private final String HEADER_SUMMARY = "Summary";
    private final String HEADER_STORY_POINTS = "Story Points";
    private final String HEADER_STATUS = "Status";
    private final String HEADER_ISSUE_TYPE = "Issue Type";
    private final String HEADER_TEAM = "Scrum Team";
    private final String SNAPSHOT_NAME_BASE = "snapshot_";
    private final String EXCEL_EXT = ".xls";
    private final String REPORT1_BASE = "report1_";
    private final String BASELINE_NAME_BASE = "baseline_";
    private final String TXT_EXT = ".txt";
    
    private final int KEY_COL = 0;
    private final int SUMMARY_COL = 1;
    private final int STATUS_COL = 2;
    private final int ISSUE_TYPE_COL = 3;
    private final int TEAM_COL = 4;
    private final int POINTS_COL = 5;

    
    private final int HEADER_ROW = 0;
    
    public String getFileTimestamp() {
   	
    	  String s;
    	  Format formatter;
    	  Date date = new Date();
    	  formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
    	  s = formatter.format(date);

    	return s;
    }
    
    public String getSnapshotFileName (String label) {
    	return SNAPSHOT_NAME_BASE+label+"_"+getFileTimestamp()+EXCEL_EXT;
    }
    
    public String getReport1FileName( String label) {
      	return REPORT1_BASE+label+"_"+getFileTimestamp()+TXT_EXT; 
    }
    
    public String getbaselineFileName(String label) {
      	return BASELINE_NAME_BASE+label+"_"+getFileTimestamp()+TXT_EXT; 
    }
    
    
    public void generateExcelSheet(String fileName, List<JiraIssue> issues,String jql) {
    	
    	try {
    	    
    	    WritableWorkbook workbook = Workbook.createWorkbook(new File(fileName));
    	    
    	    WritableSheet sheet = workbook.createSheet("Data", 0);
    	    WritableSheet sheetInfo = workbook.createSheet("Query", 1);
    	    sheetInfo.addCell(new Label(0,0,jql));
    	    Label label;
    	    List<Label> labels = new ArrayList<Label>();
    	    label = new Label(KEY_COL,HEADER_ROW,HEADER_KEY);
    	    labels.add(label);
    	    label = new Label(SUMMARY_COL,HEADER_ROW,HEADER_SUMMARY);
    	    labels.add(label);
    	    label = new Label(POINTS_COL,HEADER_ROW,HEADER_STORY_POINTS);
    	    labels.add(label);
    	    label = new Label(STATUS_COL,HEADER_ROW,HEADER_STATUS);
    	    labels.add(label);
    	    label = new Label(ISSUE_TYPE_COL,HEADER_ROW,HEADER_ISSUE_TYPE);
    	    labels.add(label);
    	    label = new Label(TEAM_COL,HEADER_ROW,HEADER_TEAM);
    	    labels.add(label);
    	    
    	
    	    
    	    int row = HEADER_ROW+1;
    	    for (JiraIssue i : issues) {
    	    	
    	    	label = new Label(KEY_COL,row,i.getKey());
        	    labels.add(label);
        	    label = new Label(SUMMARY_COL,row,i.getSummary());
        	    labels.add(label);
        	  //  label = new Label(POINTS_COL,row,i.getPoints().toString());
        	   // labels.add(label);
        	    label = new Label(STATUS_COL,row,i.getStatus());
        	    labels.add(label);
        	    label = new Label(ISSUE_TYPE_COL,row,i.getIssueType());
        	    labels.add(label);
        	    label = new Label(TEAM_COL,row,i.getTeam());
        	    labels.add(label);
        	    row++;
    	    	
    	    }
    	    
    	    for (Label l : labels) {
    	    	
 	    	   sheet.addCell(l);
 	    }
    	    
    	    workbook.write();
    	    workbook.close();
    	} catch (Exception e) {
    		throw new RuntimeException(e);

    	}
    }

	  public List<JiraIssue> load(String inputFile)   {
	    
		try {
		File inputWorkbook = new File(inputFile);
	    Workbook w;
	  
	      w = Workbook.getWorkbook(inputWorkbook);
	      // Get the first sheet
	      Sheet sheet = w.getSheet(0);
	      // Loop over first 10 column and lines
	      Cell keyCell = sheet.findCell(HEADER_KEY);
	      Cell summaryCell = sheet.findCell(HEADER_SUMMARY);
	      Cell pointsCell = sheet.findCell(HEADER_STORY_POINTS);
	      Cell statusCell = sheet.findCell(HEADER_STATUS);
	      Cell issueTypeCell = sheet.findCell(HEADER_ISSUE_TYPE);
	      Cell teamCell = sheet.findCell(HEADER_TEAM);
	      
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
	        
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	    
 }
}

