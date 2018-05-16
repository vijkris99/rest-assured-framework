#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.tests.performance;

import java.io.ByteArrayOutputStream;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import ${package}.clients.SampleApiClient;
import ${package}.endpoints.SampleApiEndpoint;
import ${package}.helpers.GeneralTestHelper;
import ${package}.helpers.SampleApiTestHelper;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;

/**
 * Example of a JMeter sampler client to test the performance of the getPostsByUser API
 * Use this as an initial reference and delete once you are done
 */
public class TestGetPostsByUser extends AbstractJavaSamplerClient {

	@Override
    public Arguments getDefaultParameters() {
		return SampleApiTestHelper.initDefaultJMeterTestParameters();
    }
	
	@Override
	public SampleResult runTest(JavaSamplerContext context) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		SampleResult overallResult = new SampleResult();
		overallResult.sampleStart();
		
		try {
			SampleApiClient sampleApiClient = new SampleApiClient(context);
			RestAssuredConfig restAssuredConfig = GeneralTestHelper.getRestAssuredLogConfig(outputStream);
			sampleApiClient.setRestAssuredConfig(restAssuredConfig);
			
			Response response = sampleApiClient.getByUser(SampleApiEndpoint.POSTS, 1);
			
			GeneralTestHelper.updateJMeterResultSampler(overallResult, outputStream, response);
			overallResult.setSuccessful(response.statusCode() == 200 &&
									response.path("[0].userId").equals(1));
		} catch (Exception e) {
			GeneralTestHelper.handleJMeterExceptions(overallResult, e);
		}
		overallResult.sampleEnd();
		
		return overallResult;
	}
}