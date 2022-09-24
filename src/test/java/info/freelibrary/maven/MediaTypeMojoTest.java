
package info.freelibrary.maven;

import java.io.File;
import java.util.Properties;

import org.junit.Test;

import info.freelibrary.maven.MediaTypeMojo.Config;

/**
 * A test of the {@link MimeTypeMojo}.
 */
public class MediaTypeMojoTest extends BetterAbstractMojoTestCase {

    /**
     * The POM file being used by the tests.
     */
    private static final File POM = new File("src/test/resources/test-pom.xml");

    /**
     * The generated sources directory path used in the tests.
     */
    private static final String TEST_GEN_SRC = new File("src/test/generated").getAbsolutePath();

    /**
     * The package name used in the tests.
     */
    private static final String TEST_PACKAGE = "info.freelibrary.maven";

    /**
     * Tests running the {@link MediaTypeMojo}.
     *
     * @throws Exception If there is trouble running the test
     */
    @Test
    public void testMojoGoal() throws Exception {
        final Properties props = getProperties(Config.PACKAGE, TEST_PACKAGE, Config.GEN_SRC, TEST_GEN_SRC);
        final File mediaTypeFile = new File("src/test/generated/info/freelibrary/maven/MediaType.java");

        // Run our test of the mojo
        lookupConfiguredMojo(POM, props, "generate-mediatype").execute();

        // Check to see that the generated file exists
        assertTrue(mediaTypeFile.exists());

        // Test the that test artifact has been cleaned up
        assertTrue(mediaTypeFile.delete());
    }
}
