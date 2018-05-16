#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import org.jsonschema2pojo.AnnotationStyle;
import org.jsonschema2pojo.Annotator;
import org.jsonschema2pojo.AnnotatorFactory;
import org.jsonschema2pojo.DefaultGenerationConfig;
import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.SchemaGenerator;
import org.jsonschema2pojo.SchemaMapper;
import org.jsonschema2pojo.SchemaStore;
import org.jsonschema2pojo.SourceType;
import org.jsonschema2pojo.rules.RuleFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.sun.codemodel.JCodeModel;

/**
 * TestNG based class that enables the auto-generation of Java data objects from a given JSON payload<br> 
 * This class leverages the "jsonschema2pojo" library internally
 */
public class JsonPayloadToPojoGenerator {
	
	private GenerationConfig generationConfig;
	private Annotator annotator;
	
	@BeforeMethod
	public void setUp() {
		generationConfig = new DefaultGenerationConfig() {
			
			@Override
			public boolean isIncludeToString() {
				return false;
			}
			
			@Override
			public boolean isIncludeHashcodeAndEquals() {
				return false;
			}
			
			@Override
			public boolean isIncludeAdditionalProperties() {
				return false;
			}
			
			@Override
			public boolean isIncludeAccessors() {
				return true;
			}
			
			@Override
			public boolean isGenerateBuilders() {
				return true;
			}
			
			@Override
			public SourceType getSourceType() {
				return SourceType.JSON;
			}
			
			@Override
			public AnnotationStyle getAnnotationStyle() {
				return AnnotationStyle.JACKSON2;
			}
		};
		
		AnnotatorFactory annotatorFactory = new AnnotatorFactory(generationConfig);
		annotator = annotatorFactory.getAnnotator(AnnotationStyle.JACKSON2);
	}
	
	@Test(dataProvider = "generationParams")
	public void generatePojoFromJsonPayload(String jsonPayloadPath, String pojoClassName,
										String pojoPackageName, String pojoDestinationDir) throws IOException {
		JCodeModel codeModel = new JCodeModel();
		URL jsonFileUrl = new File(jsonPayloadPath).toURI().toURL();
		
		RuleFactory ruleFactory = new RuleFactory(generationConfig, annotator, new SchemaStore());
		SchemaMapper mapper = new SchemaMapper(ruleFactory, new SchemaGenerator());
		mapper.generate(codeModel, pojoClassName, pojoPackageName, jsonFileUrl);
		codeModel.build(new File(pojoDestinationDir));
	}
	
	@DataProvider(name = "generationParams")
	public Object[][] generationParams() {
		return new Object[][] {
			{ "src//main//resources//payloads//UserPayload.json",
					"User", "com.vj.api.data", "src//main//java" },
			{ "src//main//resources//payloads//PostsPayload.json",
					"Posts", "com.vj.api.data", "src//main//java" }
		};
	}
}