package stepDefinitions;
import static io.restassured.RestAssured.given;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



import static org.junit.Assert.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;
import resources.APIResources;
import resources.TestData;
import resources.Utility;


public class StepDefinitionDemo extends Utility{
	RequestSpecification req_spec;
	Response response;
	ResponseSpecification res_spec;
	static String placeidfetched; // static because multiple scenarioes (add, get, delete) wil use same place_id
	 							//If static not used, after 1 scenario value would be set to null
		//if i put static, single copy of place id will be there for the entire run
	TestData td= new TestData();
	@Given("Add place request payload\\(body) using {string} {string} and {string}")
	public void add_place_request_payload_body_is_present(String name, String language, String address) throws IOException {
		
		
	req_spec=	given()
			.relaxedHTTPSValidation().
			spec(requestSpecification())
			.body(td.addPlace(name, language, address));
	}

	@When("user calls {string} using {string} HTTP request")
	public void user_calls_using_HTTP_request(String resource, String httpmethod) {
		//constructor of enum will be called with the value of resource which you pass
		APIResources myObj=APIResources.valueOf(resource);
		System.out.println(myObj.getResource());
		
		
	//	response=resp.when().post("maps/api/place/add/json") (This is static which is replaced by below line)
		
		res_spec= new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
	
		if(httpmethod.equalsIgnoreCase("POST")) {
			response=req_spec.when().post(myObj.getResource());
		}
		else if(httpmethod.equalsIgnoreCase("GET")) {
			response=req_spec.when().get(myObj.getResource());
		}
	}

	@Then("the API call is successful with status code {int}")
	public void the_API_call_is_successful_with_status_code(Integer int1) {
	    assertEquals(response.getStatusCode(),200);
	}

	@Then("{string} value in response body is {string}")
	public void value_in_response_body_is(String key, String val) {
		String responseInString=response.asString();
		System.out.println(responseInString);
		assertEquals(getJSONPath(response, key),val);
		
	}
	
	@Then("validate that the placeId created has same name as {string} fetched using {string}")
	public void validate_that_the_placeId_created_has_same_name_as_fetched_using(String expected_name, String resource) throws IOException {
	    // Prepare request spec
		
		
	 placeidfetched=	getJSONPath(response, "place_id");//fetching place_id from output of getPlaceAPI
	req_spec=	given()
			.relaxedHTTPSValidation().
			spec(requestSpecification()) //till this point we have baseURL and authentication key
			.queryParam("place_id", placeidfetched);
	
	user_calls_using_HTTP_request(resource,"GET");
	String actualName=getJSONPath(response, "name"); //fetching name from output of getPlaceAPI
	System.out.println("Expected name is --"+expected_name +" and actual name is -- "+actualName);
		assertEquals(actualName,expected_name);
	}

	
	
	@Given("Delete place request payload\\(body)")
	public void delete_place_request_payload_body() throws IOException {
	   req_spec= given().spec(requestSpecification()).body(td.deletePlacePayload(placeidfetched));
	}

	
	

}
