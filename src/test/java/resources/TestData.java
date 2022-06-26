package resources;

import java.util.ArrayList;
import java.util.List;

import pojo.AddPlace;
import pojo.Location;

public class TestData {

	public AddPlace addPlace(String name, String language, String address) {
		AddPlace addPlace= new AddPlace();
		addPlace.setAccuracy(50);
		addPlace.setAddress(address);
		addPlace.setLanguage(language);
		addPlace.setName(name);
		addPlace.setPhone_number("8826755511");
		addPlace.setWebsite("https://rahulshettyacademy.com");
		List myList = new ArrayList<String> ();
		myList.add("pet clinic");
		myList.add("shop");
		addPlace.setTypes(myList);
		
		Location l =new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);

		addPlace.setLocation(l);
		return addPlace;
		
	}
	
	public String deletePlacePayload(String placeId)
	{
		return "{\r\n    \"place_id\":\""+placeId+"\"\r\n}";
	}
}
