package actions;
import java.util.HashMap;
import io.appium.java_client.MobileElement;
import utils.Elements;
import java.util.*;

class Clear{
    public void run(HashMap<String, String> params){
        	MobileElement element = Elements.find(params, Driver.driver);
	        element.clear();
            return;
    }
}