 package actions.selenium;

import actions.selenium.utils.RESTHandlerTest;
import actions.selenium.utils.DatabaseHandlerTest;
import java.util.*;
import org.junit.Assert;

class RESTClassTest{
    
    public static ArrayList HeaderTags = new ArrayList();
    public static ArrayList HeaderValues = new ArrayList();
    
    public ArrayList <String> run(HashMap<String, Object> params){
         	String endpointURL="";
		    String method_type="";
		    String jsonRequest="";
		    String expectedResponse="";
		    String headerKey="";
		    String test_case_name = params.get("Test Case Name").toString();
        	String jsonMatchOption = params.get("JsonMatchOption").toString();
        	String header_tag_case = params.get("Header Tag").toString();
        	String auth_value = params.get("Authorization Header Value").toString();
       	 	ArrayList<String> ignoreParams =(ArrayList<String>) params.get("IgnoreParams");
       		Map <String,String> apiDetails = new HashMap<String,String>();
        	ArrayList <String> tags =  (ArrayList<String>)params.get("Tag Names");
    		ArrayList <String> values = (ArrayList<String>)params.get("Tag Values");
        	ArrayList <String> returnTags = (ArrayList<String>)params.get("Return Tags");
		    System.out.println(params);	
        
        
        	ArrayList <String> TestTags = (ArrayList<String>)params.get("Test Tags");
        	ArrayList <String> TestTagsValue = (ArrayList<String>)params.get("Test Tags Value");
        	
		  	System.out.println("Calling Database Handler....");  
        	apiDetails = new DatabaseHandlerTest().getAPIDetails(test_case_name);
        	
        int counter = 0;
        int pos = 0;
        for(String HeadVal : TestTags){
            if(!(RESTClassTest.HeaderTags.contains(HeadVal))){
                RESTClassTest.HeaderTags.add(HeadVal);
                RESTClassTest.HeaderValues.add(TestTagsValue.get(counter));
            } else {
                pos = RESTClassTest.HeaderTags.indexOf(HeadVal);
                RESTClassTest.HeaderValues.set(pos,TestTagsValue.get(counter));
            }
            counter++;
        }	
        
        	//RESTClassTest.HeaderTags.addAll(TestTags);
       		
        
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
        RESTHandlerTest rs = new RESTHandlerTest();
        String updatedRequest=jsonRequest;
        if(tags.size()>0){
            updatedRequest = rs.replaceNewValuesInRequestJson(jsonRequest,tags,values);
        }
       
     	ArrayList <String> returnValues = rs.executeRESTServiceCall(jsonMatchOption,endpointURL,updatedRequest,expectedResponse,ignoreParams,
                                  													test_case_name,method_type,headerKey,header_tag_case,auth_value,
                                                                    RESTClassTest.HeaderTags,RESTClassTest.HeaderValues,TestTags,returnTags);
   		System.out.println("response compared");
         
        for (String tag : returnValues){
            System.out.println("Tag :"+tag);
        }
        return returnValues;
    }
    
   
   
    
}