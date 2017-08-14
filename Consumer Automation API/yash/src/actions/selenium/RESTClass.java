package actions.selenium;

import actions.selenium.utils.RESTHandler;
import actions.selenium.utils.DatabaseHandler;
import java.util.*;
import org.junit.Assert;

class RESTClass{
    public ArrayList <String> run(HashMap<String, Object> params){
         	String endpointURL="";
		    String method_type="";
		    String jsonRequest="";
		    String expectedResponse="";
		    String headerKey="";
		    String test_case_name = params.get("Test Case Name").toString();
        	String jsonMatchOption = params.get("JsonMatchOption").toString();
        	String add_header_tag = params.get("Additional Header Tag").toString();
        	String add_header_value = params.get("Additional Header Value").toString();
        	String environment = params.get("Environment").toString();
       	 	ArrayList<String> ignoreParams =(ArrayList<String>) params.get("IgnoreParams");
       		Map <String,String> apiDetails = new HashMap<String,String>();
        	ArrayList <String> tags =  (ArrayList<String>)params.get("Tag Names");
    		ArrayList <String> values = (ArrayList<String>)params.get("Tag Values");
        	ArrayList <String> returnTags = (ArrayList<String>)params.get("Return Tags");
        	 System.out.println(params);	
        
		    if (environment==null || environment.isEmpty()){
                environment = "ST";
            }
		  	 System.out.println("Calling Database Handler....");  
        	apiDetails = new DatabaseHandler().getAPIDetails(test_case_name,environment);
        	
        	if(apiDetails.size()>1){
                endpointURL = apiDetails.get("endpointURL");
                jsonRequest = apiDetails.get("jsonRequest");
                expectedResponse = apiDetails.get("expectedResponse");
                method_type = apiDetails.get("method_type");
                headerKey = apiDetails.get("headerKey");
            }
        	else{
                Assert.assertTrue(false);
            }
        System.out.println("Calling RestHandler");	
        RESTHandler rs = new RESTHandler();
        String updatedRequest=jsonRequest;
        if(tags.size()>0){
            updatedRequest = rs.replaceNewValuesInRequestJson(jsonRequest,tags,values,test_case_name);
        }
       
     	ArrayList <String> returnValues = rs.executeRESTServiceCall(jsonMatchOption,endpointURL,updatedRequest,expectedResponse,ignoreParams,
                                  													test_case_name,method_type,headerKey,add_header_tag,add_header_value,returnTags);
   		System.out.println("response compared");
         System.out.println("Return Values : "+returnValues);
        for (String tag : returnValues){
            System.out.println("Tag :"+tag);
        }
        return returnValues;
    }
    
   
   
    
}