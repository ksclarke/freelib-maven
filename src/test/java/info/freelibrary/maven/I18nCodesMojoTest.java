
package info.freelibrary.maven;

import static info.freelibrary.util.Constants.EMPTY;
import static info.freelibrary.util.Constants.SPACE;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import info.freelibrary.util.FileUtils;
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
    private static final File POM = new File("src/test/resources/test-pom.xml");

    /**
     * A pattern to match the replacement characters.
     */
    private static final Pattern PATTERN = Pattern.compile("\\{\\}");

    /**
     * A placeholder value for string replacement.
     */
    private static final String VALUE = "VALUE";

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
        final Properties testProperties = new Properties();
        final Iterator<Entry<Object, Object>> iterator;

        // Run our test of the mojo
        lookupConfiguredMojo(POM, props, "generate-codes").execute();

        // Load generated properties file
        try (InputStream inStream = Files.newInputStream(propertiesFile.toPath())) {
            testProperties.load(inStream);
        }

        // Check to see that the generated file exists
        assertTrue(propertiesFile.exists());
        assertTrue(codes.exists());

        iterator = testProperties.entrySet().iterator();

        while (iterator.hasNext()) {
            final Entry<Object, Object> entry = iterator.next();
            final String key = entry.getKey().toString();
            final String value = entry.getValue().toString();
            final Matcher matcher = PATTERN.matcher(value);
            final int argCount = (int) matcher.results().count();
            final Object[] args = new Object[argCount];
            final String message;

            // Fake the arguments to the message template
            Arrays.fill(args, VALUE);

            message = StringUtils.format(testProperties.getProperty(key), args);
            assertEquals(LOGGER.getMessage(key, args), message.replaceAll("\\R", EMPTY).replaceAll("\\s+", SPACE));
        }

        // Test the that test artifact has been cleaned up
        assertTrue(propertiesFile.delete());
        assertTrue(FileUtils.delete(new File("src/test/resources/src")));
    }
}
