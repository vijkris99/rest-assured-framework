#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "street",
    "suite",
    "city",
    "zipcode",
    "geo"
})
public class Address {

    @JsonProperty("street")
    private String street;
    @JsonProperty("suite")
    private String suite;
    @JsonProperty("city")
    private String city;
    @JsonProperty("zipcode")
    private String zipcode;
    @JsonProperty("geo")
    private Geo geo;

    @JsonProperty("street")
    public String getStreet() {
        return street;
    }

    @JsonProperty("street")
    public void setStreet(String street) {
        this.street = street;
    }

    public Address withStreet(String street) {
        this.street = street;
        return this;
    }

    @JsonProperty("suite")
    public String getSuite() {
        return suite;
    }

    @JsonProperty("suite")
    public void setSuite(String suite) {
        this.suite = suite;
    }

    public Address withSuite(String suite) {
        this.suite = suite;
        return this;
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    public Address withCity(String city) {
        this.city = city;
        return this;
    }

    @JsonProperty("zipcode")
    public String getZipcode() {
        return zipcode;
    }

    @JsonProperty("zipcode")
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Address withZipcode(String zipcode) {
        this.zipcode = zipcode;
        return this;
    }

    @JsonProperty("geo")
    public Geo getGeo() {
        return geo;
    }

    @JsonProperty("geo")
    public void setGeo(Geo geo) {
        this.geo = geo;
    }

    public Address withGeo(Geo geo) {
        this.geo = geo;
        return this;
    }

}
