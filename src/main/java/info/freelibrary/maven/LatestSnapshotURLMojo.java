
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
@Mojo(name = MojoNames.SET_SNAPSHOT_URL, defaultPhase = LifecyclePhase.VALIDATE)
public class LatestSnapshotURLMojo extends AbstractMojo {

    /**
     * The Mojo's logger.
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
    @Parameter(alias = Config.SNAPSHOT_ARTIFACT, property = Config.SNAPSHOT_ARTIFACT, required = true)
    protected String myArtifact;

    /**
     * The name of the snapshot group.
     */
    @Parameter(alias = Config.SNAPSHOT_GROUP, property = Config.SNAPSHOT_GROUP, required = true)
    protected String myGroup;

    /**
     * The name of the snapshot version.
     */
    @Parameter(alias = Config.SNAPSHOT_VERSION, property = Config.SNAPSHOT_VERSION, required = true)
    protected String myVersion;

    /**
     * The base URL of the snapshot repository. The default value is:
     * https://s01.oss.sonatype.org/content/repositories/snapshots.
     */
    @Parameter(alias = Config.SNAPSHOT_REPO_URL, property = Config.SNAPSHOT_REPO_URL,
            defaultValue = "https://s01.oss.sonatype.org/content/repositories/snapshots")
    protected String myRepoURL;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        final Properties properties = myProject.getProperties();
        final MavenURL url;

        Objects.requireNonNull(myArtifact);
        Objects.requireNonNull(myVersion);
        Objects.requireNonNull(myGroup);

        url = new MavenURL(trim(myGroup).replace('.', SLASH), trim(myArtifact), trim(myVersion), trim(myRepoURL));

        properties.setProperty(Config.SNAPSHOT_URL, url.getJarURL());
        LOGGER.info(MessageCodes.MVN_014, Config.SNAPSHOT_URL, properties.getProperty(Config.SNAPSHOT_URL));
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
        final StringBuilder stringBuilder;

        if (value.charAt(0) != SLASH && value.charAt(end) != SLASH) {
            return value;
        }

        stringBuilder = new StringBuilder(value);

        if (value.charAt(0) == SLASH) {
            stringBuilder.deleteCharAt(0);
        }

        if (value.charAt(end) == SLASH) {
            stringBuilder.deleteCharAt(end);
        }

        return stringBuilder.toString();
    }

    /**
     * A URL representing a Maven repository.
     */
    private static final class MavenURL {

        /**
         * The artifact version from the Maven repo.
         */
        private final String myVersion;

        /**
         * The artifact ID from the Maven repo.
         */
        private final String myArtifactId;

        /**
         * The groupId ID from the Maven repo.
         */
        private final String myGroupId;

        /**
         * The Maven repositories base URL.
         */
        private final String myBaseURL;

        /**
         * Creates a new Maven URL.
         *
         * @param aGroupId An artifact's group ID
         * @param aArtifactId An artifact's ID
         * @param aVersion An artifact's version
         * @param aBaseURL A base Maven URL
         */
        private MavenURL(final String aGroupId, final String aArtifactId, final String aVersion,
                final String aBaseURL) {
            myGroupId = aGroupId;
            myArtifactId = aArtifactId;
            myVersion = aVersion;
            myBaseURL = aBaseURL;
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
            }

            throw new MojoFailureException(LOGGER.getMessage(MessageCodes.MVN_122, aMetadataNodes.toString()));
        }

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
         * @return A URL for the artifact's latest snapshot Jar file
         */
        private String getJarURL() throws MojoFailureException, MojoExecutionException {
            final Metadata metadata;

            try {
                final Builder parser = new Builder();
                final Document doc = parser.build(getMetadataURL());
                final Nodes nodes = doc.query("/metadata/versioning/snapshot");

                if (nodes.size() <= 0) {
                    throw new MojoFailureException(LOGGER.getMessage(MessageCodes.MVN_122, doc.toXML()));
                }

                metadata = getLatestSnapshot(nodes);
            } catch (final ParsingException details) {
                throw new MojoFailureException(LOGGER.getMessage(MessageCodes.MVN_121, details.getMessage()), details);
            } catch (final IOException details) {
                throw new MojoExecutionException(details.getMessage(), details);
            }

            return StringUtils.format(JAR_URL_PATTERN, myBaseURL, myGroupId, myArtifactId, myVersion, myArtifactId,
                    myVersion.replace("-SNAPSHOT", EMPTY), metadata.mySnapshotVersion, metadata.myBuildNumber);
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

    /**
     * The Mojo's configuration options.
     */
    final class Config {

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
         * A private constructor for a constants class.
         */
        private Config() {
            // This is intentionally left empty.
        }
    }
}
