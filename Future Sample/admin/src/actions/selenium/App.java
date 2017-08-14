package actions.selenium;
import actions.selenium.utils.Element;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.URL;
import java.io.IOException;
import java.util.*;

class App{
   DesiredCapabilities capabilities1 = new DesiredCapabilities();
   static WebDriver D8 ;
             public void run(HashMap<String, Object> params) throws IOException, InterruptedException{
                    String line = "null";
                    String cmd = "adb devices";
                    String udid = null;
                    Runtime run = Runtime.getRuntime();
                    Process pr = run.exec(cmd);
                    pr.waitFor();
                    BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
                    while ((line=buf.readLine())!=null) {
                    System.out.println(line);
                    if(line.contains("UDID")){
                           udid = line.split("\\s+")[0];
                           //System.out.println("UDID "+udid);
                    }
                    }
                    String appPackage = (String) params.get("appPackage");
                    String appActivity = (String) params.get("appActivity");   
					System.out.println("Launching App");
					capabilities1.setCapability("BROWSER_NAME", "Android");
					capabilities1.setCapability("VERSION", "6.0"); 
					capabilities1.setCapability("deviceName","Nexus 5");
					capabilities1.setCapability("platformName","Android");
					capabilities1.setCapability("appPackage", appPackage);
					capabilities1.setCapability("appActivity", appActivity);
					D8 = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities1);
					System.out.println("App Launched");  
    }
}