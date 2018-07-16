package restAssured;

import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class RestApiTests {

	@Test

	public void test_200_ok() {

		Response response = RestAssured.get("https://samples.openweathermap.org/data/2.5/weather?q=London,"

				+ "uk&appid=b6907d289e10d714a6e88b30761fae22");

		int code = response.getStatusCode();

		System.out.println("Content type is: " + response.getContentType());

		System.out.println("Response time is: " + response.getTime());

		String body = response.getBody().asString();

		System.out.println("Body is: " + body);

		// Map<String, String> map = json.loads(response.text);

		System.out.println("Status code is: " + code);

		Assert.assertEquals(code, 200);

		assert (body.contains("London"));

	}

	@Test

	public void testVerifyResponseContent() {

		Response response = RestAssured.get("https://samples.openweathermap.org/data/2.5/weather?q=London,"

				+ "uk&appid=b6907d289e10d714a6e88b30761fae22");

		JSONArray JSONResponseBody = new JSONArray(response.getBody().asString());

		System.out.println(JSONResponseBody);

		Assert.assertEquals(JSONResponseBody.getJSONObject(0).getString("name"), "London");

		/*
		 * 
		 * final JSONArray itemList = (JSONArray) response;
		 * 
		 * 
		 * 
		 * System.out.println(itemList.toString());
		 * 
		 * //HashMap<String, String> map = new HashMap<String, String>();
		 * 
		 * 
		 * 
		 * //JSONObject jObject = new JSONObject(body);
		 * 
		 * //Iterator<?> keys = jObject.keys();
		 * 
		 * 
		 * 
		 * if (itemList != null) {
		 * 
		 * 
		 * 
		 * for (int i = itemList.length() - 1; i >= 0; i--) {
		 * 
		 * 
		 * 
		 * final JSONObject obj = (JSONObject) itemList.get(i);
		 * 
		 * final JSONObject content = obj.getJSONObject("co  ntent");
		 * 
		 * if (content != null) {
		 * 
		 * final String text = content.getString("feedback");
		 * 
		 * if (StringUtils.equals(text, "name")) {
		 * 
		 * System.out.println("Found matched feedback.");
		 * 
		 * //String result = content.getInt("id");
		 * 
		 * break;
		 * 
		 * }
		 * 
		 * 
		 * 
		 * }
		 * 
		 * 
		 * 
		 * }
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * }
		 * 
		 * 
		 * 
		 */

		/*
		 * try {
		 * 
		 * 
		 * 
		 * while( keys.hasNext() ){
		 * 
		 * String key = (String)keys.next();
		 * 
		 * String value = (String)jObject.getString(key);
		 * 
		 * map.put(key, value);
		 * 
		 * }
		 * 
		 * 
		 * 
		 * } catch (JSONException e) {
		 * 
		 * // TODO: handle exception
		 * 
		 * }
		 * 
		 * 
		 * 
		 * 
		 * 
		 * String city = map.get("name");
		 * 
		 * 
		 * 
		 * System.out.println(city);
		 * 
		 */

	}

}
