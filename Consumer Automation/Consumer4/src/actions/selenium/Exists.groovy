package actions.selenium

import actions.selenium.utils.Elements
import actions.selenium.Browser
import org.openqa.selenium.WebElement

class Exists{
  public void run (def params){

    def elements = Elements.findAll(params,Browser.Driver)
//New Code      
    //  String ElementName = (String) params.get("Element Name")
     // String PageName = (String) params.get("Page Name");
     // def elements = GetObjectRepository.find_All(ElementName,PageName) 
//New Code      
      
    if(params."Number of Matches"){
      assert elements.size() == params."Number of Matches".toInteger(),"Error element: ${params.ID} was not found expected number of times: ${params."Number of Matches"}.  It was found: ${elements.size()} times."
    }
    else{
        assert elements.size() > 0,"Error element: ${params.ID} was not found."
    }

  }
}