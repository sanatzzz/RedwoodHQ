package actions.newappium;

/**
 * Created by dinkark on 13-Oct-2016
 */
import java.util.HashMap;
import actions.utils.Constants;
import io.appium.java_client.MobileElement;
import actions.utils.Elements;
import actions.utils.SwipeElement;
import actions.selenium.utils.GetObjectRepository;

class SwipeLeft {
	public void run(HashMap<String, String> params) throws Exception{
		//MobileElement element = Elements.find(params, Driver.driver);
         String ElementName = (String) params.get("Element Name");
         String PageName = (String) params.get("Page Name"); 
         MobileElement element = GetObjectRepository.find_Element(ElementName,PageName);        
        int noOfTimes = Integer.parseInt(params.get(Constants.NO_OF_TIMES));
        for (int i = 0; i < noOfTimes; i++) {
		SwipeElement.swipeLeft(element, Driver.driver);
        }
	}
}
