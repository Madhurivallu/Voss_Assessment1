package Voss_Ultimate;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.beust.jcommander.Parameter;

import io.github.bonigarcia.wdm.WebDriverManager;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Ultimateqa_class { 
     
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent1;
	public ExtentTest extentTest;
	 static WebDriver driver = null;
     
  @Parameters("browser") 
   @BeforeTest
    public void setUp(String browser) {
        // Initialize the WebDriver based on the browser parameter
        if (browser.equalsIgnoreCase("chrome")){
        	WebDriverManager.chromedriver().setup();
    		driver = new ChromeDriver();
        	System.out.println("chrome is launched");
        	
        } else if (browser.equalsIgnoreCase("firefox")) {
        	WebDriverManager.firefoxdriver().setup();
        	 driver = new FirefoxDriver();
        	 System.out.println("firefox is lanched");
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        
        // Initialize the ExtentReports object 
        htmlReporter = new ExtentHtmlReporter("target/extent1.html");
		htmlReporter.config().setDocumentTitle("Automation practice Test Report");
		htmlReporter.config().setReportName(" Ultimateqa automation Results ");
		htmlReporter.config().setTheme(Theme.DARK);
        extent1 = new ExtentReports();
		extent1.setSystemInfo("Tester", "Madhuri");
		extent1.setSystemInfo("Browser", "Chrome");
		extent1.attachReporter(htmlReporter);
	
    }
    @Test(priority=1)
    public void testPageTitle() {
        // Start the test
        extentTest = extent1.createTest("Ultimateqa Title", "Verify the page title of the Ultimateqa Website");

        // Open the website
        driver.get("https://www.ultimateqa.com/automation/");
       
        // Verify the page title
        String title=driver.getTitle();
        
        Assert.assertTrue(title.contains("Automation Practice - Ultimate QA"));

        // Log the test result
        extentTest.log(Status.PASS, "Automation Practice - Ultimate QA is correct");
    }

    @Test(priority=2)
    public void testScreenshot() throws IOException, Throwable {
        // Start the test
        extentTest = extent1.createTest("Test Screenshot", "Take a screenshot of the Ultimateqa website");
        extentTest.log(Status.PASS, "Successfully Take a screenshot");
        // Open the website
        File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File("target/screenshot.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Test Case 3: Maximize the browser window
        driver.manage().window().maximize();
        Thread.sleep(2000);

        }
    @Test(priority=3)
	public void Login_User_details() throws Throwable {
    	extentTest = extent1.createTest("login user details");
	driver.get("https://www.ultimateqa.com/automation/");
	 extentTest.log(Status.PASS, "Navigate to https://www.ultimateqa.com/automation/ ");
	
	 driver.findElement(By.linkText("Login automation")).click();
	 extentTest.log(Status.PASS, "click on Login Automation");
	 
	 //enter user name
	 driver.findElement(By.id("user[email]")).sendKeys("madhurivallu93@gmail.com");
	 extentTest.log(Status.PASS, "Enter username ");
	//enter password
	driver.findElement(By.id("user[password]")).sendKeys("Madhu3@3");
	 extentTest.log(Status.PASS, "Enter password");
	
	driver.findElement(By.xpath("//*[@id=\"sign_in_071344d6b9\"]/div[5]/button")).click();
	 extentTest.log(Status.FAIL, "click on sign in button");
	Thread.sleep(2000);
	extentTest = extent1.createTest("login user details");
	 try {
	    WebElement captcha = driver.findElement(By.id("//*[@id=\"rc-imageselect-target\"]/table/tbody/tr[2]/td[1]/div/div[2]"));
	    if (captcha.isDisplayed()) {
	        // Perform actions for when the captcha is present
	    	File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	    	 // Use Tesseract OCR to extract text from the image
	        ITesseract tesseract = new Tesseract();
	        tesseract.setDatapath("target/to/tessdata");
	        String captchaText = tesseract.doOCR(screenshot);
            // Enter captcha text in the text field
	        WebElement captchaField = driver.findElement(By.id("recaptcha-verify-button"));
	        captchaField.sendKeys(captchaText);
	        // Submit the form
	        captchaField.submit();
	        System.out.println("Captcha is present on the page, please enter the captcha");
	    } else {
	        // Perform actions for when the captcha is not present
	        System.out.println("Captcha is not present on the page, proceeding with the test");
	    }
	} catch (NoSuchElementException e) {
	    // Perform actions for when the captcha element is not found on the page
	    System.out.println("Captcha element not found on the page, proceeding with the test");

	}
	 extentTest = extent1.createTest("click to enter captcha failed");
 }
	@Test(priority=4)
	public void click_Fill_out_Forms() throws Throwable {
		extentTest = extent1.createTest("check in fill out forms details");
            driver.get("https://ultimateqa.com/automation/");
            extentTest.log(Status.PASS, "Browse to https://www.automationqa.com/ and log in");
	        Thread.sleep(200);
			
			driver.findElement(By.linkText("Fill out forms")).click();
			 extentTest.log(Status.PASS, "navigate to the 'Fill out forms' page");
			
			driver.findElement(By.id("et_pb_contact_name_0")).sendKeys("madhuri");
			 extentTest.log(Status.PASS,"enter my name1");
			
			driver.findElement(By.name("et_pb_contact_message_0")).sendKeys("Automation Tester");
			 extentTest.log(Status.PASS,"enter my message1");
			
			//click on submitbtn
			driver.findElement(By.name("et_builder_submit_button")).click();
			 extentTest.log(Status.PASS, "click on submit button");
			
			//click on user1 details
		    driver.findElement(By.id("et_pb_contact_name_1")).sendKeys("Rishi");
		    extentTest.log(Status.PASS,"enter my name2");
			
		    driver.findElement(By.name("et_pb_contact_message_1")).sendKeys("Manual Tester");
		    extentTest.log(Status.PASS,"enter my message2");
		    // enter numerical captcha
			driver.findElement(By.xpath("//*[@id=\"et_pb_contact_form_1\"]/div[2]/form/div/div/p/input")).sendKeys("15");
			 extentTest.log(Status.FAIL, "Enter numeric captcha ");
			driver.findElement(By.name("et_builder_submit_button")).click();
			 extentTest.log(Status.FAIL, "fail to submit button");
			}
			
	        @Test(priority=5)
			 public void click_on_Fake_Pricing_page() {
	        
	        	extentTest = extent1.createTest("click on Fake pricing page datails");
			driver.get("https://ultimateqa.com/automation/");
			 extentTest.log(Status.PASS, "Browse to the 'Fake Pricing Page' " );
			
			driver.findElement(By.linkText("Fake Pricing Page")).click();
			 extentTest.log(Status.PASS, "click on 'Fake Pricing Page' " );
			
			//Scroll on the page
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,350)", "");
			
			// click on purchase button
			driver.findElement(By.linkText("Purchase")).click();
			 extentTest.log(Status.FAIL, "click on 'purchase package' " );
		
			 extentTest = extent1.createTest("Failed Test");
			 extentTest.log(Status. FAIL, "Purchase by-product is failed");
				Assert.fail("Executing fail Test automation");
				}
			@AfterTest
    public void tearDown() {
        // Close the browser
        driver.quit();

        // End the test and flush the report
        
        extent1.flush();
    }

    
}


    








