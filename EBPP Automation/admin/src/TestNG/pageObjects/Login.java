package TestNG.pageObjects;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import TestNG.test.Capabilities;

public class Login {

    /**

     * All WebElements are identified by @FindBy annotation

     */

    AppiumDriver<MobileElement> driver;

    @FindBy(id="etMobileNumber")

    MobileElement username;

    

    @FindBy(id="etPassword")

    MobileElement password;

    

    @FindBy(name="Login/Signup")

    MobileElement login;

    

    public Login(AppiumDriver driver){

        this.driver = driver;

        //This initElements method will create all WebElements

        PageFactory.initElements(driver, this);

    }

    //Set user name in textbox

    public void setUserName(String strUserName){

        username.sendKeys(strUserName);

        

    }

    

    //Set password in password textbox

    public void setPassword(String strPassword){

    password.sendKeys(strPassword);

    }

    

    //Click on login button

    public void clickLogin(){

            login.click();

    }


    /**

     * This POM method will be exposed in test case to login in the application

     * @param strUserName

     * @param strPasword

     * @return

     */

    public void loginToConsumer(String strUserName,String strPasword){

        //Fill user name

        this.setUserName(strUserName);
        
        
        //Click Login button

        this.clickLogin();

        //Fill password

        this.setPassword(strPasword);


                

    }

}