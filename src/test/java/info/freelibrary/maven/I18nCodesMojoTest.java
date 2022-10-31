
package info.freelibrary.maven;

import static info.freelibrary.util.Constants.EMPTY;
import static info.freelibrary.util.Constants.SPACE;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.maven.plugin.Mojo;
import org.junit.Test;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.StringUtils;

/**
 * A test of the {@link I18nCodesMojo}.
 */
public class I18nCodesMojoTest extends BetterAbstractMojoTestCase {

    /**
     * A logger to test message codes against.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(I18nCodesMojo.class, MessageCodes.BUNDLE);

    /**
     * The POM file being used by the tests.
     */
    private static final File POM = new File("target/test-project/test-pom.xml");

    /**
     * A pattern to match the replacement characters.
     */
    private static final Pattern PATTERN = Pattern.compile("\\{\\}");

    /**
     * Tests running the {@link I18nCodesMojo}.
     *
     * @throws Exception If there is trouble running the test
     */
    @Test
    public void testMojoGoal() throws Exception {
        final File propertiesFile = new File("target/test-project/target/classes/freelib-maven_messages.properties");
        final File codes = new File("target/test-project/src/main/generated/info/freelibrary/maven/MessageCodes.java");
        final Properties properties = getProperties(I18nCodesMojo.Config.IS_TRANSCODING_NEEDED, Boolean.toString(true),
                I18nCodesMojo.Config.MESSAGE_FILES,
                "src/main/resources/freelib-maven_messages.xml,freelib-utils_messages.xml");

        // Run our test of the mojo
        final Mojo mojo = lookupConfiguredMojo(POM, properties, MojoNames.GENERATE_CODES);

        mojo.setLog(new MavenLogger(LOGGER));
        mojo.execute();

        // Load generated properties file
        try (InputStream inStream = Files.newInputStream(propertiesFile.toPath())) {
            final Properties testProperties = new Properties();

            testProperties.load(inStream);

            // Check to see that the generated file exists
            assertTrue(propertiesFile.exists());
            assertTrue(codes.exists());

            for (final Entry<Object, Object> entry : testProperties.entrySet()) {
                final String key = entry.getKey().toString();
                final String value = entry.getValue().toString();
                final Matcher matcher = PATTERN.matcher(value);
                final int argCount = (int) matcher.results().count();
                final Object[] args = new Object[argCount];
                final String message;

                // Fake the arguments to the message template
                Arrays.fill(args, "VALUE");

                message = StringUtils.format(testProperties.getProperty(key), args);
                assertEquals(LOGGER.getMessage(key, args), message.replaceAll("\\R", EMPTY).replaceAll("\\s+", SPACE));
            }
        } catch (final IOException details) {
            fail(details.getMessage());
        }
    }
}
