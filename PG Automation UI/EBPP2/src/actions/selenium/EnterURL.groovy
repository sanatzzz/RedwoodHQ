package actions.selenium;
import actions.selenium.Browser

class EnterURL{
    public void run(def params){
        
         if (params."Environment Type" == "ST")
        {
            switch (params."Track"){
      case "Consumer Portal":
      	Browser.Driver.navigate().to("")
      	break
      case "Merchant Portal":
      	Browser.Driver.navigate().to("https://sit.rpay.co.in/JioMerchant")
      	break
      case "Merchant Revamp":
      	Browser.Driver.navigate().to("https://sit.rpay.co.in/JioMerchantUIRevamp/")
      	break      
      case "Agent":
      	Browser.Driver.navigate().to("https://sit.rpay.co.in/JMAgent")
      	break      
      case "Agent Revamp":
        Browser.Driver.navigate().to("https: //sit.rpay.co.in/jioagent")
      	break      
      case "Ops Portal":
      	Browser.Driver.navigate().to("https://10.129.65.25:8443//ejoms/")
      	break      
      case "Feedzai":
      	Browser.Driver.navigate().to("https://10.129.65.62/#login ")
      	break      
      case "AML":
      	Browser.Driver.navigate().to("https://10.129.65.34:7001/AMLOCK/Login/LoginPage.jsp")
      	break
      case "PG":
      	Browser.Driver.navigate().to("https://sit.rpay.co.in/jio/")
      	break
      default:
        foundElement = Driver.findElement(By.id(params.ID))
        }
        }
        else if (params."Environment Type" == "PP" )
        {
              switch (params."Track"){
      case "Consumer Portal":
      	Browser.Driver.navigate().to("https://test.rpay.co.in")
      	break
      case "Merchant Portal":
      	Browser.Driver.navigate().to("https://test.rpay.co.in/JioMerchant")
      	break    
      case "Agent":
      	Browser.Driver.navigate().to("https://sit.rpay.co.in/JMAgent")
      	break      
      case "Agent Revamp":
        Browser.Driver.navigate().to("https://sit.rpay.co.in/jioagent")
      	break      
      case "Ops Portal":
      	Browser.Driver.navigate().to("https://uattibbsea.rjil.ril.com//ejoms/")
      	break      
      case "Feedzai":
      	Browser.Driver.navigate().to("https://10.129.65.62/#login ")
      	break      
      case "AML":
      	Browser.Driver.navigate().to("https://10.129.65.34:7001/AMLOCK/Login/LoginPage.jsp")
      	break
      case "PG":
      	Browser.Driver.navigate().to("https://sit.rpay.co.in/jio/")
      	break
      default:
        foundElement = Driver.findElement(By.id(params.ID))
              }
        }
        
        else
        {
             println "No URL specified for the particular track"
        }
    }
}