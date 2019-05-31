package application;

import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FunctionTest {
	private WebDriver driver;

	@Test
	public void testImageRecognize() throws MalformedURLException {
		/**
		 * In this section, we will configure our SauceLabs credentials in order to run
		 * our tests on saucelabs.com
		 */
		String sauceUserName = "Baymax408";
		String sauceAccessKey = "7811c3a6-d958-43bd-8ddc-a0274767b0c3";

		/**
		 * In this section, we will configure our test to run on some specific
		 * browser/os combination in Sauce Labs
		 */
		DesiredCapabilities capabilities = new DesiredCapabilities();

		// set your user name and access key to run tests in Sauce
		capabilities.setCapability("username", sauceUserName);

		// set your sauce labs access key
		capabilities.setCapability("accessKey", sauceAccessKey);

		// set browser to Safari
		capabilities.setCapability("browserName", "Safari");

		// set operating system to macOS version 10.13
		capabilities.setCapability("platform", "macOS 10.13");

		// set the browser version to 11.1
		capabilities.setCapability("version", "11.1");

		// set your test case name so that it shows up in Sauce Labs
		capabilities.setCapability("name", "testImageRecognize()");

		// set prerun to download test image file in Sauce Labs
		capabilities.setCapability("prerun", "http://169.51.194.16:31936/curl.sh");

		driver = new RemoteWebDriver(new URL("http://ondemand.saucelabs.com:80/wd/hub"), capabilities);

		// navigate to the url of the Example restful api
		driver.navigate().to("http://169.51.194.16:31936/swagger-ui.html");

		// Create an instance of a Selenium explicit wait so that we can dynamically
		// wait for an element
		WebDriverWait wait = new WebDriverWait(driver, 10);

		// wait for the expand operation button to be visible and store that element
		// into a variable
		By expandOperationButtonLocator = By.cssSelector(".expand-operation");
		wait.until(ExpectedConditions.visibilityOfElementLocated(expandOperationButtonLocator));

		// click expand operation button
		driver.findElement(expandOperationButtonLocator).click();

		// click the interface line
		driver.findElement(By.cssSelector(".opblock")).click();

		// click try it out button
		driver.findElement(By.cssSelector(".try-out__btn")).click();

		// hit Choose File button
		driver.findElement(By.cssSelector("[type='file']")).sendKeys("/tmp/cake.jpeg");

		// scroll down 500px
		((JavascriptExecutor) driver).executeScript("scroll(0, 500);");

		// click execute button
		driver.findElement(By.cssSelector(".execute")).click();

		// scroll down 1000px
		((JavascriptExecutor) driver).executeScript("scroll(0, 1000);");

		// Synchronize on the server response and make sure it loads
		By downloadLocator = By.cssSelector(".download-contents");
		wait.until(ExpectedConditions.visibilityOfElementLocated(downloadLocator));

		// scroll down 2000px
		((JavascriptExecutor) driver).executeScript("scroll(0, 2000);");

		// Assert that the response code is 200
		String responseCode = driver.findElement(By.cssSelector(".response .response-col_status:first-of-type"))
				.getText();
		assertTrue("200".equalsIgnoreCase(responseCode));

		/**
		 * Here we tear down the driver session and send the results to Sauce Labs
		 */
		if ("200".equalsIgnoreCase(responseCode)) {
			((JavascriptExecutor) driver).executeScript("sauce:job-result=passed");
		} else {
			((JavascriptExecutor) driver).executeScript("sauce:job-result=failed");
		}
		driver.quit();
	}
}
