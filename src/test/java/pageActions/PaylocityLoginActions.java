package pageActions;

import java.text.DecimalFormat;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import io.cucumber.java.Scenario;
import pageObjects.PaylocityBenefitsPageObjects;
import utilityFiles.BaseClass;
import utilityFiles.CommonFunctions;

public class PaylocityLoginActions extends CommonFunctions{

	PaylocityBenefitsPageObjects paylocityPageObjects = null;
	public PaylocityLoginActions() {
		this.paylocityPageObjects = new PaylocityBenefitsPageObjects(BaseClass.getDriver());
		PageFactory.initElements(BaseClass.getDriver(), paylocityPageObjects);
	}
	
	public int employeeTableRowCount;
	String firstName;String lastName; String dependant;
	String bCost;
	String employeeID; String fullName;
	private HashMap<String, String> hmap = new HashMap<String, String>();
	public void launchApplication() {
		BaseClass.openURL(getDetailsFromPropetiesFile().getProperty("paylocity.applicationURL"));
	}
//	get username from homepage
	public void login() {
		sendKeys(paylocityPageObjects.userName,"UserName",getDetailsFromPropetiesFile().getProperty("paylocity.username"));
		sendKeys(paylocityPageObjects.password,"Password",getDetailsFromPropetiesFile().getProperty("paylocity.password"));
		clickElement(paylocityPageObjects.login);
	}
	
//	verify page title
	public void verifyPageTitle(String strPageTitle) {
		verifyEquals(true,getPageTitle().equalsIgnoreCase(strPageTitle),"Page Title verification");
	}
	
	public void verifyAddEmployeeButton() {
		if(verifyElementExists(paylocityPageObjects.addButton)) {
			logMessage(getPageTitle() +" page loaded sucessfully" );
		}
	}
	
	public void selectButton(String buttonName) {
		employeeID =getLocatorValue(covertToWebElement(paylocityPageObjects.employeeTableData.replace("columnNO", "1")));
		employeeTableRowCount= tableRowCount(returnDriver(),paylocityPageObjects.employeeTableDetails,5);
		switch (buttonName) {
		case "Add Employee":
			clickElement(paylocityPageObjects.btn_AddEmployee);
			logMessage(buttonName +" :is clicked");
			break;
		case "Edit Employee":
			clickElement(covertToWebElement(paylocityPageObjects.editEmployee.replaceAll("EMPLOYEEID", "'" + employeeID + "'")));
			logMessage(buttonName +" :is clicked");
			break;
		case "Delete Employee":			
			fullName =getLocatorValue(covertToWebElement(paylocityPageObjects.employeeTableData.replace("columnNO", "2"))) +" "+getLocatorValue(covertToWebElement(paylocityPageObjects.employeeTableData.replace("columnNO", "3")));
			clickElement(covertToWebElement(paylocityPageObjects.deleteEmployee.replaceAll("EMPLOYEEID", "'" + employeeID + "'")));
			logMessage(buttonName +" :is clicked");
			break;
		}
	}
	
	public void verifyAddEmployeeDisplayed(String screenTitle) {
		
		verifyEquals(true,paylocityPageObjects.screen_AddEmployee_title.getText().equals(screenTitle),screenTitle +"screen displayed. " );
		verifyEquals(true,paylocityPageObjects.screen_AddEmployee_FirstName_label.getText().equals("First Name:"),screenTitle +"screen FirstName field displayed. " );
		verifyEquals(true,paylocityPageObjects.screen_AddEmployee_LastName_label.getText().equals("Last Name:"),screenTitle +"screen LastName fielddisplayed. " );
		verifyEquals(true,paylocityPageObjects.screen_AddEmployee_Dependents_label.getText().equals("Dependents:"),screenTitle +"screen Dependents field displayed. " );
		verifyEquals(true,isElementPresent(returnDriver(),paylocityPageObjects.screen_AddEmployee_Add_btn), "ADD button displayed" );
		verifyEquals(true,isElementPresent(returnDriver(),paylocityPageObjects.screen_Cancel_btn), "Cancel button in ADD employee screen displayed" );
		
	}
	
	public void verifyEditEmployeeScreenDisplayed(String screenTitle) {
		verifyEquals(true,paylocityPageObjects.screen_AddEmployee_title.getText().equals(screenTitle),screenTitle +"screen displayed. " );
		verifyEquals(true,paylocityPageObjects.screen_AddEmployee_FirstName_label.getText().equals("First Name:"),screenTitle +"screen FirstName field displayed. " );
		verifyEquals(true,paylocityPageObjects.screen_AddEmployee_LastName_label.getText().equals("Last Name:"),screenTitle +"screen LastName fielddisplayed. " );
		verifyEquals(true,paylocityPageObjects.screen_AddEmployee_Dependents_label.getText().equals("Dependents:"),screenTitle +"screen Dependents field displayed. " );
		verifyEquals(true,isElementPresent(returnDriver(),paylocityPageObjects.screen_EditEmployee_Update_btn), "UPDATE button displayed" );
		verifyEquals(true,isElementPresent(returnDriver(),paylocityPageObjects.screen_Cancel_btn), "Cancel button in ADD employee screen displayed" );
		
	}
		
