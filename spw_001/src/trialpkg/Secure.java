package trialpkg;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.apache.bcel.classfile.Utility;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import org.openqa.selenium.support.ui.Select;

import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptExecutor;
import com.thoughtworks.selenium.Selenium;

public  class Secure {
	

	private static WebDriver driver;
	static Selenium  selenium;
	private static WebDriverBackedSelenium wdb;
	static String stringToTest_tc001="To test Illegal Accessing dummy";
	static String sender="sneha.qa.24@gmail.com";
	static String recipient="muunni.24@gmail.com";
	static String non_related="qatest@lutsendata.com";
	static private String baseUrl="http://192.168.1.129";
	static private StringBuffer verificationErrors = new StringBuffer();
	
	static JavascriptExecutor js;
	static WebDriver webDriver; 
	
	@Before
	public  void setUp() throws Exception {
		
		//System.setProperty("webdriver.chrome.bin","C:\\Users\\Sneha\\AppData\\Local\\Google\\Chrome\\Application\\chrome.exe");
		System.setProperty("webdriver.firefox.bin","C:\\Mozilla Firefox\\firefox.exe");
		driver = new FirefoxDriver();
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
		
		webDriver = new HtmlUnitDriver(true);
	}

	@Test
	public void SecureMain() throws Exception {
		JavascriptExecutor js = (JavascriptExecutor) webDriver;
		selenium.open(baseUrl);
		selenium.type("id=id_username", sender);
		selenium.type("id=id_password", "123abc");
		selenium.click("css=input[type=\"submit\"]");
		
		selenium.waitForPageToLoad("2000");
	
		System.out.println("First: The page title is "+selenium.getTitle());
		// code to upload file
		
		driver.findElement(By.linkText("Compose")).click();
		
			selenium.waitForPageToLoad("3000");
			
		    driver.findElement(By.id("secure")).click();
			driver.findElement(By.id("addrin")).sendKeys(recipient);
			
			
			driver.findElement(By.id("id_subject")).sendKeys(stringToTest_tc001);

			
			
			
			
							
				driver.findElement(By.id("addrsubmit")).click();
			
				
				
				driver.switchTo().frame("id_body_ifr");
				
				selenium.typeKeys("//body[@id='tinymce']", "Finally wohoooo!!");
				 driver.switchTo().defaultContent();
				
				driver.findElement(By.id("submitter")).click();
			
			
			/* code only if it is LFT
			
			String per_done="//html/body/div/div[2]/div[4]/form/fieldset/div[9]/div/div/div/div[2]/table[2]/tbody/tr/td[3]/span";
			NoSuchElementException e1 = null;
		
			// code to wait for file to be uploaded
			for (int second = 0;; second++) {
				
				if (second >= 60) {fail("timeout");}
				try { if (driver.findElement(By.xpath(per_done)).getText().equalsIgnoreCase("548 KB")) break; } catch (Exception e) {}
				Thread.sleep(1000);
				
				
			}	
			*/
			// mail body 
			
			
			/* code to run if LFT
			//System.out.println((String)js.
			if((this.doesWebElementExist(driver,By.xpath(per_done))) && (driver.findElement(By.xpath(per_done)).getText().equalsIgnoreCase("548 KB")))
				
				{//driver.findElement(By.id("submitter")).click();
				}
				
			else
				throw e1;
			
			*/
			
			/* this section if confirm dialog pops up!
			
			this.MyWaitfunc("//html/body/div[4]/div[11]/div/button/span");
			
         if(driver.findElement(By.xpath("//html/body/div[4]/div[11]/div/button/span")).isDisplayed())
				
				driver.findElement(By.xpath("//html/body/div[4]/div[11]/div/button/span")).click();
			else
				System.out.println("Confirm dialog not up yet!");
			*/
			
		// to check if mail was sent successfully
         
         String success_str_xpath="//html/body/div/div[2]/div[3]/ul/li";
         
         Functions.MyWaitfunc(driver,success_str_xpath);
         if((Functions.doesWebElementExist(driver,By.xpath(success_str_xpath))) && (driver.findElement(By.xpath(success_str_xpath)).getText().contains("Successfully sent the email")))
				
			System.out.println("SUCCESS:Mail successfully sent !");
			else
				{ System.out.println("FAIL:Mail NOT SENT !"); System.out.println(driver.findElement(By.xpath(success_str_xpath)).getText());Exception e1 = null;
				throw e1;}
		
	}
	
	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
}