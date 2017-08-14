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
import java.util.HashMap;
import java.util.Map;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import java.util.ArrayList;


import org.junit.Assert;
class PGAPITest{
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
        String jsonMatchOption = params.get("JsonMatchOption").toString();
        ArrayList<String> ignoreParams =(ArrayList<String>) params.get("IgnoreParams");
       System.out.println("test_case_name : "+ test_case_name);
         System.out.println("JsonMatchOption : "+ jsonMatchOption);
		    Map <String,String> expectedValues= new HashMap<String,String>();
		   try{
			   Class.forName("com.mysql.jdbc.Driver");
		        
		       // System.out.println("getting connected to database");
		        con=DriverManager.getConnection("jdbc:mysql://localhost:3306/testapi","root","root");
		       // System.out.println("connected");
		                    stmt=con.prepareStatement("SELECT * FROM `api_test_data_main` WHERE test_case_name='"+test_case_name+"'");
		                  
		                     rs = stmt.executeQuery();
		                     while(rs.next()){
		                         //Retrieve request data
		                         url = rs.getString("url");
		                         method_type = rs.getString("method_type");
		                          inputObject = rs.getString("request_body");
		                          expectedOutput = rs.getString("response_body");
                                 
		      //  System.out.println(url);
		                        }
		                      rs.close();
		                      con.close();
		                      stmt.close();
		                    }

		    catch (Exception e) 
		    {
		        System.out.println(e);
		    }
        
        switch(jsonMatchOption){
            case "SchemaValidation" :
                {
                    try{
                    System.out.println("Schema matching selected");
      given().when().contentType("application/json").
      accept("application/json").body(inputObject).post(url).then()
     .assertThat()
        .body(matchesJsonSchema(expectedOutput));
                    }
                    catch(Exception e){
                        System.out.println(e);
                    }
                }
                 break;
            
            case "SpecificParameterValidation" :
                {
                System.out.println("Parameter matching selected");
		    	String resp = 	given().when().contentType("application/json").
      								accept("application/json").body(inputObject).post(url)
            							.then().contentType(ContentType.JSON).extract().response().asString();
             try{
    	   Class.forName("com.mysql.jdbc.Driver");
	       con=DriverManager.getConnection("jdbc:mysql://localhost:3306/testapi","root","root");
	       stmt=con.prepareStatement("select * from expected_param_values WHERE test_case_name='"+test_case_name+"'");
	       rs = stmt.executeQuery();
	       
           while(rs.next()){
               //Retrieve parameter data
        	   expectedValues.put(rs.getString("param_name"), rs.getString("value1"));
              }
            rs.close();
            con.close();
            stmt.close();
           
      }
      catch(Exception e){
    	  System.out.println(e);
      }
      
 
       Map<String,String> JsonNodes = getJsonMap(resp);
        
	  Map <String,String> actualValues= new HashMap<String,String>();
	  
	     expectedValues.keySet().removeAll(ignoreParams);                 
  
       for (String key : expectedValues.keySet()){
           System.out.println("Checking key : " + key);
          
               System.out.println("Inserted key : " + key);
	  actualValues.put(key, JsonNodes.get(key));
          }
          
	
      
                   
   Assert.assertEquals(expectedValues, actualValues);
        }
                	break;
                    
            case "JsonExactMatch" :
                    {
                        try{
                        String resp = 	given().when().contentType("application/json").
      								accept("application/json").body(inputObject).post(url)
            							.then().contentType(ContentType.JSON).extract().response().asString();
                        
                        Map<String,String> jsonNodes = getJsonMap(resp);
                        Map <String,String> expectedJson = getJsonMap(expectedOutput);
                        jsonNodes.keySet().removeAll(ignoreParams); 
                        expectedJson.keySet().removeAll(ignoreParams);
                        Assert.assertEquals(jsonNodes, expectedJson);
                        }
                        catch(Exception e){
                        System.out.println(e);
                    }
                    }
                break;
        }
    }
    
   public Map<String,String> IdentifyJsonNodes(JSONObject jObj,String key,String parentPath,Map<String,String> jsonNodes){
		 JSONObject j;
		 if (jObj.get(key) instanceof JSONObject){
			  j = jObj.getJSONObject(key);
			  if(parentPath==""){
				  	//jsonNodes.put(key,key);
				  	parentPath = key;
			  }
			  else{
				  	//jsonNodes.put(key, parentPath+"."+key);
			  		parentPath = parentPath+"."+key;
			  }
			 // System.out.println("Inserting Key : " + key);
		   	 // System.out.println("Inserting Value : "+ jsonNodes.get(key).toString());
			  if (j.keySet().size()>0){
					 for(String key1 : j.keySet()){
					 jsonNodes = IdentifyJsonNodes(j,key1,parentPath,jsonNodes);
					 }
				 }
		 }
		 else
		 {
			j = jObj;
    
			jsonNodes.put(key, j.getString(key));
			//System.out.println("Inserting Key : " + key);
		   	 //System.out.println("Inserting Value : "+ jsonNodes.get(key).toString());
            
		 }
		 
		 return jsonNodes;
		 
	 }
    
    public Map<String,String> getJsonMap(String jsonResponseString){
        
        JSONObject jSonResponseObject = new JSONObject(jsonResponseString);
     
          Map <String,String> jsonNodes = new HashMap<String,String>();
                    
        String parentPath="";
      
    	  for (String key : jSonResponseObject.keySet()){
    		jsonNodes=  IdentifyJsonNodes(jSonResponseObject, key,parentPath,jsonNodes);
    	  }
                    
         return jsonNodes;
    } 
    
}