	public void enterEmployeeDetails(String strFName,String strLName, String dependents ) {
		firstName =strFName; lastName=strLName; dependant =dependents;
		if(!strFName.isEmpty()) {
			sendKeys(paylocityPageObjects.screen_AddEmployee_FirstName,"FirstName",strFName);
		}		
		if(!strLName.isEmpty()) {
			sendKeys(paylocityPageObjects.screen_AddEmployee_LastName,"LastName",strLName);
		}		
		if(!dependents.isEmpty()) {
			sendKeys(paylocityPageObjects.screen_AddEmployee_Dependents,"Dependents",dependents);
		}
	}
	
	public void saveEmployeeDetails(String buttonName) {
		employeeTableRowCount =tableRowCount(returnDriver(),paylocityPageObjects.employeeTableDetails,5);
		if(buttonName.equalsIgnoreCase("Add Employee")) {
			waitForElementToBeClickable(returnDriver(),paylocityPageObjects.screen_AddEmployee_Add_btn,5);
			clickElement(paylocityPageObjects.screen_AddEmployee_Add_btn);		
		}else {
			waitForElementToBeClickable(returnDriver(),paylocityPageObjects.screen_EditEmployee_Update_btn,5);
			clickElement(paylocityPageObjects.screen_EditEmployee_Update_btn);
		}
	}
	
	public void verifyEmployeeDetailsSaved() {
		int employeeDetailsCountAfter =tableRowCount(returnDriver(),paylocityPageObjects.employeeTableDetails,5);
		verifyEquals(true, employeeDetailsCountAfter > employeeTableRowCount-1, "Employee Details table row increased");
		verifyEquals(true,getLocatorValue(covertToWebElement(paylocityPageObjects.employeeTableData.replace("columnNO", "2"))).equals(lastName),"LastName displayed as expected");
		verifyEquals(true,getLocatorValue(covertToWebElement(paylocityPageObjects.employeeTableData.replace("columnNO", "3"))).equals(firstName),"FirstName displayed as expected");
		verifyEquals(true,getLocatorValue(covertToWebElement(paylocityPageObjects.employeeTableData.replace("columnNO", "4"))).equals(dependant),"Dependents details displayed as expected");
	}
	public void verifyEditedDetailsSaved() {
		int employeeDetailsCountAfter =paylocityPageObjects.employeeTableDetails.size();
		verifyEquals(true, employeeDetailsCountAfter == employeeTableRowCount, "Employee Details table row not changed");
		verifyEquals(true,getLocatorValue(covertToWebElement(paylocityPageObjects.employeeTableData.replace("columnNO", "2"))).equals(lastName),"LastName displayed as expected");
		verifyEquals(true,getLocatorValue(covertToWebElement(paylocityPageObjects.employeeTableData.replace("columnNO", "3"))).equals(firstName),"FirstName displayed as expected");
		verifyEquals(true,getLocatorValue(covertToWebElement(paylocityPageObjects.employeeTableData.replace("columnNO", "4"))).equals(dependant),"Dependents details displayed as expected");
	}
	
	public void verifyDeleteScreenDisplayedAndDetailsDetails(String screenTitle) {
		String deleteMsg="Delete employee record for "+ fullName+"?";
		int employeeDetailsCountAfter =paylocityPageObjects.employeeTableDetails.size();
		verifyEquals(true,paylocityPageObjects.screen_DeleteEmployee_title.getText().equals(screenTitle),screenTitle +": screen displayed. " );
		verifyEquals(true,paylocityPageObjects.screen_DeleteEmployee_text.getText().equals(deleteMsg),paylocityPageObjects.screen_DeleteEmployee_text.getText() +": is displayed. " );
		verifyEquals(true,isElementPresent(returnDriver(),paylocityPageObjects.screen_DeleteEmployee_Delete_btn), "Delete button displayed" );
		verifyEquals(true,isElementPresent(returnDriver(),paylocityPageObjects.screen_DeteletEmployee_Cancel_btn), "Cancel button in delete employee screen displayed" );
		verifyEquals(true, employeeDetailsCountAfter < employeeTableRowCount, "Employee Details table row count decreased");
		
	}
	
	public String benefitsCost() {
		DecimalFormat df_obj= new DecimalFormat("#.##");
		
		if((Integer.parseInt(dependant))>=0) {
			double benefitsCost = ((double)(((Integer.parseInt(dependant))*500)+1000)/26);
			bCost =df_obj.format(benefitsCost);
		}else {
			logMessage("No of Dependant should be greater than or equal to zero");
		}
		return bCost;
	}
	
	public String netPay() {
		DecimalFormat df_obj= new DecimalFormat("#.##");
		double pay= 2000- Double.parseDouble(bCost);
		return df_obj.format(pay);
	}
	
	public void verifyBenefitCostCalculations() {
		String benefitCost = benefitsCost();
		String netpay =netPay();	
		String actualBenefitPay =getLocatorValue(covertToWebElement(paylocityPageObjects.employeeTableData.replace("columnNO", "7")));
		String actualNetPay =getLocatorValue(covertToWebElement(paylocityPageObjects.employeeTableData.replace("columnNO", "8")));
		verifyEquals(true,actualBenefitPay.equals(benefitCost),"Benefits cost calculation expected: "+benefitCost +" Actual: "+actualBenefitPay);
		verifyEquals(true,actualNetPay.equals(netpay),"NetPay cost calculation expected: "+netpay +" Actual: "+actualNetPay);
		
	}
}
