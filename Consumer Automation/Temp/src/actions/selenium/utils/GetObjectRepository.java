package actions.selenium.utils;
import actions.selenium.Browser;
import actions.appium.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import io.appium.java_client.MobileElement;


import java.util.*;

public class GetObjectRepository{
    
     public static ArrayList<String> sElementName = new ArrayList<String>();
     public static ArrayList<String> sElementType = new ArrayList<String>();
     public static ArrayList<String> sElementValue = new ArrayList<String>();
     public static ArrayList<String> sPageName = new ArrayList<String>();
     static String strAutomationDBURL = "jdbc:mysql://10.50.54.58:3306/world";
     static String strAutomationDBUserName = "root";
     static String strAutomationDBPassword = "root";
     static String strObjectRepositoryTable = "objectrepository";
   
  public static void run(HashMap<String, Object> params) throws Exception{
        
      System.out.println("Hi");
        run();
        
    }
    
    public static void run() throws Exception
    {
        
     System.out.println("Hi");
        //Print Timestamp
        //TimeStamp.run("Default");
        
        //Create Connection
        Connection con;
        Class.forName("com.mysql.jdbc.Driver");
        con=DriverManager.getConnection(strAutomationDBURL,strAutomationDBUserName,strAutomationDBPassword);
        
         System.out.println(strAutomationDBURL);
         System.out.println(strAutomationDBUserName);
         System.out.println(strAutomationDBPassword);
         System.out.println(strObjectRepositoryTable);
 //Create a Query
        String query = "SELECT * from " + strObjectRepositoryTable;
        
        System.out.println(query);


        // Create Statement Object
        Statement stmt = con.createStatement();


        // Execute the SQL Query. Store results in ResultSet
        ResultSet rs = stmt.executeQuery(query);


        // Add Values to the nodes
        while (rs.next()) {
            sElementName.add(rs.getString(1));  //Object Name
            sElementType.add(rs.getString(2));  //Locator type
            sElementValue.add(rs.getString(3)); //Locator Value
            sPageName.add(rs.getString(4));     //Page Name
        }
        
        System.out.println("Got the objects");
        
        rs.close();
        stmt.close();
        con.close();
        
    }
    
    public static MobileElement find_Element(String sElement, String sPage_name) throws Exception{
        
        String sLocatorType = null;
        String sLocatorValue = null;
        
        System.out.println("Looking for Objects properties of : ->" +  sElement  + ":" + sPage_name );
        
     for (int i = 0; i < sElementName.size(); i++) {
            if(sPageName.get(i).contains(sPage_name))
            {
                 System.out.println("Inside Loop");
                if(sElementName.get(i).contains(sElement))
                {
                     System.out.println("Inside Loop Inside");
                     System.out.println("redwood"+sElementName);
                      System.out.println("redwood"+sPage_name);
                    sLocatorType = sElementType.get(i);
                    sLocatorValue = sElementValue.get(i);
                    break;
                }
            }
 }
         System.out.println("Objects properties: ->" +  sLocatorType  + ":" + sLocatorValue );
        
         MobileElement sLocator = null;
        
        
        System.out.println("sLocatorValue"+sLocatorValue);
       
        
        
         switch (sLocatorType)
         {
              case "xpath":
                 sLocator = Driver.driver.findElement(By.xpath(sLocatorValue));      
                     break;
                 case "linkText":
                  sLocator = Driver.driver.findElement(By.linkText(sLocatorValue));
                     break;
                 case "className":
                  sLocator = Driver.driver.findElement(By.className(sLocatorValue));
                     break;
                 case "id":
                  sLocator = Driver.driver.findElement(By.id(sLocatorValue));
                     break;
                 case "cssSelector":
                  sLocator = Driver.driver.findElement(By.cssSelector(sLocatorValue));
                     break;
                 case "name":
                  System.out.println("inside name");
                  sLocator =Driver.driver.findElement(By.name(sLocatorValue));
                 System.out.println(""+sLocator);
                     break;
                 case "partialLinkText":
                  sLocator = Driver.driver.findElement(By.partialLinkText(sLocatorValue));
                     break;
                 case "tagName":
                  sLocator =Driver.driver.findElement(By.tagName(sLocatorValue));
                     break;
         }
        return sLocator;
        
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    
     public static MobileElement find_All(String sElement, String sPage_name) throws Exception{
        
        String sLocatorType = null;
        String sLocatorValue = null;
        
        System.out.println("Looking for Objects properties of : ->" +  sElement  + ":" + sPage_name );
        
     for (int i = 0; i < sElementName.size(); i++) {
            if(sPageName.get(i).contains(sPage_name))
            {
                if(sElementName.get(i).contains(sElement))
                {
                    sLocatorType = sElementType.get(i);
                    sLocatorValue = sElementValue.get(i);
                    break;
                }
            }
 }
         System.out.println("Objects properties: ->" +  sLocatorType  + ":" + sLocatorValue );
        
         MobileElement sLocator = null;
        
        
        System.out.println("sLocatorValue"+sLocatorValue);
       
        
        
         switch (sLocatorType)
         {
              case "xpath":
                 sLocator = Driver.driver.findElement(By.xpath(sLocatorValue));      
                     break;
                 case "linkText":
                  sLocator = Driver.driver.findElement(By.linkText(sLocatorValue));
                     break;
                 case "className":
                  sLocator = Driver.driver.findElement(By.className(sLocatorValue));
                     break;
                 case "id":
                  sLocator = Driver.driver.findElement(By.id(sLocatorValue));
                     break;
                 case "cssSelector":
                  sLocator = Driver.driver.findElement(By.cssSelector(sLocatorValue));
                     break;
                 case "name":
                  sLocator = Driver.driver.findElement(By.name(sLocatorValue));
                     break;
                 case "partialLinkText":
                  sLocator = Driver.driver.findElement(By.partialLinkText(sLocatorValue));
                     break;
                 case "tagName":
                  sLocator = Driver.driver.findElement(By.tagName(sLocatorValue));
                     break;
         }
        return sLocator;
        
    } 
}