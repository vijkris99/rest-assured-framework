#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.helpers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

import org.apache.jmeter.samplers.SampleResult;

import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.Header;
import io.restassured.response.Response;

/**
 * General helper class for test scripts
 */
public class GeneralTestHelper {
	
	/**
	 * Function to get the configuration settings loaded from the external properties file
	 * @return The loaded {@link Properties} object
	 * @throws IOException In case of errors
	 */
	public static Properties getConfigSettings() throws IOException {
		Properties properties = new Properties();
		File configFile = new File("src//test//resources//config.default.properties");
		properties.load(new FileInputStream(configFile));
		return properties;
	}
	
	/**
	 * Function to get the {@link RestAssuredConfig} configured to redirect all Rest Assured logs into the specified file
	 * @param logFileName The name of the log file to be created (all log files will be stored under "target/rest-assured-logs")
	 * @return The {@link RestAssuredConfig} object
	 */
	public static RestAssuredConfig getRestAssuredLogConfig(String logFileName) {
		String logFilePath = System.getProperty("user.dir").concat("${symbol_escape}${symbol_escape}target${symbol_escape}${symbol_escape}rest-assured-logs${symbol_escape}${symbol_escape}");
		new File(logFilePath).mkdirs();
		logFilePath = logFilePath.concat(logFileName).concat(".log");
		
		PrintStream logFileStream;
		try {
			logFileStream = new PrintStream(new File(logFilePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		
		LogConfig logConfig = new LogConfig(logFileStream, true);
		return new RestAssuredConfig().logConfig(logConfig);
	}
	
	/**
	 * Function to get the {@link RestAssuredConfig} configured to redirect all Rest Assured logs into the specified output stream
	 * @param outputStream The {@link ByteArrayOutputStream} to be used for logging
	 * @return The {@link RestAssuredConfig} object
	 */
	public static RestAssuredConfig getRestAssuredLogConfig(ByteArrayOutputStream outputStream) {
		PrintStream printStream = new PrintStream(outputStream);
		LogConfig logConfig = new LogConfig(printStream, true);
		
		return new RestAssuredConfig().logConfig(logConfig);
	}
	
	/**
	 * Function to update the results of the JMeter sampler as specified
	 * @param result The {@link SampleResult} object passed in from the JMeter test
	 * @param outputStream The {@link ByteArrayOutputStream} containing the logs to be written to the JMeter results
	 * @param response The {@link Response} returned by the web service end-point called within the JMeter test
	 */
	public static void updateJMeterResultSampler(SampleResult result,
										ByteArrayOutputStream outputStream,
										Response response) {
		// Update request details
		result.setRequestHeaders(outputStream.toString());
		
		// Update response details
		result.setResponseCode(String.valueOf(response.getStatusCode()));
		result.setResponseMessage(response.getBody().prettyPrint());
		
		//sampleResult.setResponseHeaders(response.getHeaders().asList().toString());
		String responseHeaders = "";
		for(Header header:response.getHeaders()) {
			responseHeaders += header.getName() + ":" + header.getValue() + "${symbol_escape}r${symbol_escape}n";
		}
		result.setResponseHeaders(responseHeaders);
		result.setContentType(response.contentType());
		result.setDataType(SampleResult.TEXT);
	}
	
	/**
	 * Function to handle any exceptions that may have occurred during a JMeter test run<br>
	 * This function should typically be called from the catch section of a try-catch block
	 * @param result The {@link SampleResult} object to be updated
	 * @param e The {@link Exception} object
	 */
	public static void handleJMeterExceptions(SampleResult result, Exception e) {
		result.setSuccessful(false);
		result.setResponseMessage(e.getMessage());
		StringWriter stringWriter = new StringWriter();
		e.printStackTrace(new PrintWriter(stringWriter));
		result.setResponseData(stringWriter.toString(), null);
		e.printStackTrace();
	}
}