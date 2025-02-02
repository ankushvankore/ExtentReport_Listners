package com.ExtentReportDemos;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class D02ReportViaTestNG {
	ExtentSparkReporter htmlReporter;
	ExtentReports report;
	ExtentTest test;
	
	WebDriver driver;
	
	@BeforeTest
	public void configure() {
		htmlReporter = new ExtentSparkReporter("Reports\\OrangeHRMTestResult.html");
		report = new ExtentReports();
		report.attachReporter(htmlReporter);
		
		report.setSystemInfo("Machine Name", "Dell");
		report.setSystemInfo("OS", "Windows 11");
		report.setSystemInfo("User", "Ankush");
		report.setSystemInfo("Browser", "Google Chrome");
		report.setSystemInfo("Processor", "Intel(R) Core(TM) i5-7300U CPU @ 2.60GHz   2.70 GHz");

		// Configuration for look of report
		htmlReporter.config().setDocumentTitle("My First Report");
		htmlReporter.config().setReportName("Google Title Report");
		htmlReporter.config().setTheme(Theme.DARK);
		htmlReporter.config().setTimeStampFormat("EEEE MMMM dd yyyy, hh:mm a '('zzz')'");
		
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
	}

	@Test(priority = 1)
	public void loginWithValidData() {
		driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys("admin");
		driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("admin123");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		
		Assert.assertTrue(driver.getCurrentUrl().contains("dash"), "Login fail because of invalid data");
	}
	@Test(priority = 2)
	public void loginWithInValidData() {
		driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys("admin");
		driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("sds");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		
		Assert.assertTrue(driver.getCurrentUrl().contains("dash"), "Login fail because of invalid data");
	}
	@Test(priority = 2)
	public void loginSkip() {
		driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys("admin");
		driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("sds");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		
		//Assert.assertTrue(driver.getCurrentUrl().contains("dash"), "Login fail because of invalid data");
		throw new SkipException("Skipping this test case...");
	}
	@AfterMethod
	public void logout(ITestResult result)
	{
		if(driver.getCurrentUrl().contains("dash"))
		{
			driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[1]/header/div[1]/div[2]/ul/li/span/i")).click();
			driver.findElement(By.partialLinkText("Log")).click();
		}
		
		if(result.getStatus() == ITestResult.SUCCESS)
		{
			test = report.createTest("Login with Valid Data");
			test.log(Status.PASS, MarkupHelper.createLabel(result.getName() + ": Pass", ExtentColor.GREEN));
		}
		else if(result.getStatus() == ITestResult.FAILURE)
		{
			test = report.createTest("Login with Invalid Data");
			test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + ": Fail", ExtentColor.RED));
			test.fail(result.getThrowable());
		}
		if(result.getStatus() == ITestResult.SKIP)
		{
			test = report.createTest("Skipping Test Case");
			test.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + ": Skipped", ExtentColor.YELLOW));
			test.skip(result.getThrowable());
		}
	}
	@AfterTest
	public void tearDown() {
		report.flush();
		driver.close();
	}
}
