package com.vj.api.endpoints;

/**
 * Enum containing a list of endpoints within the sample API under test
 * @author vj
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
