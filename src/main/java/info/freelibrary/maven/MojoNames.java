
package info.freelibrary.maven;

/**
 * A constants class for Mojo names.
 */
final class MojoNames {

    /**
     * The name of the Mojo that generates codes.
     */
    static final String GENERATE_CODES = "generate-codes";

    /**
     * The name of the Mojo that sets CPU and memory properties.
     */
    static final String SET_CPUMEM_PROPERTIES = "set-cpumem-properties";

    /**
     * The name of the Mojo that sets the file permissions.
     */
    static final String SET_FILE_PERMS = "set-file-perms";

    /**
     * The name of the Mojo that sets a property on the presence of a file.
     */
    static final String CHECK_FILE_SET_PROPERTY = "check-file-set-property";

    /**
     * The name of the Mojo that reads logging properties.
     */
    static final String READ_LOGGING_PROPERTIES = "read-logging-properties";

    /**
     * The name of the Mojo that sets the snapshot URL into a property.
     */
    static final String SET_SNAPSHOT_URL = "set-snapshot-url";

    /**
     * The name of the Mojo that reconfigures logging.
     */
    static final String CONFIGURE_LOGGING = "configure-logging";

    /**
     * The name of the Mojo that generates media-type mappings.
     */
    static final String GENERATE_MEDIATYPE = "generate-mediatype";

    /**
     * The name of the Mojo that sets a UUID property value.
     */
    static final String SET_UUID_PROPERTY = "set-uuid-property";

    /**
     * A private constructor for a constants class.
     */
    private MojoNames() {
        // This is intentionally left blank.
    }

}
