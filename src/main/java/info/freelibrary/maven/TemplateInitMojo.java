
package info.freelibrary.maven;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

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
     * The logger for IfFileThenPropertiesMojo.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateInitMojo.class, MessageCodes.BUNDLE);

    /**
     * The XML namespace for the Maven POM.
     */
    private static final String XMLNS = "http://maven.apache.org/POM/4.0.0";

    /**
     * A property name for a function's name.
     */
    private static final String FUNCTION_NAME = "function.name";

    /**
     * A property name for a function's group.
     */
    private static final String FUNCTION_GROUP = "function.group";

    /**
     * A property name for the function's version.
     */
    private static final String FUNCTION_VERSION = "function.version";

    /**
     * A properties element name.
     */
    private static final String PROPERTIES = "properties";

    /**
     * The Maven project directory.
     */
    @Parameter(defaultValue = "${project}")
    protected MavenProject myProject;

    /**
     * A path to a file to test for presence.
     */
    @Parameter(alias = Config.ARTIFACT_ID, required = true)
    protected String myArtifactId;

    /**
     * A path to a file to test for absence.
     */
    @Parameter(alias = Config.GROUP_ID, required = true)
    protected String myGroupId;

    /**
     * Properties to set if the file exists.
     */
    @Parameter(alias = Config.VERSION, defaultValue = "0.0.0-SNAPSHOT")
    protected String myVersion;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        final File pomFile = new File("pom.xml");
        final FileOutputStream fileOutputStream;
        final Serializer serializer;

        try {
            final Document pom = new Builder().build(pomFile);
            final Element root = pom.getRootElement();
            final Elements propertiesList = root.getChildElements(PROPERTIES, XMLNS);
            final FunctionProperties functionProperties = new FunctionProperties();
            final Element properties;

            // Create the properties element if it doesn't already exist
            if (propertiesList.size() == 0) {
                properties = new Element(PROPERTIES, XMLNS);
                root.appendChild(properties);
            } else {
                properties = propertiesList.get(0);
            }

            // Check if we're working with a multi-module project
            if (root.getChildElements("modules", XMLNS).size() != 0) {
                throw new UnsupportedOperationException("Single project templates not yet implemented");
            }

            // Update the values of function properties that already exist
            properties.getChildElements().forEach(element -> {
                final String name = element.getLocalName();

                if (FUNCTION_NAME.equals(name)) {
                    LOGGER.debug(MessageCodes.MVN_132, FUNCTION_NAME, myArtifactId);
                    element.removeChildren();
                    element.appendChild(myArtifactId);
                    functionProperties.update(FUNCTION_NAME);
                } else if (FUNCTION_VERSION.equals(name)) {
                    LOGGER.debug(MessageCodes.MVN_132, FUNCTION_VERSION, myVersion);
                    element.removeChildren();
                    element.appendChild(myVersion);
                    functionProperties.update(FUNCTION_VERSION);
                } else if (FUNCTION_GROUP.equals(name)) {
                    LOGGER.debug(MessageCodes.MVN_132, FUNCTION_GROUP, myGroupId);
                    element.removeChildren();
                    element.appendChild(myGroupId);
                    functionProperties.update(FUNCTION_GROUP);
                }
            });

            // If function's artifactId doesn't already exist, add it
            if (!functionProperties.isUpdated(FUNCTION_NAME)) {
                final Element functionName = new Element(FUNCTION_NAME, XMLNS);

                LOGGER.debug(MessageCodes.MVN_133, FUNCTION_NAME, myArtifactId);
                functionName.appendChild(myArtifactId);
                properties.appendChild(functionName);
            }

            // If function's groupId doesn't already exist, add it
            if (!functionProperties.isUpdated(FUNCTION_GROUP)) {
                final Element functionGroup = new Element(FUNCTION_GROUP, XMLNS);

                LOGGER.debug(MessageCodes.MVN_133, FUNCTION_GROUP, myGroupId);
                functionGroup.appendChild(myGroupId);
                properties.appendChild(functionGroup);
            }

            // If function's version doesn't already exist, add it
            if (!functionProperties.isUpdated(FUNCTION_VERSION)) {
                final Element functionVersion = new Element(FUNCTION_VERSION, XMLNS);

                LOGGER.debug(MessageCodes.MVN_133, FUNCTION_VERSION, myVersion);
                functionVersion.appendChild(myVersion);
                properties.appendChild(functionVersion);
            }

            fileOutputStream = new FileOutputStream(pomFile);
            serializer = new Serializer(fileOutputStream, StandardCharsets.UTF_8.name());
            serializer.setIndent(2);
            serializer.write(pom);
        } catch (ParsingException | IOException details) {
            throw new MojoExecutionException(details);
        }
    }

    /**
     * A collection of properties related to functions.
     */
    private class FunctionProperties {

        /**
         * Whether the artifactId of a function has been updated.
         */
        private boolean myArtifactIdUpdated;

        /**
         * Whether the groupId of a function has been updated.
         */
        private boolean myGroupIdUpdated;

        /**
         * Whether the version of a function has been updated.
         */
        private boolean myVersionUpdated;

        /**
         * Creates a new FunctionProperties object.
         */
        private FunctionProperties() {
            // This is intentionally left empty.
        }

        /**
         * Updates a function property.
         *
         * @param aProperty A function property
         */
        private void update(final String aProperty) {
            switch (aProperty) {
                case FUNCTION_NAME:
                    myArtifactIdUpdated = true;
                case FUNCTION_GROUP:
                    myGroupIdUpdated = true;
                case FUNCTION_VERSION:
                    myVersionUpdated = true;
                default:
                    throw new UnsupportedOperationException(aProperty);
            }
        }

        /**
         * Gets whether a function property has been updated.
         *
         * @param aProperty A function property
         * @return True if the function property has been updated; else, false
         */
        private boolean isUpdated(final String aProperty) {
            switch (aProperty) {
                case FUNCTION_NAME:
                    return myArtifactIdUpdated;
                case FUNCTION_GROUP:
                    return myGroupIdUpdated;
                case FUNCTION_VERSION:
                    return myVersionUpdated;
                default:
                    return false;
            }
        }
    }

    /**
     * The Mojo's configuration options.
     */
    class Config {

        /**
         * The artifactId of the template project.
         */
        static final String ARTIFACT_ID = "artifactId";

        /**
         * The groupId of the template project.
         */
        static final String GROUP_ID = "groupId";

        /**
         * The default version of the template project.
         */
        static final String VERSION = "version";
    }
}
