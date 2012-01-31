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

public class FileCabinet_suite_001 {
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
	String pathToDownloadHandle=Functions.pathToDownloadFileHandler;
	WebElement ele=null;
	String expire_field_1="4"; // unit in days
	String expire_field_2="1"; //unit in MB
	String expire_field_3="2"; //unit in days
	String more_actions_xpath="//html/body/div/div[2]/div[4]/table/tbody/tr/td[2]/div/div/div/a";
	String email_files_xpath="//*[@id='email']";
	String download_file_xpath="//html/body/div/div[2]/div[4]/table/tbody/tr/td[2]/div/div/ul/li/a";
	String add_fsync_xpath="//html/body/div/div[2]/div[4]/table/tbody/tr/td[2]/div/div/ul/li[2]/a";
	String delete_xpath="//html/body/div/div[2]/div[4]/table/tbody/tr/td[2]/div/div/ul/li[3]/a";
	String path_quota=baseUrl+"/filesync/my-files/quota/upload";
	String path_sent=baseUrl+"/filesync/my-files/sent/upload";
	String path_received=baseUrl+"/filesync/my-files/received/upload";
	String path_allfiles=baseUrl+"/filesync/my-files/all/upload";
	
String [] tmparr; String DraftId,emailBody,emailId,to_check;
String subject="";

String default_expire_date;
String small_file_expire_date;
String large_file_expire_date;
String cab_rowid;
int get_rowid;
	
	
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
		
		
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_allperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		driver.get(baseUrl + "/filesync/my-files");
		Thread.sleep(5000);
		
		
		
		/*  ---- test code------
		 * 
		 * 
		 * 
		 * 
		 * get_rowid=2;
		 
		System.out.println(Functions.GetRowIdFromCabinet(driver, selenium, (get_rowid+1)));
		cab_rowid=Functions.GetRowIdFromCabinet(driver, selenium, (get_rowid+1));
		driver.findElement(By.xpath("//*[@id='jqg_MyFilesPlusUpload_"+cab_rowid+"']")).click();
		driver.findElement(By.xpath("//html/body/div/div[2]/div[4]/table/tbody/tr/td[2]/div/div/div/a")).click();
		
		Runtime.getRuntime().exec(pathToDownloadHandle+" Opening Save");
		
		
		driver.findElement(By.xpath("//html/body/div/div[2]/div[4]/table/tbody/tr/td[2]/div/div/ul/li/a")).click();
		
		Thread.sleep(5000);
		
		
		assertEquals("My Files", driver.findElement(By.cssSelector("li.last > span")).getText());
	
		
		System.out.println(Functions.GetRowCntCabinet(driver, selenium));
*/
		
		// download "sent" files and make sure record count is unchanged
		
		
		
		
		//download "received" files and make sure record count is unchanged
		
		//download "file sync" files and make sure record count is unchanged
		
		
		//add to file sync "received" files and count should increase
		
		
		//add to file sync "sent files" and count should increase
		
		
		//add to file sync "file sync" files and count should increase
		
		
		//delete from file sync "received" files and count should decrease
		
		
	   //delete from file sync "sent files" and count should decrease
				
				
	   //delete from file sync "file sync" files and count should decrease
		
		
		//selecting multiple files for download should disable "download" option
		
		
		//upload files to file sync and count should increase
		
		
		
		
		
		
		
		driver.findElement(By.id("logout")).click();
		
		
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

	
