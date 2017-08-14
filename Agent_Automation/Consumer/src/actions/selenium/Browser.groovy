package actions.selenium;

import org.openqa.selenium.WebDriver
import org.openqa.selenium.ie.InternetExplorerDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.ie.InternetExplorerDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeDriverService
import org.openqa.selenium.ie.InternetExplorerDriverService
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.WebElement
import org.openqa.selenium.By

class Browser{

  
  public static WebDriver Driver = null
  public static MainWinHandle = null

  //start browser

    public void run(def params){
    def os = System.getProperty("os.name").toLowerCase();

	sleep(1000)
    if (params."Browser Type" == "Firefox"){
      Driver = new FirefoxDriver()
    }
    else if (params."Browser Type" == "Chrome"){
      def service

      if(os.contains("nix") || os.contains("nux")||os.contains("aix")){
          File chromedriver = new File("chromedriver")
          if(!chromedriver.exists()){
              assert false, "Please upload proper linux chromedriver file to bin directory under scripts tab."
          }
          chromedriver.setExecutable(true)
          service = new ChromeDriverService.Builder().usingPort(9518).usingDriverExecutable(chromedriver).build()
      }
      else{
        service = new ChromeDriverService.Builder().usingPort(9518).usingDriverExecutable(new File("chromedriver.exe")).build()
      }
      service.start()
      Driver = new RemoteWebDriver(service.getUrl(),DesiredCapabilities.chrome())
    }
      
      else if(params."Browser Type" == "Android")
      {
                    
                    String appPackage = (String) params.get("appPackage");
                    String appActivity = (String) params.get("appActivity"); 
                    System.out.println("Launching App");
                    System.out.println(""+appPackage);
                    System.out.println(""+appActivity);
					//String line1 = "null";
                 //   String cmd1 = "adb devices";
                  //  String udid1 = "null";
                 //   Runtime run1 = Runtime.getRuntime();
                 //   Process pr1 = run1.exec(cmd1);                        
                  //  pr1.waitFor();
                  //  BufferedReader buf1 = new BufferedReader(new InputStreamReader(pr1.getInputStream()));
                  //  while ((line=buf1.readLine())!=null) {
                  //  System.out.println(line);
                  //  if(line.contains("device")){
                    //       udid = line.split("\\s+")[0];
                    //       System.out.println("udid "+udid);
                  //  }
                  //  }
					DesiredCapabilities capabilities1 = new DesiredCapabilities();
					capabilities1.setCapability("BROWSER_NAME", "Android");
					capabilities1.setCapability("VERSION", "6.0"); 
					capabilities1.setCapability("platformName","Android");
					capabilities1.setCapability("deviceName","Nexus 5");
					capabilities1.setCapability("udid", "110c9547");
					capabilities1.setCapability("appPackage", "com.jio.bapp");
					capabilities1.setCapability("appActivity", "com.jio.bapp.activity.SplashActivity");
                    System.out.println("About to Launch");
					Driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities1);
                    System.out.println("App Launched");
                    sleep(5000)
                    WebElement MobileNumber = Driver.findElement(By.id("mobileNumber"));
                    System.out.println("App Launched 1");
                    MobileNumber.sendKeys("8335852828");
                    System.out.println("App Launched 2");
                    
          
      }
    else{
      def serviceIE = new InternetExplorerDriverService.Builder().usingPort(9516).usingDriverExecutable(new File("IEDriverServer.exe")).build()
      serviceIE.start()
      DesiredCapabilities d = DesiredCapabilities.internetExplorer()
      d.setCapability("nativeEvents", false)
      d.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);

      Driver = new RemoteWebDriver(serviceIE.getUrl(),d)

    }
    

    
  }
}