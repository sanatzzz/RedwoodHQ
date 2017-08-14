package com.test;

import org.junit.Test;
import static io.restassured.RestAssured.*;

public class JunitTest {

	
	
 @Test
     public void makeSureThatGoogleIsUp() {
         given().when().get("http://www.google.com").then().statusCode(200);
     }

    
    
}
