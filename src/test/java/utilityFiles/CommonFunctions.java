package utilityFiles;



import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class CommonFunctions {
	
	public static final Properties properties = new Properties();
	
	public WebDriver returnDriver() {
		return BaseClass.getDriver();
		
	}

	public void clickElement(WebElement objWebElement) {
		objWebElement.click();
	}
	
	public void sendKeys(WebElement objWebElement, String fieldName, String strValue) {
		objWebElement.clear();
		objWebElement.sendKeys(strValue);	
		Reporter.log("Data entered for "+fieldName + " : "+strValue);
	}
	
	public String getPageTitle() {
		return BaseClass.getDriver().getTitle();
	}
	
	public boolean verifyEquals(Boolean Value1, Boolean Value2, String strStepDesc) {
		boolean bverifyEquals = true;
		try {
			
			if (Value1 == Value2) {
				Reporter.log(strStepDesc +": Validation PASS");
				
			} else {
				bverifyEquals = false;
				Reporter.log(strStepDesc +": Validation failed");
				takeScreenshotForFailure();
			}
		} catch (Exception e) {
			bverifyEquals = false;
			Assert.assertTrue(bverifyEquals);
		}
		return bverifyEquals;
	}
	
	public boolean verifyElementExists(By byXPath) {
		try {
			returnDriver().findElement(byXPath);
		} catch (NoSuchElementException e) {
			return false;
		}
		return true;
	}
	
	public void logMessage(String strMessage) {
		Reporter.log(strMessage);
	}
	
	public boolean isAlertPresent(WebDriver driver) {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException Ex) {
			return false;
		}
	}
	
	public By covertToWebElement(String str) {
		By locator = By.xpath(str);
		return locator;
	}
	
	public String getLocatorValue(By byXPath) {
		waitForElementToBeVisible(returnDriver(),byXPath,5);
		try {
			return BaseClass.getDriver().findElement(byXPath).getText();
		}
		catch(Exception StaleElementReferenceException){
			waitForElement(returnDriver(),byXPath,5);
			return BaseClass.getDriver().findElement(byXPath).getText();
		}
	}
	
	public void clickElement(By locator) {
		BaseClass.getDriver().findElement(locator).click();
	}
	
	public boolean isElementPresent(WebDriver driver, WebElement element) {
		try {
			element.isDisplayed();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	public Properties getDetailsFromPropetiesFile() {
		try {
			properties.load(new FileReader(System.getProperty("user.dir") + "/" + "appURL.properties"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return properties;
	}
	
	public void writeJSONResp(String pathToWrite, String fileName, String dataToWrite) {
		String path = pathToWrite+ fileName;
		try {
			File file = new File(path);
			if(file.exists()) {
				file.delete();
			}
			FileWriter fr = new FileWriter(path);
			fr.write(dataToWrite);
			fr.flush();
			fr.close();
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
	
	public static Map<String, Object> toMap(JSONObject jsonobj)  throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();
        Iterator<String> keys = jsonobj.keys();
        while(keys.hasNext()) {
            String key = keys.next();
            Object value = jsonobj.get(key);
            if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }   
            map.put(key, value);
        }   return map;
    }
	
	public void waitForElementToBeClickable(WebDriver driver, WebElement element, long timeOutInSeconds) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));
			wait.until(ExpectedConditions.elementToBeClickable(element));
		} catch (Exception objException) {
			logMessage("Element not clickable in specified time");
		}
	}
	public void waitForElement(WebDriver driver,By selector, long timeOutInSeconds) {
		new WebDriverWait(driver,Duration.ofSeconds(timeOutInSeconds)).ignoring(StaleElementReferenceException.class).
		until(ExpectedConditions.visibilityOfAllElementsLocatedBy(selector));
	}
	
	public void waitForElementToBeVisible(WebDriver driver, By element, long timeOutInSeconds) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));
			wait.until(ExpectedConditions.visibilityOfElementLocated(element));
		} catch (Exception e) {
			logMessage("Element not clickable in specified time");
		}
	}
	public void waitForElementToBeVisible(WebDriver driver, List<WebElement> element, long timeOutInSeconds) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));
			wait.until(ExpectedConditions.visibilityOfAllElements(element));
		} catch (Exception e) {
			logMessage("Element not clickable in specified time");
		}
	}
	
	public void takeScreenshotForFailure() {
		String strAppendDateTimeUpdate;
		String strResultsFolder = System.getProperty("user.dir");
		strAppendDateTimeUpdate = GetDateTimeStringWithSeconds();
		File objScreenshot =((TakesScreenshot)BaseClass.getDriver()).getScreenshotAs(OutputType.FILE);
		String copyFile =strResultsFolder + "/Screenshots/" + strAppendDateTimeUpdate + ".png";
		try {
			FileUtils.copyFile(objScreenshot,
					new File(copyFile));
			Reporter.log("<br><img src='"+copyFile+ "' height='500' width='800'/><br>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public String GetDateTimeStringWithSeconds() {
		try {
			SimpleDateFormat objSimpleDtFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
			Date objDate = new Date();
			Calendar objCal = Calendar.getInstance();
			objCal.setTime(objDate);
			String strAppendDateTime = objSimpleDtFormat.format(new Date());
			strAppendDateTime = (strAppendDateTime.replace("/", "-").replace(":", "-")).trim();
			return strAppendDateTime;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "";
		}
	}
	 public int tableRowCount( WebDriver driver, List<WebElement> locator, long timeOutInSeconds) {
		 waitForElementToBeVisible(driver,locator,timeOutInSeconds);
		 return locator.size();
	 }
	
}
