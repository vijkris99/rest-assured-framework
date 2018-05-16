package com.vj.api.tests;

import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

// Note the use of static imports for easy access to Rest Assured's given-when-then construct
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.apache.http.HttpStatus;

/**
 * Sample REST API tests automated using TestNG and Rest Assured
 * @author vj
 */
public class SampleApiTestsBasic {
	
	ObjectMapper mapper = new ObjectMapper();
	int userId = 1;
	
	@Test
	public void testGetPostsByUser() {
		// Note the use of Rest Assured's simple given-when-then structure
		given()	// Use given() for setting up a request
			.baseUri("https://jsonplaceholder.typicode.com")
			.queryParam("userId", userId)
		.when()	// Use when() to send the actual request
			.get("posts")
		.then()	// Use then() to validate the response
			.assertThat()
			.statusCode(HttpStatus.SC_OK)
			.body("[0].userId", equalTo(userId));	// Use "JSONPath" to extract specific fields from your response, in conjunction with "Hamcrest matchers" for increased readability
	}
	
	@Test
	public void testGetTodosByUser() {
		given()
			.baseUri("https://jsonplaceholder.typicode.com")
			.queryParam("userId", userId)
		.when()
			.get("todos")
		.then()
			.assertThat()
			.statusCode(HttpStatus.SC_OK)
			.body("[0].userId", equalTo(userId));
	}
	
	@Test
	public void testCreatePost() {
		// Assemble the JSON payload to create a new post
		ObjectNode postPayload = mapper.createObjectNode();
		postPayload.put("userId", 1);
		postPayload.put("title", "Happy new year!");
		postPayload.put("body", "Wishing everyone a very happy new year!");
		
		given()
			.baseUri("https://jsonplaceholder.typicode.com")
			.body(postPayload.asText())	// Send the JSON payload
		.when()
			.post("posts")
		.then()
			.assertThat()
			.statusCode(HttpStatus.SC_CREATED);
	}
}