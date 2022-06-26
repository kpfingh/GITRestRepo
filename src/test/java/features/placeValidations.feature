Feature: Validating add place API
@AddPlace @Regression
Scenario Outline: Validate add place API is workig fine
Given Add place request payload(body) using "<name>" "<language>" and "<address>"
When user calls "addPlaceAPI" using "POST" HTTP request
Then the API call is successful with status code 200
And "status" value in response body is "OK"
And "scope" value in response body is "APP"
And validate that the placeId created has same name as "<name>" fetched using "getPlaceAPI"

Examples:
	|name|language|address|
	|lucknowHouse|English|39/2 Alambagh|
#	|kanpurHouse|Hindi|38/2 Alambagh|

@DeletePlace @Regression
Scenario: Verify if Delete place functionlity is working fine
Given Delete place request payload(body) 
When user calls "deletePlaceAPI" using "POST" HTTP request
Then the API call is successful with status code 200
And "status" value in response body is "OK"