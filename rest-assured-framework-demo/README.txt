To run TestNG tests:
1. Run the usual way from Eclipse
2. Run from the command line if required
3. You can also run directly from the POM

To run JMeter tests from the UI or command line:
1. Package this project into a JAR file
2. Install the JAR file into JMeter's "lib\ext" directory
3. In addition, install the JAR files for Rest Assured and related dependencies into JMeter's "lib" directory
3a. Alternatively, add the following line to the "user.properties" file within JMeter's bin folder (modify as appropriate):
	"user.classpath=C:/Users/<username>/.m2/repository/io/rest-assured/rest-assured/3.0.0/rest-assured-3.0.0.jar;C:/Users/<username>/.m2/repository/io/rest-assured/json-path/3.0.0/json-path-3.0.0.jar;C:/Users/<username>/.m2/repository/io/rest-assured/rest-assured-common/3.0.0/rest-assured-common-3.0.0.jar;C:/Users/<username>/.m2/repository/io/rest-assured/spring-mock-mvc/3.0.0/spring-mock-mvc-3.0.0.jar;C:/Users/<username>/.m2/repository/io/rest-assured/xml-path/3.0.0/xml-path-3.0.0.jar;C:/Users/<username>/.m2/repository/org/codehaus/groovy/groovy/2.4.6;C:/Users/<username>/.m2/repository/org/codehaus/groovy/groovy-xml/2.4.6;C:/Users/<username>/.m2/repository/org/codehaus/groovy/groovy-json/2.4.6;C:/Users/<username>/.m2/repository/org/hamcrest/hamcrest-core/1.3;C:/Users/<username>/.m2/repository/org/hamcrest/hamcrest-library/1.3;C:/Users/<username>/.m2/repository/org/ccil/cowan/tagsoup/tagsoup/1.2.1;" 
4. Open the JMeter UI and create JMX files corresponding to each load test present within the JAR file 
5. Once created, the JMX files can be executed directly from the JMeter UI
5. Alternatively, you can run the JMX files from the command line

To run JMeter tests from the POM:
1. Just run "mvn verify"
2. The JMeter Maven plugin takes care of the rest :)