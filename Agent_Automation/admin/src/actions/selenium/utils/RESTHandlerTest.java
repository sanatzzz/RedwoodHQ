package actions.selenium.utils;

import actions.selenium.utils.DatabaseHandler;
import java.util.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static io.restassured.RestAssured.*;
import static  io.restassured.matcher.RestAssuredMatchers.*;
import org.json.JSONObject;
import org.json.JSONArray;
import io.restassured.http.ContentType;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import org.junit.Assert;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import java.io.File;

public class RESTHandlerTest{
   
    /*
    	This Function will Identify Leaf nodes in Json Object 
        and return it to parent node in hashmap format
        
    */
    
    public Map<String,String> IdentifyJsonNodes(JSONObject jObj,String key,String parentPath,Map<String,String> jsonNodes, int count){
		 JSONObject j;
		 if (jObj.get(key) instanceof JSONObject){
			  j = jObj.getJSONObject(key);
			  if(parentPath==""){
				  	parentPath = key;
			  }
			  else{
				  	parentPath = parentPath+"."+key;
			  }
			 if (j.keySet().size()>0){
					 for(String key1 : j.keySet()){
					 jsonNodes = IdentifyJsonNodes(j,key1,parentPath,jsonNodes,0);
					 }
				 }
		 }
         else if(jObj.get(key) instanceof JSONArray){
             JSONArray jArray = (JSONArray)jObj.get(key);
			 Iterator itr= jArray.iterator();
			 while(itr.hasNext()){
		            JSONObject featureJsonObj = (JSONObject)itr.next();
		            if (featureJsonObj.keySet().size()>0){
		            	int ct =0;
						 for(String key1 : featureJsonObj.keySet()){
						 jsonNodes = IdentifyJsonNodes(featureJsonObj,key1,parentPath,jsonNodes,ct);
						 }
						 ct++;
					 }
		            
			 }
             
         }
		 else
		 {
			j = jObj;
            String insertKey = key;
			if(count>0){
				insertKey = key+count;
			}
            Object temp = j.get(key);
		    if (temp instanceof String){
		     	jsonNodes.put(insertKey, j.getString(key));                   	  
		     }
		     else if (temp instanceof Integer){
		         jsonNodes.put(insertKey, String.valueOf(j.getInt(key)));
		     }
		     else if (temp instanceof Boolean){
		           jsonNodes.put(insertKey, String.valueOf(j.getBoolean(key)));  	   
		     }
		                           
		     else if (temp instanceof Double){
		         jsonNodes.put(insertKey, String.valueOf(j.getDouble(key))) ;                  	   
		     }
    		
		}
           //System.out.println("Identify jsonNodes: "+ jsonNodes);
		 return jsonNodes;
	}
    
    /*
    	This Function will call IdentifyJsonNodes function in iterative manner
        to get key value pair from json object.
        
    */
    
    public Map<String,String> getJsonMap(String jsonResponseString){
        
        JSONObject jSonResponseObject = new JSONObject(jsonResponseString);
     	Map <String,String> jsonNodes = new HashMap<String,String>();
        String parentPath="";
      	for (String key : jSonResponseObject.keySet()){
    		jsonNodes=  IdentifyJsonNodes(jSonResponseObject, key,parentPath,jsonNodes,0);
    	}
        // System.out.println("jsonNodes: "+ jsonNodes);
         return jsonNodes;
    } 
    
    
    /*
    	This function will execute REST request by using request parameters.
        It will also compare REST response with expected response
        
        Input Parameters : 
        jsonCompareOption : There can be 3 Options SchemaMatch , EntireJsonMatch or SpecificParameterMatch
        url : API url
        inputObject : body object to be sent in REST request.
        expectedOutput : Expected Json Response
        ignoreParams : parameters to be ignored while comparing entire json
        test_case_name : test scenario name as stored in database
        method_type : get/post/put/delete
        header_key : header_key is used to fetch required headers from database for REST request.
        auth_value : Some API requires authentication to test API. This will contain header value for Authentication header.
      
    */
    
