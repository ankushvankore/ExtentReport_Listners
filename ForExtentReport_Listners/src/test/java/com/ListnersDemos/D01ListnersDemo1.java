package com.ListnersDemos;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;

@Listeners(com.ListnersDemos.ListnersClass.class)
public class D01ListnersDemo1 {
	
	@Test
	public void testCase1() {
		System.out.println("Testcase 1 Started");
		Assert.assertTrue(true);
	}
	@Test
	public void testCase2() {
		System.out.println("Testcase 2 Started");
		Assert.assertTrue(false);
	}
	@Test
	public void testCase3() {
		System.out.println("Testcase 3 Started");
		throw new SkipException("Skipping this test case!!!");
	}

	@BeforeTest
	public void beforeTest() {
	}

	@AfterTest
	public void afterTest() {
	}

}
