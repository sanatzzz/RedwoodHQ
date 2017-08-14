package actions.newappium;
import java.util.HashMap;
import actions.selenium.utils.GetObjectRepository;
import io.appium.java_client.MobileElement;
import utils.Elements;
import utils.Constants;
import utils.SwipeElement;

/**
 * Created by dinkark on 13-Oct-2016
 */
class SwipeUpSlow {
	public void run(HashMap<String, String> params) throws Exception{
		//MobileElement element = Elements.find(params, Driver.driver);
          String ElementName = (String) params.get("Element Name");
          String PageName = (String) params.get("Page Name"); 
          MobileElement element = GetObjectRepository.find_Element(ElementName,PageName);
        int noOfTimes = Integer.parseInt(params.get(Constants.NO_OF_TIMES));
        for (int i = 0; i < noOfTimes; i++) {
		SwipeElement.swipeUpSlow(element, Driver.driver);
        }
	}
}
