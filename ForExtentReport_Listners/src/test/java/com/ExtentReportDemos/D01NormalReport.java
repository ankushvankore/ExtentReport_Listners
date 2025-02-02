package com.ExtentReportDemos;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class D01NormalReport {

	public static void main(String[] args) {
		ExtentSparkReporter htmlReporter = new ExtentSparkReporter("Reports\\MyReport.html");
		ExtentReports report = new ExtentReports();
		report.attachReporter(htmlReporter);
		ExtentTest test;

		// Adding Environment Details
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

		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.get("https://www.google.com");
		String title = driver.getTitle();

		System.out.println("Title: " + title);

		driver.close();

		test = report.createTest("Verify Google Title");
		test.log(Status.PASS, MarkupHelper.createLabel("Google Title: Pass", ExtentColor.GREEN));
		
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.get("https://www.google.com");
		driver.findElement(By.name("q")).sendKeys("Sara Ali Khan", Keys.ENTER);
		
		driver.close();

		test = report.createTest("Google Search Functionality");
		test.log(Status.FAIL, MarkupHelper.createLabel("Google Search: Fail", ExtentColor.RED));
		
		report.flush();
	}

}
