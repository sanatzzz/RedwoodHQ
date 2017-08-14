package actions.selenium

import actions.selenium.utils.Elements
import actions.selenium.Browser
import org.openqa.selenium.WebElement
import java.sql.DriverManager
import java.sql.Connection
import groovy.sql.Sql

class WaitForElement{
  public void run (def params){
    //  println params.get("ID");
    //  println params.get("ID Type");
    //  def sql = Sql.newInstance('jdbc:mysql://10.50.54.58:3306/testapi', 'root','root', 'com.mysql.jdbc.Driver')
    //  println "Connected"  
    //  def fetch = sql.rows("Select * from headers")
    //  fetch.each{
    //      it ->
    //      println it.header_id+" "+it.header_key+" "+it.header_name+" "+it.header_value
    //  }

    int count = params."Timeout In Seconds".toInteger()
    while(count >= 0){
      def elements = Elements.findAll(params,Browser.Driver)
      if(elements.size() > 0) break
      sleep(1000)
      count--
    }
    if(count <= 0){
      assert false,"Element was not found in ${params."Timeout In Seconds"} seconds."
    }
	
  }
}