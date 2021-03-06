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

public class OnBoardUser {
	

	private WebDriver driver;
	Selenium selenium;
	private WebDriverBackedSelenium wdb;
	String stringToTest_tc001="To test Illegal Accessing dummy";
	String sender="sneha.qa.24@gmail.com";
	String recipient="muunni.24@gmail.com";
	String non_related="qatest@lutsendata.com";
	private String baseUrl="http://192.168.1.13";
	String senderPwd="123abc";
	private StringBuffer verificationErrors = new StringBuffer();
	
	JavascriptExecutor js;
	String subject="come on board";
	String emailBody="come on board";
	WebDriver webDriver; 
	
	@Before
	public void setUp() throws Exception {
		
		
		System.setProperty("webdriver.firefox.bin","C:\\6.0.2\\Mozilla Firefox\\firefox.exe");
		driver = new FirefoxDriver();
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
		
		webDriver = new HtmlUnitDriver(true);
	}

	@Test
	public void SecureMain() throws Exception {
		Functions.SecureSend(selenium, driver, sender, recipient, subject, emailBody,senderPwd,baseUrl);
		
		
		//code to check mail by new user
		//driver.get("/ServiceLogin?service=mail&passive=true&rm=false&continue=http://mail.google.com/mail/&scc=1&ltmpl=default&ltmplcache=2");
		driver.get("http://www.gmail.com");
		selenium.waitForPageToLoad("3000");
		driver.findElement(By.id("Email")).click();
		driver.findElement(By.id("Email")).clear();
		driver.findElement(By.id("Email")).sendKeys("muunni.24");
		driver.findElement(By.id("Passwd")).clear();
		driver.findElement(By.id("Passwd")).sendKeys("123sneha_24");
		driver.findElement(By.id("signIn")).click();
		//selenium.waitForPageToLoad("5000");
		//Functions.MyWaitfunc(driver,"//*[@id='canvas_frame']");
		//driver.switchTo().frame("canvas_frame"); 
		//Functions.MyWaitfunc(driver,"//div[@class = 'y6']/span[contains(.,'come on board')]");
		//WebElement mailElement = driver.findElement(By.xpath("//div[@class = 'y6']/span[contains(.,'come on board')]")); 
		//System.out.println(" "+mailElement.getText());
	   
		//System.out.println("tag name is "+mailElement.getTagName());
		
		//selenium.clickAt("xpath=//div[@class = 'y6']/span[contains(.,'come on board')]","0,0");
	    //mailElement.sendKeys("\13");
	    
	    //selenium.keyPress("xpath=//div[@class = 'y6']/span[contains(.,'come on board')]","\13");
		//selenium.waitForFrameToLoad("canvas_frame", "3000");
		Thread.sleep(10000);
		selenium.refresh();
		WebElement myele= driver.findElement(By.partialLinkText("come on board"));
		myele.click();
		myele.sendKeys(Keys.ENTER);
		//assertEquals("Secure E-mail Delivery", driver.findElement(By.xpath("assertEquals("Secure E-mail Delivery", driver.findElement(By.xpath("//td[2]/h3")).getText());")).getText());
		//assertEquals("Secure E-mail Delivery", driver.findElement(By.xpath("//html/body/table[2]/tbody/tr/td[2]/table/tbody/tr/td[2]/table[4]/tbody/tr/td/table/tbody/tr[4]/td/div/div/div/table/tbody/tr[2]/td[2]/h3")).getText());
		assertEquals("", driver.findElement(By.cssSelector("img[alt=\"SECURE!\"]")).getText());			
		//driver.findElement(By.id("gbi4m1")).click();
		//driver.findElement(By.id("gb_71")).click();
		
		String vmmn=driver.findElement(By.partialLinkText("View my message now")).getAttribute("href");
		
		
		driver.findElement(By.partialLinkText("Sign out")).click();
		System.out.println(" "+vmmn);
		driver.get(vmmn);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys("123abc");
		driver.findElement(By.id("id_verify_password")).clear();
		driver.findElement(By.id("id_verify_password")).sendKeys("123abc");
		driver.findElement(By.id("id_full_name")).clear();
		driver.findElement(By.id("id_full_name")).sendKeys("muunni.24@gmail.com");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Your account was successfully created!", driver.findElement(By.cssSelector("li.success")).getText());
		
		assertEquals("come on board", driver.findElement(By.cssSelector("div.subject.secure")).getText());
		
		System.out.println("New user has been on boarded successfully: personalised email option set ON");
		
		
		
		
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