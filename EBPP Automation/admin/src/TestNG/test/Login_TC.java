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
import java.net.URL;
import utils.Constants;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import TestNG.pageObjects.Login;
import TestNG.pageObjects.Mpin;
import TestNG.pageObjects.Home;
import TestNG.test.Capabilities;

public class Login_TC  {

   //AppiumDriver<MobileElement> driver;
    Login objLogin;
    Mpin objMpin;
    Home objHome;

    //HomePage objHomePage;
    
    @BeforeTest

    public void setup() throws MalformedURLException{
    String url;
        Capabilities.run();

    }


    @Test(priority=0)

    public void test_Home_Page_Appear_Correct(){

        //Create Login Page object

    objLogin = new Login(Capabilities.driver);

    objLogin.loginToConsumer("9002117075", "Test@1357");
        
    objMpin = new Mpin(Capabilities.driver);
    objHome = new Home(Capabilities.driver);
    objMpin.entermpin();
    objHome.clickpayments();

    }

    
}