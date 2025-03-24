package hooks;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

import org.openqa.selenium.edge.EdgeOptions;



import java.time.Duration;

public class DriverFactory {
	 
	//private WebDriver driver;
	 public static  ThreadLocal<WebDriver> tjDriver = new ThreadLocal<WebDriver>();
	 	 
	 
	 //ChromeOptions -- a class to pass on chrome browser options like headless and others
	 public WebDriver initialiseBrowser(String browserName) {
	        if (tjDriver.get() == null) {//Ensure webdriver is only initialized once per thread
	            switch (browserName.toLowerCase()) {
	                case "chrome":
	                   
	                    //driver = new ChromeDriver();
	                	//tjDriver.set(new ChromeDriver());///to run with head uncomment this line and comment out the below
	                	ChromeOptions chromeoptions = new ChromeOptions();
	                	chromeoptions.addArguments("--headless");
	                	tjDriver.set(new ChromeDriver(chromeoptions));
	                    break;
	                case "firefox":
	                    
	                    //driver = new FirefoxDriver();
	                	//tjDriver.set(new FirefoxDriver());///to run with head
	                	FirefoxOptions firefoxoptions = new FirefoxOptions();
	                	firefoxoptions.addArguments("--headless");
	                	tjDriver.set(new FirefoxDriver(firefoxoptions));
	                    break;
	                case "edge":
	                   
	                    //driver = new EdgeDriver();
	                	//tjDriver.set(new EdgeDriver());///to run with head
	                	EdgeOptions edgeoptions = new EdgeOptions();
	                	edgeoptions.addArguments("--headless");
	                	tjDriver.set(new EdgeDriver(edgeoptions));
	                    break;
	                case "safari":
	                   
	                    //driver = new SafariDriver();
	                	tjDriver.set(new SafariDriver());
	                    break;
	                default:
	                    throw new IllegalArgumentException("Browser not supported: " + browserName);
	            }
	            getDriver().manage().window().maximize();
	            getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	        }
	        return getDriver();
	    }
	 
	   public synchronized static WebDriver getDriver() {
	        //return driver;
		   return tjDriver.get();
	    }

	
	   public void closeDriver() {
	        if (getDriver() != null) {
	        	getDriver().quit();
	        }
	    }
}