package com.tests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import com.aventstack.extentreports.*;
import com.utils.ExtentReportManager;


public class AmazonTests {
    WebDriver driver;
    WebDriverWait wait;
    ExtentReports extent;
    ExtentTest test;

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\ADMIN\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        extent = ExtentReportManager.getExtentReports();

        driver.manage().window().maximize();
        driver.get("https://www.amazon.in");
    }

    @Test(priority = 1)
    public void verifyTitle() {
        test = ExtentReportManager.createTest("Verify Amazon Home Page Title");
        String expectedTitle = "Online Shopping site in India: Shop Online for Mobiles, Books, Watches, Shoes and More - Amazon.in";
        String actualTitle = driver.getTitle();

        try {
            Assert.assertEquals(actualTitle, expectedTitle);
            test.pass(" Title matched successfully.");
        } catch (AssertionError e) {
            test.fail(" Title mismatch: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 2)
    public void verifyProductById() {
        test = ExtentReportManager.createTest("Verify iPhone Product Display");

        driver.findElement(By.id("twotabsearchtextbox")).clear();
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("iPhone");
        driver.findElement(By.id("nav-search-submit-button")).click();

        WebElement product = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[contains(text(), 'iPhone')]")
        ));

        Assert.assertTrue(product.isDisplayed(), " Product not found.");
        test.pass("✅ Product found and displayed.");
    }

    @Test(priority = 3)
    public void verifyCategorySearch() {
        test = ExtentReportManager.createTest("Verify Category Search for Books");

        driver.findElement(By.id("twotabsearchtextbox")).clear();
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("Books");
        driver.findElement(By.id("nav-search-submit-button")).click();

        Assert.assertTrue(driver.getTitle().toLowerCase().contains("books"), " Category not matched.");
        test.pass("✅ Category search worked successfully.");
    }

    @Test(priority = 4)
    public void verifyScrollFunctionality() {
        test = ExtentReportManager.createTest("Verify Scroll Functionality");

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Double scrollYDouble = (Double) js.executeScript("return window.pageYOffset;");
        Long scrollY = scrollYDouble.longValue();

        Assert.assertTrue(scrollY > 0, " Scroll not working.");
        test.pass("✅ Page scrolled successfully.");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
        ExtentReportManager.flushReports();
    }
}
