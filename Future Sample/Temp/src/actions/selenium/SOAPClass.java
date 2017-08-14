package actions.selenium;


import actions.selenium.utils.SOAPHandler;
import org.junit.Assert;
import java.util.*;


class SOAPClass{
    public void run(HashMap<String, Object> params){
        
         	String endpointURL="";
		    String method_type="";
		    String wsdlPath="";
		    String expectedResponse="";
		    String test_case_name = params.get("Test Case Name").toString();
        	String operationName = params.get("Operation Name").toString();
         	String xmlCompareOption = params.get("XML Compare Option").toString();
        	String environment = params.get("Environment").toString();
        	String result="";
        	ArrayList <String> tags =  (ArrayList<String>)params.get("Tag Names");
    		ArrayList <String> values = (ArrayList<String>)params.get("Tag Values");
        	ArrayList <String> ignoreTags = (ArrayList<String>)params.get("Ignore Tags");
    		System.out.println("Test Case Name : "+ test_case_name);
   			Map <String,String> apiDetails = new HashMap<String,String>();
		    Map <String,String> expectedValues= new HashMap<String,String>();
        	Map <String,String> actualValues= new HashMap<String,String>();
        
        	apiDetails = new SOAPHandler().getAPIDetailsFromDB(test_case_name,environment);
        	if (environment==null || environment.isEmpty()){
                environment = "ST";
            }
        
        	if(apiDetails.size()>1){
                endpointURL = apiDetails.get("endpointURL");
                wsdlPath = apiDetails.get("wsdlPath");
                expectedResponse = apiDetails.get("expectedResponse");
            }
        	else{
                Assert.assertTrue(false);
            }
        	System.out.println("getting expected values");
        	expectedValues= new SOAPHandler().getExpectedParamsFromDB(test_case_name);
        	result = new SOAPHandler().getResponse(wsdlPath,operationName,tags,values,endpointURL,test_case_name);
        	new SOAPHandler().compareSOAPResponse(expectedValues,result,xmlCompareOption,ignoreTags,expectedResponse);
        	
        
    }
}