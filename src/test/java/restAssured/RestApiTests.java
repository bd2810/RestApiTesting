package restAssured;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class RestApiTests {
	
	/*
	 * This before class can be setup ahead so that we don't need to specify the 
	 * entire URL in the tests each time as displayed in 'testVerifyGetMethod' method below.
	 */
	
	@BeforeClass
	public static void setup() {
		String port = System.getProperty("server.port");
		if (port == null) {
			RestAssured.port = Integer.valueOf(443);
		} else {
			RestAssured.port = Integer.valueOf(port);
		}

		String basePath = System.getProperty("server.base");
		if (basePath == null) {
			basePath = "/data/2.5";
		}
		RestAssured.basePath = basePath;

		String baseHost = System.getProperty("server.host");
		if (baseHost == null) {
			baseHost = "https://samples.openweathermap.org";
		}
		RestAssured.baseURI = baseHost;

	}	

	/*
	 * Test to verify Get method verifying status code and name in the body
	 */
	
	@Test
	public void testVerifyGetMethod() {

		Response response = get("/weather?q=London,"

				+ "uk&appid=b6907d289e10d714a6e88b30761fae22");

		int code = response.getStatusCode();

		System.out.println("Content type is: " + response.getContentType());

		System.out.println("Response time is: " + response.getTime());

		String body = response.getBody().asString();

		System.out.println("Body is: " + body);

		System.out.println("Status code is: " + code);

		Assert.assertEquals(code, 200);

		assert (body.contains("London"));

	}
	
	/*
	 * Test to verify status code 200 OK when Get to correct URL is requested
	 */
	
	@Test
	public void testStatusCode200() {
		
		given().when().get("https://www.google.com/").then().statusCode(200);
		
	}
	
	@Test
	public void test2StatusCode200() {
		
		Response string = given().when().get("http://localhost:3000/posts");
		
		System.out.println(string.asString());
		
	}
	
	/*
	 * Test to verify status code 404 Not Found when Get to incorrect URL is requested
	 */
	
	@Test
	public void testStatusCode404() {
		
		given().when().get("https://www.google.com/bhavik").then().statusCode(404);
		
	}
	
	/*
	 * Test to verify the value of field 'name' equals to 'London' in the body of response
	 */
	
	@Test
	public void testVerifyNameInResponseContent() {
		
		given().when().get("https://samples.openweathermap.org/data/2.5/weather?q=London,"

				+ "uk&appid=b6907d289e10d714a6e88b30761fae22").then().body("name", equalTo("London"));		
		
	}
	
	/*
	 * Test to verify the value of field 'description' under 'weather' equals to 'light intensity drizzle' in the body of response
	 */
	
	@Test
	public void testVerifyWeatherDescInResponseContent() {
		
		given().when().get("https://samples.openweathermap.org/data/2.5/weather?q=London,"

				+ "uk&appid=b6907d289e10d714a6e88b30761fae22").then().body("weather[0].description", equalTo("light intensity drizzle"));		
		
	}
	
	/*
	 * Test to verify multiple values in the body of response in a single test:
	 * 1. field 'description' under 'weather' equals to 'light intensity drizzle'
	 * 2. Status code is 200
	 * 3. field 'name' equals to 'London'
	 */
	
	@Test
	public void testVerifyMultipleValues() {
		
		given().when().get("https://samples.openweathermap.org/data/2.5/weather?q=London,"

				+ "uk&appid=b6907d289e10d714a6e88b30761fae22").then()
		.body("weather[0].description", equalTo("light intensity drizzle"))
		.body("name", equalTo("London"))
		.statusCode(200);	
		
	}
	
	/*
	 * Test to verify the multiple values of field 'name' under 'list'in the body of response using hasItems
	 * names: Yafran, Zuwarah, Ragusa, etc.
	 */
	
	@Test
	public void testVerifyMultipleFieldValues() {
		
		given().when().get("/box/city?bbox=12,32,15,37,10&appid=b6907d289e10d714a6e88b30761fae22").then()
			.body("list.name", hasItems("Yafran","Zuwarah","Ragusa"));		
		
	}
	
	/*
	 * Test to verify Post method creates a post and returns status code 201 for created.
	 */
	@Test
	public void testPostMethod() {
		
		JSONObject objects = new JSONObject();
		objects.put("id", 2);
		objects.put("title", "first-post");
		objects.put("author", "Mark");
		
		given().contentType("application/json").body(objects.toString()).when().post("http://localhost:3000/posts").then().statusCode(201);	
		
	}
	
	/*
	 * Test to verify Post method creates a post and returns status code 201 for created.
	 */
	@Test
	public void testVerifyValuesfromAbovePostMethod() {
		
		given().when().get("http://localhost:3000/posts/2").then()
		.body("id", equalTo(2))
		.body("title", equalTo("first-post"))
		.body("author", equalTo("Mark"));
		
	}


}
