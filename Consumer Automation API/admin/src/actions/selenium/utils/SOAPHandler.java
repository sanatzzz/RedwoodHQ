package actions.selenium.utils;

import actions.selenium.utils.DatabaseHandler;
import com.eviware.soapui.SoapUI;
import com.eviware.soapui.StandaloneSoapUICore;
import com.eviware.soapui.impl.wsdl.WsdlInterface;
import com.eviware.soapui.impl.wsdl.WsdlOperation;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.WsdlRequest;
import com.eviware.soapui.impl.wsdl.WsdlSubmit;
import com.eviware.soapui.impl.wsdl.WsdlSubmitContext;
import com.eviware.soapui.impl.wsdl.support.wsdl.WsdlImporter;
import com.eviware.soapui.model.iface.Response;
import java.util.*;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.StringReader;
import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Assert;


public class SOAPHandler{
    
    
    /*
    	This function will create SOAP request by fetching specified wsdl file.
        It will execute SOAP webservice and return response in String format
        
        Input Parameters : 
        wsdlPath : Filepath where wsdl is stored
        operationName : action name to be used e.g. getPanDetails
        Tags :  Array of tagnames to be altered before sending SOAP request
        values : values for altered tag names
        endPointURL : api endoint url
        test_case_name : test scenario name as stored in database
      
    */
    
    
    public String getResponse(String wsdlPath,String operationName,ArrayList <String> Tags,ArrayList <String> values,String endPointURL,String test_case_name){
        	String result="";
           	Map <String,String>  actualValues = new HashMap<String,String>();  	
         	
	try{	System.out.println("wsdlPath :"+wsdlPath);
        System.out.println("operationName :"+operationName);
        System.out.println("endPointURL :"+endPointURL);
        System.out.println("test_case_name: "+test_case_name);
		 	SoapUI.setSoapUICore(new StandaloneSoapUICore(true));
			WsdlProject project = new WsdlProject();
			WsdlInterface[] wsdls = WsdlImporter.importWsdl(project, ""); //C:/Users/sanat.shetty/Desktop/DemoRESTAssured/WalletInquiry.wsdl
			
				WsdlInterface wsdl = wsdls[0];
				String soapVersion = wsdl.getSoapVersion().toString();
			 	int c = wsdl.getOperationCount();
				String reqContent="";
				WsdlOperation op=	wsdl.getOperationByName(operationName);
				String opName = op.getName();
				reqContent = op.createRequest(true);
        		for (int i=0; i<Tags.size(); i++){
					
                    reqContent= reqContent.replace("<"+Tags.get(i)+">?</"+Tags.get(i)+">","<"+Tags.get(i)+">"+values.get(i)+"</"+Tags.get(i)+">");
                    System.out.println("Replaced "+"<"+Tags.get(i)+">?</"+Tags.get(i)+"> with <"+Tags.get(i)+">"+values.get(i)+"</"+Tags.get(i)+">" );
				}
                reqContent= reqContent.replace("?","");
        		WsdlRequest req = op.addNewRequest("Req_"+soapVersion+"_"+opName);
				req.setRequestContent(reqContent);
				req.setEndpoint(endPointURL);
        		System.out.println("url : " + endPointURL);
				WsdlSubmitContext wsdlSubmitContext = new WsdlSubmitContext(req);
				WsdlSubmit<?> submit = (WsdlSubmit<?>) req.submit(wsdlSubmitContext, false);
				Response response = submit.getResponse();
				result = response.getContentAsString();
        		new DatabaseHandler().insertDataToAPISampleResponse(test_case_name,reqContent,result,endPointURL);
				// new DatabaseHandler().insertDataToLogTable(test_case_name,reqContent,result,endPointURL);
    }
        catch(Exception e){
            System.out.println(e);
        }
        return result;
        
    }
    
    /*
    	This function will compare SOAP response as per selected option
        Option 1 : Compare Entire xml and ignore few elements
        Option 2 : Compare specifi elements from xml
        Input Parameters : 
        expectedValues : Element values which are expected from SOAP response. This will be used only for Option 2
        result : SOAP Resposne in string format
        xmlCompareOption : xml xomparison option as described above
        ignoreTags : Elements which should be ignored while comparing xml. It will only be used in case of Option 1.
        responseXmlPath : Path where sample xml is stored.It will only be used in case of Option 1.
      
    */
    
   public void compareSOAPResponse(Map <String,String> expectedValues, String result , String xmlCompareOption , 
                                    ArrayList<String>ignoreTags,String responseXmlPath){
        		Boolean comparisonResult = false;
        		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder;
				InputSource source;
        		Map <String,String> actualValues= new HashMap<String,String>();
        
				try{
					NodeList list;					
					builder = factory.newDocumentBuilder();
					source = new InputSource(new StringReader(result));
					Document document = builder.parse(source);
                   // (document.getElementsByTagName("ns0:mobileNumber")).item(0).setTextContent("123456");
					
                    switch(xmlCompareOption){
                            
                        case "Parameter Comparison" :
                            {
                             	for(String key :expectedValues.keySet()){
									list = document.getElementsByTagName(key);
									//System.out.println("node value : " + list.item(0).getTextContent());
									actualValues.put(key, list.item(0).getTextContent());
								}   
                                
                                Assert.assertEquals(expectedValues, actualValues);
                               
                              	break;  
                            }
                        case "XML Comparison" :
                            {
                            	document.normalizeDocument();
                                Document d2 = builder.parse(new File(responseXmlPath));
								d2.normalizeDocument();
                                for (int j=0 ;j < ignoreTags.size(); j++){
                                  document.getElementsByTagName(ignoreTags.get(j)).item(0).setTextContent("");
                                  d2.getElementsByTagName(ignoreTags.get(j)).item(0).setTextContent("");
                                }
								
								XMLUnit.setIgnoreWhitespace(true);
								XMLUnit.setIgnoreAttributeOrder(true);
											
								DetailedDiff diff = new DetailedDiff(XMLUnit.compareXML(document, d2));
								List<?> allDifferences = diff.getAllDifferences();
								System.out.println("Differences : "+diff.toString());
								Assert.assertTrue(diff.similar());  
                              	break;
                            }
                    }
                    
                    
					} 
            	catch(Exception e){
					e.printStackTrace();
				}
        
        	
    }
    
    public Map<String,String> getExpectedParamsFromDB(String test_case_name){
        
        return new DatabaseHandler().getExpectedParams(test_case_name);
    }
    
    public Map<String,String> getAPIDetailsFromDB(String test_case_name, String environment){
        
        return new DatabaseHandler().getAPIDetails(test_case_name,environment);
    }
    
    
     
}