package stepDefinitions;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {
	StepDefinitionDemo obj= new StepDefinitionDemo();

	@Before("@DeletePlace")
	public void beforeScenario() throws IOException {
		//Execute only if place id is null this section will give you place id
		//since variable is static calling it using class name
		if(StepDefinitionDemo.placeidfetched==null) {
		obj.add_place_request_payload_body_is_present("A","B","C");
		obj.user_calls_using_HTTP_request("addPlaceAPI", "POST");
		obj.validate_that_the_placeId_created_has_same_name_as_fetched_using("A", "getPlaceAPI");
	}
	}
	
	
}
