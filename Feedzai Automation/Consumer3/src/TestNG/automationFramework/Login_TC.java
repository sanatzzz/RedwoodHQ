package TestNG.automationFramework;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

// Import package pageObject.*
import TestNG.pageObjects.LogIn_Page;
 
public class Login_TC {
 
     private static WebDriver driver = null;
 
@BeforeTest
     public void setURL()
     {
       System.setProperty("webdriver.chrome.driver", "E:/Jars/chromedriver.exe");


     driver = new ChromeDriver();
 
     driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
 
     driver.get("https://sit.rpay.co.in");
}
 
     // Use page Object library now
@Test
 public void enterCred()
 {
     LogIn_Page.txtbox_Username(driver).sendKeys("8335852828");
 
     LogIn_Page.txtbox_Password(driver).sendKeys("Test@1357");
 
     LogIn_Page.btn_Login(driver).click();
 }
 
@AfterTest
public void quit()
{
     System.out.println(" Login Successfully, now it is the time to Log Off buddy.");

     driver.quit();
}
 
     
 
}