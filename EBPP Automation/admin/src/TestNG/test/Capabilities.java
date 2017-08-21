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

public  class Capabilities  {
      static AppiumDriver<MobileElement> driver;
	   static  String url;
    
    public static AppiumDriver run() throws MalformedURLException{
   
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("autoAcceptAlerts",true);
        System.out.println("Initializing Capabilities");
        capabilities.setCapability("autoDismissAlerts",true);
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Nexus 5");
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "6.0");
		capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
		capabilities.setCapability(Constants.APP_PACKAGE, "com.corpay.mwallet.sit.dev");
		capabilities.setCapability(Constants.APP_ACTIVITY, "com.corpay.mwallet.app.ui.flows.login.AppLoginActivity");
		capabilities.setCapability(MobileCapabilityType.UDID, "420346fbb4ab3200");
		url = "http://127.0.0.1:4723/wd/hub";
		if (!url.startsWith(Constants.HTTP) && !url.startsWith(Constants.HTTPS)) {
			url = Constants.HTTP + url;
		}
		URL remoteAddress = new URL(url);
	    driver = new AndroidDriver<MobileElement>(remoteAddress, capabilities);
        return driver;
        
    }
}