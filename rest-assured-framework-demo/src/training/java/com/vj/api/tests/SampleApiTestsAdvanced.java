package com.vj.api.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;
import org.apache.http.HttpStatus;

/**
 * Sample REST API tests automated using TestNG and Rest Assured
 * @author vj
 */
public class SampleApiTestsAdvanced {
	
	ObjectMapper mapper;
	RequestSpecification requestSpecification;	// A "specification" lets you share common code across multiple requests or responses
	
	@BeforeClass
	public void setup() {
		mapper = new ObjectMapper();
		
		// Use the RequestSpecBuilder to "build" a specification
		// This illustrates the use of the so called "builder" pattern
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
		requestSpecBuilder.setBaseUri("https://jsonplaceholder.typicode.com");
		requestSpecBuilder.addQueryParam("userId", 1);
		requestSpecification = requestSpecBuilder.build();
	}
	
	@Test
	public void getPostsByUser_WithValidUser_ShouldReturnSuccess() {
		Response response = given()
								.spec(requestSpecification)	// Reuse an available "specification"
								.log().all()	// Log request traffic
							.when()
								.get("posts")
							.then()
								.log().all()	// Log response traffic
								.extract().response();
		
		// Implement fluent assertions using AssertJ in conjunction with the extracted response
		assertThat(response.statusCode()).as("Validate response code").isEqualTo(HttpStatus.SC_OK);
		assertThat(response.path("[0].userId").toString()).as("Validate userId in response").isEqualTo("1");
	}
	
	@Test
	public void getTodosByUser_WithValidUser_ShouldReturnSuccess() {
		Response response = given()
								.spec(requestSpecification)	// Reuse an available "specification"
								.log().all()
							.when()
								.get("todos")
							.then()
								.log().all()
								.extract().response();
		
		assertThat(response.statusCode()).as("Validate response code").isEqualTo(HttpStatus.SC_OK);
		assertThat(response.path("[0].userId").toString()).as("Validate userId in response").isEqualTo("1");
	}
	
	@Test(dataProvider="posts")
	public void createPost_ValidPost_ShouldReturnSuccess(int userId, String title, String body) {
		ObjectNode postPayload = mapper.createObjectNode();
		postPayload.put("userId", userId);
		postPayload.put("title", title);
		postPayload.put("body", body);
		
		Response response = given()
								.baseUri("https://jsonplaceholder.typicode.com")
								.body(postPayload.asText())
								.log().all()
							.when()
								.post("posts")
							.then()
								.log().all()
								.extract().response();
		
		assertThat(response.statusCode()).as("Validate response code").isEqualTo(HttpStatus.SC_CREATED);
	}
	
	@DataProvider(name="posts")
	public Object[][] posts() {
		return new Object[][] {
			{ 1, "Happy new year!", "Wishing everyone a very happy new year!" },
			{ 2, "Merry Christmas!", "Wishing everyone a merry Christmas!" }
		};
	}
}