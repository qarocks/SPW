/*
*************************************************MODIFICATION HISTORY************************************************************
Sneha Motadoo 12/08/2010 Created initial script

*/


package sanity_ff;



import java.util.Calendar;
import sanity_ff.Functions.*;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.*;

import java.util.*;

import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;




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
	String path_filesyncfiles=baseUrl+"/filesync/my-files/filesync/upload";
String [] tmparr; String DraftId,emailBody,emailId,to_check;
String subject="",result;

String default_expire_date;
String small_file_expire_date;
String large_file_expire_date;
String cab_rowid;
int get_rowid;
String fname="Oops.jpg.jpg";
Integer cabrows,tmpcabrows,row_limits[];	
	
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
		/*	
		user_allperms="snehamtd001@gmail.com";
		
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_allperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		driver.get(baseUrl + "/filesync/my-files");
		Thread.sleep(5000);
		
		
		
		
		
		
	
		
		//---------------------------------------------------------------------------------
		// download "sent" files and make sure record count is unchanged
		
		
		//selecting last data row to download
		driver.get(path_sent);
		Thread.sleep(5000);
		cabrows=(Functions.GetRowCntCabinet(driver, selenium));
		tmpcabrows=cabrows;
		get_rowid=1;
		System.out.println(Functions.GetRowIdFromCabinet_SR(driver, selenium, (get_rowid+1)));
		cab_rowid=Functions.GetRowIdFromCabinet_SR(driver, selenium, (get_rowid+1));
		driver.findElement(By.xpath("//*[@id='jqg_MyFilesPlusUploadNoDel_"+cab_rowid+"']")).click();
		Runtime.getRuntime().exec(pathToDownloadHandle+" Opening Save");
		driver.findElement(By.xpath(more_actions_xpath)).click();
		driver.findElement(By.xpath(download_file_xpath)).click();
		Thread.sleep(5000);
		assertEquals("My Files", driver.findElement(By.cssSelector("li.last > span")).getText());
		cabrows=(Functions.GetRowCntCabinet(driver, selenium));
		if(tmpcabrows.equals(cabrows))
		System.out.println("PASS: First record under sent items successfully downloaded ");
		else
			System.out.println("FAIL!");
		
		
		driver.get(path_sent);
		Thread.sleep(5000);
		for(int i=0;i<13;i++)
		{
			Thread.sleep(500);
			//selenium.waitForFrameToLoad("xpath=//*[@id='content-area']", "3000");
        selenium.keyPress("xpath=//*[@id='content-area']", "\t");
		}
		
		for(int i=0;i<1000;i++){
			
			//Thread.sleep(500);
			selenium.keyPress("xpath=//*[@id='content-area']", "\\40");
		}
		
		//Thread.sleep(3000);
		row_limits=Functions.GetRowCntLimitsCabinet(driver, selenium);
		cabrows=(Functions.GetRowCntCabinet(driver, selenium));
		tmpcabrows=cabrows;
		get_rowid=cabrows;
		System.out.println(Functions.GetRowIdFromCabinetWithLimits_SR(driver, selenium, (get_rowid+1),row_limits));
		cab_rowid=Functions.GetRowIdFromCabinet_SR(driver, selenium, (get_rowid+1));
		driver.findElement(By.xpath("//*[@id='jqg_MyFilesPlusUploadNoDel_"+cab_rowid+"']")).click();
		Runtime.getRuntime().exec(pathToDownloadHandle+" Opening Save");
		driver.findElement(By.xpath(more_actions_xpath)).click();
		driver.findElement(By.xpath(download_file_xpath)).click();
		Thread.sleep(5000);
		assertEquals("My Files", driver.findElement(By.cssSelector("li.last > span")).getText());
		cabrows=(Functions.GetRowCntCabinet(driver, selenium));
		if(tmpcabrows.equals(cabrows))
		System.out.println("PASS: Last record under sent items successfully downloaded ");
		else
			System.out.println("FAIL!");
	
		
		
		
		//---------------------------------------------------------------------------------	
		//download "received" files and make sure record count is unchanged
		
		
		
		driver.get(path_received);
		Thread.sleep(5000);
		cabrows=(Functions.GetRowCntCabinet(driver, selenium));
		tmpcabrows=cabrows;
		get_rowid=1;
		System.out.println(Functions.GetRowIdFromCabinet_SR(driver, selenium, (get_rowid+1)));
		cab_rowid=Functions.GetRowIdFromCabinet_SR(driver, selenium, (get_rowid+1));
		driver.findElement(By.xpath("//*[@id='jqg_MyFilesPlusUploadNoDel_"+cab_rowid+"']")).click();
		Runtime.getRuntime().exec(pathToDownloadHandle+" Opening Save");
		driver.findElement(By.xpath(more_actions_xpath)).click();
		driver.findElement(By.xpath(download_file_xpath)).click();
		Thread.sleep(5000);
		assertEquals("My Files", driver.findElement(By.cssSelector("li.last > span")).getText());
		cabrows=(Functions.GetRowCntCabinet(driver, selenium));
		if(tmpcabrows.equals(cabrows))
		System.out.println("PASS: First record under received items successfully downloaded ");
		else
			System.out.println("FAIL!");
		
		
		driver.get(path_received);
		Thread.sleep(5000);
		for(int i=0;i<13;i++)
		{
			Thread.sleep(500);
			//selenium.waitForFrameToLoad("xpath=//*[@id='content-area']", "3000");
        selenium.keyPress("xpath=//*[@id='content-area']", "\t");
		}
		
		for(int i=0;i<1000;i++){
			
			//Thread.sleep(500);
			selenium.keyPress("xpath=//*[@id='content-area']", "\\40");
		}
		
		//Thread.sleep(3000);
		row_limits=Functions.GetRowCntLimitsCabinet(driver, selenium);
		cabrows=(Functions.GetRowCntCabinet(driver, selenium));
		tmpcabrows=cabrows;
		get_rowid=cabrows;
		System.out.println(Functions.GetRowIdFromCabinetWithLimits_SR(driver, selenium, (get_rowid+1),row_limits));
		cab_rowid=Functions.GetRowIdFromCabinet_SR(driver, selenium, (get_rowid+1));
		driver.findElement(By.xpath("//*[@id='jqg_MyFilesPlusUploadNoDel_"+cab_rowid+"']")).click();
		Runtime.getRuntime().exec(pathToDownloadHandle+" Opening Save");
		driver.findElement(By.xpath(more_actions_xpath)).click();
		driver.findElement(By.xpath(download_file_xpath)).click();
		Thread.sleep(5000);
		assertEquals("My Files", driver.findElement(By.cssSelector("li.last > span")).getText());
		cabrows=(Functions.GetRowCntCabinet(driver, selenium));
		if(tmpcabrows.equals(cabrows))
		System.out.println("PASS: Last record under received items successfully downloaded ");
		else
			System.out.println("FAIL!");
		
		
	
		
		//---------------------------------------------------------------------------------
		//download "file sync" files and make sure record count is unchanged
		
		driver.get(path_filesyncfiles);
		Thread.sleep(5000);
		
		cabrows=(Functions.GetRowCntCabinet(driver, selenium));
		tmpcabrows=cabrows;
		get_rowid=1;
		System.out.println(Functions.GetRowIdFromCabinet(driver, selenium, (get_rowid+1)));
		cab_rowid=Functions.GetRowIdFromCabinet(driver, selenium, (get_rowid+1));
		driver.findElement(By.xpath("//*[@id='jqg_MyFilesPlusUpload_"+cab_rowid+"']")).click();
		Runtime.getRuntime().exec(pathToDownloadHandle+" Opening Save");
		driver.findElement(By.xpath(more_actions_xpath)).click();
		driver.findElement(By.xpath(download_file_xpath)).click();
		Thread.sleep(5000);
		assertEquals("My Files", driver.findElement(By.cssSelector("li.last > span")).getText());
		cabrows=(Functions.GetRowCntCabinet(driver, selenium));
		if(tmpcabrows.equals(cabrows))
		System.out.println("PASS: First record under file sync items successfully downloaded ");
		else
			System.out.println("FAIL!");
			
		
		
	
		
		
		
		
		driver.get(path_filesyncfiles);
		Thread.sleep(5000);
		for(int i=0;i<13;i++)
		{
			Thread.sleep(500);
			//selenium.waitForFrameToLoad("xpath=//*[@id='content-area']", "3000");
        selenium.keyPress("xpath=//*[@id='content-area']", "\t");
		}
		
		for(int i=0;i<1000;i++){
			
			//Thread.sleep(500);
			selenium.keyPress("xpath=//*[@id='content-area']", "\\40");
		}
		
		//Thread.sleep(3000);
		row_limits=Functions.GetRowCntLimitsCabinet(driver, selenium);
		cabrows=(Functions.GetRowCntCabinet(driver, selenium));
		tmpcabrows=cabrows;
		get_rowid=(int)cabrows;
		System.out.println("row id to find is"+ get_rowid);
		//System.out.println(Functions.GetRowIdFromCabinet(driver, selenium, (get_rowid+1)));
		cab_rowid=Functions.GetRowIdFromCabinetWithLimits(driver, selenium, (get_rowid+1),row_limits);
		System.out.println("id for last row:"+ cab_rowid);
		driver.findElement(By.xpath("//*[@id='jqg_MyFilesPlusUpload_"+cab_rowid+"']")).click();
		Runtime.getRuntime().exec(pathToDownloadHandle+" Opening Save");
		driver.findElement(By.xpath(more_actions_xpath)).click();
		driver.findElement(By.xpath(download_file_xpath)).click();
		Thread.sleep(5000);
		assertEquals("My Files", driver.findElement(By.cssSelector("li.last > span")).getText());
		cabrows=(Functions.GetRowCntCabinet(driver, selenium));
		if(tmpcabrows.equals(cabrows))
		System.out.println("PASS: Last record under file sync items successfully downloaded ");
		else
			System.out.println("FAIL!");
		
		driver.findElement(By.id("logout")).click();
		//Thread.sleep(50000);
		
	
		
		
		
		
		//---------------------------------------------------------------------------------
		//add to file sync "received" files and count should increase
		
		   // here we need to first send an email with an attachment. Log in as recipient and then check for the file in the received files list.
		
		emailBody="adding files to received file sync files";
		subject=emailBody;
		Functions.login(driver, user_onlylft, pwd_allusers);
		driver.get(path_received);
		Thread.sleep(3000);
		cabrows=(Functions.GetRowCntCabinet(driver, selenium));
		driver.findElement(By.id("logout")).click();
		tmpcabrows=cabrows;
		Functions.LFTSend(selenium, driver, user_allperms, user_onlylft, subject, emailBody, pwd_allusers, baseUrl);
		Functions.login(driver, user_onlylft, pwd_allusers);
		Thread.sleep(5000);
		driver.get(path_received);
		Thread.sleep(3000);
		result=Functions.FindFileFromCabinet_SR(driver, selenium, fname);
		if(result.equals("null"))
			assertEquals("0","1");
		else
			System.out.println("PASS:file id is : "+result);
		tmpcabrows++;
		cabrows=(Functions.GetRowCntCabinet(driver, selenium));
		if(tmpcabrows.equals(cabrows))
		System.out.println("PASS: file cabinet count increased by one");
		else
			System.out.println("FAIL!");
		driver.findElement(By.id("logout")).click();
		//---------------------------------------------------------------------------------
	
		
		//add to file sync "sent files" and count should increase
		
		
		emailBody="adding files to received file sync files";
		subject=emailBody;
		Functions.login(driver, user_allperms, pwd_allusers);
		driver.get(path_sent);
		Thread.sleep(3000);
		cabrows=(Functions.GetRowCntCabinet(driver, selenium));
		driver.findElement(By.id("logout")).click();
		tmpcabrows=cabrows;
		Functions.LFTSend(selenium, driver, user_allperms, user_onlylft, subject, emailBody, pwd_allusers, baseUrl);
		Functions.login(driver, user_allperms, pwd_allusers);
		Thread.sleep(5000);
		driver.get(path_sent);
		Thread.sleep(3000);
		result=Functions.FindFileFromCabinet_SR(driver, selenium, fname);
		if(result.equals("null"))
			assertEquals("0","1");
		else
			System.out.println("PASS:file id is : "+result);
		tmpcabrows++;
		cabrows=(Functions.GetRowCntCabinet(driver, selenium));
		if(tmpcabrows.equals(cabrows))
		System.out.println("PASS: file cabinet count increased by one");
		else
			System.out.println("FAIL!");
		driver.findElement(By.id("logout")).click();
		
		
		
		//---------------------------------------------------------------------------------
		//add to file sync "file sync" files and count should increase
		
		Functions.login(driver, user_allperms, pwd_allusers);
		driver.get(path_filesyncfiles);
		Thread.sleep(3000);
		cabrows=(Functions.GetRowCntCabinet(driver, selenium));
		
		tmpcabrows=cabrows;
        Runtime.getRuntime().exec(pathAutoItScript);
		
		Thread.sleep(3000);
		driver.findElement(By.id("cabadd")).click();
		driver.findElement(By.xpath("//html/body/div[3]/div[2]/div[2]/div/div/div/div[2]/table[2]/tbody/tr/td/div/a/span[2]")).click();
		Thread.sleep(2000);
		Functions.WaitForUpload(driver);
		driver.findElement(By.xpath("//html/body/div[3]/div/a/span")).click();
		driver.get(path_filesyncfiles);
		Thread.sleep(3000);
		
		result=Functions.FindFileFromCabinet(driver, selenium, fname);
		if(result.equals("null"))
			assertEquals("0","1");
		else
			System.out.println("PASS:file id is : "+result);
		tmpcabrows++;
		cabrows=(Functions.GetRowCntCabinet(driver, selenium));
		if(tmpcabrows.equals(cabrows))
		System.out.println("PASS: file cabinet count increased by one");
		else
			System.out.println("FAIL!");
		driver.findElement(By.id("logout")).click();
		
			
		
		//---------------------------------------------------------------------------------
		//delete from file sync "received" files and delete ption should not be available and count should not decrease
		
		emailBody="adding files to received file sync files";
		subject=emailBody;
		Functions.login(driver, user_onlylft, pwd_allusers);
		driver.get(path_received);
		Thread.sleep(3000);
		cabrows=(Functions.GetRowCntCabinet(driver, selenium));
		driver.findElement(By.id("logout")).click();
		tmpcabrows=cabrows;
		Functions.LFTSend(selenium, driver, user_allperms, user_onlylft, subject, emailBody, pwd_allusers, baseUrl);
		Functions.login(driver, user_onlylft, pwd_allusers);
		Thread.sleep(5000);
		driver.get(path_received);
		Thread.sleep(3000);
		result=Functions.FindFileFromCabinet_SR(driver, selenium, fname);
		if(result.equals("null"))
			assertEquals("0","1");
		else
			System.out.println("PASS:file id is : "+result);
		tmpcabrows++;
		cabrows=(Functions.GetRowCntCabinet(driver, selenium));
		if(tmpcabrows.equals(cabrows))
		System.out.println("PASS: file cabinet count increased by one");
		else
			System.out.println("FAIL!");
		tmpcabrows=cabrows;
		
		result=Functions.GetRowIdFromCabinet_SR(driver, selenium,2);
		driver.findElement(By.xpath("//*[@id='jqg_MyFilesPlusUploadNoDel_"+result+"']")).click();
		driver.findElement(By.xpath(more_actions_xpath)).click();
		
		try{
		driver.findElement(By.xpath(delete_xpath)).click();}
		catch(Exception e)
		{System.out.println("PASS: delte option not available");}
	
		
			driver.get(path_allfiles);
			Thread.sleep(3000);
			result=Functions.GetRowIdFromCabinet(driver, selenium,2);
			driver.findElement(By.xpath("//*[@id='jqg_MyFilesPlusUpload_"+result+"']")).click();
			driver.findElement(By.xpath(more_actions_xpath)).click();
			Thread.sleep(5000);
			cabrows=(Functions.GetRowCntCabinet(driver, selenium));
			if(tmpcabrows.equals(cabrows))
			System.out.println("PASS: file cabinet count unchanged");
			else
				System.out.println("FAIL!");
			
		driver.findElement(By.id("logout")).click();
		
		
		//---------------------------------------------------------------------------------
	   //delete from file sync "sent files" and delete option should not be available and count should be unchanged
				
		emailBody="adding files to received file sync files";
		subject=emailBody;
		Functions.login(driver, user_allperms, pwd_allusers);
		driver.get(path_sent);
		Thread.sleep(3000);
		cabrows=(Functions.GetRowCntCabinet(driver, selenium));
		driver.findElement(By.id("logout")).click();
		tmpcabrows=cabrows;
		Functions.LFTSend(selenium, driver, user_allperms, user_onlylft, subject, emailBody, pwd_allusers, baseUrl);
		Functions.login(driver, user_allperms, pwd_allusers);
		Thread.sleep(5000);
		driver.get(path_sent);
		Thread.sleep(3000);
		result=Functions.FindFileFromCabinet_SR(driver, selenium, fname);
		if(result.equals("null"))
			assertEquals("0","1");
		else
			System.out.println("PASS:file id is : "+result);
		tmpcabrows++;
		cabrows=(Functions.GetRowCntCabinet(driver, selenium));
		if(tmpcabrows.equals(cabrows))
		System.out.println("PASS: file cabinet count increased by one cnt is "+cabrows);
		else
			System.out.println("FAIL!");
		tmpcabrows=cabrows;
		
		result=Functions.GetRowIdFromCabinet_SR(driver, selenium,2);
		driver.findElement(By.xpath("//*[@id='jqg_MyFilesPlusUploadNoDel_"+result+"']")).click();
		driver.findElement(By.xpath(more_actions_xpath)).click();
		
		try{
		driver.findElement(By.xpath(delete_xpath)).click();}
		catch(Exception e)
		{System.out.println("PASS: delte option not available");}
	
		
			driver.get(path_allfiles);
			Thread.sleep(3000);
			cabrows=(Functions.GetRowCntCabinet(driver, selenium));
			tmpcabrows=cabrows;
			result=Functions.GetRowIdFromCabinet(driver, selenium,2);
			driver.findElement(By.xpath("//*[@id='jqg_MyFilesPlusUpload_"+result+"']")).click();
			driver.findElement(By.xpath(more_actions_xpath)).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath(delete_xpath)).click();
			Thread.sleep(5000);
			cabrows=(Functions.GetRowCntCabinet(driver, selenium));
			System.out.println("tmpcabrows is "+tmpcabrows+" and cabrows is "+cabrows);
			if(tmpcabrows.equals(cabrows))
			System.out.println("PASS: file cabinet count unchanged");
			else
				System.out.println("FAIL!");
			
		driver.findElement(By.id("logout")).click();
			
		
		
		//---------------------------------------------------------------------------------
	   //delete from file sync "file sync" files and count should decrease
		

		Functions.login(driver, user_allperms, pwd_allusers);
		driver.get(path_filesyncfiles);
		Thread.sleep(3000);
		cabrows=(Functions.GetRowCntCabinet(driver, selenium));
		
		tmpcabrows=cabrows;
        Runtime.getRuntime().exec(pathAutoItScript);
		
		Thread.sleep(3000);
		driver.findElement(By.id("cabadd")).click();
		driver.findElement(By.xpath("//html/body/div[3]/div[2]/div[2]/div/div/div/div[2]/table[2]/tbody/tr/td/div/a/span[2]")).click();
		Thread.sleep(2000);
		Functions.WaitForUpload(driver);
		driver.findElement(By.xpath("//html/body/div[3]/div/a/span")).click();
		driver.get(path_filesyncfiles);
		Thread.sleep(3000);
		
		result=Functions.FindFileFromCabinet(driver, selenium, fname);
		if(result.equals("null"))
			assertEquals("0","1");
		else
			System.out.println("PASS:file id is : "+result);
		tmpcabrows++;
		cabrows=(Functions.GetRowCntCabinet(driver, selenium));
		if(tmpcabrows.equals(cabrows))
		System.out.println("PASS: file cabinet count increased by one");
		else
			System.out.println("FAIL!");
		
		result=Functions.GetRowIdFromCabinet(driver, selenium,2);
		driver.findElement(By.xpath("//*[@id='jqg_MyFilesPlusUpload_"+result+"']")).click();
		driver.findElement(By.xpath(more_actions_xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(delete_xpath)).click();
		Thread.sleep(5000);
		cabrows=(Functions.GetRowCntCabinet(driver, selenium));
		tmpcabrows--;
		if(tmpcabrows.equals(cabrows))
			System.out.println("PASS: file cabinet count decreased by one");
			else
				System.out.println("FAIL!");
		
		
		
		driver.findElement(By.id("logout")).click();

		
		
		
		
		
		//---------------------------------------------------------------------------------
		//selecting multiple files for download should disable "download" option on each page
		
		// all files page
		Functions.login(driver, user_allperms, pwd_allusers);
		driver.get(path_allfiles);
		Thread.sleep(3000);
		result=Functions.GetRowIdFromCabinet(driver, selenium, 2);
		driver.findElement(By.xpath("//*[@id='jqg_MyFilesPlusUpload_"+result+"']")).click();
		result=Functions.GetRowIdFromCabinet(driver, selenium, 3);
		driver.findElement(By.xpath("//*[@id='jqg_MyFilesPlusUpload_"+result+"']")).click();
        driver.findElement(By.xpath(more_actions_xpath)).click();
		
		assertEquals(driver.findElement(By.xpath(download_file_xpath)).getAttribute("class"),"disabled");
		
		System.out.println("PASS: download option not available for multiple files");
		driver.findElement(By.id("logout")).click();
		
		
		// sent files page
				Functions.login(driver, user_allperms, pwd_allusers);
				driver.get(path_sent);
				Thread.sleep(3000);
				result=Functions.GetRowIdFromCabinet_SR(driver, selenium, 2);
				driver.findElement(By.xpath("//*[@id='jqg_MyFilesPlusUploadNoDel_"+result+"']")).click();
				result=Functions.GetRowIdFromCabinet_SR(driver, selenium, 3);
				driver.findElement(By.xpath("//*[@id='jqg_MyFilesPlusUploadNoDel_"+result+"']")).click();
		        driver.findElement(By.xpath(more_actions_xpath)).click();
				
				assertEquals(driver.findElement(By.xpath(download_file_xpath)).getAttribute("class"),"disabled");
				
				System.out.println("PASS: download option not available for multiple files");
		
				driver.findElement(By.id("logout")).click();
				// received page
				Functions.login(driver, user_allperms, pwd_allusers);
				driver.get(path_received);
				Thread.sleep(3000);
				result=Functions.GetRowIdFromCabinet_SR(driver, selenium, 2);
				driver.findElement(By.xpath("//*[@id='jqg_MyFilesPlusUploadNoDel_"+result+"']")).click();
				result=Functions.GetRowIdFromCabinet_SR(driver, selenium, 3);
				driver.findElement(By.xpath("//*[@id='jqg_MyFilesPlusUploadNoDel_"+result+"']")).click();
		        driver.findElement(By.xpath(more_actions_xpath)).click();
				
				assertEquals(driver.findElement(By.xpath(download_file_xpath)).getAttribute("class"),"disabled");
				
				System.out.println("PASS: download option not available for multiple files");
				driver.findElement(By.id("logout")).click();
				// file sync files page
				Functions.login(driver, user_allperms, pwd_allusers);
				driver.get(path_filesyncfiles);
				Thread.sleep(3000);
				result=Functions.GetRowIdFromCabinet(driver, selenium, 2);
				driver.findElement(By.xpath("//*[@id='jqg_MyFilesPlusUpload_"+result+"']")).click();
				result=Functions.GetRowIdFromCabinet(driver, selenium, 3);
				driver.findElement(By.xpath("//*[@id='jqg_MyFilesPlusUpload_"+result+"']")).click();
		        driver.findElement(By.xpath(more_actions_xpath)).click();
				
				assertEquals(driver.findElement(By.xpath(download_file_xpath)).getAttribute("class"),"disabled");
				
				System.out.println("PASS: download option not available for multiple files");
		
				driver.findElement(By.id("logout")).click();
		
		//---------------------------------------------------------------------------------
		
	*/
		//"email files" functionality - sent files,received files,file sync files
		
		//sent files
		
		/*subject="email files functionality 001";
		emailBody=subject;
		Functions.GenericMailSend(selenium, driver, user_allperms, user_onlylft, subject, emailBody, pwd_allusers, baseUrl, 0, 0, "null", 1, Functions.pathTo25Files, 0);
		Thread.sleep(5000);
		*/
		
Functions.login(driver, user_allperms, pwd_allusers);
driver.get(path_sent);
Thread.sleep(3000);
for(int i=0;i<15;i++)
{
	result=Functions.GetRowIdFromCabinet_SR(driver, selenium, i+2);
	driver.findElement(By.xpath("//*[@id='jqg_MyFilesPlusUploadNoDel_"+result+"']")).click();

}
		driver.findElement(By.xpath(email_files_xpath)).click();
	     selenium.waitForPageToLoad("5000");
		//Thread.sleep(500000);
		
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

	
