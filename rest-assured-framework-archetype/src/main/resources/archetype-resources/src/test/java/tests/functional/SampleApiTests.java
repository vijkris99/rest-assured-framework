#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.tests.functional;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

import org.apache.http.HttpStatus;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ${package}.clients.SampleApiClient;
import ${package}.data.Address;
import ${package}.data.Post;
import ${package}.data.User;
import ${package}.endpoints.SampleApiEndpoint;
import ${package}.helpers.GeneralTestHelper;

import io.restassured.config.RestAssuredConfig;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;

/**
 * Example of a TestNG based class to test the sample API
 * Use this as an initial reference and delete once you are done
 */
public class SampleApiTests {
	
	private SampleApiClient sampleApiClient;
	
	@BeforeSuite
	public void setupSuite() throws IOException {
		// Read setup information from external properties file
		Properties properties = GeneralTestHelper.getConfigSettings();
		String baseUri = properties.getProperty("sample.api.base.uri");
		
		// Initialize the test client
		sampleApiClient = new SampleApiClient(baseUri);
	}
	
	@BeforeMethod
	public void setup(Method testMethod) {
		// Setup logging configuration for the test client
		RestAssuredConfig restAssuredConfig =
							GeneralTestHelper.getRestAssuredLogConfig(testMethod.getName());
		sampleApiClient.setRestAssuredConfig(restAssuredConfig);
	}
	
	@Test
	public void getPostsByUser_WithValidUser_ShouldReturnSuccess() {
		// Step 1: Use the test client to send request to appropriate end-point
		Response response = sampleApiClient.getByUser(SampleApiEndpoint.POSTS, 1);
		
		// Step 2a: Validate the response code returned
		// Use fluent assertions from AssertJ
		// Always provide a description for all your assertions
		assertThat(response.statusCode()).as("Validate response code").isEqualTo(HttpStatus.SC_OK);
		
		// Step 2b: Validate the response data for correctness
		// This example illustrates de-serializing the response JSON into an array of "Post" data objects
		Post[] posts = response.as(Post[].class, ObjectMapperType.JACKSON_2);
		assertThat(posts[0].getUserId()).as("Validate userId in response").isEqualTo(1);
	}
	
	@Test
	public void getTodosByUser_WithValidUser_ShouldReturnSuccess() {
		// Step 1: Use the test client to send request to appropriate end-point
		Response response = sampleApiClient.getByUser(SampleApiEndpoint.TODOS, 1);
		
		// Step 2a: Validate the response code returned
		assertThat(response.statusCode()).as("Validate response code").isEqualTo(HttpStatus.SC_OK);
		
		// Step 2b: Validate the response data for correctness
		// This example illustrates the use of JsonPath to parse the response JSON directly (without de-serializing)
		assertThat(response.path("[0].userId").toString()).as("Validate userId in response").isEqualTo("1");
	}
	
	@Test(dataProvider="posts")
	public void createPost_ValidPost_ShouldReturnSuccess(int userId, String title, String body) {
		// Step 1: Build the data object (note the usage of the builder pattern)
		Post post = new Post().withUserId(userId).withTitle(title).withBody(body);
		
		// Step 2: Use the test client to send request along with data object to appropriate end-point
		Response response = sampleApiClient.createPost(post);
		
		// Step 3: Validate the response for correctness
		assertThat(response.statusCode()).as("Validate response code").isEqualTo(HttpStatus.SC_CREATED);
	}
	
	@Test
	public void createUserThenCreatePost_ValidData_ShouldReturnSuccess() {
		Address address = new Address().withStreet("May street").withCity("Woodbridge").withZipcode("12345");
		User user = new User().withName("Vijay").withAddress(address);
		
		// Use "soft" assertions where required to include multiple validations within a test method
		SoftAssertions softly = new SoftAssertions();
		
		Response response = sampleApiClient.createUser(user);
		softly.assertThat(response.statusCode()).as("Validate response code").isEqualTo(HttpStatus.SC_CREATED);
		int userId = Integer.parseInt(response.path("id").toString());
		
		Post post = new Post().withUserId(userId).withTitle("Hey").withBody("How are you?");
		response = sampleApiClient.createPost(post);
		assertThat(response.statusCode()).as("Validate response code").isEqualTo(HttpStatus.SC_CREATED);
		
		// Remember to call the assertAll() method to ensure that all soft assertions are correctly validated
		softly.assertAll();
	}
	
	@DataProvider(name="posts")
	public Object[][] posts() {
		return new Object[][] {
			{ 1, "Happy new year!", "Wishing everyone a very happy new year!" },
			{ 2, "Merry Christmas!", "Wishing everyone a merry Christmas!" }
		};
	}
}