
package info.freelibrary.maven;

import static info.freelibrary.maven.MojoNames.TEMPLATE_INIT;
import static info.freelibrary.maven.TemplateInitMojo.FUNCTION_GROUP;
import static info.freelibrary.maven.TemplateInitMojo.FUNCTION_NAME;
import static info.freelibrary.maven.TemplateInitMojo.FUNCTION_VERSION;
import static info.freelibrary.maven.TemplateInitMojo.PROPERTIES;
import static info.freelibrary.maven.TemplateInitMojo.XMLNS;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

import org.apache.maven.plugin.MojoExecutionException;
import org.junit.Test;

import com.google.common.io.Files;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.StringUtils;

import nu.xom.Builder;
import nu.xom.Element;
import nu.xom.ParsingException;

/**
 * Tests of the TemplateInitMojo.
 */
public class TemplateInitMojoTest extends BetterAbstractMojoTestCase {

    /** The tests' logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateInitMojoTest.class, MessageCodes.BUNDLE);

    /**
     * The source of the POM file used by the tests.
     */
    private static final File POM = new File("src/test/resources/template-pom.xml");

    /**
     * The template POM file used by the tests.
     */
    private static final String TEMPLATE_POM = "target/test-classes/template-{}-pom.xml";

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
     * Tests the happy path for the TemplateInitMojo using the default version.
     *
     * @throws Exception If there is trouble while executing the tests.
     */
    @Test
    public final void testTemplateInitMojoDefaultVersion() throws Exception {
        final File templatePOM = new File(StringUtils.format(TEMPLATE_POM, UUID.randomUUID().toString()));
        final Properties props = getProperties(FUNCTION_NAME, TEST_ARTIFACT_ID, FUNCTION_GROUP, TEST_GROUP_ID);

        Files.copy(POM, templatePOM);

        // Run our test of the mojo
        lookupConfiguredMojo(templatePOM, props, TEMPLATE_INIT).execute();

        try {
            final Element root = new Builder().build(templatePOM).getRootElement();
            final Element properties = root.getFirstChildElement(PROPERTIES, XMLNS);

            assertEquals(TEST_GROUP_ID, properties.getFirstChildElement(FUNCTION_GROUP, XMLNS).getValue());
            assertEquals(TEST_ARTIFACT_ID, properties.getFirstChildElement(FUNCTION_NAME, XMLNS).getValue());
            assertEquals("0.0.0-SNAPSHOT", properties.getFirstChildElement(FUNCTION_VERSION, XMLNS).getValue());
        } catch (final ParsingException | IOException details) {
            throw new MojoExecutionException(details);
        }
    }

    /**
     * Tests the happy path for the TemplateInitMojo with a supplied version.
     *
     * @throws Exception If there is trouble while executing the tests.
     */
    @Test
    public final void testTemplateInitMojoVersionSet() throws Exception {
        final File templatePOM = new File(StringUtils.format(TEMPLATE_POM, UUID.randomUUID().toString()));
        final Properties props = getProperties(FUNCTION_NAME, TEST_ARTIFACT_ID, FUNCTION_GROUP, TEST_GROUP_ID,
                FUNCTION_VERSION, TEST_VERSION);

        Files.copy(POM, templatePOM);

        // Run our test of the mojo
        lookupConfiguredMojo(templatePOM, props, TEMPLATE_INIT).execute();

        try {
            final Element root = new Builder().build(templatePOM).getRootElement();
            final Element properties = root.getFirstChildElement(PROPERTIES, XMLNS);

            assertEquals(TEST_GROUP_ID, properties.getFirstChildElement(FUNCTION_GROUP, XMLNS).getValue());
            assertEquals(TEST_ARTIFACT_ID, properties.getFirstChildElement(FUNCTION_NAME, XMLNS).getValue());
            assertEquals(TEST_VERSION, properties.getFirstChildElement(FUNCTION_VERSION, XMLNS).getValue());
        } catch (final ParsingException | IOException details) {
            throw new MojoExecutionException(details);
        }
    }

    /**
     * Tests that an exception is thrown if the artifactId isn't supplied.
     *
     * @throws Exception If there is trouble while executing the tests.
     */
    @Test
    public final void testTemplateInitMojoWithoutRequiredArtifactParam() throws Exception {
        final File templatePOM = new File(StringUtils.format(TEMPLATE_POM, UUID.randomUUID().toString()));
        final Properties props = getProperties(FUNCTION_GROUP, TEST_GROUP_ID);

        Files.copy(POM, templatePOM);

        try {
            lookupConfiguredMojo(templatePOM, props, TEMPLATE_INIT).execute();
            fail(LOGGER.getMessage(MessageCodes.MVN_134, "artifactId"));
        } catch (final NullPointerException details) {
            // This is expected
        }
    }

    /**
     * Tests that an exception is thrown if the groupId isn't supplied.
     *
     * @throws Exception If there is trouble while executing the tests.
     */
    @Test
    public final void testTemplateInitMojoWithoutRequiredGroupParam() throws Exception {
        final File templatePOM = new File(StringUtils.format(TEMPLATE_POM, UUID.randomUUID().toString()));
        final Properties props = getProperties(FUNCTION_NAME, TEST_ARTIFACT_ID);

        Files.copy(POM, templatePOM);

        try {
            lookupConfiguredMojo(templatePOM, props, TEMPLATE_INIT).execute();
            fail(LOGGER.getMessage(MessageCodes.MVN_134, "groupId"));
        } catch (final NullPointerException details) {
            // This is expected
        }
    }
}
