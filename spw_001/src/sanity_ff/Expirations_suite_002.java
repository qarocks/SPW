/************************************************MODIFICATION HISTORY************************************************************
Sneha Motadoo 12/08/2010 Created initial script.. initial cluttered script :P

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

public class Expirations_suite_002 {
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
	String [] tmparr; String DraftId,emailBody,emailId,to_check,sentId;
String subject="";

String default_expire_date;
String small_file_expire_date;
String large_file_expire_date;

String custom_date;
	
	
	@Before
	//@Ignore
	public void setUp() throws Exception {
		
		System.setProperty("webdriver.firefox.bin","C:\\6.0.2\\Mozilla Firefox\\firefox.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
		
	}

	
	
	@Test
	public void suite_002() throws Exception {
		
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
	    
		//-----------------------------------------------------------
		
		// drafts will not preserve expiration dates from the email's last save
		
		// first we upload a small file -> enter custom expiration date -> save draft -> open draft again and then new suggested date should appear 
		
		custom_date="2012-07-24";
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
		driver.findElement(By.id("statuspicker")).clear();
		driver.findElement(By.id("statuspicker")).sendKeys(custom_date);
		
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
		
		
		//-----------------------------------------------------------
		
		
		
		//deleting the custom expiration date means "never expire"
		
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
		driver.findElement(By.id("statuspicker")).clear();
		to_check=driver.findElement(By.id("statuspicker")).getAttribute("class");
		assertEquals(to_check,"hasDatepicker custom");
		
		
		System.out.println("PASS: Deleting the custom expiration date means never expire");
		
		
		//-----------------------------------------------------------
	
		//Expiration and notification settings page should show the correct expiration date(same as that of send time)
		
		  // never
		subject="to match expiration date on policy page 001";
		emailBody="to match expiration date on policy page 001";
		Functions.LFTLessthan25mbNeverExpire(selenium, driver, user_allperms, user_allperms, subject, emailBody, pwd_allusers,baseUrl);
		
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_allperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		driver.get(sentUrl);
		sentId=Functions.FindIdwithSubjectInSent(driver, selenium, subject);
		driver.get(baseUrl+"/transfer/email/view/"+sentId);
		driver.findElement(By.linkText("change")).click();
		String tmp=driver.findElement(By.id("id_email_expire")).getAttribute("value");
		assertEquals(tmp,"");
		System.out.println("PASS: never expire date appaers correctly on policies page");
		driver.findElement(By.id("logout")).click();
		
		    //custom
		String date="2012-07-24";
		Functions.LFTLessthan25mbCustomExpire(date,selenium, driver, user_allperms, user_allperms, subject, emailBody, pwd_allusers,baseUrl);
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_allperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		driver.get(sentUrl);
		sentId=Functions.FindIdwithSubjectInSent(driver, selenium, subject);
		driver.get(baseUrl+"/transfer/email/view/"+sentId);
		driver.findElement(By.linkText("change")).click();
		tmp=driver.findElement(By.id("id_email_expire")).getAttribute("value");
		assertEquals(tmp,date);
		System.out.println("PASS: custom expire date appaers correctly on policies page");
		driver.findElement(By.id("logout")).click();
		
		
		   //suggested
		subject="to match suggested expiration date on policy page for small file retention";
		emailBody="to match suggested expiration date on policy page for small file retention";
		
		date=Functions.LFTSendReturnExpireDate(selenium, driver, user_allperms, user_allperms, subject, emailBody, pwd_allusers,baseUrl);
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_allperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		driver.get(sentUrl);
		sentId=Functions.FindIdwithSubjectInSent(driver, selenium, subject);
		driver.get(baseUrl+"/transfer/email/view/"+sentId);
		driver.findElement(By.linkText("change")).click();
		tmp=driver.findElement(By.id("id_email_expire")).getAttribute("value");
		assertEquals(tmp,date);
		System.out.println("PASS: custom expire date appaers correctly on policies page");
		driver.findElement(By.id("logout")).click();
		
		subject="to match suggested expiration date on policy page for large file retention";
		emailBody="to match suggested expiration date on policy page for large file retention";
		
		date=Functions.LFTSendGreaterThan25mbReturnExpireDate(selenium, driver, user_allperms, user_allperms, subject, emailBody, pwd_allusers,baseUrl);
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_allperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		driver.get(sentUrl);
		sentId=Functions.FindIdwithSubjectInSent(driver, selenium, subject);
		driver.get(baseUrl+"/transfer/email/view/"+sentId);
		driver.findElement(By.linkText("change")).click();
		tmp=driver.findElement(By.id("id_email_expire")).getAttribute("value");
		assertEquals(tmp,date);
		System.out.println("PASS: custom expire date appears correctly on policies page");
		driver.findElement(By.id("logout")).click();
		
		
		
		
		
		
		
		//-----------------------------------------------------------
		
		//Old expiration dates are illegal
		
		
		  //Compose--> save draft
		
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_allperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		driver.get(composeUrl);
		driver.findElement(By.id("statuspicker")).clear();
		driver.findElement(By.id("statuspicker")).sendKeys("2010-01-01");
		driver.findElement(By.id("saver")).click(); Thread.sleep(3000);
		assertEquals("This email's custom expiration date must be a future date. Please fix this and try saving or sending again.", driver.findElement(By.cssSelector("li.error")).getText());
		
		System.out.println("PASS");
	
		
		driver.findElement(By.id("logout")).click();
		
		
		  //compose--> send
		subject="old expiration dates are illegal compose--> send";
		emailBody="old expiration dates are illegal compose--> send";
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_allperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		driver.get(composeUrl);
	
		
		driver.findElement(By.id("secure")).click();
		driver.findElement(By.id("statuspicker")).clear();
		driver.findElement(By.id("statuspicker")).sendKeys("2010-01-01");
		driver.findElement(By.id("addrin")).sendKeys(user_allperms);
		driver.findElement(By.id("id_subject")).sendKeys(subject);
		driver.findElement(By.id("addrsubmit")).click();

		driver.switchTo().frame("id_body_ifr");
				
		selenium.typeKeys("//body[@id='tinymce']", emailBody);
		driver.switchTo().defaultContent();
		driver.findElement(By.id("submitter")).click();
		Thread.sleep(3000);
		assertEquals("This email's custom expiration date must be a future date. Please fix this and try saving or sending again.", driver.findElement(By.cssSelector("li.error")).getText());
		System.out.println("PASS");
		
		driver.findElement(By.id("logout")).click();
		
		
	
		  //edit draft--> save draft
		
		subject="old expiration dates are illegal edit draft--> save draft";
		emailBody="old expiration dates are illegal edit draft--> save draft";
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_allperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		driver.get(composeUrl);
		driver.findElement(By.id("statuspicker")).clear();
		
		driver.findElement(By.id("id_subject")).sendKeys(subject);
		driver.findElement(By.id("saver")).click(); Thread.sleep(3000);
		DraftId=Functions.DraftsFindIdwithSubject(driver, selenium, subject);
		driver.get(baseUrl+"/transfer/draft/"+DraftId);
		driver.findElement(By.id("statuspicker")).clear();
		driver.findElement(By.id("statuspicker")).sendKeys("2010-01-01");
		driver.findElement(By.id("saver")).click(); Thread.sleep(3000);
		assertEquals("This email's custom expiration date must be a future date. Please fix this and try saving or sending again.", driver.findElement(By.cssSelector("li.error")).getText());
		
		System.out.println("PASS");
		driver.findElement(By.id("addrin")).sendKeys(user_allperms);
		
		driver.findElement(By.id("addrsubmit")).click();
		driver.switchTo().frame("id_body_ifr");
		
		selenium.typeKeys("//body[@id='tinymce']", emailBody);
		driver.switchTo().defaultContent();
		driver.findElement(By.id("secure")).click();
		driver.findElement(By.id("submitter")).click();
		Thread.sleep(3000);
		assertEquals("This email's custom expiration date must be a future date. Please fix this and try saving or sending again.", driver.findElement(By.cssSelector("li.error")).getText());
		System.out.println("PASS");
	
		driver.findElement(By.id("logout")).click();
		
		
	
		
		  //reply page
		
		
		subject="to test illegal expiration date on reply page";
		emailBody="to test illegal expiration date on reply page";
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
		
		driver.findElement(By.id("statuspicker")).clear();
		driver.findElement(By.id("statuspicker")).sendKeys("2010-01-01");
		driver.findElement(By.id("saver")).click(); Thread.sleep(3000);
		assertEquals("This email's custom expiration date must be a future date. Please fix this and try saving or sending again.", driver.findElement(By.cssSelector("li.error")).getText());
		
		System.out.println("PASS");
driver.findElement(By.id("addrin")).sendKeys(user_allperms);
		
		driver.findElement(By.id("addrsubmit")).click();
		driver.switchTo().frame("id_body_ifr");
		
		selenium.typeKeys("//body[@id='tinymce']", emailBody);
		driver.switchTo().defaultContent();
		driver.findElement(By.id("secure")).click();
		driver.findElement(By.id("submitter")).click();
		Thread.sleep(3000);
		assertEquals("This email's custom expiration date must be a future date. Please fix this and try saving or sending again.", driver.findElement(By.cssSelector("li.error")).getText());
		System.out.println("PASS");
		
		
		
		
		driver.findElement(By.id("logout")).click();
		
		
		
		  //forward page
		
		
		subject="to test illegal expiration date on forward page";
		emailBody="to test illegal file expiration date on forward page";
		
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
		driver.findElement(By.id("statuspicker")).clear();
		driver.findElement(By.id("statuspicker")).sendKeys("2010-01-01");
		driver.findElement(By.id("saver")).click(); Thread.sleep(3000);
		assertEquals("This email's custom expiration date must be a future date. Please fix this and try saving or sending again.", driver.findElement(By.cssSelector("li.error")).getText());
		
		System.out.println("PASS");
        driver.findElement(By.id("addrin")).sendKeys(user_allperms);
		
		driver.findElement(By.id("addrsubmit")).click();
		driver.switchTo().frame("id_body_ifr");
		
		selenium.typeKeys("//body[@id='tinymce']", emailBody);
		driver.switchTo().defaultContent();
		driver.findElement(By.id("secure")).click();
		driver.findElement(By.id("submitter")).click();
		Thread.sleep(3000);
		assertEquals("This email's custom expiration date must be a future date. Please fix this and try saving or sending again.", driver.findElement(By.cssSelector("li.error")).getText());
		System.out.println("PASS");
		
		driver.findElement(By.id("logout")).click();
		
		
		
		
		//-----------------------------------------------------------
		
		
		
		//"suggested expiration date" should change according to change in transfer size
		
		//compose page
		
		
		//already tested in expirations_suite_001
		
		// edit drafts page
		
	
		
		//already tested in expirations_suite_001
		
		// forward page
		
		
		
		subject="to test changing expiration date on forward page";
		emailBody="to test changing expiration date on forward page";
		
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
		
		
        Runtime.getRuntime().exec(pathAutoItScript);
		
		Thread.sleep(3000);
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		ele.click();
		Thread.sleep(2000);
		Functions.WaitForUpload(driver);
		
		to_check=driver.findElement(By.id("statuspicker")).getAttribute("value");
		System.out.println(to_check);
		tmparr=to_check.split("-");
		to_check=tmparr[2];
		
		assertEquals(to_check,small_file_expire_date);
		System.out.println("PASS:The suggested default expiration date matches with the expected on the fwd page");
		
		
        Runtime.getRuntime().exec(pathAutoItScript_greaterthan25MB);
		
		Thread.sleep(3000);
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		ele.click();
		Thread.sleep(2000);
		Functions.WaitForUpload(driver);
		
		to_check=driver.findElement(By.id("statuspicker")).getAttribute("value");
		System.out.println(to_check);
		tmparr=to_check.split("-");
		to_check=tmparr[2];
		
		assertEquals(to_check,large_file_expire_date);
		System.out.println("PASS:The suggested default expiration date matches with the expected on the fwd page");
		
		
		
		
		
		
		driver.findElement(By.id("logout")).click();
		
		
		
		
		
		
		//reply page
		
	
		subject="to test changing expiration date on reply page";
		emailBody="to test changing expiration date on reply page";
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
        Runtime.getRuntime().exec(pathAutoItScript);
		
		Thread.sleep(3000);
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		ele.click();
		Thread.sleep(2000);
		Functions.WaitForUpload(driver);
		
		to_check=driver.findElement(By.id("statuspicker")).getAttribute("value");
		System.out.println(to_check);
		tmparr=to_check.split("-");
		to_check=tmparr[2];
		
		assertEquals(to_check,small_file_expire_date);
		System.out.println("PASS:The suggested default expiration date matches with the expected on the reply page");
		
		
        Runtime.getRuntime().exec(pathAutoItScript_greaterthan25MB);
		
		Thread.sleep(3000);
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		ele.click();
		Thread.sleep(2000);
		Functions.WaitForUpload(driver);
		
		to_check=driver.findElement(By.id("statuspicker")).getAttribute("value");
		System.out.println(to_check);
		tmparr=to_check.split("-");
		to_check=tmparr[2];
		
		assertEquals(to_check,large_file_expire_date);
		System.out.println("PASS:The suggested default expiration date matches with the expected on the reply page");
		
		
		
		driver.findElement(By.id("logout")).click();
		
		
		
		
		//Following needs to be tested manually: 1) actual email expiration 2) post expiration testing- all of this will be clear from the expire_delete.docx
		
		
	}
	
	

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}//end of tearDown

}

	
	