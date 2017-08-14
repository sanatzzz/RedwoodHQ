package actions.selenium;

import java.util.*;
import static io.restassured.RestAssured.*;
import static  io.restassured.matcher.RestAssuredMatchers.*;
import org.json.JSONObject;
import io.restassured.http.ContentType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.junit.Assert;

class GetConfigSettings{
    public void run(HashMap<String, Object> params){
        
        	 Connection con;
		    PreparedStatement stmt;
		    ResultSet rs;
		    String url="";
		    String method_type="";
		    String inputObject="";
		    String expectedOutput="";
		    String headerValue="";
		    String test_case_name = params.get("Test Case Name").toString();
        
		    Map <String,String> expectedValues= new HashMap<String,String>();
		   

		    try{
			   Class.forName("com.mysql.jdbc.Driver");
		        
		        con=DriverManager.getConnection("jdbc:mysql://localhost:3306/testapi","root","root");

		                    stmt=con.prepareStatement("SELECT * FROM `api_test_data_main` WHERE test_case_name='"+test_case_name+"'");
		                  //  stmt.setString(1,api_name);
		                  //  System.out.println("statement : "+stmt.toString());
		                    rs = stmt.executeQuery();
		                     while(rs.next()){
		                         //Retrieve request data
		                         url = rs.getString("url");
		                         method_type = rs.getString("method_type");
		                          inputObject = rs.getString("request_body");
		                          expectedOutput = rs.getString("response_body");
		                        }
		                      rs.close();
		                      con.close();
		                      stmt.close();
		                    }

		    catch (Exception e) 
		    {
		        System.out.println(e);
		    }
		
  
          		String resp = given().when().header("X-API-KEY","l7xx3e887403b5ed40e78ca8edef65c87587").
   							get(url).then().extract().response().asString();
  								Assert.assertEquals(resp, expectedOutput);
          
           
        
    }
}