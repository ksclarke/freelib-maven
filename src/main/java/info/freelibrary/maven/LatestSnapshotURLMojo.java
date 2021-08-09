
package info.freelibrary.maven;

import static info.freelibrary.util.Constants.EMPTY;

import java.io.IOException;
import java.util.Objects;
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
import info.freelibrary.util.StringUtils;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Nodes;
import nu.xom.ParsingException;

/**
 * Sets the URL of a Maven artifact's latest snapshot version to a build property.
 */
@Mojo(name = "set-snapshot-url", defaultPhase = LifecyclePhase.VALIDATE)
public class LatestSnapshotURLMojo extends AbstractMojo {

    /**
     * A constant for the snapshot URL build property.
     */
    static final String SNAPSHOT_URL = "snapshot.url";

    /**
     * A constant for the snapshot artifact.
     */
    static final String SNAPSHOT_ARTIFACT = "snapshot.artifact";

    /**
     * A constant for the snapshot group.
     */
    static final String SNAPSHOT_GROUP = "snapshot.group";

    /**
     * A constant for the snapshot version.
     */
    static final String SNAPSHOT_VERSION = "snapshot.version";

    /**
     * A constant for the snapshot repository URL.
     */
    static final String SNAPSHOT_REPO_URL = "snapshot.repo.url";

    /**
     * The CPUandMemoryMojo logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LatestSnapshotURLMojo.class, MessageCodes.BUNDLE);

    /**
     * A pattern for the repo URL (i.e. {repo}/{group}/{artifact}/{version}). The "repo" part of the pattern should
     * include the HTTP(S) protocol (e.g. https://s01.oss.sonatype.org/content/repositories/snapshots).
     */
    private static final String REPO_URL_PATTERN = "{}/{}/{}/{}";

    /**
     * A pattern for the URL to be returned as a system property.
     */
    private static final String JAR_URL_PATTERN = REPO_URL_PATTERN + "/{}-{}-{}-{}.jar";

    /**
     * A constant for the metadata file the Mojo tries to retrieve.
     */
    private static final String METADATA = "/maven-metadata.xml";

    /**
     * A standard URL component path separator.
     */
    private static final char SLASH = '/';

    /**
     * The Maven project directory.
     */
    @Parameter(defaultValue = "${project}")
    protected MavenProject myProject;

    /**
     * The name of the snapshot artifact.
     */
    @Parameter(alias = SNAPSHOT_ARTIFACT, property = SNAPSHOT_ARTIFACT, required = true)
    private String myArtifact;

    /**
     * The name of the snapshot group.
     */
    @Parameter(alias = SNAPSHOT_GROUP, property = SNAPSHOT_GROUP, required = true)
    private String myGroup;

    /**
     * The name of the snapshot version.
     */
    @Parameter(alias = SNAPSHOT_VERSION, property = SNAPSHOT_VERSION, required = true)
    private String myVersion;

    /**
     * The base URL of the snapshot repository. The default value is:
     * https://s01.oss.sonatype.org/content/repositories/snapshots.
     */
    @Parameter(alias = SNAPSHOT_REPO_URL, property = SNAPSHOT_REPO_URL,
            defaultValue = "https://s01.oss.sonatype.org/content/repositories/snapshots")
    private String myRepoURL;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        final Properties properties = new Properties();
        final MavenURL url = new MavenURL();

        Objects.requireNonNull(myArtifact);
        Objects.requireNonNull(myVersion);
        Objects.requireNonNull(myGroup);

        url.myGroupId = trim(myGroup).replace('.', SLASH);
        url.myArtifactId = trim(myArtifact);
        url.myVersion = trim(myVersion);
        url.myBaseURL = trim(myRepoURL);

