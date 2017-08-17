package TestNG.test;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.UnhandledAlertException;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import utils.Constants;
import io.appium.java_client.MobileBy.ByAndroidUIAutomator;
import utils.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.Alert;
import java.util.*;

public  class Capabilities  {
    	public static AppiumDriver<MobileElement> driver;
	    static String url;
    
    public static  void run() throws MalformedURLException{
        
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("autoAcceptAlerts",true);
        System.out.println("inside accept true");
        capabilities.setCapability("autoDismissAlerts",true);
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Nexus 5");
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "6.0");
		capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
		capabilities.setCapability(Constants.APP_PACKAGE, "com.corpay.mwallet.sit.dev");
		capabilities.setCapability(Constants.APP_ACTIVITY, "com.corpay.mwallet.app.ui.flows.login.AppLoginActivity");
		capabilities.setCapability(MobileCapabilityType.UDID, "FA49RY903450");
		url = "http://127.0.0.1:4723/wd/hub";
		if (!url.startsWith(Constants.HTTP) && !url.startsWith(Constants.HTTPS)) {
			url = Constants.HTTP + url;
		}
		URL remoteAddress = new URL(url);
	    driver = new AndroidDriver<MobileElement>(remoteAddress, capabilities);
        driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
        MobileElement element = driver.findElement(By.id("etMobileNumber"));  
        element.sendKeys("8335852828");
        
    }
}