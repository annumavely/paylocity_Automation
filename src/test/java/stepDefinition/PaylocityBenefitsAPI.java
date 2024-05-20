package stepDefinition;

import utilityFiles.CommonFunctions;
import static io.restassured.RestAssured.given;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.HashMap;
import org.json.JSONObject;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

public class PaylocityBenefitsAPI extends CommonFunctions{
	
	public static Response response;
	String firstName="";
	String lastName="";
	String dependants="";
	String bCost = "";
	int recordCount;
	String requestJSONPath="\\testData\\paylocityAPI\\Request\\";
	String responseJSONPath=System.getProperty("user.dir") + "\\testData\\paylocityAPI\\Response\\";
	String authToken =getDetailsFromPropetiesFile().getProperty("paylocity.authToken");
	String addAPIResponseInputContent="";
	
	
	@Given("^an Employer add firstname as '(.*?)',lastname as '(.*?)' and no of dependants as '(.*?)' in the request and submit$")
	public void addEmployeeDetails(String strFName, String strLName, String strDependants) throws Exception {
		firstName =strFName; lastName = strLName; dependants =strDependants;
		String URL =getDetailsFromPropetiesFile().getProperty("paylocity.addURL");
		String filePath =System.getProperty("user.dir")+ requestJSONPath +"AddEmployee.json";
		String inputContent=new String(Files.readAllBytes(Paths.get(filePath)));
		JSONObject obj = new JSONObject(inputContent);
		
		obj.put("firstName", strFName);
		obj.put("lastName", strLName);
		obj.put("dependants", strDependants);
		String str=obj.toString();
		response = given().contentType("application/json").header("Authorization",authToken).body(str).post(URL);
	}
	
	@Then("^validate the response code$")
	public void validateResponseCode() throws Throwable {
		int statusCode=response.getStatusCode();
		verifyEquals(true,statusCode == 200, "HTTP status code 200 received");
		
//		String recordCount=obj.getString("recordCount");
	}
	@And("^validate response$")
	public void validateResponse() {
		String respOut=response.asString();
		String username = getDetailsFromPropetiesFile().getProperty("paylocity.username");
		JSONObject obj = new JSONObject(respOut);
//		write response to a file
		writeJSONResp(responseJSONPath,"addEmployee_Response.json",respOut);
		
		verifyEquals(true,obj.getString("username").equals(username), "In API response username displayed correctly");
		verifyEquals(true,obj.getString("firstName").equals(firstName), "In API response FirstName displayed correctly");
		verifyEquals(true,obj.getString("lastName").equals(lastName), "In API response lastname displayed correctly");
		verifyEquals(true,obj.get("dependants").toString().equals(dependants), "In API response depandants displayed correctly");
		verifyEquals(true,obj.get("benefitsCost").toString().equals(benefitsCost()), "In API response benefit cost displayed correctly");
		verifyEquals(true,obj.get("net").toString().equals(netPay()), "In API response net pay displayed correctly");
	}
	
	public String benefitsCost() {
		DecimalFormat df_obj = new DecimalFormat("#.#####");
		
		if((Integer.parseInt(dependants))>=0) {
			double benefitsCost = ((double)(((Integer.parseInt(dependants))*500)+1000)/26);
			bCost =df_obj.format(benefitsCost);
		}else {
			logMessage("No of Dependant should be greater than or equal to zero");
		}
		return bCost;
	}
	
	public String netPay() {
		DecimalFormat df_obj = new DecimalFormat("#.####");
		double pay= 2000- Double.parseDouble(bCost);
		return df_obj.format(pay);
	}
	
	@Given("^an Employer submit get employee api request to get employee details$")
	public void getEmployeeDetails() throws Exception {
		
		String URL =getDetailsFromPropetiesFile().getProperty("paylocity.getURL");
		String filePath =responseJSONPath +"addEmployee_Response.json";
		addAPIResponseInputContent=new String(Files.readAllBytes(Paths.get(filePath)));
		JSONObject obj = new JSONObject(addAPIResponseInputContent);
		String id =obj.getString("id");
		URL = URL.replaceAll("IDVALUE", id);
		response = given().header("Authorization",authToken).get(URL);
		System.out.println(response.asString());
		writeJSONResp(responseJSONPath,"getEmployeeDetails_Response.json",response.asString());
	}
	
