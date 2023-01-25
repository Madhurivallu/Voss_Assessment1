package Caluculator;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CalculatorTest {
    WebDriver driver;

    @BeforeTest
    public void setUp() {
        // Set up webdriver
    	WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
       
        // Navigate to the calculator website
        driver.get("https://www.online-calculator.com/");
    }

    @Test
    public void testAddition() {
        // Find the elements for the two numbers
        WebElement num1 = driver.findElement(By.id("num1"));
        WebElement num2 = driver.findElement(By.id("num2"));

        // Enter the numbers
        num1.sendKeys("5");
        num2.sendKeys("3");

        // Find the add button and click it
        WebElement addButton = driver.findElement(By.id("add"));
        addButton.click();

        // Find the result element and get the text
        WebElement result = driver.findElement(By.id("result"));
        String resultText = result.getText();

        // Assert that the result is 8
        Assert.assertEquals(resultText, "8");
    }

    @Test
    public void testAddition1() {
        WebElement element = driver.findElement(By.id("input"));
        element.sendKeys("2+2");
        driver.findElement(By.id("button")).click();
        String result = driver.findElement(By.id("output")).getText();
        Assert.assertEquals(result, "4");
    }

    @Test
    public void testSubtraction() {
        WebElement element = driver.findElement(By.id("input"));
        element.sendKeys("5-3");
        driver.findElement(By.id("button")).click();
        String result = driver.findElement(By.id("output")).getText();
        Assert.assertEquals(result, "2");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}

