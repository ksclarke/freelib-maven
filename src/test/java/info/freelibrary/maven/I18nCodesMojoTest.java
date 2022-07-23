
package info.freelibrary.maven;

import java.io.File;
import java.util.Properties;

import org.junit.Test;

import info.freelibrary.util.FileUtils;

/**
 * A test of the {@link I18nCodesMojo}.
 */
public class I18nCodesMojoTest extends BetterAbstractMojoTestCase {

    /**
     * The POM file being used by the tests.
     */
    private static final File POM = new File("src/test/resources/test-pom.xml");

    /**
     * Tests running the {@link I18nCodesMojo}.
     *
     * @throws Exception If there is trouble running the test
     */
    @Test
    public void testMojoGoal() throws Exception {
        final Properties props = getProperties(I18nCodesMojo.IS_TRANSCODING_NEEDED, Boolean.toString(true));
        final File propertiesFile = new File("target/classes/freelib-maven_messages.properties");
        final File codes = new File("src/test/resources/src/main/generated/info/freelibrary/maven/MessageCodes.java");

        // Run our test of the mojo
        lookupConfiguredMojo(POM, props, "generate-codes").execute();

        // Check to see that the generated file exists
        assertTrue(propertiesFile.exists());
        assertTrue(codes.exists());

        // Test the that test artifact has been cleaned up
        assertTrue(propertiesFile.delete());
        assertTrue(FileUtils.delete(new File("src/test/resources/src")));
    }
}