        properties.setProperty(SNAPSHOT_URL, url.getJarURL(getMetadata(url)));
        LOGGER.info(MessageCodes.MVN_014, SNAPSHOT_URL, properties.getProperty(SNAPSHOT_URL));
    }

    /**
     * Trims any beginning or ending slashes from a string.
     *
     * @param aValue A string value
     * @return A string value without beginning or ending slashes
     */
    private String trim(final String aValue) {
        final String value = aValue.trim();
        final int end = value.length() - 1;

        if (value.charAt(0) == SLASH || value.charAt(end) == SLASH) {
            final StringBuilder stringBuilder = new StringBuilder(value);

            if (value.charAt(0) == SLASH) {
                stringBuilder.deleteCharAt(0);
            }

            if (value.charAt(end) == SLASH) {
                stringBuilder.deleteCharAt(end);
            }

            return stringBuilder.toString();
        } else {
            return value;
        }
    }

    /**
     * Gets latest version metadata from the supplied Maven URL.
     *
     * @param aURL A URL for an artifact in a Maven repository
     * @return The artifact's latest version metadata
     * @throws MojoExecutionException If the Maven website cannot be reached
     * @throws MojoFailureException If the mojo fails to execute
     */
    private Metadata getMetadata(final MavenURL aURL) throws MojoExecutionException, MojoFailureException {
        try {
            final Builder parser = new Builder();
            final Document doc = parser.build(aURL.getMetadataURL());
            final Nodes nodes = doc.query("/metadata/versioning/snapshot");

            if (nodes.size() > 0) {
                return getLatestSnapshot(nodes);
            } else {
                throw new MojoFailureException(LOGGER.getMessage(MessageCodes.MVN_122, doc.toXML()));
            }
        } catch (final ParsingException details) {
            throw new MojoFailureException(LOGGER.getMessage(MessageCodes.MVN_121, details.getMessage()), details);
        } catch (final IOException details) {
            throw new MojoExecutionException(details.getMessage(), details);
        }
    }

    /**
     * Extracts the snapshot version and build number metadata from the supplied node array.
     *
     * @param aMetadataNodes An array of nodes containing the metadata we want
     * @return The extracted metadata
     * @throws MojoFailureException If the supplied nodes do not contain the expected metadata
     */
    private Metadata getLatestSnapshot(final Nodes aMetadataNodes) throws MojoFailureException {
        final Nodes buildNumberNodes = aMetadataNodes.get(0).query("buildNumber");
        final Nodes versionNodes = aMetadataNodes.get(0).query("timestamp");

        if (buildNumberNodes.size() > 0 && versionNodes.size() > 0) {
            final String snapshotVersion = versionNodes.get(0).getValue();
            final String buildNumber = buildNumberNodes.get(0).getValue();

            return new Metadata().setBuildNumber(buildNumber).setSnapshotVersion(snapshotVersion);
        } else {
            throw new MojoFailureException(LOGGER.getMessage(MessageCodes.MVN_122, aMetadataNodes.toString()));
        }
    }

    /**
     * A URL representing a Maven repository.
     */
    private static final class MavenURL {

        /**
         * The artifact ID from the Maven repo.
         */
        private String myArtifactId;

        /**
         * The groupId ID from the Maven repo.
         */
        private String myGroupId;

        /**
         * The artifact version from the Maven repo.
         */
        private String myVersion;

        /**
         * The Maven repositories base URL.
         */
        private String myBaseURL;

        /**
         * Returns a URL for the artifact's metadata file.
         *
         * @return A URL for the artifact's metadata file
         */
        private String getMetadataURL() {
            return StringUtils.format(REPO_URL_PATTERN + METADATA, myBaseURL, myGroupId, myArtifactId, myVersion);
        }

        /**
         * Returns a URL for the artifact's latest snapshot Jar file.
         *
         * @param aMetadata Metadata about the latest snapshot
         * @return A URL for the artifact's latest snapshot Jar file
         */
        private String getJarURL(final Metadata aMetadata) {
            return StringUtils.format(JAR_URL_PATTERN, myBaseURL, myGroupId, myArtifactId, myVersion, myArtifactId,
                    myVersion.replace("-SNAPSHOT", EMPTY), aMetadata.mySnapshotVersion, aMetadata.myBuildNumber);
        }
    }

    /**
     * Metadata about the latest snapshot version of a Maven artifact.
     */
    private static final class Metadata {

        /**
         * The latest snapshot version.
         */
        private String mySnapshotVersion;

        /**
         * The latest build number.
         */
        private String myBuildNumber;

        /**
         * Sets the latest snapshot version.
         *
         * @param aVersion A latest snapshot version
         * @return This metadata
         */
        private Metadata setSnapshotVersion(final String aVersion) {
            mySnapshotVersion = aVersion;
            return this;
        }

        /**
         * Sets the latest build number.
         *
         * @param aBuildNumber The latest build number
         * @return This metadata
         */
        private Metadata setBuildNumber(final String aBuildNumber) {
            myBuildNumber = aBuildNumber;
            return this;
        }
    }
}
