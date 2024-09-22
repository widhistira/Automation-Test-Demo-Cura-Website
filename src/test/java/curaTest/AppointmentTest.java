package curaTest;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class AppointmentTest {
    WebDriver driver;
    public static ThreadLocal<WebDriverWait> explicitWait = new ThreadLocal<WebDriverWait>();

    @BeforeTest
    public void init()throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:/Program Files/Google/Chrome/Application/chrome.exe");
        driver = new FirefoxDriver();
        driver.navigate().to("https://katalon-demo-cura.herokuapp.com/profile.php#login");
        driver.manage().window().maximize();

        //Login
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 500)");
        explicitWait.set(new WebDriverWait(driver, Duration.ofSeconds(60)));
        driver.findElement(By.id("txt-username")).sendKeys("John Doe");
        Thread.sleep(1000);
        driver.findElement(By.id("txt-password")).sendKeys("ThisIsNotAPassword");
        Thread.sleep(1000);
        driver.findElement(By.id("btn-login")).click();
        Thread.sleep(1000);
        Assert.assertEquals(driver.getCurrentUrl(),"https://katalon-demo-cura.herokuapp.com/#appointment");
    }

    @Test (priority = 0)
    public void CheckElement() {
        Assert.assertEquals(driver.findElement(By.cssSelector("section h2")).getText(),"Make Appointment");
//        Check dropdown element
        Select dropDownFacility = new Select(driver.findElement(By.id("combo_facility")));
        List<WebElement> dropDownOptions = dropDownFacility.getOptions();
        Assert.assertEquals(dropDownOptions.get(0).getAttribute("value"),"Tokyo CURA Healthcare Center");
        Assert.assertEquals(dropDownOptions.get(1).getAttribute("value"),"Hongkong CURA Healthcare Center");
        Assert.assertEquals(dropDownOptions.get(2).getAttribute("value"),"Seoul CURA Healthcare Center");
//        Check Element Text Apply for hospital
        Assert.assertEquals(driver.findElement(By.cssSelector(".checkbox-inline")).getText(),"Apply for hospital readmission");
//        Check element visit date
        Assert.assertEquals(driver.findElement(By.id("txt_visit_date")).getAttribute("placeholder"),"dd/mm/yyyy");
//        Check Element Comment
        Assert.assertEquals(driver.findElement(By.id("txt_comment")).getAttribute("placeholder"),"Comment");
    }

    @Test (priority = 1)
    public void makeAppointment()throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
//        Drop Down
        Select dropDownFacility = new Select(driver.findElement(By.id("combo_facility")));
        driver.findElement(By.id("combo_facility")).click();
        dropDownFacility.selectByValue("Seoul CURA Healthcare Center");
//        Check Box
        driver.findElement(By.id("chk_hospotal_readmission")).click();
        Thread.sleep(1000);
//        Radio Button
        driver.findElement(By.id("radio_program_medicaid")).click();
        Thread.sleep(1000);
//          Calender
        driver.findElement(By.id("txt_visit_date")).sendKeys("22/09/2024");
        Thread.sleep(1000);
        js.executeScript("window.scrollBy(0, 500)");
//        Text area Comment
        driver.findElement(By.id("txt_comment")).sendKeys("Tester Appointment");
        Thread.sleep(1000);
        js.executeScript("window.scrollBy(0, 250)");
        Assert.assertEquals(driver.findElement(By.xpath("//button[@id='btn-book-appointment']")).getText(),"Book Appointment");
        driver.findElement(By.id("btn-book-appointment")).click();
        Thread.sleep(1000);
//        Go to home page
        driver.findElement(By.className("btn btn-default")).click();
        Assert.assertEquals(driver.findElement(By.xpath("//a[.='Go to Homepage']")).getText(),"Go to Homepage");
        Assert.assertEquals(driver.getCurrentUrl(),"https://katalon-demo-cura.herokuapp.com/");
    }

    @AfterTest
    public void closeBrowser() throws InterruptedException {
        Thread.sleep(1000);
        driver.quit();
    }
}
