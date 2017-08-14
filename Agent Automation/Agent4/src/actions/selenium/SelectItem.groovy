package actions.selenium;

import actions.selenium.utils.GetObjectRepository
import actions.selenium.utils.Elements
import actions.selenium.Browser
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.Select

class SelectItem{
  
  public void run(def params){
    //WebElement element = Elements.find(params,Browser.Driver)
      String ElementName = (String) params.get("Element Name");
      String PageName = (String) params.get("Page Name"); 
      String Value = (String) params.get("Value"); 
      String Index = (String) params.get("Index"); 
      String VisibleText = (String) params.get("Visible Text"); 
      
      System.out.println("Success"+ElementName);
      System.out.println("Success"+PageName);
      System.out.println("Success"+Value);
      System.out.println("Success"+Index);
      System.out.println("Success"+VisibleText);
      
      WebElement element = GetObjectRepository.find_Element(ElementName,PageName)
         System.out.println("Success"+element);
    
   /* if(params."Visible Text"){
    	new Select(element).selectByVisibleText(params."Visible Text")  
    }
    else if(params."Value"){
    	new Select(element).selectByValue(params."Value")
    }
    else if (params.Index){
    	new Select(element).selectByVisibleText(params.Index.toInteger())
    }
	*/
          
      
      if(params."Visible Text"){
             System.out.println("Success"+VisibleText);
    	new Select(element).selectByVisibleText(VisibleText);
          System.out.println("Done");
    }
    else if(params."Value"){
           System.out.println("Success"+Value);
    	new Select(element).selectByValue(Value);
        System.out.println("Done1");
    }
    else if (params."Index"){
         System.out.println("Success"+Index);
    	new Select(element).selectByIndex(Index.toInteger());
        System.out.println("Done2");
    }
    
  }
}