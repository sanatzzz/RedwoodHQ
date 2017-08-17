package actions.newappium;
import java.util.*;
import java.util.HashMap;
import io.appium.java_client.MobileElement;
import actions.utils.Elements;


class GoBack{
    public void run(HashMap<String, Object> params){
        Driver.driver.navigate().back();
    }
}