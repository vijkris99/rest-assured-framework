#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.tests.performance;

import java.io.ByteArrayOutputStream;

import org.apache.http.HttpStatus;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;

import ${package}.clients.SampleApiClient;
import ${package}.data.Address;
import ${package}.data.Post;
import ${package}.data.User;
import ${package}.helpers.GeneralTestHelper;
import ${package}.helpers.SampleApiTestHelper;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;

/**
 * Example of a JMeter sampler client to test the performance of the createUser & createPost API's
 * Use this as an initial reference and delete once you are done
 */
public class TestCreateUserThenCreatePost extends AbstractJavaSamplerClient {
	
	@Override
    public Arguments getDefaultParameters() {
		return SampleApiTestHelper.initDefaultJMeterTestParameters();
    }
	
	@Override
	public SampleResult runTest(JavaSamplerContext context) {
		SampleResult overallResult = new SampleResult();
		overallResult.sampleStart();
		
		SampleApiClient sampleApiClient = new SampleApiClient(context);
		
		overallResult.addSubResult(createUser(sampleApiClient));
		overallResult.addSubResult(createPost(sampleApiClient));
		
		//overallResult.setSuccessful(true);	// Will be automatically set based on subResults
		//overallResult.sampleEnd();	// Will throw error for ending sample twice
		
		return overallResult;
	}
	
	private SampleResult createUser(SampleApiClient sampleApiClient) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		SampleResult subResult = new SampleResult();
		subResult.setSampleLabel("Create User");
		subResult.sampleStart();
		
		try {
			RestAssuredConfig restAssuredConfig = GeneralTestHelper.getRestAssuredLogConfig(outputStream);
			sampleApiClient.setRestAssuredConfig(restAssuredConfig);
			
			Address address = new Address().withStreet("May street").withCity("Woodbridge").withZipcode("12345");
			User user = new User().withName("Vijay").withAddress(address);
			
			Response response = sampleApiClient.createUser(user);
			JMeterVariables vars  = JMeterContextService.getContext().getVariables();
			vars.put("userId", response.path("id").toString());
			
			GeneralTestHelper.updateJMeterResultSampler(subResult, outputStream, response);
			subResult.setSuccessful(response.statusCode() == HttpStatus.SC_CREATED);
		} catch (Exception e) {
			GeneralTestHelper.handleJMeterExceptions(subResult, e);
		}
		subResult.sampleEnd();
		
		return subResult;
	}
	
	private SampleResult createPost(SampleApiClient sampleApiClient) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		SampleResult subResult = new SampleResult();
		subResult.setSampleLabel("Create User");
		subResult.sampleStart();
		
		try {
			RestAssuredConfig restAssuredConfig = GeneralTestHelper.getRestAssuredLogConfig(outputStream);
			sampleApiClient.setRestAssuredConfig(restAssuredConfig);
			
			JMeterVariables vars  = JMeterContextService.getContext().getVariables();
			int userId = Integer.parseInt(vars.get("userId"));
			Post post = new Post().withUserId(userId).withTitle("Hey").withBody("How are you?");
			Response response = sampleApiClient.createPost(post);
			
			GeneralTestHelper.updateJMeterResultSampler(subResult, outputStream, response);
			subResult.setSuccessful(response.statusCode() == HttpStatus.SC_CREATED);
		} catch (Exception e) {
			GeneralTestHelper.handleJMeterExceptions(subResult, e);
		}
		subResult.sampleEnd();
		
		return subResult;
	}
}