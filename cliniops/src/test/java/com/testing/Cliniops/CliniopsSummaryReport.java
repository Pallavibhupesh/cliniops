package com.testing.Cliniops;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
  
public class CliniopsSummaryReport {

	private static int numTests = 0;
	private static int numTestsPassed = 0;
	private static int numTestsFailed = 0;
	private static LinkedHashMap<String, String> testResults = new LinkedHashMap<String, String>();
	private static long executionStartTime = 0;
	private static long executionEndTime = 0;
	
	/**
	 * Returns duration of test execution
	 * @return
	 */
	public static long getDuration(){
		return (getExecutionEndTime() - getExecutionStartTime())/1000 ;
	}
	
	/**
	 * Returns test execution end time
	 * @return
	 */
	public static long getExecutionEndTime() {
		return executionEndTime;
	}
	
	/**
	 * Returns start of execution time
	 * @return
	 */
	public static long getExecutionStartTime() {
		return executionStartTime;
	}
	
	/**
	 * Returns total number of test scripts in the execution run
	 * @return
	 */
	public static int getNumTests() {
		return numTests;
	}
	
	/**
	 * Returns number of failed test scripts
	 * @return
	 */
	public static int getNumTestsFailed() {
		return numTestsFailed;
	}
	
	/**
	 * Returns number of test scripts passed
	 * @return
	 */
	public static int getNumTestsPassed() {
		return numTestsPassed;
	}
	
	/**
	 * Returns the Map object containing the test results
	 * @return
	 */
	public static LinkedHashMap<String, String> getTestResults() {
		if(testResults.equals(null))
			testResults = new LinkedHashMap<String, String>();
		return testResults;
	}
	
	/**
	 * Keeps a count of failed test scripts
	 */
	public static void incrementNumTestsFailed() {
		CliniopsSummaryReport.numTestsFailed = getNumTestsFailed() + 1;
	}

	/**
	 * Increments counter of number of passed test scripts
	 */
	public static void incrementNumTestsPassed() {
		CliniopsSummaryReport.numTestsPassed = getNumTestsPassed() + 1;
	}
	
	/**
	 * Sets time at which execution ends
	 */
	public static void setExecutionEndTime() {
		executionEndTime = System.currentTimeMillis();
	}
	
	/**
	 * Sets time at start of test execution
	 */
	public static void setExecutionStartTime() {
		executionStartTime = System.currentTimeMillis();
	}
	
	/**
	 * Set total number of test scripts run
	 * @param numTests
	 */
	public static void setNumTests(int numTests) {
		CliniopsSummaryReport.numTests = numTests;
	}
	
	/**
	 * Updates the results of test script execution in the testResults LinkedHashMap object
	 * @param script
	 * @param scriptLocation
	 * @param firefox
	 * @param chrome
	 * @param ie
	 */
	public static void updateTestResults(String script, String scriptLocation, String firefox, String chrome, String ie) {

		//Set result to skipped if test was not run on the browser
		if(firefox == null )
			firefox = "Skipped";
		if(chrome == null )
			chrome = "Skipped";
		if(ie == null )
			ie = "Skipped";
		String result = scriptLocation + ","+ firefox + "," + chrome + "," + ie;
		testResults.put(script, result);
		//Update number of tests passed or failed
		if(firefox.equalsIgnoreCase("fail") || chrome.equalsIgnoreCase("fail") || ie.equalsIgnoreCase("fail"))
			incrementNumTestsFailed();
		else
			incrementNumTestsPassed();
	}
	
	/**
	 * Writes the summary report at end of test execution
	 * @param reportsPath
	 */
	public static void writeReport(String reportsPath) {
		String strResultPath = reportsPath + "Log" + "/";
		//Get execution end time
		CliniopsSummaryReport.setExecutionEndTime();
		Date execTime = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String summaryReportName = strResultPath + "Test Summary_" + dateFormat.format(execTime) + ".html"; 
		BufferedWriter summaryWriter;
		try{
			summaryWriter = new BufferedWriter(new FileWriter(summaryReportName));
			//Write the summary table
			summaryWriter.write("<!DOCTYPE html><HTML><HEAD><STYLE TYPE="+ "TEXT/CSS" + ">"
					+ "TABLE{"
						+ "font-family: verdana; font-size:12px; font-weight:normal;"
						+ "border-collapse: collapse; color:black; border: 1px solid black;"
						+ "padding: 15px;}"
					+ "TR.NO_BORDER > TD, TD.NO_BORDER{background-color: white;}"
					+ "TR.BORDER>TD, TH {border: 1px solid black;}"
					+ "TR.TABLE_HEADER>TD {"
						+ "background-color: lightblue;"
						+ "border-left: 1px solid black;"
 						+ "font-weight: bold;}"
					+ "TH.TITLE{"
						+ "background-color: #66699d;"
						+ "border: 1px solid black;"
						+ "color: white;"
						+ "font-weight: bold;" 
						+ "font-size: 14px;}"
					+ "</STYLE></HEAD>"
					+ "<BODY><TABLE WIDTH=100%>"
					+ "<TR><TH WIDTH=100% CLASS=" + "TITLE" + ">Test Summary Report</TH></TR>");
			summaryWriter.write("<TR CLASS=" + "NO_BORDER" + "><TD WIDTH=100%> Scripts Executed = "
					+ getNumTests() + "</TD></TR>"
					+ "<TR CLASS="+ "NO_BORDER" + "><TD WIDTH=100%>Scripts Passed = "
					+ getNumTestsPassed() + "</TD></TR>"
					+ "<TR CLASS="+ "NO_BORDER" + "><TD WIDTH=100%>Scripts Failed = "
					+ getNumTestsFailed() + "</TD></TR>"
					+ "<TR CLASS="+ "NO_BORDER" + "><TD WIDTH=100%>Execution Time = "
					+ getDuration()	+ " seconds </TD></TR></TABLE>");
			//Write the test script summary 
			summaryWriter.write("<TABLE WIDTH=100%>");
			summaryWriter.write("<TR><TH WIDTH=100% CLASS=" + "TITLE" + ">Test Script Details</TH></TR>");
			summaryWriter.write("<TABLE WIDTH=100%>");
			summaryWriter.write("<TR COLS=5 CLASS=" + "TABLE_HEADER" + "><TD WIDTH=3%>Num</TD>"
					+ "<TD WIDTH=20%>Script Name</TD>"
					+ "<TD WIDTH=15%>Firefox</TD>"
					+ "<TD WIDTH=15%>Chrome</TD>"
					+ "<TD WIDTH=47%>IE</TD></TR>");

			//Iterate through all results
			int rowCount = 1;
			Iterator<Map.Entry<String, String>> iterator = getTestResults().entrySet().iterator() ;
			while(iterator.hasNext()){
				Map.Entry<String, String> testResult = iterator.next();
				String scriptName = testResult.getKey();
				String[] value = testResult.getValue().split(",");
				summaryWriter.write("<TR COLS=5 CLASS=" + "BORDER" + "><TD WIDTH=5%>"
						+ (rowCount++)
						+ "</TD><TD WIDTH=35% >"
						+ "<a href= " + value[0] + " >" 
						+ scriptName + "</a></TD>"
						+ "<TD WIDTH=20%>" + value[1] + "</TD>"
						+ "<TD WIDTH=20%>" + value[2] + "</</TD>"
						+ "<TD WIDTH=20%>" + value[3] + "</TD></TR>");
			}
			summaryWriter.write("</TABLE></BODY></HTML>");
			summaryWriter.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}

	}
}
