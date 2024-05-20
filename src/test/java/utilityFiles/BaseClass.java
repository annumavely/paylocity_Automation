package utilityFiles;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

	private static BaseClass baseClass;
	private static WebDriver driver;
	public static final Properties properties = new Properties();
	public final static int TIMEOUT=5;
	private BaseClass() {
		WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TIMEOUT));
	}
	
	public static void openURL(String url) {
        driver.get(url);
    }
           
    public static WebDriver getDriver() {
        return driver;              
    }
    public static void setUpDriver() {
    	if(baseClass==null) {
    		baseClass = new BaseClass();
    	}
    }
    public static void tearDown() {
    	if(driver!=null) {
            driver.quit();
       }
    	baseClass=null;
    }
}
