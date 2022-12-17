
package info.freelibrary.maven;

import static info.freelibrary.maven.MojoNames.TEMPLATE_INIT;
import static info.freelibrary.maven.TemplateInitMojo.ARTIFACT_ID;
import static info.freelibrary.maven.TemplateInitMojo.GROUP_ID;
import static info.freelibrary.maven.TemplateInitMojo.MODULE_ARTIFACT;
import static info.freelibrary.maven.TemplateInitMojo.MODULE_GROUP;
import static info.freelibrary.maven.TemplateInitMojo.MODULE_NAME;
import static info.freelibrary.maven.TemplateInitMojo.MODULE_VERSION;
import static info.freelibrary.maven.TemplateInitMojo.PROPERTIES;
import static info.freelibrary.maven.TemplateInitMojo.SKIP;
import static info.freelibrary.maven.TemplateInitMojo.VERSION;
import static info.freelibrary.maven.TemplateInitMojo.XMLNS;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.UUID;

import org.apache.maven.plugin.MojoExecutionException;
import org.junit.Test;

import com.google.common.io.Files;

import info.freelibrary.util.FileUtils;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.StringUtils;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.ParsingException;
import nu.xom.Serializer;

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
     * A module artifactId.
     */
    private static final String TEST_ARTIFACT_ID = "test-artifact";

    /**
     * A module groupId.
     */
    private static final String TEST_GROUP_ID = "info.freelibrary";

    /**
     * A module version.
     */
    private static final String TEST_VERSION = "0.0.2-SNAPSHOT";

    /**
     * A default module version.
     */
    private static final String DEFAULT_VERSION = "0.0.0-SNAPSHOT";

    /**
     * A function name.
     */
    private static final String TEST_MODULE = "test.module";

    /**
     * Tests the happy path for the TemplateInitMojo using the default version.
     *
     * @throws Exception If there is trouble while executing the tests.
     */
    @Test
    public final void testTemplateInitMojoDefaultVersion() throws Exception {
        final File templatePOM = new File(StringUtils.format(TEMPLATE_POM, UUID.randomUUID().toString()));
        final Properties props =
                getProperties(MODULE_ARTIFACT, TEST_ARTIFACT_ID, MODULE_GROUP, TEST_GROUP_ID, MODULE_NAME, TEST_MODULE);

        Files.copy(POM, templatePOM);

        // Run our test of the mojo
        lookupConfiguredMojo(templatePOM, props, TEMPLATE_INIT).execute();

        try {
            final Element root = new Builder().build(templatePOM).getRootElement();
            final Element properties = root.getFirstChildElement(PROPERTIES, XMLNS);

            assertEquals(TEST_GROUP_ID, properties.getFirstChildElement(MODULE_GROUP, XMLNS).getValue());
            assertEquals(TEST_ARTIFACT_ID, properties.getFirstChildElement(MODULE_ARTIFACT, XMLNS).getValue());
            assertEquals(DEFAULT_VERSION, properties.getFirstChildElement(MODULE_VERSION, XMLNS).getValue());
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
        final Properties props = getProperties(MODULE_ARTIFACT, TEST_ARTIFACT_ID, MODULE_GROUP, TEST_GROUP_ID,
                MODULE_VERSION, TEST_VERSION, MODULE_NAME, TEST_MODULE);

        Files.copy(POM, templatePOM);

        // Run our test of the mojo
        lookupConfiguredMojo(templatePOM, props, TEMPLATE_INIT).execute();

        try {
            final Element root = new Builder().build(templatePOM).getRootElement();
            final Element properties = root.getFirstChildElement(PROPERTIES, XMLNS);

            assertEquals(TEST_GROUP_ID, properties.getFirstChildElement(MODULE_GROUP, XMLNS).getValue());
            assertEquals(TEST_ARTIFACT_ID, properties.getFirstChildElement(MODULE_ARTIFACT, XMLNS).getValue());
            assertEquals(TEST_VERSION, properties.getFirstChildElement(MODULE_VERSION, XMLNS).getValue());
        } catch (final ParsingException | IOException details) {
            throw new MojoExecutionException(details);
        }
    }

    /**
     * Tests the happy path for the TemplateInitMojo with pre-existing module properties.
     *
     * @throws Exception If there is trouble while executing the tests.
     */
    @Test
    public final void testTemplateInitMojoPreExistingProperties() throws Exception {
        final Properties props = getProperties(MODULE_ARTIFACT, TEST_ARTIFACT_ID, MODULE_GROUP, TEST_GROUP_ID,
                MODULE_VERSION, TEST_VERSION, MODULE_NAME, TEST_MODULE);
        final File pomFile = addProperties(new File(StringUtils.format(TEMPLATE_POM, UUID.randomUUID().toString())));
        final File moduleDir = new File(pomFile.getParentFile(), TEST_MODULE);
        final Builder builder = new Builder();

        // Copy our test resources
        FileUtils.copy(new File("src/test/resources/test.module"), moduleDir);

        // Run our test of the mojo
        lookupConfiguredMojo(pomFile, props, TEMPLATE_INIT).execute();

        try {
            final Element root = builder.build(pomFile).getRootElement();
            final Element properties = root.getFirstChildElement(PROPERTIES, XMLNS);
            final Element module = builder.build(new File(moduleDir, "pom.xml")).getRootElement();

            // Check parent POM module values
            assertEquals(TEST_GROUP_ID, properties.getFirstChildElement(MODULE_GROUP, XMLNS).getValue());
            assertEquals(TEST_ARTIFACT_ID, properties.getFirstChildElement(MODULE_ARTIFACT, XMLNS).getValue());
            assertEquals(TEST_VERSION, properties.getFirstChildElement(MODULE_VERSION, XMLNS).getValue());

            // Check module POM values
            assertEquals(TEST_GROUP_ID, module.getFirstChildElement(GROUP_ID, XMLNS).getValue());
            assertEquals(TEST_ARTIFACT_ID, module.getFirstChildElement(ARTIFACT_ID, XMLNS).getValue());
            assertEquals(TEST_VERSION, module.getFirstChildElement(VERSION, XMLNS).getValue());
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
        final Properties props = getProperties(MODULE_GROUP, TEST_GROUP_ID, MODULE_NAME, TEST_MODULE);

        Files.copy(POM, templatePOM);

        try {
            lookupConfiguredMojo(templatePOM, props, TEMPLATE_INIT).execute();
            fail(LOGGER.getMessage(MessageCodes.MVN_134, MODULE_ARTIFACT));
        } catch (final MojoExecutionException details) {
            assertNotNull(details);
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
        final Properties props = getProperties(MODULE_ARTIFACT, TEST_ARTIFACT_ID, MODULE_NAME, TEST_MODULE);

        Files.copy(POM, templatePOM);

        try {
            lookupConfiguredMojo(templatePOM, props, TEMPLATE_INIT).execute();
            fail(LOGGER.getMessage(MessageCodes.MVN_134, MODULE_GROUP));
        } catch (final MojoExecutionException details) {
            assertNotNull(details);
        }
    }

    /**
     * Tests if execution is skipped when skip flag supplied.
     *
     * @throws Exception If there is trouble while executing the tests.
     */
    @Test
    public final void testTemplateInitMojoSkipParam() throws Exception {
        final File templatePOM = new File(StringUtils.format(TEMPLATE_POM, UUID.randomUUID().toString()));
        final Properties props = getProperties(SKIP, Boolean.TRUE.toString(), MODULE_NAME, TEST_MODULE);

        Files.copy(POM, templatePOM);

        lookupConfiguredMojo(templatePOM, props, TEMPLATE_INIT).execute();

        try {
            final Element root = new Builder().build(templatePOM).getRootElement();
            final Element properties = root.getFirstChildElement(PROPERTIES, XMLNS);

            assertNull(properties.getFirstChildElement(MODULE_VERSION, XMLNS));
        } catch (final ParsingException | IOException details) {
            throw new MojoExecutionException(details);
        }
    }

    /**
     * Adds module properties to the POM so we can test with them present too.
     *
     * @param aPomFile A POM file
     * @return A POM document
     * @throws Exception If there is trouble reading or writing the POM file.
     */
    private File addProperties(final File aPomFile) throws Exception {
        final Element properties;
        final Document pom;

        Files.copy(POM, aPomFile);
        pom = new Builder().build(aPomFile);

        properties = pom.getRootElement().getFirstChildElement(PROPERTIES, XMLNS);
        properties.appendChild(new Element(TemplateInitMojo.MODULE_NAME, XMLNS));
        properties.appendChild(new Element(TemplateInitMojo.MODULE_ARTIFACT, XMLNS));
        properties.appendChild(new Element(TemplateInitMojo.MODULE_GROUP, XMLNS));

        try (OutputStream outputStream = java.nio.file.Files.newOutputStream(aPomFile.toPath())) {
            final Serializer serializer = new Serializer(outputStream, StandardCharsets.UTF_8.name());

            serializer.setLineSeparator(System.lineSeparator());
            serializer.write(pom);
        }

        return aPomFile;
    }
}
