package com.vj.api.helpers;

import org.apache.jmeter.config.Arguments;

/**
 * Helper class for the sample API test scripts
 * @author vj
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