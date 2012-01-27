/*
*************************************************MODIFICATION HISTORY************************************************************
Sneha Motadoo 12/08/2010 Created initial script

*/


package sanity_ff;



import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.*;

import java.util.*;

import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;




import com.thoughtworks.selenium.Selenium;

public class Expirations_suite_001 {
	private WebDriver driver;
	
	private String baseUrl=Functions.baseUrl;
	private String composeUrl=Functions.compose_url;
	private String inboxUrl=Functions.inbox_url;
	private String sentUrl=Functions.sent_url;
	private String outboxUrl=Functions.outbox_url;
	private String draftsUrl=Functions.drafts_url;
	
	Selenium selenium;
	private StringBuffer verificationErrors = new StringBuffer();
	String user_allperms="sneha.qa.24@gmail.com",user_noperms="muunni.24@gmail.com",user_onlysecure="snehamtd002@yahoo.com",user_onlylft="snehamtd001@gmail.com";
	String pwd_allusers="123abc";
	String pathAutoItScript=Functions.pathToLessthan25MbFilesScript;
	String pathAutoItScript_greaterthan25MB=Functions.pathToGreaterthan25MbFilesScript;
	WebElement ele=null;
	String expire_field_1="4"; // unit in days
	String expire_field_2="1"; //unit in MB
	String expire_field_3="2"; //unit in days
	String [] tmparr; String DraftId,emailBody,emailId,to_check;
String subject="";

String default_expire_date;
String small_file_expire_date;
String large_file_expire_date;
	
	
	@Before
	//@Ignore
	public void setUp() throws Exception {
		
		System.setProperty("webdriver.firefox.bin","C:\\6.0.2\\Mozilla Firefox\\firefox.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
		
	}

	
	
	@Test
	public void suite_001() throws Exception {
		
		
		//This code will consider the local time zone ,admin settings as files > 1mb will expire in 1 day
		  //use getInstance() method to get object of java Calendar class
	    Calendar cal = Calendar.getInstance();
	   
	    //use getTime() method of Calendar class to get date and time
	    String time=String.valueOf(cal.getTime());	    
	    String day_of_month[]=time.split(" ");
	    System.out.println(day_of_month[2]);
	    String dayofmonth=day_of_month[2];
	    
	    default_expire_date=String.valueOf(Integer.parseInt(dayofmonth)+Integer.parseInt(expire_field_1));
		small_file_expire_date=default_expire_date;
		large_file_expire_date=String.valueOf(Integer.parseInt(dayofmonth)+Integer.parseInt(expire_field_3));
	    
	
	   
	    
	    driver.get(baseUrl + "/");
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys("admin");
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys("123");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(By.id("users")).click();
		driver.findElement(By.id("base-temp")).click();
		driver.findElement(By.id("internal")).click();
		driver.findElement(By.id("id_retain_small_0")).clear();
		driver.findElement(By.id("id_retain_small_0")).sendKeys(expire_field_1);
		WebElement select = driver.findElement(By.xpath("//*[@id='id_retain_small_1']"));
		List<WebElement> options = select.findElements(By.tagName("option"));
		   for (WebElement option : options) {
		 		    if ("days".equalsIgnoreCase(option.getText())){
		     option.click();
		     
		     break;
		    }
		   }
		Thread.sleep(2000);
		driver.findElement(By.name("retain_fulcrum_0")).clear();
		driver.findElement(By.name("retain_fulcrum_0")).sendKeys(expire_field_2);
		
	    
          select = driver.findElement(By.xpath("//html/body/div/div[2]/div[4]/div/form/fieldset[6]/div[3]/div/div/select"));
		 options = select.findElements(By.tagName("option"));
		   for (WebElement option : options) {
		 		    if ("MB".equalsIgnoreCase(option.getText())){
		     option.click();
		     
		     break;
		    }
		   }
		Thread.sleep(2000);
		
		driver.findElement(By.id("id_retain_large_0")).clear();
		driver.findElement(By.id("id_retain_large_0")).sendKeys(expire_field_3);
		select = driver.findElement(By.xpath("//*[@id='id_retain_large_1']"));
		 options = select.findElements(By.tagName("option"));
		   for (WebElement option : options) {
		 		    if ("days".equalsIgnoreCase(option.getText())){
		     option.click();
		     
		     break;
		    }
		   }
		Thread.sleep(2000);
		
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		// ERROR: Caught exception [ERROR: Unsupported command [isTextPresent]]
		
		assertEquals("Default value for domain users were saved successfully!", driver.findElement(By.cssSelector("li.success")).getText());
		driver.findElement(By.id("logout")).click();
		
		// as soon as compose page loads: need to test for default expiration as (current date + expire_field_1)
		
		
		
		//for ex: here small file is assumed as < 1 MB and large files are greater than 1 MB
		
		// Now, to log in as end-user and check these dates on compose page,edit drafts page and forward email pages
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_allperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		driver.get(composeUrl);
		assertEquals("Send Email", driver.findElement(By.id("heading")).getText());
		
		to_check=driver.findElement(By.id("statuspicker")).getAttribute("value");
		System.out.println(to_check);
		String tmparr[]=to_check.split("-");
		to_check=tmparr[2];
		assertEquals(to_check,default_expire_date);
		System.out.println("PASS:The default expiration date matches with the expected");
		
		
		
		// now, to upload small files and check the expiration date
		
        Runtime.getRuntime().exec(pathAutoItScript);
		
		Thread.sleep(3000);
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		ele.click();
		Thread.sleep(2000);
		Functions.WaitForUpload(driver);
		to_check=driver.findElement(By.id("statuspicker")).getAttribute("value");
		System.out.println(to_check);
		String tmparr1[]=to_check.split("-");
		to_check=tmparr1[2];
		assertEquals(to_check,small_file_expire_date);
		System.out.println("PASS:The default expiration date for small files matches with the expected");
		
		
		driver.findElement(By.xpath("//html/body/div/div[2]/div[4]/form/fieldset/div[9]/div/div/div/div[2]/div/table/tbody/tr/td[4]/div")).click();
		
         Runtime.getRuntime().exec(pathAutoItScript_greaterthan25MB);
		
		Thread.sleep(3000);
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		ele.click();
		Thread.sleep(2000);
		
		to_check=driver.findElement(By.id("statuspicker")).getAttribute("value");
		System.out.println(to_check);
		String tmparr2[]=to_check.split("-");
		to_check=tmparr2[2];
		assertEquals(to_check,large_file_expire_date);
		System.out.println("PASS:The default expiration date for large files matches with the expected");
		
		
		
		driver.findElement(By.id("logout")).click();
		
		
		//---------------------------------------------------- end of expiration testing on Compose page---------------------------------------
		
		
		
		
		
		// to test expirations on edit drafts page
		  
		   //to check basic expiration
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_allperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		driver.get(composeUrl);
		assertEquals("Send Email", driver.findElement(By.id("heading")).getText());
		
		to_check=driver.findElement(By.id("statuspicker")).getAttribute("value");
		System.out.println(to_check);
		tmparr=to_check.split("-");
		to_check=tmparr[2];
		assertEquals(to_check,default_expire_date);
		System.out.println("PASS:The default expiration date matches with the expected");
		
		subject="to test basic expiration on edit drafts page";
		driver.findElement(By.id("id_subject")).sendKeys(subject);
		driver.findElement(By.id("saver")).click(); Thread.sleep(3000);
		driver.get(draftsUrl); selenium.waitForPageToLoad("3000");
		Thread.sleep(3000);
		DraftId=Functions.DraftsFindIdwithSubject(driver, selenium, subject);
		driver.get(baseUrl+"/transfer/draft/"+DraftId);
		selenium.waitForPageToLoad("3000");
		assertEquals("Edit Draft", driver.findElement(By.id("heading")).getText());
		
		to_check=driver.findElement(By.id("statuspicker")).getAttribute("value");
		System.out.println(to_check);
		tmparr=to_check.split("-");
		to_check=tmparr[2];
		assertEquals(to_check,default_expire_date);
		System.out.println("PASS:The default expiration date matches with the expected on the edit drafts page");
		driver.findElement(By.id("logout")).click();
		
		//---------------------------------------------------
		
		// to check small file expiration on edit drafts page
		
		
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_allperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		driver.get(composeUrl);
		assertEquals("Send Email", driver.findElement(By.id("heading")).getText());
		
		to_check=driver.findElement(By.id("statuspicker")).getAttribute("value");
		System.out.println(to_check);
		tmparr=to_check.split("-");
		to_check=tmparr[2];
		assertEquals(to_check,default_expire_date);
		System.out.println("PASS:The default expiration date matches with the expected");
		
		subject="to test small file expiration on edit drafts page";
		driver.findElement(By.id("id_subject")).sendKeys(subject);
		
         Runtime.getRuntime().exec(pathAutoItScript);
		
		Thread.sleep(3000);
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		ele.click();
		Thread.sleep(2000);
		Functions.WaitForUpload(driver);
		driver.findElement(By.id("saver")).click(); Thread.sleep(3000);
		driver.get(draftsUrl); selenium.waitForPageToLoad("3000");
		Thread.sleep(3000);
		DraftId=Functions.DraftsFindIdwithSubject(driver, selenium, subject);
		driver.get(baseUrl+"/transfer/draft/"+DraftId);
		selenium.waitForPageToLoad("3000");
		assertEquals("Edit Draft", driver.findElement(By.id("heading")).getText());
		
		to_check=driver.findElement(By.id("statuspicker")).getAttribute("value");
		System.out.println(to_check);
		tmparr=to_check.split("-");
		to_check=tmparr[2];
		assertEquals(to_check,small_file_expire_date);
		System.out.println("PASS:The suggested small file expiration date matches with the expected on the edit drafts page");
		driver.findElement(By.id("logout")).click();
		
		//to check large file expiration on edit drafts page
		
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_allperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		driver.get(composeUrl);
		assertEquals("Send Email", driver.findElement(By.id("heading")).getText());
		
		to_check=driver.findElement(By.id("statuspicker")).getAttribute("value");
		System.out.println(to_check);
		tmparr=to_check.split("-");
		to_check=tmparr[2];
		assertEquals(to_check,default_expire_date);
		System.out.println("PASS:The default expiration date matches with the expected");
		
		subject="to test large file expiration on edit drafts page";
		driver.findElement(By.id("id_subject")).sendKeys(subject);
		
         Runtime.getRuntime().exec(pathAutoItScript_greaterthan25MB);
		
		Thread.sleep(3000);
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		ele.click();
		Thread.sleep(2000);
		Functions.WaitForUpload(driver);
		driver.findElement(By.id("saver")).click(); Thread.sleep(3000);
		driver.get(draftsUrl); selenium.waitForPageToLoad("3000");
		Thread.sleep(3000);
		DraftId=Functions.DraftsFindIdwithSubject(driver, selenium, subject);
		driver.get(baseUrl+"/transfer/draft/"+DraftId);
		selenium.waitForPageToLoad("3000");
		assertEquals("Edit Draft", driver.findElement(By.id("heading")).getText());
		
		to_check=driver.findElement(By.id("statuspicker")).getAttribute("value");
		System.out.println(to_check);
		tmparr=to_check.split("-");
		to_check=tmparr[2];
		assertEquals(to_check,large_file_expire_date);
		System.out.println("PASS:The suggested large file expiration date matches with the expected on the edit drafts page");
		driver.findElement(By.id("logout")).click();
		
		
		//------------------------------------------------------------------------------------
		
	
		
	
		
		// to test expirations on forward mail page
		
		subject="to test default expiration date on forward page";
		emailBody="to test default expiration date on forward page";
		
		Functions.SecureSend(selenium, driver, user_allperms, user_allperms, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_allperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		//driver.get(inboxUrl);
		
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);	
		driver.get(baseUrl+"/transfer/email/view/"+emailId);		
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		
		to_check=driver.findElement(By.id("statuspicker")).getAttribute("value");
		System.out.println(to_check);
		tmparr=to_check.split("-");
		to_check=tmparr[2];
		
		assertEquals(to_check,default_expire_date);
		System.out.println("PASS:The suggested default expiration date matches with the expected on the forward page");
		driver.findElement(By.id("logout")).click();
		
		//to test small file expiration date on forward page
		
		subject="to test small file expiration date on forward page";
		emailBody="to test small file expiration date on forward page";
		
		Functions.LFTSend(selenium, driver, user_allperms, user_allperms, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_allperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		//driver.get(inboxUrl);
		
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);	
		driver.get(baseUrl+"/transfer/email/view/"+emailId);		
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		
		to_check=driver.findElement(By.id("statuspicker")).getAttribute("value");
		System.out.println(to_check);
		tmparr=to_check.split("-");
		to_check=tmparr[2];
		
		assertEquals(to_check,small_file_expire_date);
		System.out.println("PASS:The suggested small file expiration date matches with the expected on the forward page");
		driver.findElement(By.id("logout")).click();
		
		// to test large file expiration date on forward page
		
		
		subject="to test large file expiration date on forward page";
		emailBody="to test large file expiration date on forward page";
		
		Functions.LFTSendGreaterThan25mb(selenium, driver, user_allperms, user_allperms, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_allperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		//driver.get(inboxUrl);
		
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);	
		driver.get(baseUrl+"/transfer/email/view/"+emailId);		
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		
		to_check=driver.findElement(By.id("statuspicker")).getAttribute("value");
		System.out.println(to_check);
		tmparr=to_check.split("-");
		to_check=tmparr[2];
		
		assertEquals(to_check,large_file_expire_date);
		System.out.println("PASS:The suggested large file expiration date matches with the expected on the forward page");
		driver.findElement(By.id("logout")).click();
		
		
		//---------------------------------------

		// to test expirations on reply page
		
		
		subject="to test default expiration date on reply page";
		emailBody="to test default expiration date on reply page";
		Functions.LFTSendGreaterThan25mb(selenium, driver, user_allperms, user_allperms, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_allperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		//driver.get(inboxUrl);
		
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);	
		driver.get(baseUrl+"/transfer/email/view/"+emailId);	
		
		assertEquals("Reply", driver.findElement(By.id("reply")).getText());
		driver.findElement(By.id("reply")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Reply", driver.findElement(By.id("heading")).getText());
		assertEquals("Re: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		
		
		
		to_check=driver.findElement(By.id("statuspicker")).getAttribute("value");
		System.out.println(to_check);
		tmparr=to_check.split("-");
		to_check=tmparr[2];
		
		assertEquals(to_check,default_expire_date);
		System.out.println("PASS:The suggested default expiration date matches with the expected on the reply page");
		driver.findElement(By.id("logout")).click();
		
		
		
		
	  }// end of test
		

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}//end of tearDown

}
