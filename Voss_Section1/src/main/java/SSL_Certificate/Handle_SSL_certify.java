package SSL_Certificate;

import java.io.IOException;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Handle_SSL_certify {
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent1;
	public ExtentTest test;
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
        htmlReporter = new ExtentHtmlReporter("target/extent.html");
		htmlReporter.config().setDocumentTitle("SSL Certificate Test Report");
		htmlReporter.config().setReportName(" SSl expiry date Results ");
		htmlReporter.config().setTheme(Theme.DARK);
        extent1 = new ExtentReports();
		extent1.setSystemInfo("Tester", "Madhuri");
		extent1.setSystemInfo("Browser", "Chrome");
		extent1.attachReporter(htmlReporter);
	
			}
  @Test
  
	 public void testSSL() throws IOException {
	        test = extent1.createTest("Test SSL Certificate");
	        driver.get("https://www.ultimateqa.com");
	        test.log(Status.INFO, "Navigated to https://www.ultimateqa.com");

	        // identify SSL certificate
	        URL url = new URL("https://www.ultimateqa.com");
	        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
	        conn.connect();
	        X509Certificate cert = (X509Certificate) conn.getServerCertificates()[0];
	        test.log(Status.INFO, "Identified SSL certificate for this website");

	        // validate SSL certificate details
	        Date expiry = cert.getNotAfter();
		    Date current = new Date();
		    if (current.after(expiry)) {
		        System.out.println("SSL certificate has expired.");
		    } else {
		        System.out.println("SSL certificate is valid.");
		    }
		    System.out.println("Subject: " + cert.getSubjectDN());
		    System.out.println("Issuer: " + cert.getIssuerDN());
		    System.out.println("Expiry: " + expiry);
  


	                test.log(Status.INFO, "Expiry date of SSL certificate: " + expiry);
	                if (expiry.before(new Date())) {
	                    test.log(Status.FAIL, "SSL certificate has expired");
	                } else {
	                    test.log(Status.PASS, "SSL certificate is valid");
	                }
	            }
	        

	@AfterTest
    public void tearDown() {
        // Close the browser
        driver.quit();

        // End the test and flush the report
        
        extent1.flush();
}

}
