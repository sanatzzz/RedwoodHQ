package actions.selenium.utils;
import org.json.*;
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
import java.text.SimpleDateFormat;
import actions.selenium.Global_Params;
public class RESTHandler{
   
    /*
    	This Function will Identify Leaf nodes in Json Object 
        and return it to parent node in hashmap format
        
    */
    
  /*   public Map<String,String> IdentifyJsonNodes(JSONObject jObj,String key,String parentPath,Map<String,String> jsonNodes, int count){
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
	} */
    
    
    public Map<String,String> IdentifyJsonNodes(JSONObject jObj,String key,String parentPath,Map<String,String> jsonNodes, int count,String parentKey){
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
				jsonNodes = IdentifyJsonNodes(j,key1,parentPath,jsonNodes,0,key);
			}
		}
	} 
	else if(jObj.get(key) instanceof JSONArray){
		JSONArray jArray = (JSONArray)jObj.get(key);
		Iterator itr= jArray.iterator();
		int ct =0;
		while(itr.hasNext()){
			Object o = itr.next();
			if(o instanceof JSONObject){
				JSONObject featureJsonObj = (JSONObject)o;
				if (featureJsonObj.keySet().size()>0){
					for(String key1 : featureJsonObj.keySet()){
						jsonNodes = IdentifyJsonNodes(featureJsonObj,key1,parentPath,jsonNodes,ct,key);
					}
				}
			}
			else{
				jsonNodes.put(parentKey+"_"+key, jArray.toString());
			}
			ct++;                    
		}
	} else {
		j = jObj;
		String insertKey = key;
		if(count>0){
			insertKey = key+"__"+count;
		}
		Object temp = j.get(key);
		if (temp instanceof String){
			jsonNodes.put(parentKey+"_"+insertKey, j.getString(key));                            
		}
		else if (temp instanceof Integer){
			jsonNodes.put(parentKey+"_"+insertKey, String.valueOf(j.getInt(key)));
		}
		else if (temp instanceof Boolean){
			jsonNodes.put(parentKey+"_"+insertKey, String.valueOf(j.getBoolean(key)));                
		}
		else if (temp instanceof Double){
			jsonNodes.put(parentKey+"_"+insertKey, String.valueOf(j.getDouble(key))) ;                                
		}
		else if(temp instanceof ArrayList<?>){
			ArrayList<?> list = (ArrayList<?>) temp;
			ArrayList<String> returnArray = new ArrayList<>();
			if(list.get(0) instanceof String){
				for (int i=0;i<list.size();i++){
					returnArray.add(list.get(i).toString());
				}
			}
			if(list.get(0) instanceof Integer){
				for (int i=0;i<list.size();i++){
					returnArray.add(list.get(i).toString());
				}
			}
			for (int i=0;i<list.size();i++){
				returnArray.add(list.get(i).toString());
			}
			jsonNodes.put(parentKey+"__"+insertKey,returnArray.toString());
		}

	}