	@And("^validate response obtained for get api$")
	public void validateGetAPIDetails() {
		JSONObject expectedObj = new JSONObject(addAPIResponseInputContent);
		JSONObject actuOobj = new JSONObject(response.asString());
		verifyEquals(true,expectedObj.getString("username").equals(actuOobj.getString("username")), "In API response username displayed correctly");
		verifyEquals(true,expectedObj.getString("firstName").equals(actuOobj.getString("firstName")), "In API response FirstName displayed correctly");
		verifyEquals(true,expectedObj.getString("lastName").equals(actuOobj.getString("lastName")), "In API response lastname displayed correctly");
		verifyEquals(true,expectedObj.get("dependants").toString().equals(actuOobj.get("dependants").toString()), "In API response depandants displayed correctly");
		verifyEquals(true,expectedObj.get("benefitsCost").toString().equals(actuOobj.get("benefitsCost").toString()), "In API response benefit cost displayed correctly");
		verifyEquals(true,expectedObj.get("net").toString().equals(actuOobj.get("net").toString()), "In API response net pay displayed correctly"); 
	}
	
	@Given("^an Employer update firstname as '(.*?)',lastname as '(.*?)' and no of dependants as '(.*?)' in the request and submit$")
	public void updateEmployeeDetails(String strFName, String strLName, String strDependants) throws Exception {
		firstName =strFName; lastName = strLName; dependants =strDependants;
		
		addAPIResponseInputContent=new String(Files.readAllBytes(Paths.get(responseJSONPath +"addEmployee_Response.json")));
		JSONObject add_obj = new JSONObject(addAPIResponseInputContent);
		String id =add_obj.getString("id");
		
		String URL =getDetailsFromPropetiesFile().getProperty("paylocity.updateURL");
		String filePath =System.getProperty("user.dir")+ requestJSONPath +"UpdateEmployee.json";
		
		String inputContent=new String(Files.readAllBytes(Paths.get(filePath)));
		JSONObject obj = new JSONObject(inputContent);
		obj.put("id", id);
		obj.put("firstName", strFName);
		obj.put("lastName", strLName);
		obj.put("dependants", strDependants);
		String str=obj.toString();
		response = given().contentType("application/json").header("Authorization",authToken).body(str).put(URL);
		System.out.println(response.toString());
		writeJSONResp(responseJSONPath,"updateEmployee_Response.json",response.toString());	
	}
	
	@And("^validate response obtained for update api$")
	public void validateUpdateAPIDetails() {
		JSONObject expectedObj = new JSONObject(addAPIResponseInputContent);
		JSONObject actuOobj = new JSONObject(response.asString());
		System.out.println(response.asString());
		verifyEquals(true,expectedObj.getString("id").equals(actuOobj.getString("id")), "In API response username displayed correctly");
		verifyEquals(true,expectedObj.getString("username").equals(actuOobj.getString("username")), "In API response username displayed correctly");
		verifyEquals(false,expectedObj.getString("firstName").equals(firstName), "In API response FirstName displayed correctly");
		verifyEquals(true,expectedObj.getString("lastName").equals(lastName), "In API response lastname displayed correctly");
		verifyEquals(false,expectedObj.get("dependants").toString().equals(dependants), "In API response depandants displayed correctly");
		verifyEquals(false,expectedObj.get("benefitsCost").toString().equals(benefitsCost()), "In API response benefit cost displayed correctly");
		verifyEquals(false,expectedObj.get("net").toString().equals(netPay()), "In API response net pay displayed correctly"); 
	}
	@Given("^an Employer submit delete employee api request to delete employee details$")
	public void deleteEmployeeDetails() throws IOException {
		String URL =getDetailsFromPropetiesFile().getProperty("paylocity.deleteURL");
		String filePath =responseJSONPath +"addEmployee_Response.json";
		addAPIResponseInputContent=new String(Files.readAllBytes(Paths.get(filePath)));
		JSONObject obj = new JSONObject(addAPIResponseInputContent);
		String id =obj.getString("id");
		logMessage("Employee details detleted for ID: "+id);
		URL = URL.replaceAll("IDVALUE", id);
		response = given().header("Authorization",authToken).delete(URL);
		System.out.println(response.asString());	
	}
	
	@Given("^an Employer submit get employee list api request$")
	public void getEmployeeDetailsList() {
		String URL =getDetailsFromPropetiesFile().getProperty("paylocity.getEmployeeListURL");
		response = given().header("Authorization",authToken).get(URL);
		System.out.println(response.asString());
		writeJSONResp(responseJSONPath,"GetEmployeeList_Response.json",response.asString());
	}
	
	@And("^display no of records displayed$")
	public void getEmployeeList() {
		String responseData =response.asString().replaceAll(",", ":");
		HashMap<String, Integer> h1 = new HashMap<String, Integer>();
        String[] str = responseData.replaceAll("\"", "").split(":");
        for(String str1: str){
            if(h1.containsKey(str1)){
                h1.put(str1, h1.get(str1)+1);
            }else{
                h1.put(str1,1);
            }
        }
        recordCount = h1.get("id");
	}
}
