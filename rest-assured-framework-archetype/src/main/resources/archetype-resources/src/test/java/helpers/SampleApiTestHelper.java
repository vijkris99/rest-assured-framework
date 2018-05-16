#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.helpers;

import org.apache.jmeter.config.Arguments;

/**
 * Example of a helper class for the sample API test scripts
 * Use this as an initial reference and delete once you are done
 */
public class SampleApiTestHelper {
	/**
	 * Function to initialize the default input parameters for a JMeter based test of the sample API
	 * @return The {@link Arguments} object containing the default set of input parameters
	 */
	public static Arguments initDefaultJMeterTestParameters() {
        Arguments defaultParameters = new Arguments();
        defaultParameters.addArgument("baseURI", "https://jsonplaceholder.typicode.com");
        return defaultParameters;
    }
}