package curaTest;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

import static curaTest.AppointmentTest.explicitWait;


public class LoginPageTest {
    WebDriver driver;

    @BeforeTest
    private void testLogin() {
        System.setProperty("webdriver.chrome.driver", "C:/Program Files/Google/Chrome/Application/chrome.exe");
        driver = new FirefoxDriver();
        driver.navigate().to("https://katalon-demo-cura.herokuapp.com/profile.php#login");
        driver.manage().window().maximize();
    }

    @Test (priority = 0)
    private void inputField() {
        explicitWait.set(new WebDriverWait(driver, Duration.ofSeconds(60)));
        Assert.assertEquals(driver.findElement(By.cssSelector("section h2")).getText(),"Login");
        Assert.assertEquals(driver.findElement(By.cssSelector("section p")).getText(),"Please login to make appointment.");
//        check field user
        Assert.assertEquals(driver.findElement(By.id("txt-username")).getAttribute("name"),"username");
        Assert.assertEquals(driver.findElement(By.id("txt-password")).getAttribute("name"), "password");
        driver.findElement(By.id("btn-make-appointment")).click();
    }

    @Test (priority = 1)
    public void loginWithNullValues() {
        driver.findElement(By.id("btn-login")).click();
        Assert.assertEquals(driver.findElement(By.xpath("//p[@class='lead text-danger']")).getText(),"Login failed! Please ensure the username and password are valid.");
    }

    @Test (priority = 2)
    public void loginWithWrongValues()throws InterruptedException {
        driver.findElement(By.id("txt-username")).sendKeys("JhonTravolta");
        Thread.sleep(1000);
        driver.findElement(By.id("txt-password")).sendKeys("wakwakw");
        Thread.sleep(1000);
        driver.findElement(By.id("btn-login")).click();
        Assert.assertEquals(driver.findElement(By.xpath("//p[@class='lead text-danger']")).getText(),"Login failed! Please ensure the username and password are valid." );
    }

    @Test (priority = 3)
    public void loginWithCorrectValues()throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 500)");
        driver.findElement(By.id("txt-username")).sendKeys("John Doe");
        Thread.sleep(1000);
        driver.findElement(By.id("txt-password")).sendKeys("ThisIsNotAPassword");
        Thread.sleep(1000);
        driver.findElement(By.id("btn-login")).click();
        Thread.sleep(1000);
        Assert.assertEquals(driver.getCurrentUrl(),"https://katalon-demo-cura.herokuapp.com/#appointment");
    }

    @AfterTest
    public void closeBrowser()throws InterruptedException{
    Thread.sleep(1000);
    driver.quit();
    }
}
