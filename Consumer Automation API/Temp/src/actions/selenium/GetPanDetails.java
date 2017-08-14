package actions.selenium;
import java.io.File;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
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


class GetPanDetails{
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
    		System.out.println("Test Case Name : "+ test_case_name);
   
		    Map <String,String> expectedValues= new HashMap<String,String>();
        	Map <String,String> actualValues= new HashMap<String,String>();
		   try{
			   Class.forName("com.mysql.jdbc.Driver");
		       con=DriverManager.getConnection("jdbc:mysql://localhost:3306/testapi","root","root");
		       stmt=con.prepareStatement("SELECT * FROM `api_test_data_main` WHERE test_case_name='"+test_case_name+"'");
		       rs = stmt.executeQuery();
		       while(rs.next()){
		            url = rs.getString("url");
		            method_type = rs.getString("method_type");
		            inputObject = rs.getString("request_body");
		            expectedOutput = rs.getString("response_body");
               }
		            rs.close();
		            stmt.close();
               stmt=con.prepareStatement("SELECT * FROM `api_test_data_main` WHERE test_case_name='"+test_case_name+"'");
		       rs = stmt.executeQuery();
		       while(rs.next()){
		            expectedValues.put(rs.getString("param_name"), rs.getString("value1"));
		       }
               rs.close();
		       con.close();
		       stmt.close();
		      }

		    catch (Exception e) 
		    {
		        System.out.println(e);
		    }
		
	try{
		 	SoapUI.setSoapUICore(new StandaloneSoapUICore(true));
			WsdlProject project = new WsdlProject();
			WsdlInterface[] wsdls = WsdlImporter.importWsdl(project, "C:/Users/sanat.shetty/Desktop/DemoRESTAssured/WalletInquiry.wsdl");
			for(int j=0;j<wsdls.length;j++){
				WsdlInterface wsdl = wsdls[j];
				String soapVersion = wsdl.getSoapVersion().toString();
			 	int c = wsdl.getOperationCount();
				String reqContent="";
				String result="";
				WsdlOperation op=	wsdl.getOperationByName("getPanDetails");
				String opName = op.getName();
				reqContent = op.createRequest(true);
                reqContent= reqContent.replace("<wal:mobileNumber>?</wal:mobileNumber>","<wal:mobileNumber>9820198577</wal:mobileNumber>");
				reqContent= reqContent.replace("?","");
				WsdlRequest req = op.addNewRequest("Req_"+soapVersion+"_"+opName);
				req.setRequestContent(reqContent);
				req.setEndpoint("https://sittibewma.rjil.ril.com:10037/Services/WalletInquiry_v1_0/OperationsEndpoint");
				WsdlSubmitContext wsdlSubmitContext = new WsdlSubmitContext(req);
				WsdlSubmit<?> submit = (WsdlSubmit<?>) req.submit(wsdlSubmitContext, false);
				Response response = submit.getResponse();
				result = response.getContentAsString();
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder;
				InputSource source;
				try{
					NodeList list;					
					builder = factory.newDocumentBuilder();
					source = new InputSource(new StringReader(result));
					Document document = builder.parse(source);
                    (document.getElementsByTagName("ns0:mobileNumber")).item(0).setTextContent("123456");
					for(String key :expectedValues.keySet()){
						list = document.getElementsByTagName(key);
						System.out.println("node value : " + list.item(0).getTextContent());
						actualValues.put(key, list.item(0).getTextContent());
					}
					} 
            	catch(Exception e){
					e.printStackTrace();
				}
            Assert.assertEquals(expectedValues, actualValues);
            
         }
    }
        catch(Exception e){
            System.out.println(e);
        }
        
    }
}