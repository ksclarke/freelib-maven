
package info.freelibrary.maven;

import static info.freelibrary.util.Constants.BUNDLE_NAME;

import java.io.File;
import java.util.Iterator;
import java.util.Properties;

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
 * A Maven mojo that can insert properties into the build as a result of checking the existence of a file.
 */
@Mojo(name = "check-file-set-property", defaultPhase = LifecyclePhase.INITIALIZE)
public class IfFileThenPropertiesMojo extends AbstractMojo {

    private static final Logger LOGGER = LoggerFactory.getLogger(UUIDGeneratingMojo.class, BUNDLE_NAME);

    /**
     * The Maven project directory.
     */
    @Parameter(defaultValue = "${project}")
    protected MavenProject myProject;

    /**
     * A path to a file to test for presence.
     */
    @Parameter(alias = "exists")
    private File myExistsFile;

    /**
     * A path to a file to test for absence.
     */
    @Parameter(alias = "missing")
    private File myMissingFile;

    /**
     * Properties to set if the file exists.
     */
    @Parameter(alias = "properties")
    private Properties myProperties;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (myExistsFile != null && myMissingFile != null) {
            if (myExistsFile.exists() && !myMissingFile.exists()) {
                addProperties();
            }
        } else if (myExistsFile != null && myExistsFile.exists()) {
            addProperties();
        } else if (myMissingFile != null && !myMissingFile.exists()) {
            addProperties();
        }
    }

    private void addProperties() {
        final Properties properties = myProject.getProperties();

        if (myProperties != null) {
            final Iterator keyIterator = myProperties.keySet().iterator();

            while (keyIterator.hasNext()) {
                final String key = keyIterator.next().toString();
                final String value = myProperties.getProperty(key);

                LOGGER.debug(MessageCodes.MVN_014, key, value);

                properties.put(key, value);
            }
        } else {
            LOGGER.warn(MessageCodes.MVN_015, myExistsFile);
        }
    }
}
