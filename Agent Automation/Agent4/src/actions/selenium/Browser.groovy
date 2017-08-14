package actions.selenium;

import org.openqa.selenium.WebDriver
import org.openqa.selenium.ie.InternetExplorerDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.ie.InternetExplorerDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
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
     // Driver = new RemoteWebDriver(service.getUrl(),DesiredCapabilities.chrome())
        
     //handles password saving alert asked by chrome  
        ChromeOptions options=new ChromeOptions();
        options.addArguments("--start-maximized");
        
        Map<String,Object> prefs=new HashMap<String,Object>();
        prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		options.setExperimentalOption("prefs", prefs);

        DesiredCapabilities capabilities=DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY,options);
        
        Driver = new RemoteWebDriver(service.getUrl(),capabilities);
        
        
        
    }
      
      else if(params."Browser Type" == "Android")
      {
                    
                    String appPackage = (String) params.get("appPackage");
                    String appActivity = (String) params.get("appActivity"); 
                    System.out.println("Launching App");
                    System.out.println(""+appPackage);
                    System.out.println(""+appActivity);
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