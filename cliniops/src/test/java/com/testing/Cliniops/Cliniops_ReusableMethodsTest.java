//chech line 370 wb.close()
package com.testing.Cliniops;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class Cliniops_ReusableMethodsTest {   
	private static String htmlName=null;
	private static String scriptPath = null;
	private static BufferedWriter bw = null;
	private static boolean isPass = true;
	private static String testScriptName = null;
	private static int rowCount = 1;
	private static String browserName=null;
	private static int reportFlag=0;
	private static String firefoxRes=null;
	private static String chromeRes=null;
	private static String ieRes=null;

	/**
	 * 
	 * @param expectedTextColor
	 * @param actualTextColor
	 * @param stepName
	 * @param dr
	 * @throws IOException
	 * */
	public static void checkHighlightText(String expectedTextColor,String actualTextColor,String stepName,WebDriver dr) throws IOException
	{if(expectedTextColor.trim().equals(actualTextColor.trim())){
		updateReport("Pass",stepName, "Text highlighted",dr);
	}else{
		updateReport("Fail",stepName, "Text not highlighted",dr);
	}

	}
	/**
	 * 
	 * @param actualText
	 * @param expectedText
	 * @param objName
	 * @param stepName
	 * @param dr
	 * @throws IOException
	 */
	public static void checkContentsMatch(String actualText, String expectedText, String objName,String stepName, WebDriver dr) throws IOException{
		if(expectedText.equals(actualText.trim())){
			updateReport("Pass", stepName, "Actual text is matching with expected text",dr);
		}else{
			updateReport("Fail", stepName, "Actual text is not matching with expected text",dr);
		}
	}


	/**
	 * Check if the object is enabled or not
	 * @param obj
	 * @param objName
	 * @throws IOException
	 */		
	public static void checkDisabled(WebElement obj,String objName,WebDriver dr) throws IOException{
		if(obj.getAttribute("disabled").trim().contains("true")){
			updateReport("Pass","Checkdisabled",objName+" is disabled",dr);
		}
		else{
			updateReport("Fail","Checkdisabled",objName+" is not disabled",dr);
		}
	}

	/**
	 * To click on specific object
	 * @param obj:WebElement to be clicked
	 * @param objName:Name of WebElement to be clicked
	 * @throws IOException
	 */ 
	public static void clickElement(WebElement obj, String objName,String stepName,WebDriver dr) throws IOException{
		if(obj.isDisplayed()){
			obj.click();
			updateReport("Pass", stepName, "clicked on selected object",dr);
			System.out.println("Pass: "+ objName + " is clicked.");
		}else{
			updateReport("Fail", stepName, "Not clicked on selected object",dr);
		}
	}


	/**
	 * To Select the dropdown
	 * @param dd:WebElement dropdown
	 * @param index:Index of element to be selected
	 * @throws IOException
	 */ 
	public static void dropDown(WebElement dd, int index,WebDriver dr) throws IOException{
		Select select = new Select(dd);
		if(dd.isDisplayed()){
			select.selectByIndex(index);
			updateReport("Pass", "DropDown", "selected dd object by using index",dr);
		}
		else{
			updateReport("Fail", "DropDown", "Not selected dd object by using index",dr);
		}
	}

	public static void checkDropdownDEnableSelectedOpt(WebElement dd,String value,String chkEnable,String stepName ,WebDriver dr) throws IOException{
    	Select select = new Select(dd);
    	if(chkEnable.equalsIgnoreCase("Enabled")){	
    		if(dd.isEnabled() && select.getFirstSelectedOption().getText().equalsIgnoreCase(value)){
    			updateReport("Pass", stepName, stepName + " dropdown is enabled and value "+ value + " is selected", dr);
    			System.out.println(select.getFirstSelectedOption().getText());
    		}
    		else if(!dd.isEnabled() && select.getFirstSelectedOption().getText().equalsIgnoreCase(value)){
    			updateReport("Fail", stepName, stepName + " dropdown is disabled and value "+ value + " is selected", dr);
    		}
    		else if(dd.isEnabled() && !select.getFirstSelectedOption().getText().equalsIgnoreCase(value)){
    			updateReport("Fail", stepName, stepName + " dropdown is enabled and value "+ value + " is selected", dr);
    			System.out.println("hellooo****");
    			System.out.println(select.getFirstSelectedOption().getText());
    		}
    		else{
    			updateReport("Fail", stepName, stepName + " dropdown is disabled and value "+ value + " is selected", dr);
    		}
    	}
    	else if(chkEnable.equalsIgnoreCase("Disabled")){
    		if(!dd.isEnabled() && select.getFirstSelectedOption().getText().equalsIgnoreCase(value)){
    			updateReport("Pass", stepName, stepName + " dropdown is disabled and value "+ value + " is selected", dr);
    		}
    		else if(dd.isEnabled() && select.getFirstSelectedOption().getText().equalsIgnoreCase(value)){
    			updateReport("Fail", stepName,stepName + " dropdown is enabled and value "+ value + " is selected" , dr);
    		}
    		else if(!dd.isEnabled() && !select.getFirstSelectedOption().getText().equalsIgnoreCase(value)){
    			updateReport("Fail", stepName, stepName + " dropdown is disabled and value "+ value + " is selected", dr);
    		}
    		else{
    			updateReport("Fail", stepName, stepName + " dropdown is enabled and value "+ value + " is selected", dr);
    		}
    	}
    }

	/** 
	 * Used to enter text into text box field
	 * @param obj:WebElement object in which text needs to be entered
	 * @param textVal:String value to be entered
	 * @param objName:Name of object
	 * @param stepName:Name of step
	 * @throws IOException
	 */
	public static void enterText(WebElement obj, String textVal, String objName,String stepName,WebDriver dr) throws IOException{
		if(obj.isDisplayed()){
			obj.sendKeys(textVal);
			updateReport("Pass",stepName,objName+" is entered in "+objName+" field",dr);
		}else{
			updateReport("Fail",stepName,objName+" field is not displayed,please check application",dr);
		}
	}


	/** 
	 * To validate if the text on element matches expectedTest
	 * @param obj:WebElement whose text needs to be verified
	 * @param expectedText:Expected value of text
	 * @param objName:Name of object
	 * @param stepName:Step Name
	 * @param dr:Web driver
	 */	
	public static void validateText(WebElement obj, String expectedText, String objName,String stepName,WebDriver dr) throws IOException{
		if(obj.isDisplayed()){
			String actualText = obj.getText().trim();
			if(expectedText.equals(actualText)){
				updateReport("Pass", stepName, "Actual text is matching with expected text",dr);
			}else{
				updateReport("Fail", stepName, "Actual text is not matching with expected text",dr);
			}
		}else{
			updateReport("Fail",stepName,objName+ " is not displayed, please check your application",dr);
		}
	}

	/**
	 * Read checkBox text	
	 * @param obj
	 * @param expectedText
	 * @param objName
	 * @param dr
	 * @throws IOException
	 */
	public static void readingCheckbox(WebElement obj,String expectedText,String objName,WebDriver dr) throws IOException{
		if(obj.isDisplayed())
		{
			String Actualtext=obj.getAttribute("checked").trim();
			if(expectedText.equals(Actualtext)){
				updateReport("Pass","readingCheckbox",objName+" is checked",dr);
			}
			else{
				updateReport("Fail","readingCheckbox",objName+" is not checked",dr);
			}
		}
		else{
			updateReport("Fail","readingCheckbox",objName+" is not displayed,please check your application",dr);
		}
	}

	/**
	 * Read text box value
	 * @param obj
	 * @param objName
	 * @param dr
	 * @throws IOException
	 */
	public static void readingText(WebElement obj,String objName,WebDriver dr) throws IOException{
		if(obj.isDisplayed())
		{
			String Actualtext=obj.getText().trim();
			if(Actualtext.isEmpty())
			{
				updateReport("Fail","ReadingText",objName+" has No data",dr);
			}
			else{
				updateReport("Pass","ReadingText",objName+" contains "+Actualtext,dr);
			}
		}
	}


	/**
	 * Validate message displayed on the web page
	 * @param obj
	 * @param expectedText
	 * @param objName
	 * @param attributeName
	 * @param stepName
	 * @param dr
	 * @throws IOException
	 */
	public static void validateTextAttribute(WebElement obj, String expectedText, String objName,String attributeName,String stepName,WebDriver dr) throws IOException{
		if(obj.isEnabled()){	
			String actualText = obj.getAttribute(attributeName);
			if(expectedText.equals(actualText)){
				updateReport("Pass","validateMsg_Attribute","Actual message matching with expected message:"+ actualText,dr);
			}else{
				updateReport("Fail","validateMsg_Attribute","Actual message not matching with expected message:"+actualText,dr);
			}
		}else{
			updateReport("Fail","validateMsg_Attribute",objName +"is not displayed, please check your application",dr);
		}
	}


	/**
	 * Check if an object is displayed
	 * @param obj
	 * @param objName
	 * @param stepName
	 * @param dr
	 * @throws IOException
	 */
	public static void checkObjectDisplay(WebElement obj,String objName,String stepName,WebDriver dr) throws IOException{
		if(obj.isDisplayed()){
			updateReport("Pass", stepName, objName+" appears",dr);
		}
		else{
			updateReport("Fail", stepName, objName+" not displayed",dr);
		}
	}

	/**
	 * 
	 * @param obj
	 * @param objName
	 * @param dr
	 * @throws IOException
	 */
	public static void checkEnabled(WebElement obj,String objName,WebDriver dr) throws IOException{
		if(obj.isEnabled()){
			updateReport("Pass","checkEnabled",objName+" is enabled",dr);
		}
		else{
			updateReport("Fail","checkEnabled",objName+" is not enabled",dr);
		}
	}

	/**
	 * Select drop down by value    
	 * @param dd
	 * @param value
	 * @param dr
	 * @throws IOException
	 */
	public static void dropDownByValue(WebElement dd, String value,WebDriver dr) throws IOException{

		Select select = new Select(dd);
		if(dd.isDisplayed()){
			select.selectByValue(value);
			updateReport("Pass", "DropDown", "selected dd object by using Value",dr);
		}
		else{
			updateReport("Fail", "DropDown", "Not selected dd object by using Value",dr);
		}

	}

	public static void validateURL(String expectedURL,String ActualURL,String stepName,WebDriver dr) throws IOException{
		if(expectedURL.trim().equals(ActualURL.trim())){
			updateReport("Pass",stepName,"Actual URL matching with expected URL",dr);
		}
		else{
			updateReport("Fail",stepName,"Actual URL matching with expected URL",dr);
		}

	}


	/**
	 * Read data from excel sheet
	 * @param filePath
	 * @param sheetName
	 * @return
	 * @throws IOException
	 */
	public static String[][] readSheet(String filePath, String sheetName) throws IOException{
		/*Step 1: Get the XL Path*/
		File xlFile = new File(filePath);

		/*Step2: Access the Xl File*/
		FileInputStream xlDoc = new FileInputStream(xlFile);

		/*Step3: Access the work book */
		HSSFWorkbook wb = new HSSFWorkbook(xlDoc);


		/*Step4: Access the Sheet */
		HSSFSheet sheet = wb.getSheet(sheetName);

		int iRowCount =  sheet.getLastRowNum()+1;
		int iColCount = sheet.getRow(0).getLastCellNum();//Row count starts from '0' in excel

		System.out.println("Total row = " + iRowCount + " total col = " + iColCount);

		String[][] xlData = new String[iRowCount][iColCount];

		for(int i =0; i<iRowCount;i++){
			for(int j = 0; j <iColCount;j++){
				//xlData[i][j] = sheet.getRow(i).getCell(j).getStringCellValue().trim();
				Cell cell = sheet.getRow(i).getCell(j);
				switch(cell.getCellType()){
				case Cell.CELL_TYPE_STRING:
					xlData[i][j] = sheet.getRow(i).getCell(j).getStringCellValue().trim();
					break;
				case Cell.CELL_TYPE_NUMERIC:
					xlData[i][j] = new Double(sheet.getRow(i).getCell(j).getNumericCellValue()).toString() ;
					break;
				}
			}

		}
	//	wb.close();
		return xlData;
	}


	/**
	 * Start HTML report for the test script
	 * @param scriptName
	 * @param reportsPath
	 * @param browser
	 * @throws IOException
	 */
	public static void startReport(String scriptName, String reportsPath,String browser) throws IOException{
		browserName=browser;
		if(reportFlag==0){
			reportFlag=1;
			String strResultPath = null;
			testScriptName =scriptName;

			Date curDate = new Date(); 
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			String strTimeStamp = dateFormat.format(curDate);

			if (reportsPath == "") { 
				reportsPath = "C:\\";
			}
			if (!reportsPath.endsWith("\\")) { 
				reportsPath = reportsPath + "\\";
			}
			strResultPath = reportsPath + "Log" + "/" +testScriptName +"/"; 
			//File f is only used to create directory for script result
			File f = new File(strResultPath);
			f.mkdirs();
			htmlName = strResultPath  + testScriptName + "_" + strTimeStamp 
					+ ".html";
			
			//Getting absolute path of script report to update reference in summary report
			File rep = new File(htmlName);
			FileWriter report = new FileWriter(rep);
			scriptPath = rep.getAbsolutePath();
			//bw = new BufferedWriter(report);
			
			//bw = new BufferedWriter(new FileWriter(htmlName));
			bw = new BufferedWriter(report);

			bw.write("<HTML><BODY><TABLE BORDER=0 CELLPADDING=3 CELLSPACING=1 WIDTH=100%>");
			bw.write("<TABLE BORDER=0 BGCOLOR=BLACK CELLPADDING=3 CELLSPACING=1 WIDTH=100%>");
			bw.write("<TR><TD BGCOLOR=#66699 WIDTH=27%><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Browser Name</B></FONT></TD><TD COLSPAN=6 BGCOLOR=#66699><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>"
					+ browserName + "</B></FONT></TD></TR>");
			bw.write("<HTML><BODY><TABLE BORDER=1 CELLPADDING=3 CELLSPACING=1 WIDTH=100%>");
			bw.write("<TR COLS=7><TD BGCOLOR=#BDBDBD WIDTH=3%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>SL No</B></FONT></TD>"
					+ "<TD BGCOLOR=#BDBDBD WIDTH=10%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Step Name</B></FONT></TD>"
					+ "<TD BGCOLOR=#BDBDBD WIDTH=10%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Execution Time</B></FONT></TD> "
					+ "<TD BGCOLOR=#BDBDBD WIDTH=10%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Status</B></FONT></TD>"
					+ "<TD BGCOLOR=#BDBDBD WIDTH=47%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Detail Report</B></FONT></TD></TR>");
			startSummaryReport(reportsPath);
		}
		else{
			bw.write("<TABLE BORDER=0 BGCOLOR=BLACK CELLPADDING=3 CELLSPACING=1 WIDTH=100%>");
			bw.write("<TR><TD BGCOLOR=#66699 WIDTH=23%><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Browser Name</B></FONT></TD><TD COLSPAN=6 BGCOLOR=#66699 WIDTH=77%><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>"
					+ browserName + "</B></FONT></TD></TR>");
			bw.write("<HTML><BODY><TABLE BORDER=1 CELLPADDING=3 CELLSPACING=1 WIDTH=100%>");

		}

	}

	/**
	 * Update HTML report for each step
	 * @param resType
	 * @param action
	 * @param result
	 * @param dr  
	 * @throws IOException
	 */
	public static void updateReport(String resType,String action, String result,WebDriver dr) throws IOException {
		String strTime;
		Date execTime = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		strTime = dateFormat.format(execTime);
		if (resType.startsWith("Pass")) {
			if(isPass!=false){isPass = true;}
			bw.write("<TR COLS=7><TD BGCOLOR=#EEEEEE WIDTH=3%><FONT FACE=VERDANA SIZE=2>"
					+ (rowCount++)
					+ "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=10%><FONT FACE=VERDANA SIZE=2>"
					+action
					+ "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=10%><FONT FACE=VERDANA SIZE=2>"
					+ strTime
					+ "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=10%><FONT FACE=VERDANA SIZE=2 COLOR = GREEN>"
					+ "Passed"
					+ "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=47%><FONT FACE=VERDANA SIZE=2 COLOR = GREEN>"
					+ result + "</FONT></TD></TR>");

		}
		else if (resType.startsWith("Fail")) {
			isPass = false;
			String ss1Path= screenShot(dr);
			bw.write("<TR COLS=7><TD BGCOLOR=#EEEEEE WIDTH=3%><FONT FACE=VERDANA SIZE=2>"
					+ (rowCount++)
					+ "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=10%><FONT FACE=VERDANA SIZE=2>"
					+action
					+ "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=10%><FONT FACE=VERDANA SIZE=2>"
					+ strTime
					+ "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=10%><FONT FACE=VERDANA SIZE=2 COLOR = RED>"
					+ "<a href= "
					+ ss1Path
					+ "  style=\"color: #FF0000\"> Failed </a>"

				+ "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=47%><FONT FACE=VERDANA SIZE=2 COLOR = RED>"
				+ result + "</FONT></TD></TR>");
		} 
	}
	
	/**
	 * Start the summary report for the test suite and record start time
	 * @param scriptName
	 * @param reportsPath
	 * @throws IOException
	 */
	public static void startSummaryReport(String reportsPath) throws IOException{

		//set SummaryReport start time
		if(CliniopsSummaryReport.getExecutionStartTime() == 0)
			CliniopsSummaryReport.setExecutionStartTime();
		
	}

	/**
	 * Add test script execution result and script Location to CliniopsSummaryReport object
	 * @throws IOException
	 */
	public static void updateSummaryReport() throws IOException{
		CliniopsSummaryReport.setNumTests(CliniopsSummaryReport.getNumTests()+1);
		CliniopsSummaryReport.updateTestResults(testScriptName, scriptPath, firefoxRes, chromeRes, ieRes);
		firefoxRes = null;
		chromeRes = null;
		ieRes = null;
		
	}

	/**
	 * Write the summary report at end of execution of all test cases
	 * @param reportsPath
	 */
	public static void writeSummaryReport(String reportsPath){
		CliniopsSummaryReport.writeReport(reportsPath);
		firefoxRes = null;
		chromeRes = null;
		ieRes = null;
	}
	
	/**
	 * Screenshot generation in failed case
	 * @param dr
	 * @return
	 * @throws IOException
	 */
	public static String screenShot(WebDriver dr) throws IOException{
		Date execTime = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String strTime = dateFormat.format(execTime);
		String  ss1Path = ".\\test-output\\Suite\\Screenshots\\"+ strTime+".png";
		File scrFile = ((TakesScreenshot)dr).getScreenshotAs(OutputType.FILE);
		File destFile=new File(ss1Path);
		FileUtils.copyFile(scrFile, destFile);
		String absPath=destFile.getAbsolutePath();
		return absPath;

	}	


	/**
	 * To close report
	 * @throws IOException
	 */
	public static void closeReport() throws IOException{
		rowCount = 1;
		browserName = null;
		reportFlag = 0;
		scriptPath = null;
		bw.close();
	}
	/**   
	 * Login page module
	 * @param dr
	 * @throws IOException,InterruptedException
	 */	
	public static void login(WebDriver dr) throws InterruptedException, IOException{
		dr.get("https://bridgetherapeutics.cliniops.com");
		dr.findElement(By.id("username")).sendKeys("Abhishek");
		Thread.sleep(2000);
		dr.findElement(By.id("password")).sendKeys("Testing@123");
		Thread.sleep(2000);
		dr.findElement(By.id("Authenticate")).click();
		Thread.sleep(7000);
		dr.findElement(By.xpath("//*[text()='Cisplatin/Etoposide/Rad................-Small Cell Lung Cancer']")).click();
		Thread.sleep(3000);
		dr.findElement(By.xpath("//*[text()='English']")).click();
		dr.findElement(By.xpath(".//*[@id='login']/div[7]/input")).click();
	}


	public void updateResults(){

		if(browserName.equalsIgnoreCase("Firefox")){
			if(isPass)
				firefoxRes = "Pass";
			else
				firefoxRes = "Fail";
		}
		else if(browserName.equalsIgnoreCase("Chrome")){
			if(isPass)
				chromeRes = "Pass";
			else
				chromeRes = "Fail";
		}
		else if(browserName.equalsIgnoreCase("IE")){
			if(isPass)
				ieRes = "Pass";
			else
				ieRes = "Fail";
		}
		isPass=true;

	}

}
