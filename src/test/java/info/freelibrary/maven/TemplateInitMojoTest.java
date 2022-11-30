
package info.freelibrary.maven;

import java.io.File;
import java.util.Properties;

import org.junit.Test;

import info.freelibrary.maven.TemplateInitMojo.Config;

/**
 * Tests of the TemplateInitMojo.
 */
public class TemplateInitMojoTest extends BetterAbstractMojoTestCase {

    /**
     * The POM file being used by the tests.
     */
    private static final File POM = new File("target/test-classes/template-pom.xml");

    /**
     * A function artifactId.
     */
    private static final String TEST_ARTIFACT_ID = "test-artifact";

    /**
     * A function groupId.
     */
    private static final String TEST_GROUP_ID = "info.freelibrary";

    /**
     * A function version.
     */
    private static final String TEST_VERSION = "0.0.2-SNAPSHOT";

    /**
     * Tests the happy path for the TemplateInitMojo.
     *
     * @throws Exception If there is trouble while executing the tests.
     */
    @Test
    public final void testTemplateInitMojo() throws Exception {
        final Properties props = getProperties(Config.ARTIFACT_ID, TEST_ARTIFACT_ID, Config.GROUP_ID, TEST_GROUP_ID);

        // Run our test of the mojo
        lookupConfiguredMojo(POM, props, MojoNames.TEMPLATE_INIT).execute();

        assertTrue(true);
    }

}
