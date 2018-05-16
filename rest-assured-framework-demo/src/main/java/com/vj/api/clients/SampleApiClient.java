package com.vj.api.clients;

import static io.restassured.RestAssured.given;

import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;

import com.vj.api.data.Post;
import com.vj.api.data.User;
import com.vj.api.endpoints.SampleApiEndpoint;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.RestAssuredConfig;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

/**
 * Test client for the sample API available @ https://jsonplaceholder.typicode.com
 * @author vj
 */
public class SampleApiClient {
	
	private RequestSpecification requestSpecification;
	
	/**
	 * Constructor to initialize the sample API client
	 * @param baseUri The base URI of the service
	 */
	public SampleApiClient(String baseUri) {
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
		requestSpecBuilder.setBaseUri(baseUri);
		requestSpecification = requestSpecBuilder.build();
		
		ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
		responseSpecBuilder.setDefaultParser(Parser.JSON);
		ResponseSpecification responseSpecification = responseSpecBuilder.build();
		//this.responseSpecification = responseSpecification;	// Does not work, for some reason -> bug with Rest Assured!
		RestAssured.responseSpecification = responseSpecification;	// Setting the responseSpec globally as an alternative...
	}
	
	/**
	 * Constructor to initialize the test client from a JMeter test
	 * @param context The {@link JavaSamplerContext} object
	 */
	public SampleApiClient(JavaSamplerContext context) {
		this(context.getParameter("baseURI"));
	}
	
	/**
	 * Function to set the {@link RestAssuredConfig} for the test client
	 * @param restAssuredConfig The {@link RestAssuredConfig} object
	 */
	public void setRestAssuredConfig(RestAssuredConfig restAssuredConfig) {
		this.requestSpecification.config(restAssuredConfig);
	}
	
	
	public Response get(SampleApiEndpoint endpoint) {
		return given()
				.spec(requestSpecification)
				.log().all()
			.when()
				.get(endpoint.getValue())
			.then()
				.log().all()
				.extract().response();
	}
	
	public Response getByUser(SampleApiEndpoint endpoint, int userId) {
		return given()
				.spec(requestSpecification)
				.queryParam("userId", userId)
				.log().all()
			.when()
				.get(endpoint.getValue())
			.then()
				.log().all()
				.extract().response();
	}
	
	public Response createPost(Post post) {
		return given()
				.spec(requestSpecification)
				.body(post, ObjectMapperType.JACKSON_2)	// Serialization using Jackson
				.log().all()
			.when()
				.post("posts")
			.then()
				.log().all()
				.extract().response();
	}
	
	public Response createUser(User user) {
		return given()
				.spec(requestSpecification)
				.body(user, ObjectMapperType.JACKSON_2)	// Serialization using Jackson
				.log().all()
			.when()
				.post("users")
			.then()
				.log().all()
				.extract().response();
	}
}