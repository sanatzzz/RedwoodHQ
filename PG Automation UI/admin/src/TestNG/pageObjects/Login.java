package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Login {

    /**

     * All WebElements are identified by @FindBy annotation

     */

    WebDriver driver;

    @FindBy(xpath="//*[@id=\"j_id__ctru12:r1:0:s1:ot189::content\"]")

    WebElement username;

    

    @FindBy(xpath="//*[@id=\"j_id__ctru12:r1:0:s1:it587::content\"]")

    WebElement password;

    

    @FindBy(xpath="//*[@id=\"j_id__ctru12:r1:0:s1:cb1\"]")

    WebElement login;

    

    public Login(WebDriver driver){

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

        //Fill password

        this.setPassword(strPasword);

        //Click Login button

        this.clickLogin();

                

    }

}