package TestNG.test;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import java.net.MalformedURLException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.UnhandledAlertException;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.testng.Assert;
import utils.Constants;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import TestNG.pageObjects.Login;
import TestNG.test.Capabilities;

public class Login_TC  {

    //AppiumDriver<MobileElement> driver;
    Login objLogin;

    //HomePage objHomePage;

    

    @BeforeTest

    public void setup() throws MalformedURLException{

     Capabilities.run();

    }

    /**

     * This test go to http://demo.guru99.com/V4/

     * Verify login page title as guru99 bank

     * Login to application

     * Verify the home page using Dashboard message

     */

    @Test(priority=0)

    public void test_Home_Page_Appear_Correct(){

        //Create Login Page object

    objLogin = new Login(Capabilities.driver);

    //Verify login page title

//    String loginPageTitle = objLogin.getLoginTitle();

//   Assert.assertTrue(loginPageTitle.toLowerCase().contains("guru99 bank"));

    //login to application

    objLogin.loginToConsumer("8335852828", "Test@1357");

    // go the next page

//    objHomePage = new Guru99HomePage(driver);

    //Verify home page

//    Assert.assertTrue(objHomePage.getHomePageDashboardUserName().toLowerCase().contains("manger id : mgr123"));

    }

    
}