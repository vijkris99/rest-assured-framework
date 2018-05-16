#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.clients;

import static io.restassured.RestAssured.given;

import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;

import ${package}.data.Post;
import ${package}.data.User;
import ${package}.endpoints.SampleApiEndpoint;

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
 * Example of a test client for the sample API available @ https://jsonplaceholder.typicode.com
 * Use this as an initial reference and delete once you are done
 */
public class SampleApiClient {
	
	private RequestSpecification requestSpecification;
	
	/**
	 * Constructor to initialize the sample API client
	 * Customize the constructor as required, based on the API under test
	 * For example, you may include additional parameters such as port, basePath, etc. 
	 * @param baseUri The base URI of the service
	 */
	public SampleApiClient(String baseUri) {
		// Setup a request specification that can be applied to multiple requests
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
		requestSpecBuilder.setBaseUri(baseUri);
		requestSpecification = requestSpecBuilder.build();
		
		// Setup a response specification that can be applied to all responses
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
	
	/**
	 * Function to send a GET request to the specified endpoint
	 * @param endpoint The endpoint to send the request to
	 * @return The {@link Response} returned from the end-point
	 */
	public Response get(SampleApiEndpoint endpoint) {
		return given()
				.spec(requestSpecification)	// Reuse the request specification
				.log().all()	// Log all details about the request
			.when()
				.get(endpoint.getValue())	// Send a GET request to the specified endpoint
			.then()
				.log().all()	// Log all details about the response
				.extract().response();	// Extract the response and send it out to the calling test script
	}
	
	/**
	 * Function to send a GET request filtered by user to the specified endpoint
	 * @param endpoint The endpoint to send the request to
	 * @param userId The userId by which to filter the response
	 * @return The {@link Response} returned from the end-point
	 */
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
	
	/**
	 * Function to create a new post
	 * @param post The payload to be used to create the new post
	 * @return The {@link Response} returned from the end-point
	 */
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
	
	/**
	 * Function to create a new user
	 * @param post The payload to be used to create the new user
	 * @return The {@link Response} returned from the end-point
	 */
	public Response createUser(User user) {
		return given()
				.spec(requestSpecification)
				.body(user, ObjectMapperType.JACKSON_2)
				.log().all()
			.when()
				.post("users")
			.then()
				.log().all()
				.extract().response();
	}
}