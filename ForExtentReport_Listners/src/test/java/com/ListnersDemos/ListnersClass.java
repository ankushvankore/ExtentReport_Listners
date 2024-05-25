package com.ListnersDemos;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ListnersClass implements ITestListener
{
	public void onStart(ITestContext result)
	{
		System.out.println("Test Started");
	}
	public void onFinish(ITestContext result)
	{
		System.out.println("Test Finish");
	}
	public void onTestFailure(ITestResult result)
	{
		System.out.println("Test Fail");
		File file = ((TakesScreenshot)com.ListnersDemos.D03OHRMWithListners.driver).getScreenshotAs(OutputType.FILE);
		try {
			FileHandler.copy(file, new File("OHRM.jpeg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void onTestSkipped(ITestResult result)
	{
		System.out.println("Test Skipped");
	}
	public void onTestStart(ITestResult result)
	{
		System.out.println("Test Case Started");
	}
	public void onTestSuccess(ITestResult result)
	{
		System.out.println("Test Pass");
	}
	public void onTestFailedButWithinSuccessPercentage(ITestResult result)
	{
		
	}
	public void onTestFailedWithTimeout(ITestResult result)
	{
		
	}
}
