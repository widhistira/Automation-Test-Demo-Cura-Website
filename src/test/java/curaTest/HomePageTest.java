package curaTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class HomePageTest {
    WebDriver driver;

    @BeforeTest
    private void OpenBrowser()throws InterruptedException {
//    Open Browser
        System.setProperty("webdriver.chrome.driver", "C:/Program Files/Google/Chrome/Application/chrome.exe");
        driver = new FirefoxDriver();
//    go to homePage
        driver.navigate().to("https://katalon-demo-cura.herokuapp.com/");
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(10));
    }

    @Test (priority = 0)
    private void checkElement() {
//  check h1 Element
        Assert.assertEquals(driver.findElement(By.cssSelector("header h1")).getText(), "CURA Healthcare Service");
        Assert.assertEquals(Color.fromString(driver.findElement(By.cssSelector("header h1")).getCssValue("color")).asHex(), "#ffffff");
//  check h3 Element
        Assert.assertEquals(driver.findElement(By.cssSelector("header h3")).getText(), "We Care About Your Health");
        Assert.assertEquals(Color.fromString(driver.findElement(By.cssSelector("header h3")).getCssValue("color")).asHex(), "#4fb6e7");
//  check button
        Assert.assertEquals(driver.findElement(By.id("btn-make-appointment")).getText(), "Make Appointment");
        Assert.assertEquals(driver.findElement(By.id("btn-make-appointment")).getCssValue("background-color"), "rgba(115, 112, 181, 0.8)");
//    check toggle menu
        Assert.assertEquals(driver.findElement(By.id("menu-toggle")).getCssValue("background-color"), "rgba(115, 112, 181, 0.8)");
    }

    @Test (priority = 1)
    private void clickToggleMenu()throws InterruptedException {
        driver.findElement(By.id("menu-toggle")).click();
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"sidebar-wrapper\"]/ul/li[1]")).getText(),"CURA Healthcare");
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"sidebar-wrapper\"]/ul/li[2]")).getText(),"Home");
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"sidebar-wrapper\"]/ul/li[3]")).getText(),"Login");
        Thread.sleep(1000);
        driver.findElement(By.id("menu-toggle")).click();
    }

    @Test (priority = 2)
    public void clickButtonAppointment()throws InterruptedException {
        driver.findElement(By.id("btn-make-appointment")).click();
        Assert.assertEquals(driver.findElement(By.id("btn-make-appointment")).getText(), "Make Appointment");
        Assert.assertEquals(driver.getCurrentUrl(),"https://katalon-demo-cura.herokuapp.com/profile.php#login");
    }

    @AfterTest
    private void closeBrowser()throws InterruptedException{
        Thread.sleep(1000);
        driver.quit();
    }
}
