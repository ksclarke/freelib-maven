
package info.freelibrary.maven;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;
import nu.xom.Serializer;

/**
 * A Maven mojo that initializes a project template.
 */
@Mojo(name = MojoNames.TEMPLATE_INIT)
public class TemplateInitMojo extends AbstractMojo {

    /**
     * The XML namespace for the Maven POM.
     */
    static final String XMLNS = "http://maven.apache.org/POM/4.0.0";

    /**
     * A property name for a function's name.
     */
    static final String FUNCTION_NAME = "function.name";

    /**
     * A property name for a function's group.
     */
    static final String FUNCTION_GROUP = "function.group";

    /**
     * A property name for the function's version.
     */
    static final String FUNCTION_VERSION = "function.version";

    /**
     * A properties element name.
     */
    static final String PROPERTIES = "properties";

    /**
     * The logger for IfFileThenPropertiesMojo.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateInitMojo.class, MessageCodes.BUNDLE);

    /**
     * The Maven project directory.
     */
    @Parameter(defaultValue = "${project}")
    protected MavenProject myProject;

    /**
     * A path to a file to test for presence.
     */
    @Parameter(alias = FUNCTION_NAME, property = FUNCTION_NAME, required = true)
    protected String myArtifactId;

    /**
     * A path to a file to test for absence.
     */
    @Parameter(alias = FUNCTION_GROUP, property = FUNCTION_GROUP, required = true)
    protected String myGroupId;

    /**
     * Properties to set if the file exists.
     */
    @Parameter(alias = FUNCTION_VERSION, property = FUNCTION_VERSION, defaultValue = "0.0.0-SNAPSHOT")
    protected String myVersion;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        Objects.requireNonNull(myArtifactId, LOGGER.getMessage(MessageCodes.MVN_134, "artifactId"));
        Objects.requireNonNull(myGroupId, LOGGER.getMessage(MessageCodes.MVN_134, "groupId"));

        try {
            final File pomFile = myProject.getFile();
            final Document pom = new Builder().build(pomFile);
            final Element root = pom.getRootElement();
            final Element properties = getProperties(root);

            // Check if we're working with a multi-module project
            if (root.getChildElements("modules", XMLNS).size() != 0) {
                throw new UnsupportedOperationException("Single project templates not yet implemented");
            }

            // Else, we have a multi-module project; in our case, used for functions
            updateProperty(properties, FUNCTION_NAME, myArtifactId);
            updateProperty(properties, FUNCTION_GROUP, myGroupId);
            updateProperty(properties, FUNCTION_VERSION, myVersion);

            try (OutputStream outputStream = Files.newOutputStream(pomFile.toPath())) {
                final Serializer serializer = new Serializer(outputStream, StandardCharsets.UTF_8.name());

                serializer.setLineSeparator(System.lineSeparator());
                serializer.write(pom);
            }
        } catch (ParsingException | IOException details) {
            throw new MojoExecutionException(details);
        }
    }

    /**
     * Updates a property in the POM file.
     *
     * @param aPropertyList A list of properties
     * @param aName A property name
     * @param aValue A property value
     */
    private void updateProperty(final Element aPropertyList, final String aName, final String aValue) {
        final Element property = aPropertyList.getFirstChildElement(aName, XMLNS);

        if (property != null) {
            property.removeChildren();
            property.appendChild(aValue);
            LOGGER.debug(MessageCodes.MVN_132, aName, aValue);
        } else {
            final Element functionProperty = new Element(aName, XMLNS);

            functionProperty.appendChild(aValue);
            aPropertyList.appendChild(functionProperty);
            LOGGER.debug(MessageCodes.MVN_133, aName, aValue);
        }
    }

    /**
     * Gets the POM's properties.
     *
     * @param aRoot The root element of the POM
     * @return The properties element
     */
    private Element getProperties(final Element aRoot) {
        final Elements propertiesList = aRoot.getChildElements(PROPERTIES, XMLNS);
        final Element properties;

        if (propertiesList.size() == 0) {
            properties = new Element(PROPERTIES, XMLNS);
            aRoot.appendChild(properties);
        } else {
            properties = propertiesList.get(0);
        }

        return properties;
    }
}
