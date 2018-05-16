#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.endpoints;

/**
 * Example of an enum containing a list of endpoints within the sample API under test
 * Use this as an initial reference and delete once you are done
 */
public enum SampleApiEndpoint {
	POSTS("posts"),
	COMMENTS("comments"),
	ALBUMS("albums"),
	PHOTOS("photos"),
	TODOS("todos"),
	USERS("users");
	
	private String value;
	
	SampleApiEndpoint(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