return jsonNodes;
}


    
    
    /*
    	This Function will call IdentifyJsonNodes function in iterative manner
        to get key value pair from json object.
        
    */
    
    public Map<String,String> getJsonMap(String jsonResponseString){
        System.out.println("processing json :" +jsonResponseString);
        JSONObject jSonResponseObject = new JSONObject(jsonResponseString);
        System.out.println("converted to Json object");
     	Map <String,String> jsonNodes = new HashMap<String,String>();
        String parentPath="";
      	for (String key : jSonResponseObject.keySet()){
    		jsonNodes=  IdentifyJsonNodes(jSonResponseObject, key,parentPath,jsonNodes,0,key);
    	}
        System.out.println("jsonNodes: "+ jsonNodes);
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
        add_header_value : Some API requires additional headers and data from previous APIs.
      
    */
    
    public ArrayList<String> executeRESTServiceCall(String jsonCompareOption, String url, String inputObject,String expectedOutput,ArrayList<String> ignoreParams,
                                       		String test_case_name,String method_type,String header_key,String add_header_tag,String add_header_value,ArrayList<String> returnTags){
        System.out.println("jsonCompareOption :" + jsonCompareOption);
           System.out.println("url :" + url);
           System.out.println("inputObject :" + inputObject);
           System.out.println("header_key :" + header_key);
        
           System.out.println("Additional Header Tag :" + add_header_tag);
           System.out.println("Additional Header Values :" + add_header_value);
        
        ArrayList<String> returnValues = new ArrayList<String>();
        Map <String,String> expectedValues = new HashMap<String,String>();
        System.out.println("Getting Headers");
        Headers headers = new DatabaseHandler().getHeaders(header_key,add_header_tag,add_header_value);
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
                        
                    }
		    	System.out.println("Response : "+ resp);
           
              // new DatabaseHandler().insertDataToLogTable(test_case_name,inputObject,resp,url);
                
                new DatabaseHandler().insertDataToAPISampleResponse(test_case_name,inputObject,resp,url);
                expectedValues= new DatabaseHandler().getExpectedParams(test_case_name);
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
                        else if(method_type.equals("post")){
                             System.out.println("post request fired");
                            resp = 	given().headers(headers).when().
      								body(inputObject).post(url)
            							.then().extract().response().asString();
                        }
                        else if(method_type.equals("put")){
                             System.out.println("post request fired");
                            resp = 	given().headers(headers).when().
      								body(inputObject).put(url)
            							.then().extract().response().asString();
                        }
                        else if(method_type.equals("delete")){
                             System.out.println("post request fired");
                            resp = 	given().headers(headers).when().
      								body(inputObject).delete(url)
            							.then().extract().response().asString();
                        }
                        System.out.println("Response : "+ resp);
                        System.out.println("expectedOutput : "+ expectedOutput);
                     //   new DatabaseHandler().insertDataToLogTable(test_case_name,inputObject,resp,url);
                        new DatabaseHandler().insertDataToAPISampleResponse(test_case_name,inputObject,resp,url);
                        Map<String,String> jsonNodes = getJsonMap(resp);
                        Map <String,String> expectedJson = getJsonMap(expectedOutput);
                        for (String tag : returnTags){
                            returnValues.add(jsonNodes.get(tag));
                		}
                        System.out.println("till here!! ");
                        //jsonNodes.keySet().removeAll(ignoreParams); 
                        //expectedJson.keySet().removeAll(ignoreParams);
                       	Map<String,String> updatedjsonNodes = ignoreParamsFunction(ignoreParams,jsonNodes);
                        Map<String,String> updatedexpectedJson = ignoreParamsFunction(ignoreParams,expectedJson);
                        System.out.println("updatedJsonNodes: "+updatedjsonNodes);
                        Assert.assertEquals(updatedexpectedJson,updatedjsonNodes);
                        
                        
                    }
          break;
        }
        
        return returnValues;
    }
    
    public String replaceNewValuesInRequestJson(String jsonRequest,ArrayList<String> tags,ArrayList<String>values,String api_name){
        Map<String,String> updatedRequest = getJsonMap(jsonRequest);
         System.out.println("Entered Replace Tags ");
        String oldValue ="";
        String newValue ="";
        if(tags.size()>0){
             System.out.println("Entered Replace Tags IF " + tags.size());
            for(int i=0;i<tags.size();i++){
                oldValue = updatedRequest.get(tags.get(i));
                newValue = values.get(i);
                
                if(newValue.equals("CURR_DATE")){
                	newValue = Global_Params.getChecksumTimestamp(api_name);
                }
                System.out.println("\""+tags.get(i)+"\":\""+oldValue+"\"");
                System.out.println("New Value : "+ newValue);
                jsonRequest=jsonRequest.replace("\""+tags.get(i)+"\":\""+oldValue+"\"","\""+tags.get(i)+"\":\""+newValue+"\"");
            }
        }
        System.out.println("Updated Json : "+jsonRequest);
        return jsonRequest;
    }
   
    public Map<String,String> ignoreParamsFunction(ArrayList<String> ignoreParams,Map<String,String> inputHashMap){
        System.out.println(" ignore These "+ignoreParams );
        Iterator<Map.Entry<String,String>> it = inputHashMap.entrySet().iterator();
       
        while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();	
            for(String key: ignoreParams){
                 System.out.println(" outsideIF "+key );
                if(pair.toString().contains(key.toString())){
                    //System.out.println(pair.getKey() + " = " + pair.getValue());
                    it.remove();
                    System.out.println(" insideIF "+key );
                }
                    //it.remove(); // avoids a ConcurrentModificationException
            }
        }
        System.out.println(inputHashMap);
        return inputHashMap;
    }
    
}