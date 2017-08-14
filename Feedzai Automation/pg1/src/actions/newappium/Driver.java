package actions.newappium;

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
import org.openqa.selenium.Alert;
//import actions.selenium.utils.Elements;

public class Driver {
	public static AppiumDriver<MobileElement> driver;
	String url;

	public void run(HashMap<String, String> params) throws MalformedURLException {
        //static AppiumDriver<MobileElement> driver;
		if (driver != null) {
			return;
		}
		File classpathRoot = new File(System.getProperty(Constants.USER_HOME));
		File appDir = new File(classpathRoot, params.get(Constants.APP_DIR));
		File app = new File(appDir, params.get(Constants.APP_NAME));
                
		// Capabilities for driver
		DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("autoAcceptAlerts",true);
        System.out.println("inside accept true");
        capabilities.setCapability("autoDismissAlerts",true);
        //capabilities.SetCapability("autoDismissAlerts",true);
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, params.get(Constants.PLATFORM));
         System.out.println("inside Android1"+params.get(Constants.PLATFORM));
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, params.get(Constants.DEVICE_NAME));
         System.out.println("inside Android2"+params.get(Constants.DEVICE_NAME));
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, params.get(Constants.VERSION));
         System.out.println("inside Android3"+params.get(Constants.VERSION));
		capabilities.setCapability(MobileCapabilityType.NO_RESET, params.get(Constants.NO_RESET));
         System.out.println("inside Android4"+params.get(Constants.NO_RESET));
		capabilities.setCapability(Constants.APP_PACKAGE, params.get(Constants.APP_PACKAGE));
         System.out.println("inside Android5"+params.get(Constants.APP_PACKAGE));
		capabilities.setCapability(Constants.APP_ACTIVITY, params.get(Constants.LAUNCH_ACTIVITY));
         System.out.println("inside Android6"+params.get(Constants.LAUNCH_ACTIVITY));
		//capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
		capabilities.setCapability(MobileCapabilityType.UDID, params.get(Constants.UDID));
         System.out.println("inside Android7"+params.get(Constants.UDID));
         System.out.println("inside Android8"+params.get(Constants.PLATFORM));
		url = params.get(Constants.APPIUM_NODE_URL);
		if (!url.startsWith(Constants.HTTP) && !url.startsWith(Constants.HTTPS)) {
			url = Constants.HTTP + url;
		}
		URL remoteAddress = new URL(url);
            System.out.println("inside Android add"+remoteAddress);
		switch (params.get(Constants.PLATFORM)) {
		case Constants.ANDROID:
                System.out.println("inside Android9");
			driver = new AndroidDriver<MobileElement>(remoteAddress, capabilities);
                //Driver.findElement(By.name("Allow")).click();
                 
                //driver.findElement(ByAndroidUIAutomator.id("permission_allow_button")).click();
               // Alert alert = driver.switchTo().alert();
               // alert.accept();
              
			break;
		case Constants.IOS:
			driver = new IOSDriver<MobileElement>(remoteAddress, capabilities);
              // Alert alert = driver.switchTo().alert();
                //alert.accept();
			break;
		}
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}
}