    public ArrayList<String> executeRESTServiceCall(String jsonCompareOption, String url, String inputObject,String expectedOutput,ArrayList<String> ignoreParams,
                                       		String test_case_name,String method_type,String header_key,String header_tag_case,String auth_value,
                                                    ArrayList<String> global_headers_tags,ArrayList<String> global_headers_values,ArrayList<String> TestTags,ArrayList<String> returnTags){
        
        System.out.println("jsonCompareOption :" + jsonCompareOption);
           System.out.println("url :" + url);
           System.out.println("inputObject :" + inputObject);
           System.out.println("header_key :" + header_key);
        ArrayList<String> returnValues = new ArrayList<String>();
        Map <String,String> expectedValues = new HashMap<String,String>();
        System.out.println("Getting Headers");
        Headers headers = new DatabaseHandlerTest().getHeaders(header_key,header_tag_case,auth_value,global_headers_tags,global_headers_values,TestTags);
        String resp ="";
        switch(jsonCompareOption){
            case "SchemaValidation" :
                {
                    System.out.println("schema matching selected : "+expectedOutput);
                    File schemaFile= new File(expectedOutput);
                    if(inputObject== null){
                            inputObject="";
                        }
                    try{
                         if(inputObject== null){
                            inputObject="";
                        }
                    if(method_type.equals("get")){     
                  given().headers(headers).when().body(inputObject).get(url).then()
                       .assertThat().body(matchesJsonSchema(schemaFile));
                    }
                    else if(method_type.equals("post")){
                        given().headers(headers).when().body(inputObject).post(url).then()
                       .assertThat().body(matchesJsonSchema(schemaFile));
                    }
                        
                          
                    }
                    catch(Exception e){
                        System.out.println(e);
                    }
                }
          		break;
            
          	case "SpecificParameterValidation" :
                {
                    if(inputObject== null){
                            inputObject="";
                        }
                System.out.println("Parameter matching selected");
                     if(method_type.equals("get")){
                         resp =given().headers(headers).when().contentType("application/json").
      								accept("application/json").body(inputObject).get(url)
            							.then().contentType("application/json").extract().response().asString();
                     }
                    else if(method_type.equals("post")){
                        resp =given().headers(headers).when().
      								body(inputObject).post(url)
            							.then().contentType("application/json").extract().response().asString();
                        System.out.println("Response : "+ resp);
                    }
		    	
           
              // new DatabaseHandlerTest().insertDataToLogTable(test_case_name,inputObject,resp,url);
                
                new DatabaseHandlerTest().insertDataToAPISampleResponse(test_case_name,inputObject,resp,url);
                expectedValues= new DatabaseHandlerTest().getExpectedParams(test_case_name);
      			Map<String,String> JsonNodes = getJsonMap(resp);
                Map <String,String> actualValues= new HashMap<String,String>();
               	for (String key : expectedValues.keySet()){
                   actualValues.put(key, JsonNodes.get(key));
                  }
                for (String tag : returnTags){
                    returnValues.add(actualValues.get(tag));
                }
                expectedValues.keySet().removeAll(ignoreParams);  
                actualValues.keySet().removeAll(ignoreParams);    
                Assert.assertEquals(expectedValues, actualValues);
                }
                break;
                    
            case "JsonExactMatch" :
                    {
                        
                        if(inputObject== null){
                            inputObject="";
                        }
                       if(method_type.equals("get")){
                        System.out.println("get request fired");
                           resp = 	given().headers(headers).when().
      								body(inputObject).get(url)
            							.then().extract().response().asString();
                       }
                        else{
                             System.out.println("post request fired");
                            resp = 	given().headers(headers).when().
      								body(inputObject).post(url)
            							.then().extract().response().asString();
                        }
                     //   new DatabaseHandlerTest().insertDataToLogTable(test_case_name,inputObject,resp,url);
                         System.out.println("Response : "+ resp);
                         System.out.println("Response : "+ expectedOutput);
                        new DatabaseHandlerTest().insertDataToAPISampleResponse(test_case_name,inputObject,resp,url);
                        Map<String,String> jsonNodes = getJsonMap(resp);
                        Map <String,String> expectedJson = getJsonMap(expectedOutput);
                        for (String tag : returnTags){
                            returnValues.add(jsonNodes.get(tag));
                		}
                       
                        jsonNodes.keySet().removeAll(ignoreParams); 
                        expectedJson.keySet().removeAll(ignoreParams);
                        //Assert.assertEquals(expectedJson,jsonNodes);
                        
                        
                    }
          break;
        }
        
        return returnValues;
    }
    
    public String replaceNewValuesInRequestJson(String jsonRequest,ArrayList<String> tags,ArrayList<String>values){
        Map<String,String> updatedRequest = getJsonMap(jsonRequest);
        String oldValue ="";
        String newValue ="";
        if(tags.size()>0){
            for(int i=0;i<tags.size();i++){
                oldValue = updatedRequest.get(tags.get(i));
                newValue = values.get(i);
                System.out.println("\""+tags.get(i)+"\":\""+oldValue+"\"");
                System.out.println("New Value : "+ newValue);
                jsonRequest=jsonRequest.replace("\""+tags.get(i)+"\":\""+oldValue+"\"","\""+tags.get(i)+"\":\""+newValue+"\"");
            }
        }
        System.out.println("Updated Json : "+jsonRequest);
         return jsonRequest;
    }
   
    
    
}