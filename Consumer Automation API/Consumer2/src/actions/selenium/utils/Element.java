package actions.selenium.utils;
import java.util.HashMap;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import java.util.*;


public class Element{
     static Map<String, String> hm = new HashMap<String,String>();
      public Element() {
          System.out.println("Inside Elements");
          hm =   new ObjectRepository().obr;    
      } 
  public static WebElement find(HashMap<String, Object> params,WebDriver Driver){
    assert Driver != null;
      String ObjectName=null;
      String FindElement=null;
      //"Error browser is not opened.  Use Open Browser action."

    WebElement foundElement = null;
    
    switch ((String) params.get("ID Type")){
      case "Class Name":
       
      	foundElement = Driver.findElement(By.className((String) params.get("ObjectName.value()")));
      	break;
      case "Css Selector":
    	 	foundElement = Driver.findElement(By.cssSelector((String) params.get("ObjectName.value()")));
      	break;
      case "ID":
            ObjectName = params.get("ObjectName").toString();
            System.out.println("ObjectName"+ObjectName);
             System.out.println(hm.toString());
            FindElement = hm.get(ObjectName);
             System.out.println("FindElemet"+FindElement);
            foundElement = Driver.findElement(By.id(FindElement));
      	break;     
      case "Link Text":
    	 	foundElement = Driver.findElement(By.linkText((String) params.get("ObjectName.value()")));
      	break;      
      case "XPath":
            ObjectName = params.get("ObjectName").toString();
            System.out.println("FindElement1"+ObjectName);
            System.out.println(hm.toString());
            FindElement = hm.get(ObjectName);
            System.out.println("FindElemet"+FindElement);
            foundElement = Driver.findElement(By.xpath(FindElement));
      	break;      
      case "Name":
    	 	foundElement = Driver.findElement(By.name((String) params.get("ObjectName.value()")));
      	break;      
      case "Partial Link Text":
    	 	foundElement = Driver.findElement(By.partialLinkText((String) params.get("ObjectName.value()")));
      	break;      
      case "Tag Name":
    	 	foundElement = Driver.findElement(By.tagName((String) params.get("ObjectName.value()")));
      	break;
      default:
    	 	foundElement = Driver.findElement(By.className((String) params.get("ObjectName.value()")));
    }
    
    return foundElement;
  }
  
  public static WebElement findAll(HashMap<String, Object> params,WebDriver Driver){
    assert Driver != null; //"Error browser is not opened.  Use Open Browser action."//
    
    List<WebElement> foundElements;
      
    
    	    switch ((String) params.get("ID Type")){
    	      case "Class Name":
    	       
    	      	foundElements = Driver.findElements(By.className((String) params.get("ObjectName.value()")));
    	      	break;
    	      case "Css Selector":
    	    	 	foundElements = Driver.findElements(By.cssSelector((String) params.get("ObjectName.value()")));
    	      	break;
    	      case "ID":
    	    	 	foundElements = Driver.findElements(By.id((String) params.get("ObjectName.value()")));
    	      	break;     
    	      case "Link Text":
    	    	 	foundElements = Driver.findElements(By.linkText((String) params.get("ObjectName.value()")));
    	      	break;      
    	      case "XPath":
    	    	 	foundElements = Driver.findElements(By.xpath((String) params.get("ObjectName.value()")));
    	      	break;      
    	      case "Name":
    	    	 	foundElements = Driver.findElements(By.name((String) params.get("ObjectName.value()")));
    	      	break;      
    	      case "Partial Link Text":
    	    	 	foundElements = Driver.findElements(By.partialLinkText((String) params.get("ObjectName.value()")));
    	      	break;      
    	      case "Tag Name":
    	    	 	foundElements = Driver.findElements(By.tagName((String) params.get("ObjectName.value()")));
    	      	break;
    	      default:
    	    	 	foundElements = Driver.findElements(By.className((String) params.get("ObjectName.value()")));
    }
    
    return (WebElement) foundElements;
  }
}