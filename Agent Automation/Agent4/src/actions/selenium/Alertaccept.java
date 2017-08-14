package actions.selenium;
import actions.selenium.Browser;
import org.openqa.selenium.Alert;
import java.util.*;

class Alertaccept{
    public void run(HashMap<String, Object> params){
        try {
                                  Alert alert = Browser.Driver.switchTo().alert();
                                  
                                  // System.out.println(alert.getText());
                                  alert.accept();
                           } catch (Exception e) {
                                  System.out.println("No alert found");
                           }

      /*  var options = new ChromeOptions();

 	options.AddArguments("chrome.switches", "--disable-extensions --disable-extensions-file-access-check --disable-extensions-http-throttling --disable-infobars --enable-automation --start-maximized");
	 options.AddUserProfilePreference("credentials_enable_service", false);
 	options.AddUserProfilePreference("profile.password_manager_enabled", false);
	 var driver = new ChromeDriver(options);
     */
    }
}