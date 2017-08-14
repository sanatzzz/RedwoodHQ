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

        
    }
}