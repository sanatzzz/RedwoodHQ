package actions.selenium;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

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

public class GetPanDetails {
	
	public void run(HashMap<String, String> params) throws Exception
	{

     //   String params = "dfdfd";
        Connection con,conn;
		    Statement stmt =null;
		    ResultSet rs;
		    
		    String LogFilePath ="",LogPath = "", CurrentDate = "";
			String url="",method_type="",inputObject="",expectedOutput="",wsdlPath = "",operationName="";
		    
		    Map <String,String> expectedValues1= new HashMap<String,String>();
		    
		    String headerValue="";
		    String custId="";
		  // String test_case_name = "Get_Pan_Details_BC_Portal";
 			 String test_case_name = params.get("test_case_name").toString();        
         	 System.out.println("Test Case Name : "+ test_case_name);
		    int Suffix = 0;
		   
		    
		    
		    Map <String,String> expectedValues= new HashMap<String,String>();
		    Map <String,String> actualValues= new HashMap<String,String>();
		   try{
			   Class.forName("com.mysql.jdbc.Driver");
		       con=DriverManager.getConnection("jdbc:mysql://10.50.54.58:3306/testapi","root","root");
		       conn = DriverManager.getConnection("jdbc:mysql://10.50.54.58:3306/testapi","root","root");
		       /*stmt=con.prepareStatement("SELECT * FROM `api_test_data_main` WHERE test_case_name='"+test_case_name+"'");
		       
		       rs = stmt.executeQuery();
		       while(rs.next()){
		            url = rs.getString("url");
		            method_type = rs.getString("method_type");
		            inputObject = rs.getString("request_body");
		            expectedOutput = rs.getString("response_body");
              }
              
		   */
		   	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
           	Date date = new Date();
           	CurrentDate = dateFormat.format(date);
           	CurrentDate = CurrentDate.replace("/", "");
           	CurrentDate = CurrentDate.replace(" ", "_");
           	CurrentDate = CurrentDate.replace(":", "_");
           	
           
           	LogPath = "E:/SOAP_LOG_API/";
		    	LogFilePath = LogPath+test_case_name+"_"+CurrentDate+"_"+Suffix+".csv";
		    
		    	File f = new File(LogFilePath);
		    	if (!f.exists() && !f.isDirectory()) 
		    	{
		    		// f.mkdir();
		    		f.createNewFile();
		    	}
           
		       
		       String SQL =  "SELECT * FROM `soap_api_test_data_main` WHERE test_case_name='"+test_case_name+"'";
               
               stmt = conn.createStatement();
               rs = stmt.executeQuery(SQL);
              
               String ss = "";
               
               while(rs.next())
               {
                      ss = rs.getString(1);
                      url = rs.getString(2);
                  //  inputObject = rs.getString(3);
		              expectedOutput = rs.getString(4);
		              method_type = rs.getString(5);
		              wsdlPath = rs.getString(6).toString();
                   //	  wsdlPath = params.get("wsdl_path").toString(); 
                   	System.out.println(wsdlPath);
		              operationName = rs.getString(7).toString();
                   	//	operationName = params.get("operationName").toString(); 
		            System.out.println(operationName);
                   
		              System.out.println("################################################################");
			          System.out.println(" Test Case Name : ######## " + ss + " ######## ");  
	                  System.out.println(" URL : " + url  );
	                  System.out.println(" Method_type :  " + method_type );
	                  System.out.println(" inputObject " + inputObject );
	                  System.out.println(" ExpectedOutput : " + expectedOutput);
	                  System.out.println("################################################################");
	                  
	                  
          //        expectedValues1.put("testCaseNameXYX", expectedOutput);
                  
               
               }
               rs.close();
               stmt.close();
               
           /*    
		   }
		    
		   
		    catch (Exception e) 
		    {
		        System.out.println(e);
		    }
            
		 */
	
		 	SoapUI.setSoapUICore(new StandaloneSoapUICore(true));
			WsdlProject project = new WsdlProject();
			WsdlInterface[] wsdls = WsdlImporter.importWsdl(project, wsdlPath);		//"C:/Users/Pooja1.khanna/Downloads/WalletInquiry_v1_0.wsdl"
			for(int j=0;j<wsdls.length;j++){
				WsdlInterface wsdl = wsdls[j];
				String soapVersion = wsdl.getSoapVersion().toString();
			 	int c = wsdl.getOperationCount();
				String reqContent= "";
				String result="";
                System.out.println("***********" + operationName);
				WsdlOperation op= wsdl.getOperationByName(operationName);
				String opName = op.getName();
               // String Mobile_No =  (String)params.get("Mobile_No");
               String Mobile_No =  "9953214164";
               reqContent = op.createRequest(true);
               reqContent= reqContent.replace("<wal:mobileNumber>?</wal:mobileNumber>","<wal:mobileNumber>"+Mobile_No+"</wal:mobileNumber>");
               reqContent= reqContent.replace("?","");
				WsdlRequest req = op.addNewRequest("Req_"+soapVersion+"_"+opName);
				req.setRequestContent(reqContent);
				req.setEndpoint("https://sittibewma.rjil.ril.com:10037/Services/WalletInquiry_v1_0/OperationsEndpoint");
				WsdlSubmitContext wsdlSubmitContext = new WsdlSubmitContext(req);
				WsdlSubmit<?> submit = (WsdlSubmit<?>) req.submit(wsdlSubmitContext, false);
				Response response = submit.getResponse();
				
				System.out.println(response.toString());
				
				result = response.getContentAsString();
				
				System.out.println(result);
				
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder;
				InputSource source;
				try{
					NodeList list;					
					builder = factory.newDocumentBuilder();
					source = new InputSource(new StringReader(result));
					
										
					Document document = builder.parse(source);
					(document.getElementsByTagName("ns0:custId")).item(0).getTextContent();
					
						for(String key :expectedValues.keySet())
						{
							list = document.getElementsByTagName(key);
							System.out.println("node value : " + list.item(0).getTextContent());
							actualValues.put(key, list.item(0).getTextContent());
							System.out.println("##################################");
							System.out.println(list);
							System.out.println("##################################");
						}
						
					} 
           	catch(Exception e)
				{
					e.printStackTrace();
				} 
				
				// assertXMLEquals(expectedOutput, result);
				
		//		compareSOAPResponse(expectedOutput, result,  null, result,test_case_name);
				

	//			Boolean comparisonResult = false;
	//			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	//			DocumentBuilder builder;
	//			InputSource source;
				
				
				try{
									
				builder = factory.newDocumentBuilder();
				source = new InputSource(new StringReader(result));
				
						XMLUnit.setIgnoreWhitespace(true);
						XMLUnit.setIgnoreAttributeOrder(true);
						
						
						TreeMap<Float, String> HMExpted = new TreeMap<>();
						
						Scanner scanner = new Scanner(expectedOutput);
						int i = 1;
						while (scanner.hasNextLine()) 
						{
							
						  String line1 = scanner.nextLine();
						  System.out.println(line1);
						  HMExpted.put((float) i, line1);
						
						  
					//	  System.out.println(HMExpted.keySet());
					//	  System.out.println(HMExpted.values());
						  
						  i= i+1;
						}
						scanner.close();
						
						// Actual Hash Map
						
						TreeMap<Float, String> HMActual = new TreeMap<>();
						
						Scanner scanner1 = new Scanner(result);
						int m = 1;
						while (scanner1.hasNextLine()) 
						{
							
						  String line2 = scanner1.nextLine();
						  System.out.println(line2);
						  HMActual.put((float) m, line2);
						  System.out.println(HMActual);
						  
				//  	 System.out.println(HMActual.keySet());
				//  	 System.out.println(HMActual.values());
						  
						  m= m+1;
						}
						scanner1.close();
						
						FileWriter writer = null;
						try {
						     writer = new FileWriter(LogFilePath);
						     writer.append('\n');
						     writer.append("API File Writer For the Test Case : " + test_case_name );
						     writer.append('\n'); writer.append('\n'); 
						     
						     writer.append("URL : " + url );
						     writer.append('\n');
						     
						 //  writer.append("Expected OutPut : " + expectedOutput );
						 //  writer.append('\n');
						     
						     
						     writer.append(" $$$$$$$$$$$$$$$$$$$$$$$ Match in A to B $$$$$$$$$$$$$$$$$$$$$$$$$$$$ ");
						     writer.append('\n');
						
						  for (Float k : HMExpted.keySet())
					        {
					            if (!HMActual.get(k).equals(HMExpted.get(k))) 
					            {
					                System.out.println("FAIL, Mismatch in error value ["+ k +"] : Exp :  "  + HMExpted.get(k).trim() + " Act : " + HMActual.get(k).trim() );
					             //   writer.append("FAIL, Mismatch in error value ["+ k +"] : Exp :  "  + HMExpted.get(k).trim() + " Act : " + HMActual.get(k).trim());
					                
					                writer.append("FAIL");     									writer.append(',');
					                writer.append("["+ k +"] : Mismatch in error value ");	   	writer.append(',');
					                writer.append("Exp :  "  + HMExpted.get(k).trim()); 		writer.append(',');
					                writer.append("Act : " + HMActual.get(k).trim());      		writer.append(',');
					                writer.append('\n');
					                
					            }
					            else
					            {
					            	System.out.println("PASS, Matching tags ["+ k +"] : Exp " + HMExpted.get(k).trim() + " Act : " +  HMActual.get(k).trim() );
					            	writer.append("PASS");     								writer.append(',');
					                writer.append("["+ k +"] : Matching tags ");	   		writer.append(',');
					                writer.append("Exp :  "  + HMExpted.get(k).trim()); 	writer.append(',');
					                writer.append("Act : " + HMActual.get(k).trim());   	writer.append(',');
					                writer.append('\n');
					            }
					        }
						  
					//	  System.out.println(" $$$$$$$$$$$$$$$$$$$$$$$ Match in B to A $$$$$$$$$$$$$$$$$$$$$$$$$$$$ ");
						  writer.append(" $$$$$$$$$$$$$$$$$$$$$$$ Match in B to A $$$$$$$$$$$$$$$$$$$$$$$$$$$$ ");
						  writer.append('\n');
						  
						  for (Float l : HMExpted.keySet())
					        {
							    if (!HMExpted.get(l).equals(HMActual.get(l))) 
					            {
					                System.out.println("FAIL, Mismatch in error value ["+ l +"] : Exp : "  + HMExpted.get(l).trim() + " Act : " + HMActual.get(l).trim() );
					                
					                writer.append("FAIL");     writer.append(',');
					                writer.append("["+ l +"] : Mismatch in error value ");	   	writer.append(',');
					                writer.append("Exp :  "  + HMExpted.get(l).trim()); 		writer.append(',');
					                writer.append("Act : " + HMActual.get(l).trim());      		writer.append(',');
					                writer.append('\n');
					                
					            }
					            else
					            {
					            	System.out.println("PASS, Matching tags ["+ l +"] : Exp " + HMExpted.get(l).trim() + " Act : " +  HMActual.get(l).trim() );
					            	
					            	System.out.println("PASS, Matching tags ["+ l +"] : Exp " + HMExpted.get(l).trim() + " Act : " +  HMActual.get(l).trim() );
					            	writer.append("PASS");     								writer.append(',');
					                writer.append("["+ l +"] : Matching tags ");	   		writer.append(',');
					                writer.append("Exp :  "  + HMExpted.get(l).trim()); 	writer.append(',');
					                writer.append("Act : " + HMActual.get(l).trim());      	writer.append(',');
					                writer.append('\n');
					            	
					            }
					        } 
						
					//	System.out.println(" $$$$$$$$$$$$$$$$$$$$$$$ Verification of API Completed $$$$$$$$$$$$$$$$$$$$$$$$$$$$ ");
						  
						  writer.append(" $$$$$$$$$$$$$$$$$$$$$$$ Verification of API Completed $$$$$$$$$$$$$$$$$$$$$$$$$$$$ ");
						  writer.append('\n');
						
						writer.flush();
					    writer.close();
						} 
					    catch (Exception e) 
						{
						
						}
						
						/*
				        DetailedDiff diff = new DetailedDiff(XMLUnit.compareXML(expectedOutput, result));
				        java.util.List allDifferences = diff.getAllDifferences();
				        System.out.println(allDifferences.toString());
			        	Assert.assertEquals("Differences found: "+ diff.toString(), 0, allDifferences.size());
			        	*/
				        

				    
				
				} 
				catch(Exception e)
					{
						e.printStackTrace();
					}
		
				
			} 
		}
       
		catch(Exception e)
		{
           System.out.println(e);
        }
       
		
	}
	
	 public static void compareSOAPResponse(String expectedOutput, String result , ArrayList<String>ignoreTags,String responseXmlPath,String test_case_name)
	{} // end method
	 
	 public static void assertXMLEquals(String expectedXML, String actualXML) throws Exception {
	        XMLUnit.setIgnoreWhitespace(true);
	        XMLUnit.setIgnoreAttributeOrder(true);
	        
	        try {
	        		DetailedDiff diff = new DetailedDiff(XMLUnit.compareXML(expectedXML, actualXML));

		        	java.util.List allDifferences = diff.getAllDifferences();
		        
		        	System.out.println(allDifferences.toString());
		        	
		        	Assert.assertEquals("Differences found: "+ diff.toString(), 0, allDifferences.size());

				
				} 
	        catch (Exception e) 
	        {
				System.out.println(e.getLocalizedMessage());
			}
	      }
	 


	private static String Format(BigDecimal valueOf, String string) {
		// TODO Auto-generated method stub
		return null;
	}

	 
}
