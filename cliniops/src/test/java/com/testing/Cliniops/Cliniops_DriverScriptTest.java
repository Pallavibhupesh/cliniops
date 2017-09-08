package com.testing.Cliniops;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//import org.apache.tools.ant.taskdefs.UpToDate;
import org.testng.annotations.Test;

public class Cliniops_DriverScriptTest {  

     
	@Test
	public static void cliniopsDriver() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException, ClassNotFoundException, InstantiationException{

		Cliniops_AutomationScriptsTest casObject = new Cliniops_AutomationScriptsTest();
		String testDataPath =".\\cliniops_AutomationScripts_Execution.xls";
		String reportsPath=".\\test-output\\suite\\";
		String testDataSheet="Sheet1";
		String[][] recData = Cliniops_ReusableMethodsTest.readSheet(testDataPath, testDataSheet);
		String tc = null;
		//looping through the rows
		for(int i=0; i<recData.length; i++){

			if (recData[i][1].equalsIgnoreCase("y")){
				tc = recData[i][2];
				System.out.println("Testcase to run: "+recData[i][2]);

				//we are getting handle to the method for invoking...
				Method testcase = Cliniops_AutomationScriptsTest.class.getMethod(tc);
				
				//invoke---executes the method
				if(recData[i][3]!=null && recData[i][3].equalsIgnoreCase("Y"))
				{
					Cliniops_ReusableMethodsTest.startReport(tc, reportsPath,"FIREFOX");
					casObject.selectBrowser("firefox");
					testcase.invoke(casObject);
					casObject.closeBrowser();
				}
				if(recData[i][4]!=null && recData[i][4].equalsIgnoreCase("Y"))
				{
					Cliniops_ReusableMethodsTest.startReport(tc, reportsPath,"CHROME");
					casObject.selectBrowser("chrome");
					testcase.invoke(casObject);
					casObject.closeBrowser();
				}
				if(recData[i][5]!=null && recData[i][5].equalsIgnoreCase("Y"))
				{
					Cliniops_ReusableMethodsTest.startReport(tc, reportsPath,"IE");
					casObject.selectBrowser("IE");
					testcase.invoke(casObject);
					casObject.closeBrowser();
				}						
				//Update Data for Summary Report after test run for all browsers
				Cliniops_ReusableMethodsTest.updateSummaryReport();
				//Close individual test script report
				Cliniops_ReusableMethodsTest.closeReport();					
			}
			else if(recData[i][1].equalsIgnoreCase("n")){
				System.out.println(recData[i][2] + "in line number "+i+ "skipped from execution");
			}
		}	
		//Write and close summary report
		Cliniops_ReusableMethodsTest.writeSummaryReport(reportsPath);

	}
}