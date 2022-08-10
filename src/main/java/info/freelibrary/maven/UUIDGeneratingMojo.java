
package info.freelibrary.maven;

import java.util.Properties;
import java.util.UUID;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * A Maven mojo that can generate UUIDs as a part of the build process.
 */
@Mojo(name = "set-uuid-property", defaultPhase = LifecyclePhase.INITIALIZE)
public class UUIDGeneratingMojo extends AbstractMojo {

    /**
     * The logger used by UUIDGeneratingMojo.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UUIDGeneratingMojo.class, MessageCodes.BUNDLE);

    /**
     * The Maven project directory.
     */
    @Parameter(defaultValue = "${project}")
    protected MavenProject myProject;

    /**
     * An optional String value from which to construct the UUID.
     */
    @Parameter(alias = "string")
    protected String myString;

    /**
     * An optional build property name for the requested UUID.
     */
    @Parameter(alias = "name", defaultValue = "uuid")
    protected String myName;

    /**
     * An option to not override the property if it's already set.
     */
    @Parameter(alias = "override", defaultValue = "true")
    protected boolean myPropertyOverrides;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        final String uuid = myString == null ? UUID.randomUUID().toString() : UUID.fromString(myString).toString();
        final Properties properties = myProject.getProperties();

        LOGGER.debug(MessageCodes.MVN_013, myName, uuid);

        if (myPropertyOverrides || !properties.containsKey(myName)) {
            properties.setProperty(myName, uuid);
        }
    }

}
