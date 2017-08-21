package TestNG.pageObjects;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import io.appium.java_client.pagefactory.AndroidFindBy;
import TestNG.test.Capabilities;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.android.AndroidDriver;
import java.util.concurrent.TimeUnit;
import java.util.*;

public class Mpin{

   AppiumDriver<MobileElement> driver;

    @AndroidFindBy(id="tv1")

    MobileElement mpin1;

    
    @AndroidFindBy(id="tv3")

    MobileElement mpin3;
 

    @AndroidFindBy(id="tv5")

    MobileElement mpin5;
    
    @AndroidFindBy(id="tv7")

    MobileElement mpin7;
    
    
 

    public Mpin(AppiumDriver driver){

        this.driver = driver;

        //This initElements method will create all WebElements
        System.out.println("Creating Elements");

        PageFactory.initElements(new AppiumFieldDecorator(driver,5,TimeUnit.SECONDS), this);

    }

    //Set mpin

    public void mpinclick(){
        

        mpin1.click();
        mpin3.click();
        mpin5.click();
        mpin7.click();

       
    }


    /**
     * This POM method will be exposed in test case to enter mpin in the application
     */

    public void entermpin(){

        //Enter mpin
        this.mpinclick();
        
    }

}