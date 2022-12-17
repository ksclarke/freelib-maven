
package info.freelibrary.maven;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.StringUtils;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;
import nu.xom.Serializer;

/**
 * A Maven mojo that initializes a project template.
 */
@Mojo(name = MojoNames.TEMPLATE_INIT, defaultPhase = LifecyclePhase.INITIALIZE)
public class TemplateInitMojo extends AbstractMojo {

    /**
     * The XML namespace for the Maven POM.
     */
    static final String XMLNS = "http://maven.apache.org/POM/4.0.0";

    /**
     * The POM file's artifact ID.
     */
    static final String ARTIFACT_ID = "artifactId";

    /**
     * The POM file's group ID.
     */
    static final String GROUP_ID = "groupId";

    /**
     * The POM file's version.
     */
    static final String VERSION = "version";

    /**
     * A property name for a function's name.
     */
    static final String MODULE_ARTIFACT = "module.artifact";

    /**
     * A property name for a function's group.
     */
    static final String MODULE_GROUP = "module.group";

    /**
     * A property name for the function's version.
     */
    static final String MODULE_VERSION = "module.version";

    /**
     * A property name for a function's module.
     */
    static final String MODULE_NAME = "module.name";

    /**
     * A properties element name.
     */
    static final String PROPERTIES = "properties";

    /**
     * A property to indicate if the plugin should be skipped.
     */
    static final String SKIP = "skip";

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
     * The name of an optional module's artifactId.
     */
    @Parameter(alias = MODULE_ARTIFACT, property = MODULE_ARTIFACT)
    protected String myModuleArtifact;

    /**
     * The name of an optional module's groupId.
     */
    @Parameter(alias = MODULE_GROUP, property = MODULE_GROUP)
    protected String myModuleGroup;

    /**
     * The name of an optional module's version.
     */
    @Parameter(alias = MODULE_VERSION, property = MODULE_VERSION, defaultValue = "0.0.0-SNAPSHOT")
    protected String myModuleVersion;

    /**
     * The name of an optional module.
     */
    @Parameter(alias = MODULE_NAME, property = MODULE_NAME)
    protected String myModuleName;

    /**
     * The plugin's skip flag.
     */
    @Parameter(alias = SKIP, property = SKIP, defaultValue = "false")
    protected boolean myExecutionShouldBeSkipped;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (!myExecutionShouldBeSkipped) {
            try {
                final File pomFile = myProject.getFile();
                final Document pom = new Builder().build(pomFile);
                final Element root = pom.getRootElement();
                final Element properties = getProperties(root);
                final Elements modules = root.getChildElements("modules", XMLNS);

                // Check if we're working with a stand-alone (i.e., not a multi-module) project
                if (modules.size() == 0) {
                    throw new UnsupportedOperationException("Simple project templates not yet implemented");
                }

                checkModuleParameters();
                checkModuleConsistency(modules);

                // Else, we have a multi-module project; in our case, used for functions
                updateProperty(properties, MODULE_ARTIFACT, myModuleArtifact);
                updateProperty(properties, MODULE_GROUP, myModuleGroup);
                updateProperty(properties, MODULE_VERSION, myModuleVersion);

                // Write project POM to disk
                writePOM(pom, pomFile);

                // Update the child POM too
                updateChildPOM();
            } catch (ParsingException | IOException details) {
                throw new MojoExecutionException(details);
            }
        }
    }

    /**
     * Write the supplied POM file to disk.
     *
     * @param aPOM A POM document
     * @param aFile A POM file
     * @throws IOException If there is trouble writing the file to disk
     */
    private void writePOM(final Document aPOM, final File aFile) throws IOException {
        try (OutputStream outputStream = Files.newOutputStream(aFile.toPath())) {
            final Serializer serializer = new Serializer(outputStream, StandardCharsets.UTF_8.name());

            serializer.setLineSeparator(System.lineSeparator());
            serializer.write(aPOM);
        }
    }

    /**
     * Updates the child project (module), if necessary.
     */
    private void updateChildPOM() throws ParsingException, IOException {
        final File pomFile = Path.of(myProject.getBasedir().toString(), myModuleName, "pom.xml").toFile();
        final Document pom = new Builder().build(pomFile);
        final Element root = pom.getRootElement();
        final Element artifactId = root.getFirstChildElement(ARTIFACT_ID, XMLNS);
        final Element groupId = root.getFirstChildElement(GROUP_ID, XMLNS);
        final Element version = root.getFirstChildElement(VERSION, XMLNS);

        if (artifactId != null) {
            artifactId.removeChildren();
            artifactId.appendChild(myModuleArtifact);
        } else {
            root.appendChild(createElement(ARTIFACT_ID, myModuleArtifact));
        }

        if (groupId != null) {
            groupId.removeChildren();
            groupId.appendChild(myModuleGroup);
        } else {
            root.appendChild(createElement(GROUP_ID, myModuleGroup));
        }

        if (version != null) {
            version.removeChildren();
            version.appendChild(myModuleVersion);
        } else {
            root.appendChild(createElement(VERSION, myModuleVersion));
        }

        writePOM(pom, pomFile);
    }

    /**
     * Checks the parameters expected for a module template.
     *
     * @throws MojoExecutionException If required parameters are missing or invalid
     */
    private void checkModuleParameters() throws MojoExecutionException {
        if (StringUtils.trimToNull(myModuleName) == null) {
            throw new MojoExecutionException(LOGGER.getMessage(MessageCodes.MVN_134, MODULE_NAME));
        }

        if (StringUtils.trimToNull(myModuleGroup) == null) {
            throw new MojoExecutionException(LOGGER.getMessage(MessageCodes.MVN_134, MODULE_GROUP));
        }

        if (StringUtils.trimToNull(myModuleArtifact) == null) {
            throw new MojoExecutionException(LOGGER.getMessage(MessageCodes.MVN_134, MODULE_ARTIFACT));
        }
    }

    /**
     * Checks the module name for consistency with the defined modules.
     *
     * @param aModuleList A list of modules defined in the POM
     * @throws MojoExecutionException If the expected module cannot be found
     */
    private void checkModuleConsistency(final Elements aModuleList) throws MojoExecutionException {
        final Elements modules = aModuleList.get(0).getChildElements("module", XMLNS);
        boolean hasModule = false;

        for (int index = 0; index < modules.size(); index++) {
            final Element module = modules.get(index);

            if (module != null && myModuleName.equals(module.getValue())) {
                hasModule = true;
            }
        }

        if (!hasModule) {
            throw new MojoExecutionException(LOGGER.getMessage(MessageCodes.MVN_137, myModuleName));
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
        if (StringUtils.trimToNull(aValue) != null) {
            Element property;

            if ((property = aPropertyList.getFirstChildElement(aName, XMLNS)) != null) {
                property.removeChildren();
                LOGGER.debug(MessageCodes.MVN_132, aName, aValue);
                property.appendChild(aValue);
            } else {
                property = createElement(aName, aValue);
                LOGGER.debug(MessageCodes.MVN_133, aName, aValue);
                aPropertyList.appendChild(property);
            }
        }
    }

    /**
     * Create an XML element in our namespace using the supplied name and value.
     *
     * @param aName An XML element name
     * @param aValue An element value
     * @return A newly created XML element
     */
    private Element createElement(final String aName, final String aValue) {
        final Element element = new Element(aName, XMLNS);

        element.appendChild(aValue);
        return element;
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
