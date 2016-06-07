package com.dak.seleniumPOC;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

public class AppTest {

	private static String PHANTOMJS_BINARY;
	private static String projectBaseDir;
	private static String screenshotsLocation;
	
	private static int screenshotCount = 0;
	private static final Boolean IGNORE_SSL_ERRORS = false;
	
	private static DesiredCapabilities DEFAULT_CAPABILITIES;

	@BeforeClass
	public static void beforeTest() {
		PHANTOMJS_BINARY = System.getProperty("phantomjs.binary");


		assertNotNull("phantomjs.binary property is null!", PHANTOMJS_BINARY);
		assertTrue("phantomjs.binary file does not exist!", new File(PHANTOMJS_BINARY).exists());

		projectBaseDir = (String) System.getProperties().get("basedir");
		assertNotNull("basedir property is null!", PHANTOMJS_BINARY);

		screenshotsLocation = projectBaseDir + File.separator + "target" + File.separator + "screenshots" + File.separator;
		
		DEFAULT_CAPABILITIES = new DesiredCapabilities();	

		DEFAULT_CAPABILITIES.setJavascriptEnabled(true);
		DEFAULT_CAPABILITIES.setCapability("takesScreenshot", true);
		DEFAULT_CAPABILITIES.setCapability(
				PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
				PHANTOMJS_BINARY
				);
		
		if(IGNORE_SSL_ERRORS){
			final String [] phantomJsArgs = {"--ignore-ssl-errors=true", "--ssl-protocol=any"};
			DEFAULT_CAPABILITIES.setCapability(
					PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_CLI_ARGS, 
					phantomJsArgs);
		}

	}

	@Test
	public void navigateToPageAndTakeScreenshot() throws IOException, InterruptedException {
		final WebDriver driver = new PhantomJSDriver(DEFAULT_CAPABILITIES);
		driver.manage().window().setSize(new Dimension(1600, 1200));
		
		final String baseURL = "https://github.com";

		driver.navigate().to(baseURL);

		final File imageFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		final File screenshotDest = new File(screenshotsLocation + "screenshot" + getNextScreenshotNumber() + ".png");
		FileUtils.copyFile(imageFile, screenshotDest);
	}

	private static synchronized int getNextScreenshotNumber(){
		return ++screenshotCount;
	}
}
