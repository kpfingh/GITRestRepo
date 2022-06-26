package resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utility {
	static RequestSpecification req;
	public RequestSpecification requestSpecification() throws IOException {
		if(req==null) { // This if clause is used to append the request and response in log-file
						//If this is not done, evrery time this file will be created and only
				//the latest request and response will be logged(mentioned in examples section of feature file) 
						//will be there and it will override all previous requests-response
						
		PrintStream mystream = new PrintStream(new FileOutputStream("./logging.txt"));
		req= new RequestSpecBuilder()
				.setBaseUri(getGlobalValue("baseURI"))
				.addQueryParam("key", "qaclick123")
				.addFilter(RequestLoggingFilter.logRequestTo(mystream)) //to log request to a file (FILE or CONSOLE)
				.addFilter(ResponseLoggingFilter.logResponseTo(mystream))//to log response to a file
				.setContentType(ContentType.JSON)
				.build();
		return req;
		}
		else {
			return req;
		}
	}
	
	public static String getGlobalValue(String key) throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream("src/test/java/resources/global.properties");
		prop.load(fis);
		return prop.getProperty(key);
		
	}
	
	
	public String getJSONPath(Response response, String key) {
		
		String response_in_str=response.asString();
		JsonPath js = new JsonPath(response_in_str);
		return js.get(key).toString();
		
		
	}
	
}
