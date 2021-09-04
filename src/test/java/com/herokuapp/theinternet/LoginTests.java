package com.herokuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LoginTests {
	
	
	
	private WebDriver driver ;
	
	@BeforeMethod(alwaysRun = true)
	public void setUp() {
				//create driver
				System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
				
				driver = new ChromeDriver();
				
				sleep(3000);
				
				//maximize browser window
				driver.manage().window().maximize();
	}
	
	@Test(priority = 1,groups = { "postiveTests", "smokeTests" })
	public void positiveloginTest() {
		System.out.println("Starting loginTest");
		
		
		// open test page
		String url = "https://the-internet.herokuapp.com/login";
		driver.get(url);
		System.out.println("Page is Opened");
		
		sleep(3000);
		
		// enter username
		WebElement username = driver.findElement(By.id("username"));
		username.sendKeys("tomsmith");
		sleep(3000);
		//enter password
		WebElement password = driver.findElement(By.name("password"));
		password.sendKeys("SuperSecretPassword!");
		sleep(3000);
		//click login button
		WebElement loginButton = driver.findElement(By.tagName("button"));
		loginButton.click();
		sleep(5000);
		
		// Verification on new url
		String expectedUrl = "https://the-internet.herokuapp.com/secure";
		String actualUrl=driver.getCurrentUrl();
		Assert.assertEquals(actualUrl, expectedUrl,"Actual page url is not the same expected.");
		
		//logout button is visible
		WebElement logOutButton = driver.findElement(By.xpath("//a[@class='button secondary radius']"));
		Assert.assertTrue(logOutButton.isDisplayed(),"Log out button is not visible");
	
		//successful login message
		//WebElement successMessage = driver.findElement(By.cssSelector("#flash"));
		WebElement successMessage = driver.findElement(By.xpath("//div[@id='flash']"));
		String expectedMessage = "You logged into a secure area!";
		String actualMessage = successMessage.getText();
		//Assert.assertEquals(actualMessage, expectedMessage,"Actual Message is not the same as expected");
		
		Assert.assertTrue(actualMessage.contains(expectedMessage),"Actual message does not contain expected message. \n Actual Message  :" + actualMessage + "\n Expected Message: " +expectedMessage );
		
	
	}

	
	
	@Parameters({"username","password","expectedMessage"})
	@Test(priority = 2,groups = { "negativeTests", "smokeTests" })
	public void negativeLoginTest(String username,String password,String expectedErrorMessage) {
		System.out.println("Starting negativeLoginTest");
	
		// open test page
		String url = "https://the-internet.herokuapp.com/login";
		driver.get(url);
		System.out.println("Page is Opened");
		
		sleep(2000);
		
		// enter username
		WebElement usernameElement = driver.findElement(By.id("username"));
		usernameElement.sendKeys(username);
//		username.sendKeys("tomsmith");
		sleep(2000);
		
		//enter password
		WebElement passwordElement = driver.findElement(By.name("password"));
		passwordElement.sendKeys(password);
		sleep(2000);
		//click login button
		WebElement loginButton = driver.findElement(By.tagName("button"));
		loginButton.click();
		sleep(2000);
		
		//verifications
		WebElement errorMessage = driver.findElement(By.id("flash"));
//		String expectedErrorMessage = "Your username is invalid!";
		String actualErrorMessage = errorMessage.getText();
		
		Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage),
				"Actual error message does not contain expected message. \n Actual :" + actualErrorMessage + "\n Expected" +expectedErrorMessage );
		
		
	}
	
	@AfterMethod(alwaysRun = true)
	private void tearDown() {
		// close browser
		driver.quit();
	}
	
	private void sleep(long m) {
		try {
			Thread.sleep(m);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
